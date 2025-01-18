package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.slf4j.*;
import org.xml.sax.SAXException;
import sfedu.xast.models.*;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import sfedu.xast.utils.Constants;
import sfedu.xast.utils.Status;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataProviderXml{

    Logger logger = LoggerFactory.getLogger(DataProviderXml.class);

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
     * creating record in xml file
     * @param persInf
     * @param profInf
     * @return true or false
     */
    public boolean createProfInf(ProfInf profInf, PersInf persInf) {
        if(persInf == null || profInf == null) {
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlProfInfFilePath));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Element person = doc.createElement("person");

            person.setAttribute("id", persInf.getId());
            person.appendChild(createElementWithTextNode(doc, "SkillName", profInf.getSkillName()));
            person.appendChild(createElementWithTextNode(doc, "SkillDescription", profInf.getSkillDescription()));
            person.appendChild(createElementWithTextNode(doc, "Cost", String.valueOf(profInf.getCost())));
            person.appendChild(createElementWithTextNode(doc, "PersDescription", profInf.getPersDescription()));
            person.appendChild(createElementWithTextNode(doc, "Exp", String.valueOf(profInf.getExp())));
            person.appendChild(createElementWithTextNode(doc, "Rating", String.valueOf(profInf.getRating())));

            root.appendChild(person);
            saveXml(doc, Constants.xmlProfInfFilePath);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from xml file using personal id
     * @param profInf
     * @param id
     * @return ProfInf object
     */
    public ProfInf readProfInf(ProfInf profInf,String id) throws XMLParseException, ParserConfigurationException, IOException, SAXException {
        if(profInf==null){
            throw new XMLParseException("ProfInf object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlProfInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    profInf.setSkillName(personElement.getElementsByTagName("SkillName").item(0).getTextContent());
                    profInf.setSkillDescription(personElement.getElementsByTagName("SkillDescription").item(0).getTextContent());
                    profInf.setCost(Double.parseDouble(personElement.getElementsByTagName("Cost").item(0).getTextContent()));
                    profInf.setPersDescription(personElement.getElementsByTagName("PersDescription").item(0).getTextContent());
                    profInf.setExp(Double.parseDouble(personElement.getElementsByTagName("Exp").item(0).getTextContent()));
                    profInf.setRating(Double.parseDouble(personElement.getElementsByTagName("Rating").item(0).getTextContent()));
                    return profInf;
                }
            }
            throw new XMLParseException("Can't find person with id " + id);
        } catch (XMLParseException | ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * read information from csv profinf using id
     * @param id
     * @return Profinf
     * @throws SQLException
     */
    public ProfInf readProfInfWithId(String id) throws XMLParseException, ParserConfigurationException, IOException, SAXException {
        ProfInf profInf = new ProfInf();
        if(profInf==null){
            throw new XMLParseException("ProfInf object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlProfInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    profInf.setSkillName(personElement.getElementsByTagName("SkillName").item(0).getTextContent());
                    profInf.setSkillDescription(personElement.getElementsByTagName("SkillDescription").item(0).getTextContent());
                    profInf.setCost(Double.parseDouble(personElement.getElementsByTagName("Cost").item(0).getTextContent()));
                    profInf.setPersDescription(personElement.getElementsByTagName("PersDescription").item(0).getTextContent());
                    profInf.setExp(Double.parseDouble(personElement.getElementsByTagName("Exp").item(0).getTextContent()));
                    profInf.setRating(Double.parseDouble(personElement.getElementsByTagName("Rating").item(0).getTextContent()));
                    return profInf;
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
     * @param profInf
     * @return true or false
     */
    public boolean updateProfInf(ProfInf profInf) {
        try {
            if (profInf == null) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlProfInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(profInf.getPersId())) {
                    personElement.getElementsByTagName("SkillName").item(0).setTextContent(profInf.getSkillName());
                    personElement.getElementsByTagName("SkillDescription").item(0).setTextContent(profInf.getSkillDescription());
                    personElement.getElementsByTagName("Cost").item(0).setTextContent(String.valueOf(profInf.getCost()));
                    personElement.getElementsByTagName("PersDescription").item(0).setTextContent(profInf.getPersDescription());
                    personElement.getElementsByTagName("Exp").item(0).setTextContent(String.valueOf(profInf.getExp()));
                    personElement.getElementsByTagName("Rating").item(0).setTextContent(String.valueOf(profInf.getRating()));
                    saveXml(doc, Constants.xmlProfInfFilePath);
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
    public boolean deleteProfInf(String id) {
        if(id == null){
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlProfInfFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    personElement.getParentNode().removeChild(personElement);
                    saveXml(doc, Constants.xmlProfInfFilePath);

                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * method, which add new data to the xml file skillExchange
     * contains information about users and offering skill
     * @param skillExchange
     * @return true or false
     */
    public boolean createSkillExchange(SkillExchange skillExchange) {
        if(skillExchange == null) {
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlSkillExchangeFilePath));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Element exchange = doc.createElement("exchange");

            exchange.setAttribute("id", skillExchange.getExchangeId());
            exchange.appendChild(createElementWithTextNode(doc, "SkillOffered", skillExchange.getSkillOffered()));
            exchange.appendChild(createElementWithTextNode(doc, "UserOffering", skillExchange.getUserOffering()));
            exchange.appendChild(createElementWithTextNode(doc, "UserRequesting", skillExchange.getUserRequesting()));

            root.appendChild(exchange);
            saveXml(doc, Constants.xmlSkillExchangeFilePath);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from xml file using personal id
     * @param skillExchange
     * @return SkillExchange object
     */
    public SkillExchange readSkillExchange(SkillExchange skillExchange) throws XMLParseException, ParserConfigurationException, IOException, SAXException {
        if(skillExchange==null){
            throw new XMLParseException("SkillExchange object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlSkillExchangeFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("exchange");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(skillExchange.getExchangeId())) {
                    skillExchange.setSkillOffered(personElement.getElementsByTagName("SkillOffered").item(0).getTextContent());
                    skillExchange.setUserOffering(personElement.getElementsByTagName("UserOffering").item(0).getTextContent());
                    skillExchange.setUserRequesting(personElement.getElementsByTagName("UserRequesting").item(0).getTextContent());
                    return skillExchange;
                }
            }
            throw new XMLParseException("Can't find exchange with id " + skillExchange.getExchangeId());
        } catch (XMLParseException | ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in xml file by personal id
     * @param skillExchange
     * @return true or false
     */
    public boolean updateSkillExchange(SkillExchange skillExchange) {
        try {
            if (skillExchange == null) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlSkillExchangeFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("exchange");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(skillExchange.getExchangeId())) {
                    personElement.getElementsByTagName("SkillOffered").item(0).setTextContent(skillExchange.getSkillOffered());
                    personElement.getElementsByTagName("UserOffering").item(0).setTextContent(skillExchange.getUserOffering());
                    personElement.getElementsByTagName("UserRequesting").item(0).setTextContent(skillExchange.getUserRequesting());

                    saveXml(doc, Constants.xmlSkillExchangeFilePath);
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
    public boolean deleteSkillExchange(String id) {
        if(id == null){
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlSkillExchangeFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("exchange");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(id)) {
                    personElement.getParentNode().removeChild(personElement);
                    saveXml(doc, Constants.xmlSkillExchangeFilePath);

                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * method, which add new data to the xml file Review
     * contains information about users and offering skill
     * @param review
     * @return true or false
     */
    public boolean createReview(Review review) {
        if(review == null) {
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlReviewFilePath));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Element reviewTag = doc.createElement("review");

            reviewTag.setAttribute("id", review.getReviewId());
            reviewTag.appendChild(createElementWithTextNode(doc, "Rating", String.valueOf(review.getRating())));
            reviewTag.appendChild(createElementWithTextNode(doc, "Comment", review.getComment()));
            reviewTag.appendChild(createElementWithTextNode(doc, "Reviewer", review.getReviewer()));
            reviewTag.appendChild(createElementWithTextNode(doc, "UserEvaluated", review.getUserEvaluated()));

            root.appendChild(reviewTag);
            saveXml(doc, Constants.xmlReviewFilePath);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from xml file using personal id
     * @param review
     * @return Review object
     */
    public Review readReview(Review review) throws XMLParseException, ParserConfigurationException, IOException, SAXException {
        if(review==null){
            throw new XMLParseException("Review object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlReviewFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("review");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(review.getReviewId())) {
                    review.setRating(Double.valueOf(personElement.getElementsByTagName("Rating").item(0).getTextContent()));
                    review.setComment(personElement.getElementsByTagName("Comment").item(0).getTextContent());
                    review.setReviewer(personElement.getElementsByTagName("Reviewer").item(0).getTextContent());
                    review.setUserEvaluated(personElement.getElementsByTagName("UserEvaluated").item(0).getTextContent());
                    return review;
                }
            }
            throw new XMLParseException("Can't find review with id " + review.getReviewId());
        } catch (XMLParseException | ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in xml file by personal id
     * @param review
     * @return true or false
     */
    public boolean updateReview(Review review) {
        try {
            if (review == null) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlReviewFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("review");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(review.getReviewId())) {
                    personElement.getElementsByTagName("Rating").item(0).setTextContent(String.valueOf(review.getRating()));
                    personElement.getElementsByTagName("Comment").item(0).setTextContent(review.getComment());
                    personElement.getElementsByTagName("Reviewer").item(0).setTextContent(review.getReviewer());
                    personElement.getElementsByTagName("UserEvaluated").item(0).setTextContent(review.getUserEvaluated());

                    saveXml(doc, Constants.xmlReviewFilePath);
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
     * @param review
     * @return true/false
     */
    public boolean deleteReview(Review review) {
        if(review == null){
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlReviewFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("review");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(review.getReviewId())) {
                    personElement.getParentNode().removeChild(personElement);
                    saveXml(doc, Constants.xmlReviewFilePath);

                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * method, which add new data to the xml file Transaction
     * @param transaction
     * @return true or false
     */
    public boolean createTransaction(Transaction transaction) {
        if(transaction == null) {
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlTransactionFilePath));
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            Element transactionTag = doc.createElement("transaction");

            transactionTag.setAttribute("id", transaction.getTransactionId());
            transactionTag.appendChild(createElementWithTextNode(doc, "Date", String.valueOf(transaction.getDate())));
            transactionTag.appendChild(createElementWithTextNode(doc, "Status", String.valueOf(transaction.getStatus())));
            transactionTag.appendChild(createElementWithTextNode(doc, "ChangeId", transaction.getChangeId()));
            root.appendChild(transactionTag);

            saveXml(doc, Constants.xmlTransactionFilePath);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from xml file using id
     * @param transaction
     * @return Review object
     */
    public Transaction readTransaction(Transaction transaction) throws XMLParseException, ParserConfigurationException, IOException, SAXException, ParseException {
        if(transaction==null){
            throw new XMLParseException("Transaction object must not be null");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlTransactionFilePath));
            doc.getDocumentElement().normalize();

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));

            NodeList nodeList = doc.getElementsByTagName("transaction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(transaction.getTransactionId())) {
                    Date date = dateFormat.parse(personElement.getElementsByTagName("Date").item(0).getTextContent());
                    transaction.setDate(date);
                    transaction.setStatus(Status.valueOf(personElement.getElementsByTagName("Status").item(0).getTextContent()));
                    transaction.setChangeId(personElement.getElementsByTagName("ChangeId").item(0).getTextContent());
                    return transaction;
                }
            }
            throw new XMLParseException("Can't find transaction with id " + transaction.getTransactionId());
        } catch (XMLParseException | ParserConfigurationException | SAXException | IOException | ParseException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in xml file by id
     * @param transaction
     * @return true or false
     */
    public boolean updateTransaction(Transaction transaction) {
        try {
            if (transaction == null) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlTransactionFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("transaction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(transaction.getTransactionId())) {
                    personElement.getElementsByTagName("Date").item(0).setTextContent(String.valueOf(transaction.getDate()));
                    personElement.getElementsByTagName("Status").item(0).setTextContent(String.valueOf(transaction.getStatus()));
                    personElement.getElementsByTagName("ChangeId").item(0).setTextContent(transaction.getChangeId());

                    saveXml(doc, Constants.xmlTransactionFilePath);
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
     * @param transaction
     * @return true/false
     */
    public boolean deleteTransaction(Transaction transaction) {
        if(transaction == null || transaction.getTransactionId() == null){
            return false;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(new File(Constants.xmlTransactionFilePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("transaction");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element personElement = (Element) nodeList.item(i);
                if (personElement.getAttribute("id").equals(transaction.getTransactionId())) {
                    personElement.getParentNode().removeChild(personElement);
                    saveXml(doc, Constants.xmlTransactionFilePath);

                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
