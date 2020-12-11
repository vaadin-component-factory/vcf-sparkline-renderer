package com.vaadin.componentfactory.sparklinerenderer;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    private String name;
    private List<Measurement> bodyTemp = new ArrayList<>();
    private List<Measurement> oxygenSaturation = new ArrayList<>();

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(List<Measurement> bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public List<Measurement> getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(List<Measurement> oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }
}
