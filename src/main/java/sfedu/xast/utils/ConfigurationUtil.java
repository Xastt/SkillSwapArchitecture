package sfedu.xast.utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationUtil {
    private Properties properties;
    private String filePath;

    public ConfigurationUtil(String filePath) {
        this.filePath = filePath;
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream(filePath)){
            properties.load(fis);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void createProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    public void updateProperty(String key, String newValue) {
        if (properties.containsKey(key)) {
            properties.setProperty(key, newValue);
            saveProperties();
        } else {
            System.out.println("Свойство с ключом '" + key + "' не найдено.");
        }
    }

    public void deleteProperty(String key) {
        if (properties.remove(key) != null) {
            saveProperties();
        } else {
            System.out.println("Свойство с ключом '" + key + "' не найдено.");
        }
    }

    private void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
