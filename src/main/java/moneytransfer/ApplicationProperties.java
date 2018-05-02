package moneytransfer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final String PROPERTIES_FILE = "app.properties";

    private final static Properties properties = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        InputStream in = ApplicationProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        System.out.println("Read all properties from file");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Properties get() {
        return properties;
    }
}
