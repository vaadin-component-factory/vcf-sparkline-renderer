package com.vaadin.componentfactory.sparklinerenderer;

/*
 * #%L
 * Vaadin SparklineRenderer for Vaadin 10
 * %%
 * Copyright (C) 2019 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

/**
 * SparklineRenderer for Grid. Renders a sparkline as html object containing svg image.
 *
 * @param <ITEM> the type of the input object that can be used by the rendered
 *               sparkline
 */
public class SparklineRenderer<ITEM> extends ComponentRenderer<Component, ITEM> {

    private static final String UNIT_PX = "PX";
    private static final String SVG_ASPECT_RATIO = "none";

    private ValueProvider<ITEM, SparklineValues> valueProvider;
    private ValueProvider<ITEM, SparklineConfiguration> configurationValueProvider;

    private Double minY = 0.0;
    private Double maxY = 0.0;

    private SparklineConfiguration sparklineConfiguration;

    private boolean licenceVerified;

    /**
     * SvgComponent for displaying svg in object tag
     */
    @Tag("object")
    static class SvgComponent extends Component implements HasSize, HasStyle {
        SvgComponent() {
            getElement().setAttribute("type", "image/svg+xml");
        }
    }

    /**
     * Create a SparklineRenderer with given value provider for SparklineValues and default SparklineConfiguration.
     *
     * @param valueProvider ValueProvider for SparklineValues to be rendered
     */
    public SparklineRenderer(ValueProvider<ITEM, SparklineValues> valueProvider) {
        super();
        this.valueProvider = valueProvider;
        this.configurationValueProvider = (ValueProvider<ITEM, SparklineConfiguration>) item -> new SparklineConfiguration();
    }

    /**
     * Create a SparklineRenderer with given value providers for SparklineValues and SparklineConfiguration.
     *
     * @param valueProvider              ValueProvider for SparklineValues to be rendered
     * @param configurationValueProvider ValueProvider for SparklineConfiguration to be rendered
     */
    public SparklineRenderer(ValueProvider<ITEM, SparklineValues> valueProvider, ValueProvider<ITEM, SparklineConfiguration> configurationValueProvider) {
        super();
        this.valueProvider = valueProvider;
        this.configurationValueProvider = configurationValueProvider;
    }

    /**
     * Used to create SvgComponent
     *
     * @param item for which the sparkline is rendered
     * @return SvgComponent containing the sparkline
     */
    @Override
    public Component createComponent(ITEM item) {
        SparklineValues values = valueProvider.apply(item);
        SparklineConfiguration configuration = configurationValueProvider.apply(item);

        switch (configuration.getRenderMode()) {
            case SVG:
                SvgComponent svgComponent = new SvgComponent();
                svgComponent.setHeight(configuration.getSparklineHeightPx() + UNIT_PX);
                svgComponent.setWidth(configuration.getSparklineWidthPx() + UNIT_PX);
                svgComponent.getElement().setAttribute("data", getSVGStreamResource(String.valueOf(item.hashCode()), drawChart(values, configuration), configuration.getSparklineWidthPx(), configuration.getSparklineHeightPx()));
                return svgComponent;
            case PNG:
                Image imageComponent = new Image(getPNGStreamResource(String.valueOf(item.hashCode()), drawChart(values, configuration), configuration.getSparklineWidthPx(), configuration.getSparklineHeightPx()), "");
                imageComponent.setHeight(configuration.getSparklineHeightPx() + UNIT_PX);
                imageComponent.setWidth(configuration.getSparklineWidthPx() + UNIT_PX);
                return imageComponent;
        }
        return null;
    }

