package sfedu.xast.api;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.*;
import sfedu.xast.models.*;
import sfedu.xast.utils.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
     * @return true/false
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

    /**
     * creating record in SkillExchange_Collection in MongoDB
     * @param skillExchange
     * @return true or false
     */
    public boolean createSkillExchange(SkillExchange skillExchange) {
        if (skillExchange == null) {
            return false;
        }
        String collectionName = Constants.skillExchangeCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        Document doc = new Document("id", skillExchange.getExchangeId())
                .append("SkillOffered", skillExchange.getSkillOffered())
                .append("UserOffering", skillExchange.getUserOffering())
                .append("UserRequesting", skillExchange.getUserRequesting());

        collection.insertOne(doc);
        return true;
    }

    /**
     * reading records in SkillExchange_Collection in MongoDB using id
     * @param skillExchange
     * @return PersINf object
     */
    public SkillExchange readSkillExchange(SkillExchange skillExchange) {
        if (skillExchange == null) {
            throw new MongoException("SkillExchange object must not be null");
        }
        try {
            String collectionName = Constants.skillExchangeCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", skillExchange.getExchangeId())).first();
            if (doc != null) {
                skillExchange.setExchangeId(doc.getString("id"));
                skillExchange.setSkillOffered(doc.getString("SkillOffered"));
                skillExchange.setUserOffering(doc.getString("UserOffering"));
                skillExchange.setUserRequesting(doc.getString("UserRequesting"));
                return skillExchange;
            } else {
                throw new MongoException("Can't find exchange with id " + skillExchange.getExchangeId());
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in SkillExchange_Collection in MongoDB by personal id
     * @param skillExchange
     * @return true or false
     */
    public boolean updateSkillExchange(SkillExchange skillExchange) {
        try {
            if (skillExchange == null) {
                return false;
            }
            String collectionName = Constants.skillExchangeCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = new Document("id", skillExchange.getExchangeId())
                    .append("SkillOffered", skillExchange.getSkillOffered())
                    .append("UserOffering", skillExchange.getUserOffering())
                    .append("UserRequesting", skillExchange.getUserRequesting());
            collection.updateOne(Filters.eq("id", skillExchange.getExchangeId()), new Document("$set", doc));
            return true;
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete records in SkillExchange_Collection in MongoDB using id
     * @param id
     * @return
     */
    public boolean deleteSkillExchange(String id) {
        if(id == null){
            return false;
        }
        String collectionName = Constants.skillExchangeCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.deleteOne(Filters.eq("id", id));
        return true;
    }

    /**
     * creating record in Review_Collection in MongoDB
     * @param review
     * @return true or false
     */
    public boolean createReview(Review review) {
        if (review == null) {
            return false;
        }
        String collectionName = Constants.reviewCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        Document doc = new Document("id", review.getReviewId())
                .append("Rating", review.getRating())
                .append("Comment", review.getComment())
                .append("Reviewer", review.getReviewer())
                .append("UserEvaluated", review.getUserEvaluated());

        collection.insertOne(doc);
        return true;
    }

    /**
     * reading records in Review_Collection in MongoDB using id
     * @param review
     * @return Review object
     */
    public Review readReview(Review review) {
        if (review == null) {
            throw new MongoException("Review object must not be null");
        }
        try {
            String collectionName = Constants.reviewCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", review.getReviewId())).first();
            if (doc != null) {
                review.setReviewId(doc.getString("id"));
                review.setRating(doc.getDouble("Rating"));
                review.setComment(doc.getString("Comment"));
                review.setReviewer(doc.getString("Reviewer"));
                review.setUserEvaluated(doc.getString("UserEvaluated"));
                return review;
            } else {
                throw new MongoException("Can't find review with id " + review.getReviewId());
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in Review_Collection in MongoDB by personal id
     * @param review
     * @return true or false
     */
    public boolean updateReview(Review review) {
        try {
            if (review == null) {
                return false;
            }
            String collectionName = Constants.reviewCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = new Document("id", review.getReviewId())
                    .append("Rating", review.getRating())
                    .append("Comment", review.getComment())
                    .append("Reviewer", review.getReviewer())
                    .append("UserEvaluated", review.getUserEvaluated());
            collection.updateOne(Filters.eq("id", review.getReviewId()), new Document("$set", doc));
            return true;
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete records in Review_Collection in MongoDB using id
     * @param review
     * @return
     */
    public boolean deleteReview(Review review) {
        if(review == null){
            return false;
        }
        String collectionName = Constants.reviewCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.deleteOne(Filters.eq("id", review.getReviewId()));
        return true;
    }

    /**
     * creating record in Transaction_Collection in MongoDB
     * @param transaction
     * @return true or false
     */
    public boolean createTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        String collectionName = Constants.transactionCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        Document doc = new Document("id", transaction.getTransactionId())
                .append("Date", transaction.getDate())
                .append("Status", transaction.getStatus().name())
                .append("ChangeId", transaction.getChangeId());

        collection.insertOne(doc);
        return true;
    }

    /**
     * reading records in Transaction_Collection in MongoDB using id
     * @param transaction
     * @return Review object
     */
    public Transaction readTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new MongoException("Transaction object must not be null");
        }
        try {
            String collectionName = Constants.transactionCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = collection.find(Filters.eq("id", transaction.getTransactionId())).first();
            if (doc != null) {
                transaction.setTransactionId(doc.getString("id"));
                transaction.setDate(doc.getDate("Date"));
                transaction.setStatus(Status.valueOf(doc.getString("Status")));
                transaction.setChangeId(doc.getString("ChangeId"));
                return transaction;
            } else {
                throw new MongoException("Can't find transaction with id " + transaction.getTransactionId());
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in Transaction_Collection in MongoDB by id
     * @param transaction
     * @return true or false
     */
    public boolean updateTransaction(Transaction transaction) {
        try {
            if (transaction == null) {
                return false;
            }
            String collectionName = Constants.transactionCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
            Document doc = new Document("id", transaction.getTransactionId())
                    .append("Date", transaction.getDate())
                    .append("Status", transaction.getStatus().name())
                    .append("ChangeId", transaction.getChangeId());
            collection.updateOne(Filters.eq("id", transaction.getTransactionId()), new Document("$set", doc));
            return true;
        }catch (MongoException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete records in Transaction_Collection in MongoDB using id
     * @param transaction
     * @return
     */
    public boolean deleteTransaction(Transaction transaction) {
        if(transaction == null){
            return false;
        }
        String collectionName = Constants.transactionCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        collection.deleteOne(Filters.eq("id",transaction.getTransactionId()));
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

    public List<SkillOut> readProfInfBySkillName(String skillPart) throws IOException {
        List<SkillOut> profInfList = new ArrayList<>();

        String collectionName = Constants.profInfCollection;
        MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

        for (Document doc : collection.find(Filters.regex("SkillName", skillPart, "i"))) {
            SkillOut skillOut = new SkillOut();

            skillOut.setId(doc.getString("id"));
            skillOut.setSkillName(doc.getString("SkillName"));
            skillOut.setSkillDescription(doc.getString("SkillDescription"));
            skillOut.setCost(doc.getDouble("Cost"));
            skillOut.setPersDescription(doc.getString("PersDescription"));
            skillOut.setExp(doc.getDouble("Exp"));
            skillOut.setRating(doc.getDouble("Rating"));

            profInfList.add(skillOut);
        }

        if (profInfList.isEmpty()) {
            throw new IOException("Can't find persons with skill name containing: " + skillPart);
        }

        return profInfList;
    }

    /**
     * print persons with needed skill
     * @param skillOutList
     */
    public void printProfInfList(List<SkillOut> skillOutList) {
        if (skillOutList == null || skillOutList.isEmpty()) {
            System.out.println("Список профилей пуст.");
            return;
        }

        for (SkillOut skillOut : skillOutList) {
            System.out.println("ID: " + skillOut.getId());
            System.out.println("Навык: " + skillOut.getSkillName());
            System.out.println("Описание навыка: " + skillOut.getSkillDescription());
            System.out.println("Стоимость: " + skillOut.getCost());
            System.out.println("Описание пользователя: " + skillOut.getPersDescription());
            System.out.println("Опыт: " + skillOut.getExp());
            System.out.println("Рейтинг: " + skillOut.getRating());
            System.out.println("-----------------------------");
        }
    }

    public boolean insertRating(String persId, Double rating, Double ratingBefore) {
        if (persId == null || rating == null || ratingBefore == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Double finalRating;

        if (ratingBefore == 0.0) {
            finalRating = rating;
        } else {
            finalRating = (ratingBefore + rating) / 2.0;
        }

        try {
            String collectionName = Constants.profInfCollection;
            MongoCollection<Document> collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);

            Document updateDoc = new Document("$set", new Document("Rating", finalRating));
            UpdateResult result = collection.updateOne(Filters.eq("id", persId), updateDoc);

            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
