package sfedu.xast.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class XmlUtil {
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
            e.printStackTrace();
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
            System.out.println("Элемент с тегом '" + tagName + "' не найден.");
        }
    }

    public void deleteElement(String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node nodeToRemove = nodeList.item(0);
            nodeToRemove.getParentNode().removeChild(nodeToRemove);
            saveChanges();
        } else {
            System.out.println("Элемент с тегом '" + tagName + "' не найден.");
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
            e.printStackTrace();
        }
    }
    }