    /**
     * Draw the sparkline as a JFreeChart
     */
    private JFreeChart drawChart(SparklineValues values, SparklineConfiguration configuration) {
        TimeSeriesCollection dataSet = new TimeSeriesCollection();
        TimeSeries data = new TimeSeries("Sparkline");

        findMinandMaxValues(values);

        if (configuration.isFillGapsWhenNullValues()) {
            values.filterOutNullValues();
        }

        for (int index = 0; index < values.getValues().size(); index++) {
            SparklineValues.SparklineValue value = values.getValues().get(index);
            SparklineValues.SparklineValue nextValue = null;
            if (index < values.getValues().size() - 1) {
                nextValue = values.getValues().get(index + 1);
            }
            data.addOrUpdate(new FixedMillisecond(value.getInstant().toEpochMilli()), value.getValue());

            interpolateIfNeeded(data, value, nextValue, configuration.getPlotBands());
        }
        dataSet.addSeries(data);

        DateAxis x = new DateAxis();
        x.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
        x.setTickLabelsVisible(false);
        x.setTickMarksVisible(false);
        x.setAxisLineVisible(false);
        x.setNegativeArrowVisible(false);
        x.setPositiveArrowVisible(false);
        x.setVisible(false);

        NumberAxis y = new NumberAxis();
        y.setTickLabelsVisible(false);
        y.setTickMarksVisible(false);
        y.setAxisLineVisible(false);
        y.setNegativeArrowVisible(false);
        y.setPositiveArrowVisible(false);
        y.setVisible(false);
        if (configuration.isAutoScaleYAxis()) {
            y.setRangeWithMargins(minY, maxY);
        }

        XYPlot plot = new XYPlot();
        plot.setInsets(new RectangleInsets(-1, -1, 0, 0));
        plot.setDataset(dataSet);
        plot.setDomainAxis(x);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setOutlineStroke(new BasicStroke(0));
        //plot.setBackgroundAlpha(0);
        plot.setBackgroundPaint(SparklineConfiguration.TRANSPARENT);
        plot.setRangeAxis(y);
        StandardXYItemRenderer renderer = new StandardXYItemRenderer(
                StandardXYItemRenderer.LINES) {
            @Override
            public Paint getItemPaint(int row, int column) {
                for (SparkLinePlotBand plotBand : configuration.getPlotBands()) {
                    if (plotBand.getOptionalLineColor().isPresent()) {
                        if (data.getValue(column).doubleValue() >= plotBand.getFromValue() && data.getValue(column).doubleValue() <= plotBand.getToValue()) {
                            return plotBand.getOptionalLineColor().get();
                        }
                    }
                }
                return super.getItemPaint(row, column);
            }
        };
        plot.setRenderer(renderer);
        renderer.setSeriesStroke(0, new BasicStroke(configuration.getLineWidth()));
        renderer.setSeriesPaint(0, configuration.getLineColor());


        for (SparkLinePlotBand plotBand : configuration.getPlotBands()) {
            plotBand.getOptionalBackgroundColor().ifPresent(backgroundColor -> {
                final IntervalMarker target = new IntervalMarker(plotBand.getFromValue(), plotBand.getToValue());
                target.setPaint(backgroundColor);
                target.setOutlinePaint(SparklineConfiguration.TRANSPARENT);
                plot.addRangeMarker(target, Layer.BACKGROUND);
            });
        }

        JFreeChart chart = new JFreeChart(
                null,
                JFreeChart.DEFAULT_TITLE_FONT,
                plot, false
        );
        chart.setBackgroundPaint(SparklineConfiguration.TRANSPARENT);
        chart.setBorderVisible(false);

        return chart;
    }

    /**
     * Find min and max values of SparklineValues
     */
    private void findMinandMaxValues(SparklineValues values) {
        minY = values.getValues().stream().filter(value -> value.getValue() != null).mapToDouble(SparklineValues.SparklineValue::getValue).min().orElse(0.0);
        maxY = values.getValues().stream().filter(value -> value.getValue() != null).mapToDouble(SparklineValues.SparklineValue::getValue).max().orElse(0.0);

        if (minY.equals(maxY)) {
            minY = minY - 1.0;
            maxY = maxY + 1.0;
        }
    }

