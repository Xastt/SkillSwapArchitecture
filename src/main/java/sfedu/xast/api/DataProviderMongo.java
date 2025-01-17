package sfedu.xast.api;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.opencsv.exceptions.CsvException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sfedu.xast.models.HistoryContent;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;
import sfedu.xast.utils.Constants;

import java.io.IOException;
import java.time.LocalDateTime;

public class DataProviderMongo {

    Logger logger = LoggerFactory.getLogger(DataProviderMongo.class);

    private MongoClient mongoClient;
    private String databaseName;

    public DataProviderMongo(String databaseName) {
        this.databaseName = databaseName;
        this.mongoClient = MongoClients.create(Constants.DataBaseUrlMongo);
    }

    /**
     * creating record in PersInf_Collection in MongoDB
     * @param persInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createPersInf(PersInf persInf) {
        if (persInf == null) {
            return false;
        }
        String collectionName = Constants.persInfCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        Document doc = new Document("id", persInf.getId())
                .append("surname", persInf.getSurname())
                .append("name", persInf.getName())
                .append("phoneNumber", persInf.getPhoneNumber())
                .append("email", persInf.getEmail());

        collection.insertOne(doc);
        return true;
    }

    /**
     * reading records in PersInf_Collection in MongoDB using personal id
     * @param persInf
     * @param id
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public PersInf readPersInf(PersInf persInf, String id) {
        if (persInf == null) {
            throw new MongoException("PersInf object must not be null");
        }
        try {
            String collectionName = Constants.persInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc != null) {
                persInf.setId(doc.getString("id"));
                persInf.setSurname(doc.getString("surname"));
                persInf.setName(doc.getString("name"));
                persInf.setPhoneNumber(doc.getString("phoneNumber"));
                persInf.setEmail(doc.getString("email"));
                return persInf;
            } else {
                throw new MongoException("Can't find person with id " + id);
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in PersInf_Collection in MongoDB by personal id
     * @param persInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updatePersInf(PersInf persInf) {
        try {
            if (persInf == null) {
                return false;
            }
            String collectionName = Constants.persInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = new Document("surname", persInf.getSurname())
                    .append("name", persInf.getName())
                    .append("phoneNumber", persInf.getPhoneNumber())
                    .append("email", persInf.getEmail());
            collection.updateOne(Filters.eq("id", persInf.getId()), new Document("$set", doc));
            return true;
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete records in PersInf_Collection in MongoDB using id
     * @param id
     * @return
     */
    public boolean deletePersInf(String id) {
        if(id == null){
            return false;
        }
        String collectionName = Constants.persInfCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.deleteOne(Filters.eq("id", id));
        return true;
    }

