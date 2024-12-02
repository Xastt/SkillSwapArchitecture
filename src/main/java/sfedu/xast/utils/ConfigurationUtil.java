package sfedu.xast.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationUtil {
    private Properties properties;

    public ConfigurationUtil(String filaPath) {
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream(filaPath)){
            properties.load(fis);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
