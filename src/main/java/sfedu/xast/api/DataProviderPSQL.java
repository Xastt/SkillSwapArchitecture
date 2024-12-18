package sfedu.xast.api;

import sfedu.xast.Status;
import sfedu.xast.models.*;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DataProviderPSQL {

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
    public String createPersInf(PersInf persInf) throws SQLException {
        String sqlPersInf = "INSERT INTO persInf (id, surname, name, phoneNumber, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlPersInf, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, persInf.getId());
            ps.setString(2, persInf.getSurname());
            ps.setString(3, persInf.getName());
            ps.setString(4, persInf.getPhoneNumber());
            ps.setString(5, persInf.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getString(1);
                }else{
                    throw new SQLException("Could not get generated key");
                }
            }
        }
    }

    /**
     * reading records from table persInf using personal id
     * @param id
     * @return PersInf object
     * @throws SQLException
     */
    public PersInf readPersInf(String id) throws SQLException {
        String sql = "SELECT * FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PersInf(
                            rs.getString("surname"),
                            rs.getString("name"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"));
                }else{
                    throw new SQLException("Could not get personal information");
                }
            }
        }
    }

    /**
     * updating records in table persInf by personal id
     * @param persInf
     * @throws SQLException
     */
    public void updatePersInf(PersInf persInf) throws SQLException {
        String sql = "UPDATE persInf SET surname = ?, name = ?, phoneNumber = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persInf.getSurname());
            ps.setString(2, persInf.getName());
            ps.setString(3, persInf.getPhoneNumber());
            ps.setString(4, persInf.getEmail());
            ps.setString(5, persInf.getId());
            ps.executeUpdate();
        }
    }

    /**
     * deleting records from table persInf by id
     * @param id
     * @throws SQLException
     */
    public void deletePersInf(String id) throws SQLException {
        String sql = "DELETE FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * getting all records from table persInf
     * @return list of users in table persInf
     * @throws SQLException
     */
    public List<PersInf> readAllPersInf() throws SQLException {
        List<PersInf> users = new ArrayList<>();
        String sql = "SELECT * FROM persInf";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                users.add(new PersInf(
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                ));
            }
        }
        return users;
    }

    /**
     * creating record in table profInf
     * @param profInf
     * @param persInf
     * @throws SQLException
     */
    public void createProfInf(ProfInf profInf, PersInf persInf) throws SQLException {
        String insertProfSql = "INSERT INTO profInf (pers_id, skill_name, skill_description, cost, pers_description, exp, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement profStmt = connection.prepareStatement(insertProfSql)) {
            profStmt.setString(1, persInf.getId());
            profStmt.setString(2, profInf.getSkillName());
            profStmt.setString(3, profInf.getSkillDescription());
            profStmt.setDouble(4, profInf.getCost());
            profStmt.setString(5, profInf.getPersDescription());
            profStmt.setDouble(6, profInf.getExp());
            profStmt.setDouble(7, profInf.getRating());
            profStmt.executeUpdate();
        }
    }

    /**
     * reading records from table profInf using personal id
     * @param id
     * @return record
     * @throws SQLException
     */
    public ProfInf readProfInf(String id) throws SQLException {
        String sql = "SELECT * FROM profInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProfInf(
                            rs.getString("id"),
                            rs.getString("skillName"),
                            rs.getString("skillDescription"),
                            rs.getDouble("cost"),
                            rs.getString("persDescription"),
                            rs.getDouble("exp"),
                            rs.getDouble("rating"));
                }else{
                    throw new SQLException("Could not get personal information");
                }
            }
        }
    }

    /**
     * updating records in table profInf by personal id
     * @param profInf
     * @throws SQLException
     */
    public void updateProfInf(ProfInf profInf) throws SQLException {
        String sql = "UPDATE profInf SET skillName = ?, skillDescription = ?, cost = ?, persDescription = ?, exp = ?, rating = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, profInf.getSkillName());
            ps.setString(2, profInf.getSkillDescription());
            ps.setDouble(3, profInf.getCost());
            ps.setString(4, profInf.getPersDescription());
            ps.setDouble(5, profInf.getExp());
            ps.setDouble(6, profInf.getRating());
            ps.setString(7, profInf.getPersId());
        }
    }

    /**
     * deleting records from table profInf by id
     * @param id
     * @throws SQLException
     */
    public void deleteProfInf(String id) throws SQLException {
        String sql = "DELETE FROM profInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * getting all records from table profInf
     * @return list of users in table profInf
     * @throws SQLException
     */
    public List<ProfInf> readAllProfInf() throws SQLException {
        List<ProfInf> users = new ArrayList<>();
        String sql = "SELECT * FROM profInf";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new ProfInf(
                        rs.getString("id"),
                        rs.getString("skillName"),
                        rs.getString("skillDescription"),
                        rs.getDouble("cost"),
                        rs.getString("persDescription"),
                        rs.getDouble("exp"),
                        rs.getDouble("rating")
                ));
            }
        }
        return users;
    }

    /**
     * method, which add new data to the table skillExchange
     * contains information about users and offering skill
     * @param skillExchange
     * @param profInf
     * @param persInf
     * @throws SQLException
     */
    public void createSkillExchange(SkillExchange skillExchange, ProfInf profInf, PersInf persInf1, PersInf persInf2) throws SQLException {
        String sqlSkillExchange = "INSERT INTO skillExchange (exchangeId, skillOffered, userOffering, userRequesting) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlSkillExchange)) {
            ps.setString(1, skillExchange.getExchangeId());
            ps.setString(2, profInf.getSkillName());
            ps.setString(3, persInf1.getId());
            ps.setString(4, persInf2.getId());
            ps.executeUpdate();
        }
    }

    /**
     * reading records from table skillExchange using echangeId
     * @param exchangeId
     * @return SkillExchange object
     * @throws SQLException
     */
    public SkillExchange readSkillExchange(String exchangeId) throws SQLException {
        String sql = "SELECT * FROM skillExchange WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, exchangeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SkillExchange(
                            rs.getString("skillOffered"),
                            rs.getString("userOffering"),
                            rs.getString("userRequesting")
                    );
                }else{
                    throw new SQLException("Couldn't find exchange with id: " + exchangeId);
                }
            }
        }
    }

    /**
     * updating records in table skillExchange using echangeId
     * @param skillExchange
     * @throws SQLException
     */
    public void updateSkillExchange(SkillExchange skillExchange) throws SQLException {
        String sql = "UPDATE skillExchange SET skillOffered = ?, userOffering = ?, userRequesting = ? WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, skillExchange.getSkillOffered());
            ps.setString(2, skillExchange.getUserOffering());
            ps.setString(3, skillExchange.getUserRequesting());
            ps.setString(4, skillExchange.getExchangeId());
            ps.executeUpdate();
        }
    }

    /**
     * deleting records from table skillExchange by exchangeId
     * @param exchangeId
     * @throws SQLException
     */
    public void deleteSkillExchange(String exchangeId) throws SQLException {
        String sql = "DELETE FROM skillExchange WHERE exchangeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, exchangeId);
            ps.executeUpdate();
        }
    }

    /**
     * getting all records from table skillExchange
     * @return records from table skillExchange
     * @throws SQLException
     */
    public List<SkillExchange> readAllSkillExchange() throws SQLException {
        List<SkillExchange> exchanges = new ArrayList<>();
        String sql = "SELECT * FROM skillExchange";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exchanges.add(new SkillExchange(
                            rs.getString("skillOffered"),
                            rs.getString("userOffering"),
                            rs.getString("userRequesting"))
                    );
                }
            }
        }
        return exchanges;
    }

    /**
     * method, which add new data to the table review
     * contains user review about provided skill
     * @param review
     * @param persInf1
     * @param persInf2
     * @param profInf
     * @throws SQLException
     */
    public void createReview(Review review, PersInf persInf1, PersInf persInf2, ProfInf profInf) throws SQLException {
        String sqlReview = "INSERT INTO review (reviewId, rating, comment, reviewer, userEvaluated, skill) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlReview)) {
            ps.setString(1, review.getReviewId());
            ps.setDouble(2, review.getRating());
            ps.setString(3, review.getComment());
            ps.setString(4, persInf1.getId());
            ps.setString(5, persInf2.getId());
            ps.setString(6, profInf.getSkillName());
            ps.executeUpdate();
        }
    }

    /**
     * reading records from table review using reviewId
     * @param reviewId
     * @return new Review object
     * @throws SQLException
     */
    public Review readReview(String reviewId) throws SQLException {
        String sql = "SELECT * FROM review WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reviewId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Review(
                            rs.getDouble("rating"),
                            rs.getString("comment"),
                            rs.getString("reviewer"),
                            rs.getString("userEvaluated"),
                            rs.getString("skill")
                    );
                }else {
                    throw new SQLException("Couldn't find review with id: " + reviewId);
                }
            }
        }
    }

    /**
     * updating records in table review using reviewId
     * @param review
     * @throws SQLException
     */
    public void updateReview(Review review) throws SQLException {
        String sql = "UPDATE review SET raiting = ?, comment = ?, reviewer = ?, userEvaluated = ?, skill = ? WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setString(3, review.getReviewer());
            ps.setString(4, review.getUserEvaluated());
            ps.setString(5, review.getSkill());
            ps.setString(6, review.getReviewId());
            ps.executeUpdate();
        }
    }

    /**
     * deleting records from table review by reviewId
     * @param reviewId
     * @throws SQLException
     */
    public void deleteReview(String reviewId) throws SQLException {
        String sql = "DELETE FROM review WHERE reviewId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reviewId);
            ps.executeUpdate();
        }
    }

    /**
     * getting all records from table review
     * @return records from table review
     * @throws SQLException
     */
    public List<Review> readAllReview() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(new Review(rs.getDouble("rating"),
                            rs.getString("comment"),
                            rs.getString("reviewer"),
                            rs.getString("userEvaluated"),
                            rs.getString("skill"))
                    );
                }
            }
        }
        return reviews;
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

    /**
     * getting all records from table transaction
     * @return Transaction object
     * @throws SQLException
     */
    public List<Transaction> readAllTransaction() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String statusString = rs.getString("status");
                    Status status = Status.valueOf(statusString);
                    transactions.add(new Transaction(
                            rs.getDate("date"),
                            status,
                            rs.getString("user1"),
                            rs.getString("user2"),
                            rs.getString("skillOffered")
                    ));
                }
            }
        }
        return transactions;
    }
}
