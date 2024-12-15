package sfedu.xast.dao;

//TODO MUST CREATE TABLE profInf!!!

import sfedu.xast.models.ProfInf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfInfDao {
    private Connection connection;

    public ProfInfDao(Connection connection) {
        this.connection = connection;
    }

    //ASSIGN id of persInf with profInf id!!!
    public void create(ProfInf profInf) throws SQLException {
        String sql = "INSERT INTO profInf(skillName, skillDescription, cost, persDecsription, exp, rating) VALUES (?,?,?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, profInf.getSkillName());
            ps.setString(2, profInf.getSkillDescription());
            ps.setDouble(3, profInf.getCost());
            ps.setString(4, profInf.getPersDescription());
            ps.setDouble(5, profInf.getRating());
            ps.setDouble(6, profInf.getRating());
            ps.executeUpdate();
        }
    }

    //CHANGE TYPES OF ID
    public ProfInf read(Long id) throws SQLException {
        String sql = "SELECT * FROM profInf WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return new ProfInf(
                            rs.getString("skillName"),
                            rs.getString("skillDescription"),
                            rs.getDouble("cost"),
                            rs.getString("persDescription"),
                            rs.getInt("exp"),
                            rs.getInt("rating"));
                }
            }
            return null;
        }
    }

    //TYPE OF ID
    public void update(ProfInf profInf) throws SQLException {
        String sql = "UPDATE profInf SET skillName = ?, skillDescription = ?, cost = ?, persDescription = ?, exp = ?, rating = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, profInf.getSkillName());
            ps.setString(2, profInf.getSkillDescription());
            ps.setDouble(3, profInf.getCost());
            ps.setString(4, profInf.getPersDescription());
            ps.setDouble(5, profInf.getRating());
            ps.setDouble(6, profInf.getRating());
        }
    }
}
