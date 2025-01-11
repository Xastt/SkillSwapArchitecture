package sfedu.xast.utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class XmlUtil {
    Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    private Document document;
    private String filePath;

    public XmlUtil(String filePath) {
        try{
            this.filePath = filePath;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(filePath);
            document.getDocumentElement().normalize();
        }catch(Exception e){
            logger.error("Can not load properties from file: {}",e.getMessage());
        }
    }

    public String getValue(String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    public void createElement(String tagName, String textContent) {
        Element newElement = document.createElement(tagName);
        newElement.setTextContent(textContent);
        document.getDocumentElement().appendChild(newElement);
        saveChanges();
    }

    public void updateElement(String tagName, String newTextContent) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(newTextContent);
            saveChanges();
        } else {
            logger.error("Can not update element with tag surname: {}, not found", tagName);
        }
    }

    public void deleteElement(String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node nodeToRemove = nodeList.item(0);
            nodeToRemove.getParentNode().removeChild(nodeToRemove);
            saveChanges();
        } else {
            logger.error("Can not delete element with tag surname: {}, not found", tagName);
        }
    }

    private void saveChanges() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            logger.error("Can not save changes to file: {}",e.getMessage());
        }
    }
    }
