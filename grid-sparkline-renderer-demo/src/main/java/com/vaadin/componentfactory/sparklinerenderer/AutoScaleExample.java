package com.vaadin.componentfactory.sparklinerenderer;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutoScaleExample extends VerticalLayout {

    public AutoScaleExample() {
        setSizeFull();
        List<Patient> listOfPatient = createListOfPatient(200);

        Grid<Patient> grid = new Grid<>();
        grid.addColumn(Patient::getName).setHeader("Patient name").setSortable(true);
        grid.setItems(listOfPatient);
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValuesTemp, p -> new SparklineConfiguration())).setHeader("Body temparature");
        add(grid);

        Grid<Patient> grid2 = new Grid<>();
        grid2.addColumn(Patient::getName).setHeader("Patient name").setSortable(true);
        grid2.setItems(listOfPatient);
        grid2.addColumn(new SparklineRenderer<>(this::createSparklineValuesTemp, p -> new SparklineConfiguration().withAutoScaleYAxis(true))).setHeader("Body temparature");
        add(grid2);

        add(new H2("Default y-axis scaling"));
        add(grid);
        add(new H2(""));
        add(new H2("Scale between min and max"));
        add(grid2);
    }

    private SparklineValues createSparklineValuesTemp(Patient patient) {
        return new SparklineValues(patient.getBodyTemp().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    private List<Patient> createListOfPatient(int numberOfPatients) {
        List<Patient> patients = new ArrayList<>(numberOfPatients);
        for (int i = 0; i < numberOfPatients; i++) {
            Patient patient = new Patient("Patient " + i);
            List<Measurement> temps = new ArrayList<>();
            for (int j = 0; j < 24; j++) {
                temps.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 1, j, 0, 0).toInstant(ZoneOffset.UTC), 36.0 + 1.0 * Math.random() + (Math.random() < 0.5 ? 4 * Math.random() : 0.0)));
            }
            patient.setBodyTemp(temps);
            patients.add(patient);
        }
        return patients;
    }

}
