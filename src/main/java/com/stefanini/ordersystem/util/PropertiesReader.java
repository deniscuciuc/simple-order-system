package com.stefanini.ordersystem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
    private final Properties properties;

    public PropertiesReader(String fileName) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(fileName));
        } catch (IOException exception) {
            logger.error("File {} not found \n " + exception.getMessage(), fileName);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "Such key not found");
    }
}
