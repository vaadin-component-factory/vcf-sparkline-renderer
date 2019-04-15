package com.vaadin.flow.component.sparklinerenderer;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SparklineRendererTest {

    private Grid<Patient> systemUnderTest;

    @Before
    public void setUp() {
        systemUnderTest = new Grid<>();
        systemUnderTest.setItems(new Patient());
        systemUnderTest.addColumn(new SparklineRenderer<>(this::createSparklineValues, this::createConfiguration)).setHeader("Daily listeners");
    }

    private SparklineConfiguration createConfiguration(Patient patient) {
        return new SparklineConfiguration().withPlotBand(new SparkLinePlotBand(36.0, 37.0).withBackgroundColor(Color.GREEN));
    }

    private SparklineValues createSparklineValues(Patient patient) {
        return new SparklineValues(patient.temperatureMeasurements.stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    @Test
    public void onAttach_init() {
        systemUnderTest.addAttachListener(e -> new AttachEvent(systemUnderTest, true));

        Assert.assertTrue(true);
    }


    static class Patient {
        List<Measurement> temperatureMeasurements = Arrays.asList(new Measurement(Instant.now().minusSeconds(10800), 36.8), new Measurement(Instant.now().minusSeconds(7200), 37.0), new Measurement(Instant.now().minusSeconds(3600), 37.2));
    }

}
