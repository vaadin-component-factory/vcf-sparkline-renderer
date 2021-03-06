package com.vaadin.componentfactory.sparklinerenderer;

import java.time.Instant;

public class Measurement {

    private Instant instant;
    private Double value;

    public Measurement(Instant instant, Double value) {
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
