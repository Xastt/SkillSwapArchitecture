package sfedu.xast;

import org.junit.jupiter.api.Test;
import sfedu.xast.utils.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ConfigurationTest {

    Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);

    @Test
    public void testCRUDWithXML() {
        XmlUtil xmlUtil = new XmlUtil("src/main/resources/constantsXML.xml");
        xmlUtil.createElement("experience", "5");
        logger.info("New XML element created successfully!");
        logger.info("Value of element is {}", xmlUtil.getValue("experience"));
        xmlUtil.updateElement("experience", "3");
        logger.info("Updated value of element is {}", xmlUtil.getValue("experience"));
        xmlUtil.deleteElement("experience");
        logger.info("Delete element");
    }

    @Test
    public void testCRUDWithProperties() {
        ConfigurationUtil configUtil = new ConfigurationUtil("src/main/resources/constantProperties.properties");
        configUtil.createProperty("newKey", "newValue");
        logger.info("New PROPERTY element created successfully!");
        logger.info("Value of new property key is {}", configUtil.getProperty("newKey"));
        configUtil.updateProperty("newKey", "updatedValue");
        logger.info("Updated value of new property key is {}", configUtil.getProperty("newKey"));
        configUtil.deleteProperty("newKey");
        logger.info("Delete new property key");
    }

    @Test
    public void testCRUDWithYAML(){
        YamlUtil yamlUtil = new YamlUtil("src/main/resources/constantsYAML.yaml");
        yamlUtil.create("newKey", "newValue");
        logger.info("New YAML element created successfully!");
        logger.info("Value of new yaml key is {}", yamlUtil.get("newKey"));
        yamlUtil.update("newKey", "updatedValue");
        logger.info("Updated value of new yaml key is {}", yamlUtil.get("newKey"));
        yamlUtil.delete("newKey");
        logger.info("Delete new yaml key");
    }
}
