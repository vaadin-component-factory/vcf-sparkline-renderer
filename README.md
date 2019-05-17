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

[<img src="https://github.com/vaadin/sparkline-renderer/blob/master/grid-sparkline-renderer-demo/src/main/resources/screenshot.png" width="700" alt="Screenshot of SparklineRenderer with Grid">]()

#### Basic use
```java
public class SparklineRendererDemoView extends DemoView {
    
private void basicDemo() {
//... 
    Grid<Song> grid = new Grid<>();
        grid.addColumn(Song::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Song::getArtist).setHeader("Artist").setSortable(true);
        grid.setItems(createListOfOneSongs());
        // add a column with SparklineRenderer to Grid
        grid.addColumn(new SparklineRenderer<>(this::createSparklineValues,  song -> new SparklineConfiguration())).setHeader("Daily listeners");
//...
}

private SparklineValues createSparklineValues(Song song) {
    // convert existing time series data to a SparklineValues object
    return new SparklineValues(song.getDailyListeners().getMeasurements().stream().map(measurement -> new SparklineValues.SparklineValue(measurement.getInstant(), measurement.getValue())).collect(Collectors.toList()));
}
}
```

#### Add PlotBands

```java
public class SparklineRendererDemoView extends DemoView {
    
private void plotBandDemo() {
//... 
    grid.addColumn(new SparklineRenderer<>(this::createSparklineValuesTemp, this::createSparklineConfTemp)).setHeader("Body temparature");//...
}

private SparklineConfiguration createSparklineConfTemp(Patient patient) {
        return new SparklineConfiguration()
                // scale y-axis between min and ax value
                .withAutoScaleYAxis(true)
                // add a plotband with line color and background color
                .withPlotBand(new SparkLinePlotBand(36.0, 38.0)
                        .withBackgroundColor(SparklineConfiguration.LIGHT_GREEN).withLineColor(Color.GREEN))
                // add a plotband with line color
                .withPlotBand(new SparkLinePlotBand(38.0, 41.0)
                        .withLineColor(Color.RED));
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
