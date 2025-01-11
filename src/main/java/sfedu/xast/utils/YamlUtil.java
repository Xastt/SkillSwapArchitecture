package sfedu.xast.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class YamlUtil {

    Logger logger = LoggerFactory.getLogger(YamlUtil.class);

    private Map<String, Object> yamlMap;
    private String filePath;

    public YamlUtil(String filePath) {
        this.filePath = filePath;
        Yaml yaml = new Yaml();
        try (InputStream in = new FileInputStream(filePath)) {
            yamlMap = yaml.load(in);
            if (yamlMap == null) {
                yamlMap = new LinkedHashMap<>();
            }
        } catch (FileNotFoundException e) {
            yamlMap = new LinkedHashMap<>(); // Если файл не найден, создаем новый Map
        } catch (IOException e) {
            logger.error("Can not load properties from file: {}",e.getMessage());
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
            logger.error("Can not update key: {} to value: {}",key,newValue);
        }
    }

    public void delete(String key) {
        if (yamlMap.remove(key) != null) {
            save();
        } else {
            logger.error("Can not delete key: {}, not found",key);
        }
    }

    private void save() {
        Yaml yaml = new Yaml();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            yaml.dump(yamlMap, writer);
        } catch (IOException e) {
            logger.error("Can not save yaml file: {}",e.getMessage());
        }
    }
}
