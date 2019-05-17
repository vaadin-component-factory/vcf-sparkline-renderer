package com.vaadin.flow.component.sparklinerenderer;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.concurrent.ScheduledExecutorService;

@Route("sparkline-renderer")
@Theme(Lumo.class)
@Push(transport = Transport.WEBSOCKET_XHR)
public class SparklineRendererDemoView extends DemoView {

    public ScheduledExecutorService executorService;

    @Override
    protected void initView() {
        basicDemo();
    }

    private void basicDemo() {
        VerticalLayout content = new VerticalLayout();
        Tab tab1 = new Tab("Simple example");
        Tab tab2 = new Tab("Using plot bands");
        Tab tab3 = new Tab("Null values");
        Tab tab4 = new Tab("Auto scale y-axis");
        Tab tab5 = new Tab("Using plot bands, PNG rendering");
        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4, tab5);
        tabs.addSelectedChangeListener(selectedChangeEvent -> {
            Tab selectedTab = selectedChangeEvent.getSource().getSelectedTab();
            if (selectedTab.equals(tab1)) {
                content.removeAll();
                content.add(new SimpleExample());
            } else if (selectedTab.equals(tab2)) {
                content.removeAll();
                content.add(new PlotBandExample());
            } else if (selectedTab.equals(tab3)) {
                content.removeAll();
                content.add(new NullValuesExample());
            } else if (selectedTab.equals(tab4)) {
                content.removeAll();
                content.add(new AutoScaleExample());
            } else if (selectedTab.equals(tab5)) {
                content.removeAll();
                content.add(new PlotBandExamplePNG());
            }
        });


        tab1.setSelected(true);
        content.setSizeFull();
        content.add(new SimpleExample());
        add(tabs, content);
    }

}
