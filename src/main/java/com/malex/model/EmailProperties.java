package com.malex.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailProperties {

    private static Properties properties;

    private static String propertyFileName = System.getProperty("user.dir") + "/config_mail.properties";

    static {
        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException ex) {
            System.err.println("No Property file found!");
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
