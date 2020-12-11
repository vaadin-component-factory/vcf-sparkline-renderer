package com.vaadin.componentfactory.sparklinerenderer.examples;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.componentfactory.sparklinerenderer.SparkLinePlotBand;
import com.vaadin.componentfactory.sparklinerenderer.SparklineConfiguration;
import com.vaadin.componentfactory.sparklinerenderer.SparklineRenderer;
import com.vaadin.componentfactory.sparklinerenderer.SparklineValues;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "")
public class Home extends Div {

    public Home() {
        Grid<Patient> grid = new Grid<>();
        grid.setItems(new Patient());
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValues, this::createConfiguration)).setHeader("Daily listeners");
    }

    private SparklineConfiguration createConfiguration(Patient patient) {
        return new SparklineConfiguration().withPlotBand(new SparkLinePlotBand(36.0, 37.0).withBackgroundColor(Color.GREEN));
    }

    private SparklineValues createSparklineValues(Patient patient) {
        return new SparklineValues(patient.temperatureMeasurements.stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    static class Patient {
        List<Measurement> temperatureMeasurements = Arrays.asList(new Measurement(Instant.now().minusSeconds(10800), 36.8), new Measurement(Instant.now().minusSeconds(7200), 37.0), new Measurement(Instant.now().minusSeconds(3600), 37.2));
    }
}
