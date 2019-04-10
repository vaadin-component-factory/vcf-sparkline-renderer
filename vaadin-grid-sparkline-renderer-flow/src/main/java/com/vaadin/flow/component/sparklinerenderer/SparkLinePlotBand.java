package com.vaadin.flow.component.sparklinerenderer;

/*
 * #%L
 * Vaadin Grid SparklineRenderer Flow
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


import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;

/**
 * Horizontal plot band for spark line. Can be used e.g. to display threshold intervals with given (optional) line color and (optional) background color.
 */
public class SparkLinePlotBand {

    private Double fromValue;
    private Double toValue;
    private Color lineColor;
    private Color backgroundColor;

    /**
     * Crate a plotband without colors
     *
     * @param fromValue lower value of the plot band interval
     * @param toValue   upper value of the plot band interval
     */
    public SparkLinePlotBand(Double fromValue, Double toValue) {
        this.fromValue = fromValue;
        this.toValue = toValue;
    }

    /**
     * Crate a plotband with given color
     *
     * @param fromValue       lower value of the plot band interval
     * @param toValue         upper value of the plot band interval
     * @param lineColor       line color when the value is inside the interval [fromValue, toValue]
     * @param backgroundColor background color between the interval [fromValue, toValue]
     */
    public SparkLinePlotBand(Double fromValue, Double toValue, @Nullable Color lineColor, @Nullable Color backgroundColor) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.lineColor = lineColor;
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return lower value of the plot band interval
     */
    public Double getFromValue() {
        return fromValue;
    }

    /**
     * @param fromValue lower value of the plot band interval
     */
    public void setFromValue(Double fromValue) {
        this.fromValue = fromValue;
    }

    /**
     * @return upper value of the plot band interval
     */
    public Double getToValue() {
        return toValue;
    }

    /**
     * @param toValue upper value of the plot band interval
     */
    public void setToValue(Double toValue) {
        this.toValue = toValue;
    }

    /**
     * @return optional of line color when the value is inside the interval [fromValue, toValue]
     */
    public Optional<Color> getOptionalLineColor() {
        return Optional.ofNullable(lineColor);
    }

    /**
     * @param lineColor line color when the value is inside the interval [fromValue, toValue]
     */
    public void setLineColor(@Nullable Color lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return optional of background color between the interval [fromValue, toValue]
     */
    public Optional<Color> getOptionalBackgroundColor() {
        return Optional.ofNullable(backgroundColor);
    }

    /**
     * @param backgroundColor background color between the interval [fromValue, toValue]
     */
    public void setBackgroundColor(@Nullable Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return SparkLinePlotBand with given lower value of the plot band interval
     */
    public SparkLinePlotBand withFromValue(Double fromValue) {
        this.fromValue = fromValue;
        return this;
    }

    /**
     * @return SparkLinePlotBand with given upper value of the plot band interval
     */
    public SparkLinePlotBand withToValue(Double toValue) {
        this.toValue = toValue;
        return this;
    }

    /**
     * @return SparkLinePlotBand with given line color
     */
    public SparkLinePlotBand withLineColor(Color lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    /**
     * @return SparkLinePlotBand with given background color
     */
    public SparkLinePlotBand withBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
}
