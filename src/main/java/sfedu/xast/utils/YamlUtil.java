package sfedu.xast.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlUtil {

    private Map<String, Object> yamlMap;
    private String filePath;

    public YamlUtil(String filePath) {
        this.filePath = filePath;
        Yaml yaml = new Yaml();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if(in != null) {
                yamlMap = yaml.load(in);
            }else{
                yamlMap = new LinkedHashMap<>();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        return yamlMap.get(key);
    }

    public void create(String key, Object value) {
        yamlMap.put(key, value);
        save();
    }

    public void update(String key, Object newValue) {
        if (yamlMap.containsKey(key)) {
            yamlMap.put(key, newValue);
            save();
        } else {
            System.out.println("Ключ '" + key + "' не найден.");
        }
    }

    public void delete(String key) {
        if (yamlMap.remove(key) != null) {
            save();
        } else {
            System.out.println("Ключ '" + key + "' не найден.");
        }
    }

    private void save() {
        Yaml yaml = new Yaml();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            yaml.dump(yamlMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
