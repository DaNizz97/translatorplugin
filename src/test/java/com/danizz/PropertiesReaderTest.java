package com.danizz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesReaderTest {

    private PropertiesReader propertiesReader;
    @Before
    public void setUp() throws Exception {
        propertiesReader = new PropertiesReader("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/test.properties");
    }

    @Test
    public void getProperties() {
        assertNotEquals("sdsdsd", propertiesReader.getProperties("test"));
    }

    @Test
    public void getRightProperty() {
        assertEquals("test", propertiesReader.getProperties("test-property"));
    }

}