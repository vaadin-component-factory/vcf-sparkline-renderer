package com.vaadin.flow.component.sparklinerenderer;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleExample extends VerticalLayout {

    public SimpleExample() {
        setSizeFull();
        Grid<Song> grid = new Grid<>();
        grid.addColumn(Song::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);
        grid.setItems(createListOfSongs(20));
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValues, song -> new SparklineConfiguration())).setHeader("Daily listeners");
        add(grid);
    }

    private List<Song> createListOfSongs(int numberOfSongs) {
        List<Song> songs = new ArrayList<>(numberOfSongs);
        for (int i = 0; i < numberOfSongs; i++) {
            Song song = new Song("Song of" + i, "Artist no " + i);
            List<Measurement> dailyListeners = new ArrayList<>();
            for (int j = 1; j < 30; j++) {
                dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, j, 0, 0, 0).toInstant(ZoneOffset.UTC), 1000 * Math.random()));
            }
            song.setDailyListeners(new Measurements("Listeners in January", dailyListeners));
            songs.add(song);
        }
        return songs;
    }

    private SparklineValues createSparklineValues(Song song) {
        return new SparklineValues(song.getDailyListeners().getMeasurements().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

}
