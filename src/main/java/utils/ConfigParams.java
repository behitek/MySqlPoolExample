package utils;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigParams {
    private static String resources_dir = "./resources";
    private static Properties propReader = new Properties();

    static {
        try {
            if (!new File(resources_dir).exists()) {
                resources_dir = System.getProperty("user.dir") + "/src/main/resources";
            }
            propReader.load(new FileInputStream(getResources_dir() + "/conf.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String key) {
        //get the property value
        return propReader.getProperty(key);
    }

    public static String getResources_dir() {
        return resources_dir;
    }
}
