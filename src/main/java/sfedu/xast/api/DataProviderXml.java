package sfedu.xast.api;

import org.slf4j.*;
import org.xml.sax.SAXException;
import sfedu.xast.models.*;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import sfedu.xast.utils.Constants;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DataProviderXml{

    Logger logger = LoggerFactory.getLogger(DataProviderXml.class);

    /**
     * creating record in xml file
     * @param persInf
     * @return true or false
     */
    public boolean createPersInf(PersInf persInf) {
        if(persInf == null) {
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlPersInfFilePath));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Element person = doc.createElement("person");

            person.setAttribute("id", persInf.getId());
            person.appendChild(createElementWithTextNode(doc, "surname", persInf.getSurname()));
            person.appendChild(createElementWithTextNode(doc, "name", persInf.getName()));
            person.appendChild(createElementWithTextNode(doc, "phoneNumber", persInf.getPhoneNumber()));
            person.appendChild(createElementWithTextNode(doc, "email", persInf.getEmail()));

            root.appendChild(person);
            saveXml(doc, Constants.xmlPersInfFilePath);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from xml file using personal id
     * @param persInf
     * @param id
     * @return PersINf object
     */
    public PersInf readPersInf(PersInf persInf,String id) throws XMLParseException, ParserConfigurationException, IOException, SAXException {
        if(persInf==null){
            throw new XMLParseException("PersInf object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlPersInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    persInf.setSurname(personElement.getElementsByTagName("surname").item(0).getTextContent());
                    persInf.setName(personElement.getElementsByTagName("name").item(0).getTextContent());
                    persInf.setPhoneNumber(personElement.getElementsByTagName("phoneNumber").item(0).getTextContent());
                    persInf.setEmail(personElement.getElementsByTagName("email").item(0).getTextContent());
                    return persInf;
                }
            }
            throw new XMLParseException("Can't find person with id " + id);
        } catch (XMLParseException | ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in xml file by personal id
     * @param persInf
     * @return true or false
     */
    public boolean updatePersInf(PersInf persInf) {
        try {
            if (persInf == null) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlPersInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(persInf.getId())) {
                    personElement.getElementsByTagName("surname").item(0).setTextContent(persInf.getSurname());
                    personElement.getElementsByTagName("name").item(0).setTextContent(persInf.getName());
                    personElement.getElementsByTagName("phoneNumber").item(0).setTextContent(persInf.getPhoneNumber());
                    personElement.getElementsByTagName("email").item(0).setTextContent(persInf.getEmail());
                    saveXml(doc, Constants.xmlPersInfFilePath);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from xml file using id
     * @param id
     * @return true/false
     */
    public boolean deletePersInf(String id) {
        if(id == null){
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlPersInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    personElement.getParentNode().removeChild(personElement);
                    saveXml(doc, Constants.xmlPersInfFilePath);

                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * save records in xml file
     * @param doc
     * @param xmlFilePath
     * @throws TransformerException
     */
    private void saveXml(Document doc, String xmlFilePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    /**
     * create tag in xml document
     * @param doc
     * @param tagName
     * @param textContent
     * @return
     */
    private Element createElementWithTextNode(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        return element;
    }
}
