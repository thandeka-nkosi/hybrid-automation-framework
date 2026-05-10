package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ApiConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config/api-config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("api-config.properties file not found under src/test/resources/config");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load api-config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }
}
