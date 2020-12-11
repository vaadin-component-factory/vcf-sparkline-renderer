package com.vaadin.componentfactory.sparklinerenderer;

import java.util.List;

public class Measurements {

    private String name;
    private List<Measurement> measurements;

    public Measurements(String name, List<Measurement> measurements) {
        this.name = name;
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
