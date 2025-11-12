package core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {



    private static final Properties properties = new Properties();

    static {
        try {

            FileInputStream configFis = new FileInputStream("src/test/java/resources/config.properties");
            properties.load(configFis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    // ✅ Single-argument version
    public static String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
        //return properties.getProperty(key);
    }

    // ✅ Two-argument version (safe fallback)
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }
    /*private static final Properties properties = new Properties();

    static {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/test/java/resources/env.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key, String defaultValue){

        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }*/

}
