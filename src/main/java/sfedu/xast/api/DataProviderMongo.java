package sfedu.xast.api;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sfedu.xast.models.HistoryContent;
import sfedu.xast.utils.Constants;

import java.time.LocalDateTime;

public class DataProviderMongo {

    Logger logger = LoggerFactory.getLogger(DataProviderMongo.class);

    private final MongoCollection<Document> collection;

    public DataProviderMongo(String databaseName, String collectionName) {
        MongoClient mongoClient = MongoClients.create(Constants.DataBaseUrl);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection(collectionName);
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

            collection.insertOne(doc);
        }catch(Exception e){
            logger.error("Error in insert data: {}", e.getMessage());
        }

    }

    public HistoryContent findHistoryContentById(String id) {
        try {
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

            collection.updateOne(Filters.eq("id", historyContent.getId()), new Document("$set", updatedDoc));
        }catch (Exception e){
            logger.error("Error in update data: {}", e.getMessage());
        }
    }

    public void deleteHistoryContent(String id) {
        try {
            collection.deleteOne(Filters.eq("id", id));
        }catch (Exception e){
            logger.error("Error in delete data: {}", e.getMessage());
        }
    }

}
