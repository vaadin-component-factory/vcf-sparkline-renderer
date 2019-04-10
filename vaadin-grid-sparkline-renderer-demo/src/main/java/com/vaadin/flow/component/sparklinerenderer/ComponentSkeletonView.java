package com.vaadin.flow.component.sparklinerenderer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Route("sparkline-renderer")
@Theme(Lumo.class)
@Push(transport = Transport.WEBSOCKET_XHR)
public class ComponentSkeletonView extends DemoView {

    public ScheduledExecutorService executorService;

    @Override
    protected void initView() {
        basicDemo();
    }

    private void basicDemo() {
        Grid<Song> grid = new Grid<>();
        grid.addColumn(Song::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);
        grid.setItems(createListOfOneSongs());
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValues, this::createConfiguration)).setHeader("Daily listeners");

        grid.setHeight("120px");
        add(grid);

        Grid<Song> grid2 = new Grid<>();
        grid2.addColumn(Song::getName).setHeader("Name").setSortable(true);
        grid2.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);

        grid2.addColumn(new SparklineRenderer<>(this::createSparklineValues2, this::createConfiguration2))
                .setResizable(true)
                .setSortable(true)
                .setHeader("Daily listeners");

        add(grid2);
        final UI ui = UI.getCurrent().getUI().get();

        Runnable dalayedDataThread = () -> {
            ui.access(()-> {
                grid2.setItems(createListOfSongs(100));
                ui.push();
            });
        };
        executorService = Executors
                .newSingleThreadScheduledExecutor();
        executorService.schedule(dalayedDataThread, 500, TimeUnit.MILLISECONDS);

    }

    private SparklineValues createSparklineValues(Song song) {
        return new SparklineValues(song.getDailyListeners().getMeasurements().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    private SparklineValues createSparklineValues2(Song song) {
        return new SparklineValues(song.getDailyListeners().getMeasurements().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
    }

    private SparklineConfiguration createConfiguration(Song song) {
        List<SparkLinePlotBand> plotBand = new ArrayList<>();
        plotBand.add(new SparkLinePlotBand(0.0, 200.0).withLineColor(SparklineConfiguration.RED));
        plotBand.add(new SparkLinePlotBand(200.0, 400.0).withLineColor(SparklineConfiguration.YELLOW));
        plotBand.add(new SparkLinePlotBand(400.0, 600.0).withLineColor(SparklineConfiguration.GREEN).withBackgroundColor(SparklineConfiguration.LIGHT_GREEN));
        plotBand.add(new SparkLinePlotBand(600.0, 800.0).withLineColor(SparklineConfiguration.YELLOW));
        plotBand.add(new SparkLinePlotBand(800.0, 1200.0).withLineColor(SparklineConfiguration.RED));
        return new SparklineConfiguration().withPlotBands(plotBand);
    }

    private SparklineConfiguration createConfiguration2(Song song) {
        List<SparkLinePlotBand> plotBand = new ArrayList<>();
        plotBand.add(new SparkLinePlotBand(0.0, 450.0).withLineColor(SparklineConfiguration.GREEN).withBackgroundColor(SparklineConfiguration.LIGHT_GREEN));
        plotBand.add(new SparkLinePlotBand(450.0, 700.0).withLineColor(SparklineConfiguration.YELLOW));
        plotBand.add(new SparkLinePlotBand(700.0, 1000.0).withLineColor(SparklineConfiguration.RED));
        return new SparklineConfiguration().withPlotBands(plotBand);
    }

    private List<Song> createListOfSongs(int numberOfSongs) {
        List<Song> songs = new ArrayList<>(numberOfSongs);
        for (int i=0; i<numberOfSongs; i++) {
            Song song = new Song("Song of"+i, "Artist no "+i);
            List<Measurement> dailyListeners = new ArrayList<>();
            for (int j=1; j<30; j++) {
                dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, j, 0, 0, 0).toInstant(ZoneOffset.UTC), 1000*Math.random()));
            }
            song.setDailyListeners(new Measurements("Listeners in January", dailyListeners));
            songs.add(song);
        }
        return songs;
    }

    private List<Song> createListOfOneSongs() {
        List<Song> songs = new ArrayList<>(1);
        Song song = new Song("Song of 1", "Artist no 1");
        List<Measurement> dailyListeners = new ArrayList<>();
        dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0, 0).toInstant(ZoneOffset.UTC), 100.0));
        dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 2, 0, 0, 0).toInstant(ZoneOffset.UTC), 500.0));
        dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 3, 0, 0, 0).toInstant(ZoneOffset.UTC), 1000.0));
        dailyListeners.add(new Measurement(LocalDateTime.of(2019, Month.JANUARY, 4, 0, 0, 0).toInstant(ZoneOffset.UTC), 100.0));
        song.setDailyListeners(new Measurements("Listeners in January", dailyListeners));
        songs.add(song);
        return songs;
    }
}

