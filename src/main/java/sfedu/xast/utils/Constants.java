package sfedu.xast.utils;

public class Constants {
    public static final String DataBaseUrlMongo = "mongodb://localhost:27017";
    public static final String ValidFileName = "src/test/resources/test.txt";

    /**
     * sql requests for DB
     */
    //persInf table
    public static String insertPersInf = "INSERT INTO persInf (id, surname, name, phoneNumber, email) VALUES (?,?,?,?,?)";
    public static String readPersInf = "SELECT * FROM persInf WHERE id = ?";
    public static String updatePersInf = "UPDATE persInf SET surname = ?, name = ?, phoneNumber = ?, email = ? WHERE id = ?";
    public static String deletePersInf = "DELETE FROM persInf WHERE id = ?";
    //profInf table
    public static String insertProfInf = "INSERT INTO profInf (persId, skillName, skillDescription, cost, persDescription, exp, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static String readProfInf = "SELECT * FROM profInf WHERE persId = ?";
    public static String updateProfInf = "UPDATE profInf SET skillName = ?, skillDescription = ?, cost = ?, persDescription = ?, exp = ?, rating = ? WHERE persId = ?";
    public static String deleteProfInf = "DELETE FROM profInf WHERE persId = ?";
    public static String readProfInfBySkillName = "SELECT ps.id, ps.surname, ps.name, ps.phonenumber, ps.email,\n" +
            "\tpf.skillname, pf.skilldescription, pf.cost, pf.persdescription,\n" +
            "\tpf.exp, pf.rating FROM persinf ps JOIN profinf pf ON\n" +
            "\tps.id = pf.persid WHERE skillName LIKE ?";
    public static String updateProfInfRating = "UPDATE profInf SET rating = ? WHERE persId = ?";
    //skillExchange
    public static String insertSkillExchange = "INSERT INTO skillExchange (exchangeId, skillOffered, userOffering, userRequesting) VALUES (?,?,?,?)";
    public static String readSkillExchange = "SELECT * FROM skillExchange WHERE exchangeId = ?";
    public static String updateSkillExchange = "UPDATE skillExchange SET skillOffered = ?, userOffering = ?, userRequesting = ? WHERE exchangeId = ?";
    public static String deleteSkillExchange = "DELETE FROM skillExchange WHERE exchangeId = ?";
    //review
    public static String insertReview = "INSERT INTO review (reviewId, rating, comment, reviewer, userEvaluated) VALUES (?,?,?,?,?)";
    public static String readReview = "SELECT * FROM review WHERE reviewId = ?";
    public static String updateReview = "UPDATE review SET rating = ?, comment = ?, reviewer = ?, userEvaluated = ? WHERE reviewId = ?";
    public static String deleteReview = "DELETE FROM review WHERE reviewId = ?";
    //transactionn
    public static String insertTransaction = "INSERT INTO transaction(transactionId, date, status, changeId) VALUES (?,?,?,?)";
    public static String readTransaction = "SELECT * FROM transaction WHERE transactionId = ?";
    public static String updateTransaction = "UPDATE transaction SET date = ?, status = ?, changeId = ? WHERE transactionId = ?";
    public static String deleteTransaction = "DELETE FROM transaction WHERE transactionId = ?";

    /**
     * filePath's to csv files
     */
    public static String csvPersInfFilePath = "src/main/resources/csvFiles/persInf.csv";
    public static String csvProfInfFilePath = "src/main/resources/csvFiles/profInf.csv";
    public static String csvSkillExchangeFilePath = "src/main/resources/csvFiles/skillExchange.csv";
    public static String csvReviewFilePath = "src/main/resources/csvFiles/review.csv";
    public static String csvTransactionFilePath = "src/main/resources/csvFiles/transaction.csv";
    /**
     * MongoDB Collections
     */
    public static String persInfCollection = "PersInf_Collection";
    public static String profInfCollection = "ProfInf_Collection";
    public static String skillExchangeCollection = "SkillExchange_Collection";
    public static String reviewCollection = "Review_Collection";
    public static String transactionCollection = "Transaction_Collection";
    public static String historyContentCollection = "historyContent";
    /**
     * filePath's to csv filess
     */
    public static String xmlPersInfFilePath = "src/main/resources/xmlFiles/persInf.xml";
    public static String xmlProfInfFilePath = "src/main/resources/xmlFiles/profInf.xml";
    public static String xmlSkillExchangeFilePath = "src/main/resources/xmlFiles/skillExchange.xml";
    public static String xmlReviewFilePath = "src/main/resources/xmlFiles/review.xml";
    public static String xmlTransactionFilePath = "src/main/resources/xmlFiles/transaction.xml";

    public static String LAB1_HBN_CFG = "src/main/resources/lab1_hbn_cfg.xml";
    public static String HBN_CFG = "src/main/resources/hibernate.cfg.xml";
}