    /**
     * creating record in ProfInf_Collection in MongoDB
     * @param persInf
     * @param profInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createProfInf(ProfInf profInf, PersInf persInf) {
        if (persInf == null || profInf == null) {
            return false;
        }
        String collectionName = Constants.profInfCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        Document doc = new Document("id", persInf.getId())
                .append("SkillName",  profInf.getSkillName())
                .append("SkillDescription", profInf.getSkillDescription())
                .append("Cost", profInf.getCost())
                .append("PersDescription", profInf.getPersDescription())
                .append("Exp", profInf.getExp())
                .append("Rating", profInf.getRating());

        collection.insertOne(doc);
        return true;
    }

    /**
     * reading records in ProfInf_Collection in MongoDB using personal id
     * @param profInf
     * @param id
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public ProfInf readProfInf(ProfInf profInf, String id) {
        if (profInf == null) {
            throw new MongoException("ProfInf object must not be null");
        }
        try {
            String collectionName = Constants.profInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc != null) {
                profInf.setPersId(doc.getString("id"));
                profInf.setSkillName(doc.getString("SkillName"));
                profInf.setSkillDescription(doc.getString("SkillDescription"));
                profInf.setCost(doc.getDouble("Cost"));
                profInf.setPersDescription(doc.getString("PersDescription"));
                profInf.setExp(doc.getDouble("Exp"));
                profInf.setRating(doc.getDouble("Rating"));
                return profInf;
            } else {
                throw new MongoException("Can't find person with id " + id);
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public ProfInf readProfInfWithId(String id) {
        ProfInf profInf = new ProfInf();
        try {
            String collectionName = Constants.profInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc != null) {
                profInf.setPersId(doc.getString("id"));
                profInf.setSkillName(doc.getString("SkillName"));
                profInf.setSkillDescription(doc.getString("SkillDescription"));
                profInf.setCost(doc.getDouble("Cost"));
                profInf.setPersDescription(doc.getString("PersDescription"));
                profInf.setExp(doc.getDouble("Exp"));
                profInf.setRating(doc.getDouble("Rating"));
                return profInf;
            } else {
                throw new MongoException("Can't find person with id " + id);
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in ProfInf_Collection in MongoDB by personal id
     * @param profInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateProfInf(ProfInf profInf) {
        try {
            if (profInf == null) {
                return false;
            }
            String collectionName = Constants.profInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = new Document("SkillName", profInf.getSkillName())
                    .append("SkillDescription", profInf.getSkillDescription())
                    .append("Cost", profInf.getCost())
                    .append("PersDescription", profInf.getPersDescription())
                    .append("Exp", profInf.getExp())
                    .append("Rating", profInf.getRating());
            collection.updateOne(Filters.eq("id", profInf.getPersId()), new Document("$set", doc));
            return true;
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete records in PersInf_Collection in MongoDB using id
     * @param id
     * @return
     */
    public boolean deleteProfInf(String id) {
        if(id == null){
            return false;
        }
        String collectionName = Constants.profInfCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.deleteOne(Filters.eq("id", id));
        return true;
    }

    public void insertHistoryContent(HistoryContent historyContent) {
        try{
            Document doc = new Document("id", historyContent.getId())
                    .append("className", historyContent.getClassName())
                    .append("createdDate", historyContent.getCreatedDate().toString())
                    .append("actor", historyContent.getActor())
                    .append("methodName", historyContent.getMethodName())
                    .append("object", historyContent.getObject())
                    .append("status", historyContent.getStatus().name());

            String collectionName = Constants.historyContentCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            collection.insertOne(doc);
        }catch(Exception e){
            logger.error("Error in insert data: {}", e.getMessage());
        }

    }

    public HistoryContent findHistoryContentById(String id) {
        try {
            String collectionName = Constants.historyContentCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc != null) {
                HistoryContent historyContent = new HistoryContent();
                historyContent.setId(doc.getString("id"));
                historyContent.setClassName(doc.getString("className"));
                historyContent.setCreatedDate(LocalDateTime.parse(doc.getString("createdDate")));
                historyContent.setActor(doc.getString("actor"));
                historyContent.setMethodName(doc.getString("methodName"));

                Document objectDoc = doc.get("object", Document.class);
                if (objectDoc != null) {
                    historyContent.setObject(objectDoc);
                }
                historyContent.setStatus(HistoryContent.Status.valueOf(doc.getString("status")));
                return historyContent;
            }
        }catch (Exception e){
            logger.error("Error in find history content: {}", e.getMessage());
        }
        return null;
    }

    public void updateHistoryContent(HistoryContent historyContent) {
        try {
            Document updatedDoc = new Document("className", historyContent.getClassName())
                    .append("createdDate", historyContent.getCreatedDate().toString())
                    .append("actor", historyContent.getActor())
                    .append("methodName", historyContent.getMethodName())
                    .append("object", historyContent.getObject())
                    .append("status", historyContent.getStatus().name());

            String collectionName = Constants.historyContentCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            collection.updateOne(Filters.eq("id", historyContent.getId()), new Document("$set", updatedDoc));
        }catch (Exception e){
            logger.error("Error in update data: {}", e.getMessage());
        }
    }

    public void deleteHistoryContent(String id) {
        try {
            String collectionName = Constants.historyContentCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            collection.deleteOne(Filters.eq("id", id));
        }catch (Exception e){
            logger.error("Error in delete data: {}", e.getMessage());
        }
    }
}
