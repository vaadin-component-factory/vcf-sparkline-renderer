# Vaadin SprarklineRenderer

Vaadin SprarklineRenderer is an add-on renderer for Grid (Flow).

## License & Author

This Add-on is distributed under [Commercial Vaadin Add-on License version 3](http://vaadin.com/license/cval-3) (CVALv3). For license terms, see LICENSE.txt.

Vaadin SprarklineRenderer is written by Vaadin Ltd.

To purchase a license, visit http://vaadin.com/pricing

### Installing
Add SprarklineRenderer to your project
```xml
<dependency>
    <groupId>com.vaadin</groupId>
    <artifactId>vaadin-grid-sparkline-renderer-flow</artifactId>
    <version>${project.version}</version>
</dependency>
```

### Using Vaadin SprarklineRenderer

[<img src="vaadin-grid-sparkline-renderer-demo/src/main/resources/screenshot.png" width="700" alt="Screenshot of SparklineRenderer with Grid">]()

#### Basic use
```java
public class ComponentSkeletonView extends DemoView {
    
private void basicDemo() {
//...
    Grid<Song> grid = new Grid<>();
        grid.addColumn(Song::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);
        grid.setItems(createListOfOneSongs());
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValues, this::createConfiguration)).setHeader("Daily listeners");
//...
}

private SparklineValues createSparklineValues(Song song) {
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
}
```

## Setting up for development

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone https://github.com/vaadin/sparkline-renderer.git
```

To build and install the project into the local repository run

```mvn install -DskipITs```

in the root directory. `-DskipITs` will skip the integration tests, which require a TestBench license. If you want to run all tests as part of the build, run

```mvn install```

To compile and run demos locally execute

```
mvn compile
mvn -pl vaadin-component-skeleton-flow-demo -Pwar jetty:run
```
