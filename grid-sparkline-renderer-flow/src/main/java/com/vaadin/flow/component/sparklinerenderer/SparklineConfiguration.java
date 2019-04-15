package com.vaadin.flow.component.sparklinerenderer;

/*
 * #%L
 * Vaadin SparklineRenderer for Vaadin 10
 * %%
 * Copyright (C) 2019 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 * 
 * See the file license.html distributed with this software for more
 * information about licensing.
 * 
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SparklineConfiguration for setting Sparkline's width, height, line width, line color, and plot bands etc..
 */
public class SparklineConfiguration implements Serializable {

    // Set of predefined colors
    public static final Color LIGHT_GREEN = new Color(219, 240, 214);
    public static final Color GREEN = new Color(89, 180, 72);
    public static final Color RED = new Color(179, 49, 70);
    public static final Color YELLOW = new Color(254, 197, 71);
    public static final Color BLUE = new Color(0, 102, 204);
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0f);

    private Color lineColor = BLUE;
    private Float lineWidth = 0.75f;

    private int sparklineHeightPx = 25;
    private int sparklineWidthPx = 120;

    private boolean fillGapsWhenNullValues = false;
    private boolean autoScaleYAxis = false;

    private List<SparkLinePlotBand> plotBands;

    /**
     * Create a default SparklineConfiguration
     */
    public SparklineConfiguration() {
        plotBands = new ArrayList<>();
    }

    /**
     * Add a vertical plot band to Sparkline
     *
     * @param plotBand SparkLinePLotBand
     */
    public void addPlotBand(SparkLinePlotBand plotBand) {
        plotBands.add(plotBand);
    }

    /**
     * Remove plot bands
     */
    public void clearPlotBands() {
        plotBands.clear();
    }

    /**
     * @return list of plot bands
     */
    public List<SparkLinePlotBand> getPlotBands() {
        return plotBands;
    }

    /**
     * @param plotBands list of plot bands
     */
    public void setPlotBands(List<SparkLinePlotBand> plotBands) {
        this.plotBands = plotBands;
    }

    /**
     * @return are null value gaps filled
     */
    public boolean isFillGapsWhenNullValues() {
        return fillGapsWhenNullValues;
    }

    /**
     * @param fillGapsWhenNullValues when false, null values are shown as gaps, when true, gaps are filled
     */
    public void setFillGapsWhenNullValues(boolean fillGapsWhenNullValues) {
        this.fillGapsWhenNullValues = fillGapsWhenNullValues;
    }

    /**
     * @return line color
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor line color
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return line width
     */
    public Float getLineWidth() {
        return lineWidth;
    }

    /**
     * @param lineWidth line width
     */
    public void setLineWidth(Float lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * @return pixel height of spark line
     */
    public int getSparklineHeightPx() {
        return sparklineHeightPx;
    }

    /**
     * @param sparklineHeightPx pixel height of spark line
     */
    public void setSparklineHeightPx(int sparklineHeightPx) {
        this.sparklineHeightPx = sparklineHeightPx;
    }

    /**
     * @return pixel width of spark line
     */
    public int getSparklineWidthPx() {
        return sparklineWidthPx;
    }

    /**
     * @param sparklineWidthPx pixel width of spark line
     */
    public void setSparklineWidthPx(int sparklineWidthPx) {
        this.sparklineWidthPx = sparklineWidthPx;
    }

    /**
     * @return SparklineConfiguration with given line width
     */
    public SparklineConfiguration withLineWidth(Float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * @return SparklineConfiguration with given pixel height of spark line
     */
    public SparklineConfiguration withSparklineHeightPx(int sparklineHeightPx) {
        this.sparklineHeightPx = sparklineHeightPx;
        return this;
    }

    /**
     * @return SparklineConfiguration with given pixel width of spark line
     */
    public SparklineConfiguration withSparklineWidthPx(int sparklineWidthPx) {
        this.sparklineWidthPx = sparklineWidthPx;
        return this;
    }

    /**
     * @return SparklineConfiguration with given line color
     */
    public SparklineConfiguration withLineColor(Color lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    /**
     * @return SparklineConfiguration with given fillGapsWhenNullValues setting
     */
    public SparklineConfiguration withFillGapsWhenNullValues(boolean fillGapsWhenNullValues) {
        this.fillGapsWhenNullValues = fillGapsWhenNullValues;
        return this;
    }

    /**
     * @return SparklineConfiguration with given plot bands
     */
    public SparklineConfiguration withPlotBands(List<SparkLinePlotBand> plotBands) {
        this.plotBands = plotBands;
        return this;
    }

    /**
     * @return SparklineConfiguration with given plot band
     */
    public SparklineConfiguration withPlotBand(SparkLinePlotBand plotBand) {
        if (plotBands == null) {
            plotBands = new ArrayList<>();
        }
        plotBands.add(plotBand);
        return this;
    }

    /**
     * @return SparklineConfiguration with given value of y-axis auto scaling
     */
    public SparklineConfiguration withAutoScaleYAxis(boolean autoScaleYAxis) {
        this.autoScaleYAxis = autoScaleYAxis;
        return this;
    }

    /**
     * @return given value of y-axis auto scaling
     */
    public boolean isAutoScaleYAxis() {
        return autoScaleYAxis;
    }
}
