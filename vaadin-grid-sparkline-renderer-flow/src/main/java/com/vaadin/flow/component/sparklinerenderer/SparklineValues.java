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


import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SparklineValues implements Serializable {

    private List<SparklineValue> values = new ArrayList<>();

    public SparklineValues(List<SparklineValue> values) {
        this.values = values;
    }

    public SparklineValues() {
    }

    public List<SparklineValue> getValues() {
        return values;
    }

    public void setValues(List<SparklineValue> values) {
        this.values = values;
    }

    public static class SparklineValue implements Serializable {
        private Instant instant;
        private Double value;

        public SparklineValue(Instant instant, Double value) {
            this.instant = instant;
            this.value = value;
        }

        public Instant getInstant() {
            return instant;
        }

        public void setInstant(Instant instant) {
            this.instant = instant;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }

}
