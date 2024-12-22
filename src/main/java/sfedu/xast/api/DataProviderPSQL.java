package sfedu.xast.api;

import org.slf4j.*;
import sfedu.xast.utils.Status;
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
     * @throws SQLException
     */
    public boolean createPersInf(PersInf persInf) {
        String sqlPersInf = "INSERT INTO persInf (id, surname, name, phoneNumber, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlPersInf)) {
            ps.setString(1, persInf.getId());
            ps.setString(2, persInf.getSurname());
            ps.setString(3, persInf.getName());
            ps.setString(4, persInf.getPhoneNumber());
            ps.setString(5, persInf.getEmail());
            int affectedRows = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
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
            logger.error(e.getMessage());
        }
        return persInf;
    }

    /**
     * updating records in table persInf by personal id
     * @param persInf
     * @throws SQLException
     */
    public boolean updatePersInf(PersInf persInf) throws SQLException {
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
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * deleting records from table persInf by id
     * @param id
     * @throws SQLException
     */
    public boolean deletePersInf(String id) {
        String sql = "DELETE FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * creating record in table profInf
     * @param profInf
     * @param persInf
     * @throws SQLException
     */
    public boolean createProfInf(ProfInf profInf, PersInf persInf) {
        String insertProfSql = "INSERT INTO profInf (persId, skillName, skillDescription, cost, persDescription, exp, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement profStmt = connection.prepareStatement(insertProfSql)) {
            profStmt.setString(1, persInf.getId());
            profStmt.setString(2, profInf.getSkillName());
            profStmt.setString(3, profInf.getSkillDescription());
            profStmt.setDouble(4, profInf.getCost());
            profStmt.setString(5, profInf.getPersDescription());
            profStmt.setDouble(6, profInf.getExp());
            profStmt.setDouble(7, profInf.getRating());
            int affectedRows = profStmt.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from table profInf using personal id
     * @param profInf
     * @param id
     * @return ProfInf object
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
            logger.error(e.getMessage());
        }
        return profInf;
    }

    /**
     * updating records in table profInf by personal id
     * @param profInf
     * @throws SQLException
     */
    public boolean updateProfInf(ProfInf profInf) throws SQLException {
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
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * deleting records from table profInf by id
     * @param id
     * @throws SQLException
     */
    public boolean deleteProfInf(String id) {
        String sql = "DELETE FROM profInf WHERE persId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<ProfInf> readProfInfBySkillName(String skillPart) throws SQLException {
        String sql = "SELECT * FROM profInf WHERE skillName LIKE ?";
        List<ProfInf> profInfList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + skillPart + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProfInf profInf = new ProfInf();
                profInf.setSkillName(rs.getString("skillName"));
                profInf.setSkillDescription(rs.getString("skillDescription"));
                profInf.setCost(rs.getDouble("cost"));
                profInf.setPersDescription(rs.getString("persDescription"));
                profInf.setExp(rs.getDouble("exp"));
                profInf.setRating(rs.getDouble("rating"));
                profInfList.add(profInf);
            }

            if (profInfList.isEmpty()) {
                throw new SQLException("Can't find persons with skill name containing: " + skillPart);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return profInfList;
    }

    public void printProfInfList(List<ProfInf> profInfList) {
        if (profInfList == null || profInfList.isEmpty()) {
            System.out.println("Список профилей пуст.");
            return;
        }

        for (ProfInf profInf : profInfList) {
            System.out.println("Навык: " + profInf.getSkillName());
            System.out.println("Описание навыка: " + profInf.getSkillDescription());
            System.out.println("Стоимость: " + profInf.getCost());
            System.out.println("Описание пользователя: " + profInf.getPersDescription());
            System.out.println("Опыт: " + profInf.getExp());
            System.out.println("Рейтинг: " + profInf.getRating());
            System.out.println("-----------------------------"); // Разделитель для удобства
        }
    }


    /**
     * method, which add new data to the table skillExchange
     * contains information about users and offering skill
     * @param skillExchange
     * @throws SQLException
     */
    public boolean createSkillExchange(SkillExchange skillExchange) {
        String sqlSkillExchange = "INSERT INTO skillExchange (exchangeId, skillOffered, userOffering, userRequesting) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlSkillExchange)) {
            ps.setString(1, skillExchange.getExchangeId());
            ps.setString(2, skillExchange.getSkillOffered());
            ps.setString(3, skillExchange.getUserOffering());
            ps.setString(4, skillExchange.getUserRequesting());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from table skillExchange using echangeId
     * @param skillExchange
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
            logger.error(e.getMessage());
        }
        return skillExchange;
    }

    /**
     * updating records in table skillExchange using echangeId
     * @param skillExchange
     * @throws SQLException
     */
    public boolean updateSkillExchange(SkillExchange skillExchange) throws SQLException {
        if (skillExchange == null) {
            throw new SQLException("SkillExchange object must not be null");
        }
        String sql = "UPDATE skillExchange SET skillOffered = ?, userOffering = ?, userRequesting = ? WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillExchange.getSkillOffered());
            ps.setString(2, skillExchange.getUserOffering());
            ps.setString(3, skillExchange.getUserRequesting());
            ps.setString(4, skillExchange.getExchangeId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * deleting records from table skillExchange by exchangeId
     * @param exchangeId
     * @throws SQLException
     */
    public boolean deleteSkillExchange(String exchangeId){
        String sql = "DELETE FROM skillExchange WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, exchangeId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * method, which add new data to the table review
     * contains user review about provided skill
     * @param review
     * @throws SQLException
     */
    public boolean createReview(Review review) {
        String sqlReview = "INSERT INTO review (reviewId, rating, comment, reviewer, userEvaluated) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlReview)) {
            ps.setString(1, review.getReviewId());
            ps.setDouble(2, review.getRating());
            ps.setString(3, review.getComment());
            ps.setString(4, review.getReviewer());
            ps.setString(5, review.getUserEvaluated());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from table review using reviewId
     * @param review
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
            logger.error(e.getMessage());
        }
        return review;
    }

    /**
     * updating records in table review using reviewId
     * @param review
     * @throws SQLException
     */
    public boolean updateReview(Review review) throws SQLException {
        String sql = "UPDATE review SET rating = ?, comment = ?, reviewer = ?, userEvaluated = ? WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setString(3, review.getReviewer());
            ps.setString(4, review.getUserEvaluated());
            ps.setString(5, review.getReviewId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * deleting records from table review by reviewId
     * @param review
     * @throws SQLException
     */
    public boolean deleteReview(Review review) throws SQLException {
        String sql = "DELETE FROM review WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, review.getReviewId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * contains information abount skill exchange between usersa
     * @param transaction
     * @throws SQLException
     */
    public boolean createTransaction(Transaction transaction) throws SQLException {
        String sqlTransaction = "INSERT INTO transaction(transactionId, date, status, changeId) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlTransaction)) {
            ps.setString(1, transaction.getTransactionId());
            ps.setDate(2,  new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(3, String.valueOf(transaction.getStatus()));
            ps.setString(4, transaction.getChangeId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
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
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(transaction != null){
                        String statusString = rs.getString("status");
                        Status status = Status.valueOf(statusString);
                        transaction.setDate(rs.getDate("date"));
                        transaction.setStatus(status);
                        transaction.setChangeId(rs.getString("changeId"));
                    }else{
                        throw new SQLException("Transaction object must not be null");
                    }
                }else{
                    throw new SQLException("Couldn't find transaction with id: " + transaction.getTransactionId());
                }
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return transaction;
    }

    /**
     *  updating records in table transaction using transactionId
     * @param transaction
     * @throws SQLException
     */
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transaction SET date = ?, status = ?, changeId = ? WHERE transactionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(2, String.valueOf(transaction.getStatus()));
            ps.setString(3, transaction.getChangeId());
            ps.setString(4, transaction.getTransactionId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * deleting records from table transaction by transactionId
     * @param transaction
     * @throws SQLException
     */
    public boolean deleteTransaction(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM transaction WHERE transactionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, transaction.getTransactionId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
