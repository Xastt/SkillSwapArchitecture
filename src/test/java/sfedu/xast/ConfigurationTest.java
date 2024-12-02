package sfedu.xast;

import org.junit.Test;
import sfedu.xast.utils.ConfigurationUtil;
import sfedu.xast.utils.XmlUtil;
import sfedu.xast.utils.YamlUtil;

public class ConfigurationTest {

    @Test
    public void testCRUDWithXML() {
        XmlUtil xmlUtil = new XmlUtil("src/main/resources/constantsXML.xml");
        xmlUtil.createElement("experience", "5");
        System.out.println(xmlUtil.getValue("experience"));
        xmlUtil.updateElement("experience", "3");
        System.out.println(xmlUtil.getValue("experience"));
        xmlUtil.deleteElement("experience");
    }

    @Test
    public void testCRUDWithProperties() {
        ConfigurationUtil configUtil = new ConfigurationUtil("src/main/resources/constantProperties.properties");
        configUtil.createProperty("newKey", "newValue");
        System.out.println(configUtil.getProperty("newKey"));
        configUtil.updateProperty("newKey", "updatedValue");
        System.out.println(configUtil.getProperty("newKey"));
        configUtil.deleteProperty("newKey");
    }

    @Test
    public void testCRUDWithYAML(){
        YamlUtil yamlUtil = new YamlUtil("src/main/resources/constantsYAML.yaml");
        yamlUtil.create("newKey", "newValue");
        System.out.println(yamlUtil.get("newKey"));
        yamlUtil.update("newKey", "updatedValue");
        System.out.println(yamlUtil.get("newKey"));
        yamlUtil.delete("newKey");
        System.out.println(yamlUtil.get("newKey"));
    }
}
