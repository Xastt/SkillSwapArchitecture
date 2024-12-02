package sfedu.xast.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlUtil {

    private Map<String, Object> yamlMap;

    public YamlUtil(String filePath) {
        Yaml yaml = new Yaml();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream(filePath)) {
            yamlMap = yaml.load(in);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        return yamlMap.get(key);
    }

}
