package sfedu.xast.api;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import sfedu.xast.models.HistoryContent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.management.Query.eq;


public class DataProviderMongo {
    private final String url = "mongodb://localhost:27017";
    private final String dbName = "test";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DataProviderMongo() {
        mongoClient = MongoClients.create(url);
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection("historyContent");
    }

    public void create(HistoryContent historyContent) {
        Document doc = new Document("className", historyContent.getClassName())
                .append("createdDate", historyContent.getCreatedDate())
                .append("actor", historyContent.getActor())
                .append("methodName", historyContent.getMethodName())
                .append("object", historyContent.getObject())
                .append("status", historyContent.getStatus());
        collection.insertOne(doc);
    }

    public List<HistoryContent> findAll() {
        List<HistoryContent> res = new ArrayList<>();
        for (Document doc : collection.find()) {
            HistoryContent historyContent = new HistoryContent();
            historyContent.setId(doc.getObjectId("_id").toString());
            historyContent.setClassName(doc.getString("className"));
            historyContent.setCreatedDate(doc.getString("createdDate"));
            historyContent.setActor(doc.getString("actor"));
            historyContent.setMethodName(doc.getString("methodName"));
            historyContent.setObject((Map<String, Object>) doc.get("object"));
            //historyContent.setStatus(HistoryContent.Status.valueOf(doc.getString("status")));
            historyContent.setStatus(doc.getString("status"));
            res.add(historyContent);
        }
        return res;
    }

    /*public HistoryContent findById(String id) {
        Document doc = collection.find(eq("_id", new org.bson.types.ObjectId(id))).first();
        if (doc != null) {
            HistoryContent historyContent = new HistoryContent();
            historyContent.setId(doc.getObjectId("_id").toString());
            historyContent.setClassName(doc.getString("className"));
            historyContent.setCreatedDate(doc.getString("createdDate"));
            historyContent.setActor(doc.getString("actor"));
            historyContent.setMethodName(doc.getString("methodName"));
            historyContent.setObject((Map<String, Object>) doc.get("object"));
            historyContent.setStatus(HistoryContent.Status.valueOf(doc.getString("status")));
            return historyContent;
        }
        return null;
    }*/

    /*public void update(String id, HistoryContent historyContent) {
        Document updatedDoc = new Document("className", historyContent.getClassName())
                .append("createdDate", historyContent.getCreatedDate())
                .append("actor", historyContent.getActor())
                .append("methodName", historyContent.getMethodName())
                .append("object", historyContent.getObject())
                .append("status", historyContent.getStatus().toString());

        collection.updateOne(eq("_id", new org.bson.types.ObjectId(id)), new Document("$set", updatedDoc));
    }*/

    /*public void delete(String id) {
        collection.deleteOne(eq("_id", new org.bson.types.ObjectId(id)));
    }*/

    public void close() {
        mongoClient.close();
    }

    public static void main(String[] args) {


    }
}
