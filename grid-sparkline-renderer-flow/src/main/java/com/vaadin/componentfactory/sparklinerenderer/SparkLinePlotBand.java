package com.vaadin.componentfactory.sparklinerenderer;

/*
 * #%L
 * Vaadin Grid SparklineRenderer Flow
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

import java.awt.*;
import java.io.Serializable;
import java.util.Optional;

/**
 * Horizontal plot band for spark line. Can be used e.g. to display threshold intervals with given (optional) line color and (optional) background color.
 */
public class SparkLinePlotBand implements Serializable {

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
    public SparkLinePlotBand(Double fromValue, Double toValue, Color lineColor, Color backgroundColor) {
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
    public void setLineColor(Color lineColor) {
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
    public void setBackgroundColor(Color backgroundColor) {
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
