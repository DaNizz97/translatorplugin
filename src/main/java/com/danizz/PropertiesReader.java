package com.danizz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private final String path;
    private Properties properties;

    public PropertiesReader(String path) {
        this.path = path;
        properties = new Properties();
    }

    public String getProperties(String propertyName) {
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Property file " + path + "doesn't exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyName);
    }
}
