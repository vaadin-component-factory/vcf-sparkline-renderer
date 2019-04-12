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
        public SparklineValue(Instant instant, @Nullable Double value) {
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
        public void setValue(@Nullable Double value) {
            this.value = value;
        }
    }
}
