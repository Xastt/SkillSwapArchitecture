package sfedu.xast.api;

import org.slf4j.*;
import sfedu.xast.Status;
import sfedu.xast.models.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DataProviderPSQL {

    Logger logger = LoggerFactory.getLogger(DataProviderPSQL.class);

    private static Connection connection;

    /**
     * getting a database connection
     * @return connection with databse
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null) {
            Properties props = new Properties();
            try(InputStream input = DataProviderPSQL.class.getClassLoader().
                    getResourceAsStream("database.properties")) {
                props.load(input);
            }
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    /**
     * creating record in table persInf
     * @param persInf
     * @return personal id
     * @throws SQLException
     */
    public void createPersInf(PersInf persInf) {
        String sqlPersInf = "INSERT INTO persInf (id, surname, name, phoneNumber, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlPersInf)) {
            ps.setString(1, persInf.getId());
            ps.setString(2, persInf.getSurname());
            ps.setString(3, persInf.getName());
            ps.setString(4, persInf.getPhoneNumber());
            ps.setString(5, persInf.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * reading records from table persInf using personal id
     * @param id
     * @param persInf
     * @return PersInf object
     * @throws SQLException
     */
    public PersInf readPersInf(PersInf persInf, String id)  {
        String sql = "SELECT * FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if( persInf!=null ) {
                        persInf.setName(rs.getString("name"));
                        persInf.setSurname(rs.getString("surname"));
                        persInf.setPhoneNumber(rs.getString("phoneNumber"));
                        persInf.setEmail(rs.getString("email"));
                    }else{
                        throw new SQLException("PersInf object must not be null");
                    }
                }else{
                    throw new SQLException("Can't find person with id " + id);
                }
        }catch (SQLException e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
        return persInf;
    }

    /**
     * updating records in table persInf by personal id
     * @param persInf
     * @throws SQLException
     */
    public void updatePersInf(PersInf persInf) throws SQLException {
        if (persInf == null) {
            throw new SQLException("PersInf object must not be null");
        }
        String sql = "UPDATE persInf SET surname = ?, name = ?, phoneNumber = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persInf.getSurname());
            ps.setString(2, persInf.getName());
            ps.setString(3, persInf.getPhoneNumber());
            ps.setString(4, persInf.getEmail());
            ps.setString(5, persInf.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * deleting records from table persInf by id
     * @param id
     * @throws SQLException
     */
    public void deletePersInf(String id) {
        String sql = "DELETE FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * creating record in table profInf
     * @param profInf
     * @param persInf
     * @throws SQLException
     */
    public void createProfInf(ProfInf profInf, PersInf persInf) {
        String insertProfSql = "INSERT INTO profInf (persId, skillName, skillDescription, cost, persDescription, exp, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement profStmt = connection.prepareStatement(insertProfSql)) {
            profStmt.setString(1, persInf.getId());
            profStmt.setString(2, profInf.getSkillName());
            profStmt.setString(3, profInf.getSkillDescription());
            profStmt.setDouble(4, profInf.getCost());
            profStmt.setString(5, profInf.getPersDescription());
            profStmt.setDouble(6, profInf.getExp());
            profStmt.setDouble(7, profInf.getRating());
            profStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * reading records from table profInf using personal id
     * @param id
     * @return record
     * @throws SQLException
     */
    public ProfInf readProfInf(ProfInf profInf, String id) throws SQLException {
        String sql = "SELECT * FROM profInf WHERE persId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(profInf != null ) {
                        profInf.setSkillName(rs.getString("skillName"));
                        profInf.setSkillDescription(rs.getString("skillDescription"));
                        profInf.setCost(rs.getDouble("cost"));
                        profInf.setPersDescription(rs.getString("persDescription"));
                        profInf.setExp(rs.getDouble("exp"));
                        profInf.setRating(rs.getDouble("rating"));
                    }else{
                        throw new SQLException("PersInf object must not be null");
                    }
                }else{
                    throw new SQLException("Can't find person with id " + id);
                }
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
        return profInf;
    }

    /**
     * updating records in table profInf by personal id
     * @param profInf
     * @throws SQLException
     */
    public void updateProfInf(ProfInf profInf) throws SQLException {
        if (profInf == null) {
            throw new SQLException("Profnf object must not be null");
        }
        String sql = "UPDATE profInf SET skillName = ?, skillDescription = ?, cost = ?, persDescription = ?, exp = ?, rating = ? WHERE persId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, profInf.getSkillName());
            ps.setString(2, profInf.getSkillDescription());
            ps.setDouble(3, profInf.getCost());
            ps.setString(4, profInf.getPersDescription());
            ps.setDouble(5, profInf.getExp());
            ps.setDouble(6, profInf.getRating());
            ps.setString(7, profInf.getPersId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * deleting records from table profInf by id
     * @param id
     * @throws SQLException
     */
    public void deleteProfInf(String id) {
        String sql = "DELETE FROM profInf WHERE persId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * method, which add new data to the table skillExchange
     * contains information about users and offering skill
     * @param skillExchange
     * @param profInf
     * @param persInf1
     * @param persInf2
     * @throws SQLException
     */
    public void createSkillExchange(SkillExchange skillExchange) {
        String sqlSkillExchange = "INSERT INTO skillExchange (exchangeId, skillOffered, userOffering, userRequesting) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlSkillExchange)) {
            ps.setString(1, skillExchange.getExchangeId());
            ps.setString(2, skillExchange.getSkillOffered());
            ps.setString(3, skillExchange.getUserOffering());
            ps.setString(4, skillExchange.getUserRequesting());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * reading records from table skillExchange using echangeId
     * @param exchangeId
     * @return SkillExchange object
     * @throws SQLException
     */
    public SkillExchange readSkillExchange(SkillExchange skillExchange) {
        String sql = "SELECT * FROM skillExchange WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillExchange.getExchangeId());
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(skillExchange != null ) {
                        skillExchange.setSkillOffered(rs.getString("skillOffered"));
                        skillExchange.setUserOffering(rs.getString("userOffering"));
                        skillExchange.setUserRequesting(rs.getString("userRequesting"));
                    }else{
                        throw new SQLException("SkillExchange object must not be null");
                    }
                }else{
                    throw new SQLException("Couldn't find exchange with id: " + skillExchange.getExchangeId());
                }

        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
        return skillExchange;
    }

    /**
     * updating records in table skillExchange using echangeId
     * @param skillExchange
     * @throws SQLException
     */
    public void updateSkillExchange(SkillExchange skillExchange) throws SQLException {
        if (skillExchange == null) {
            throw new SQLException("SkillExchange object must not be null");
        }
        String sql = "UPDATE skillExchange SET skillOffered = ?, userOffering = ?, userRequesting = ? WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillExchange.getSkillOffered());
            ps.setString(2, skillExchange.getUserOffering());
            ps.setString(3, skillExchange.getUserRequesting());
            ps.setString(4, skillExchange.getExchangeId());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * deleting records from table skillExchange by exchangeId
     * @param exchangeId
     * @throws SQLException
     */
    public void deleteSkillExchange(String exchangeId){
        String sql = "DELETE FROM skillExchange WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, exchangeId);
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * method, which add new data to the table review
     * contains user review about provided skill
     * @param review
     * @throws SQLException
     */
    public void createReview(Review review) {
        String sqlReview = "INSERT INTO review (reviewId, rating, comment, reviewer, userEvaluated) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlReview)) {
            ps.setString(1, review.getReviewId());
            ps.setDouble(2, review.getRating());
            ps.setString(3, review.getComment());
            ps.setString(4, review.getReviewer());
            ps.setString(5, review.getUserEvaluated());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * reading records from table review using reviewId
     * @param reviewId
     * @return new Review object
     * @throws SQLException
     */
    public Review readReview(Review review) throws SQLException {
        String sql = "SELECT * FROM review WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, review.getReviewId());
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(review != null){
                        review.setRating(rs.getDouble("rating"));
                        review.setComment(rs.getString("comment"));
                        review.setReviewer(rs.getString("reviewer"));
                        review.setUserEvaluated(rs.getString("userEvaluated"));
                    }else{
                        throw new SQLException("Review object must not be null");
                    }
                }else {
                    throw new SQLException("Couldn't find review with id: " + review.getReviewId());
                }
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
        return review;
    }

    /**
     * updating records in table review using reviewId
     * @param review
     * @throws SQLException
     */
    public void updateReview(Review review) throws SQLException {
        String sql = "UPDATE review SET rating = ?, comment = ?, reviewer = ?, userEvaluated = ? WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setString(3, review.getReviewer());
            ps.setString(4, review.getUserEvaluated());
            ps.setString(5, review.getReviewId());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }

    /**
     * deleting records from table review by reviewId
     * @param reviewId
     * @throws SQLException
     */
    public void deleteReview(Review review) throws SQLException {
        String sql = "DELETE FROM review WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, review.getReviewId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
    }


    /**
     * contains information abount skill exchange between usersa
     * @param transaction
     * @param persInf1
     * @param persInf2
     * @param status
     * @param profInf
     * @throws SQLException
     */
    public void createTransaction(Transaction transaction, PersInf persInf1, PersInf persInf2, Status status, ProfInf profInf) throws SQLException {
        String sqlTransaction = "INSERT INTO transaction(transactionId, date, status, user1, user2, skillOffered) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlTransaction)) {
            ps.setString(1, transaction.getTransactionId());
            ps.setDate(2, new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(3, status.name());
            ps.setString(4, persInf1.getEmail());
            ps.setString(5, persInf2.getEmail());
            ps.setString(6, profInf.getSkillName());
        }
    }

    /**
     * reading records from table transaction using transactionId
     * @param transaction
     * @return new Transaction object
     * @throws SQLException
     */
    public Transaction readTransaction(Transaction transaction) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE transactionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, transaction.getTransactionId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String statusString = rs.getString("status");
                    Status status = Status.valueOf(statusString);
                    return new Transaction(
                            rs.getDate("date"),
                            status,
                            rs.getString("user1"),
                            rs.getString("user2"),
                            rs.getString("skillOffered")
                    );
                }else{
                    throw new SQLException("Couldn't find transaction with id: " + transaction.getTransactionId());
                }
            }
        }
    }

    /**
     *  updating records in table transaction using transactionId
     * @param transaction
     * @param persInf1
     * @param persInf2
     * @param status
     * @param profInf
     * @throws SQLException
     */
    public void updateTransaction(Transaction transaction, PersInf persInf1, PersInf persInf2, Status status, ProfInf profInf) throws SQLException {
        String sql = "UPDATE transaction SET date = ?, status = ?, user1 = ?, user2 = ?, skillOffered = ? WHERE transactionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(2, status.name());
            ps.setString(3, persInf1.getEmail());
            ps.setString(4, persInf2.getEmail());
            ps.setString(5, profInf.getSkillName());
            ps.setString(6, transaction.getTransactionId());
            ps.executeUpdate();
        }
    }

    /**
     * deleting records from table transaction by transactionId
     * @param transaction
     * @throws SQLException
     */
    public void deleteTransaction(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM transaction WHERE transactionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, transaction.getTransactionId());
            ps.executeUpdate();
        }
    }


}
