package com.vaadin.flow.component.componentskeleton;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SparklineRendererTest {

    private Grid systemUnderTest;

    @Before
    public void setUp() {
        systemUnderTest = new Grid();
    }

    @Test
    public void onAttach_init() {
        systemUnderTest.addAttachListener(e -> new AttachEvent(systemUnderTest, true));

        Assert.assertTrue(true);
    }
}
