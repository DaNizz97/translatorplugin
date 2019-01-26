package com.danizz;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

    private final String path;
    private Properties properties;

    public PropertiesManager(String path) {
        this.path = path;
        properties = new Properties();
    }

    private void loadPropertyFileForInput() {
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Property file " + path + "doesn't exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperties(String propertyName) {
        loadPropertyFileForInput();
        return properties.getProperty(propertyName);
    }
