package com.danizz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesManagerTest {

    private PropertiesManager propertiesManager;

    @Before
    public void setUp() {
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/test/resources/test.properties");
    }

    @Test
    public void getProperties() {
        assertNotEquals("sdsdsd", propertiesManager.getProperties("test"));
    }

    @Test
    public void getRightProperty() {
        assertEquals("test", propertiesManager.getProperties("test-property"));
    }

    @Test
    public void setPropertyAndCheckIt() {
        propertiesManager.setProperty("property-name", "value");
        assertEquals("value", propertiesManager.getProperties("property-name"));
    }

    @Test
    public void getStoredProperties() {
        PropertiesManager pm = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/test/resources/test.properties");
        String s = pm.getProperties("property-name");
        assertEquals("value", s);
    }
}