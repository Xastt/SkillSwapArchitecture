package sfedu.xast.utils;

public class Constants {
    public final String APP_NAME = "SkillSwap";
    public static final String CONFIG_PROPERTIES = "src/main/resources/constantProperties.properties";
    public static final String CONFIG_YML = "src/main/resources/constantsYAML.yaml";
    public static final String CONFIG_XML = "src/main/resources/constantsXML.xml";
    public static final String DataBaseUrl = "mongodb://localhost:27017";
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

    public static String csvPersInfFilePath = "src/test/resources/test.csv";
    public static String csvPersInfTestFilePath = "src/test/resources/csvFilesTest/persInfTest.csv";
}

