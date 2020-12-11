package com.vaadin.componentfactory.sparklinerenderer;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlotBandExample extends VerticalLayout {

    public PlotBandExample() {
        setSizeFull();

        Grid<Patient> grid = new Grid<>();
        grid.addColumn(Patient::getName).setHeader("Patient name").setSortable(true);
        grid.setItems(createListOfPatient(200));
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValuesTemp, this::createSparklineConfTemp)).setHeader("Body temparature");
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValuesSatur, this::createSparklineConfSatur)).setHeader("Oxgygen saturation");
        add(grid);
    }

    private SparklineValues createSparklineValuesTemp(Patient patient) {
        return new SparklineValues(patient.getBodyTemp().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    private SparklineValues createSparklineValuesSatur(Patient patient) {
        return new SparklineValues(patient.getOxygenSaturation().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    private SparklineConfiguration createSparklineConfTemp(Patient patient) {
        return new SparklineConfiguration()
                .withAutoScaleYAxis(true)
                .withPlotBand(new SparkLinePlotBand(36.0, 37.5)
                        .withBackgroundColor(SparklineConfiguration.LIGHT_GREEN).withLineColor(Color.GREEN))
                .withPlotBand(new SparkLinePlotBand(37.5, 39.0)
                        .withLineColor(Color.YELLOW))
                .withPlotBand(new SparkLinePlotBand(39.0, 41.0)
                        .withLineColor(Color.RED));
    }

    private SparklineConfiguration createSparklineConfSatur(Patient patient) {
        return new SparklineConfiguration()
                .withAutoScaleYAxis(true)
                .withPlotBand(new SparkLinePlotBand(70.0, 90.0)
                        .withLineColor(Color.RED))
                .withPlotBand(new SparkLinePlotBand(90.0, 100.0)
                        .withLineColor(Color.GREEN));
    }

    private List<Patient> createListOfPatient(int numberOfPatients) {
        List<Patient> patients = new ArrayList<>(numberOfPatients);
        for (int i = 0; i < numberOfPatients; i++) {
            Patient patient = new Patient("Patient " + i);
            List<Measurement> temps = new ArrayList<>();
            List<Measurement> satur = new ArrayList<>();
            for (int j = 0; j < 24; j++) {
                temps.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 1, j, 0, 0).toInstant(ZoneOffset.UTC), 36.0 + 1.0 * Math.random() + (Math.random() < 0.5 ? 4 * Math.random() : 0.0)));
            }
            for (int j = 0; j < 24; j++) {
                satur.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 1, j, 0, 0).toInstant(ZoneOffset.UTC), 100 - 10 * Math.random() - (Math.random() < 0.5 ? Math.random() * 20 : 0)));
            }
            patient.setBodyTemp(temps);
            patient.setOxygenSaturation(satur);
            patients.add(patient);
        }
        return patients;
    }
}
