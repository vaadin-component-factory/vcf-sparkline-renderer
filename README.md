# Vaadin ComponentSkeleton for Flow

Vaadin ComponentSkeleton for Flow is a UI component add-on for Vaadin.

## License & Author

This Add-on is distributed under [Commercial Vaadin Add-on License version 3](http://vaadin.com/license/cval-3) (CVALv3). For license terms, see LICENSE.txt.

Vaadin ComponentSkeleton is written by Vaadin Ltd.

To purchase a license, visit http://vaadin.com/pricing

### Installing
Add ComponentSkeleton to your project
```xml
<dependencies>
  <dependency>
    <groupId>com.vaadin</groupId>
    <artifactId>vaadin-component-skeleton-flow</artifactId>
    <version>${vaadin.componentskeleton.version}</version>
  </dependency>
</dependencies>
```

### Using Vaadin ComponentSkeleton

[<img src="https://raw.githubusercontent.com/vaadin/vaadin-component-skeleton/master/screenshot.gif" width="700" alt="Screenshot of vaadin-component-skeleton">](https://vaadin.com/components/vaadin-component-skeleton)

#### Basic use
```java
Component component = new Component();
```

## Setting up for development

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com:vaadin/vaadin-component-skeleton-flow.git
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