    /**
     * Use linear interpolation if needed to create extra values on the both sides of plot bands' boundaries
     * to allow changing the color of sparkline's line within the plot bands
     */
    private void interpolateIfNeeded(TimeSeries data, SparklineValues.SparklineValue value, SparklineValues.SparklineValue nextValue, List<SparkLinePlotBand> plotBands) {
        if (nextValue != null && value.getValue() != null && nextValue.getValue() != null) {
            long x1 = value.getInstant().toEpochMilli();
            long x2 = nextValue.getInstant().toEpochMilli();
            double y1 = value.getValue();
            double y2 = nextValue.getValue();

            double delta = (maxY - minY) / 100.0;

            for (SparkLinePlotBand plotBand : plotBands) {
                double from = plotBand.getFromValue();
                double to = plotBand.getToValue();

                if (from >= y1 && from < y2) {
                    addInterpolatedItem(data, x1, x2, y1, y2, from - delta);
                    addInterpolatedItem(data, x1, x2, y1, y2, from + delta);
                }
                if (to >= y1 && to < y2) {
                    addInterpolatedItem(data, x1, x2, y1, y2, to - delta);
                    addInterpolatedItem(data, x1, x2, y1, y2, to + delta);
                }
                if (from <= y1 && from > y2) {
                    addInterpolatedItem(data, x1, x2, y1, y2, from - delta);
                    addInterpolatedItem(data, x1, x2, y1, y2, from + delta);
                }
                if (to <= y1 && to > y2) {
                    addInterpolatedItem(data, x1, x2, y1, y2, to - delta);
                    addInterpolatedItem(data, x1, x2, y1, y2, to + delta);
                }
            }
        }
    }

    /**
     * Use linear interpolation to calculate timestamp for new item with value of newY and add it into the data
     */
    private void addInterpolatedItem(TimeSeries data, long x1, long x2, double y1, double y2, double newY) {
        double newX = x1 + (((newY - y1) * (x2 - x1)) / (y2 - y1));
        FixedMillisecond newTimestamp = new FixedMillisecond((long) newX);
        if (data.getValue(newTimestamp) == null) {
            data.add(newTimestamp, newY);
        }
    }

    /**
     * Create a PNG StreamResource out of JFreeChart
     * <p>
     * The implementation is copied with some modifications from the class below:
     * https://github.com/F43nd1r/vaadin-jfreechart-flow/blob/master/src/main/java/org/vaadin/addon/JFreeChartWrapper.java
     */
    private StreamResource getPNGStreamResource(String name, JFreeChart chart, int width, int height) {
        String randomName = UUID.randomUUID().toString() + ".png";
        return new StreamResource((name == null ? randomName : name + ".png"), (StreamResourceWriter) (stream, session) -> {
            session.lock();
            try {
                ChartUtils.writeChartAsPNG(stream, chart, width, height);
            } finally {
                session.unlock();
            }
        });
    }

    /**
     * Create an SVG StreamResource out of JFreeChart
     * <p>
     * The implementation is copied with some modifications from the class below:
     * https://github.com/F43nd1r/vaadin-jfreechart-flow/blob/master/src/main/java/org/vaadin/addon/JFreeChartWrapper.java
     */
    private StreamResource getSVGStreamResource(String name, JFreeChart chart, int width, int height) {
        String randomName = UUID.randomUUID().toString() + ".svg";
        return new StreamResource((name == null ? randomName : name + ".svg"), (StreamResourceWriter) (stream, session) -> {
            session.lock();
            try (Writer writer = new OutputStreamWriter(stream)) {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder;
                try {
                    docBuilder = docBuilderFactory.newDocumentBuilder();
                } catch (ParserConfigurationException e1) {
                    throw new RuntimeException(e1);
                }
                Document document = docBuilder.newDocument();
                Element svgelem = document.createElement("svg");
                document.appendChild(svgelem);
                // Create an instance of the SVG Generator
                SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

                // draw the chart in the SVG generator
                chart.draw(svgGenerator, new Rectangle(width, height));
                Element el = svgGenerator.getRoot();
                el.setAttributeNS(null, "viewBox", "0 0 " + width + " " + height + "");
                el.setAttributeNS(null, "style", "width:100%;height:100%;");
                el.setAttributeNS(null, "preserveAspectRatio", SVG_ASPECT_RATIO);
                svgGenerator.stream(el, writer, true, false);
            } finally {
                session.unlock();
            }
        });
    }
}
