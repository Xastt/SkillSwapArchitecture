package sfedu.xast.dao;

import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO CREATE TABLE persInf !!!

public class PersInfDao {
    private Connection connection;

    public PersInfDao(Connection connection) {
        this.connection = connection;
    }

    public void create(PersInf persInf, ProfInf profInf) throws SQLException {

        String sqlPersInf = "INSERT INTO persInf (id, surname, name, phoneNumber, email) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlPersInf)) {
            ps.setString(1, persInf.getId());
            ps.setString(2, persInf.getSurname());
            ps.setString(3, persInf.getName());
            ps.setString(4, persInf.getPhoneNumber());
            ps.setString(5, persInf.getEmail());
            ps.executeUpdate();
        }

        String insertProfSql = "INSERT INTO profInf (pers_id, skill_name, skill_description, cost, pers_description, exp, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement profStmt = connection.prepareStatement(insertProfSql)) {
            profStmt.setString(1, profInf.getPersId());
            profStmt.setString(2, profInf.getSkillName());
            profStmt.setString(3, profInf.getSkillDescription());
            profStmt.setDouble(4, profInf.getCost());
            profStmt.setString(5, profInf.getPersDescription());
            profStmt.setDouble(6, profInf.getExp());
            profStmt.setDouble(7, profInf.getRating());
            profStmt.executeUpdate();
        }
    }

    //CHANGE TYPES OF ID
    public PersInf read(Long id) throws SQLException {
        String sql = "SELECT * FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PersInf(
                            rs.getLong("id"),
                            rs.getString("surname"),
                            rs.getString("name"),
                            rs.getString("phoneNumber"),
                            rs.getString("email"));
                }
            }
        }
        return null;
    }

    public void update(PersInf persInf) throws SQLException {
        String sql = "UPDATE persInf SET surname = ?, name = ?, phoneNumber = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persInf.getSurname());
            ps.setString(2, persInf.getName());
            ps.setString(3, persInf.getPhoneNumber());
            ps.setString(4, persInf.getEmail());
            ps.setLong(5, persInf.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM persInf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<PersInf> readAll() throws SQLException {
        List<PersInf> users = new ArrayList<>();
        String sql = "SELECT * FROM persInf";
        try (PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    users.add(new PersInf(
                            rs.getLong("id"),
                            rs.getString("surname"),
                            rs.getString("name"),
                            rs.getString("phoneNumber"),
                            rs.getString("email")
                    ));
                }
            }
        return users;
    }
}
