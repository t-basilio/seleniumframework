package tbasilio.tests.testcomponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InitialProperties {

    private static final Properties prop = new Properties(); // you can concat PREFIXPATH with filePath
    private static final String PATH = "src/test/resources/global-data.properties";

    public static String getProperty(String propertyName) {
        try {
            prop.load(new FileInputStream(PATH));
            return prop.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read properties file", e);
        }
    }
}
