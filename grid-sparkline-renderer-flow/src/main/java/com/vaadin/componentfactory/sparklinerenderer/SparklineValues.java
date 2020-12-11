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

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SparklineValues holds a list of SparklineValue:s
 */
public class SparklineValues implements Serializable {

    private List<SparklineValue> values = new ArrayList<>();

    /**
     * Construct SparklineValues
     * @param values list of SparklineValue
     */
    public SparklineValues(List<SparklineValue> values) {
        this.values = values;
    }

    /**
     * Construct SparklineValues
     */
    public SparklineValues() {
    }

    /**
     * @return list of SparklineValue
     */
    public List<SparklineValue> getValues() {
        return values;
    }

    /**
     * @param values list of SparklineValue
     */
    public void setValues(List<SparklineValue> values) {
        this.values = values;
    }

    /**
     * Remove SparklineValue:s which numeric value is null
     */
    public void filterOutNullValues() {
        values = values.stream().filter(value->value.getValue()!=null).collect(Collectors.toList());
    }

    /**
     * Single value of Sparkline, value might be null
     */
    public static class SparklineValue implements Serializable {
        private Instant instant;
        private Double value;

        /**
         * Construct a SparklineValue
         * @param instant timestamp
         * @param value numeric value
         */
        public SparklineValue(Instant instant, Double value) {
            this.instant = instant;
            this.value = value;
        }

        /**
         * @return timestamp of the value
         */
        public Instant getInstant() {
            return instant;
        }

        /**
         * @param instant timestamp of the value
         */
        public void setInstant(Instant instant) {
            this.instant = instant;
        }

        /**
         * @return numeric value which might be null
         */
        public Double getValue() {
            return value;
        }

        /**
         * @param value numeric value which might be null
         */
        public void setValue(Double value) {
            this.value = value;
        }
    }
}
