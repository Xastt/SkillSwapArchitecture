package sfedu.xast.dao;

import sfedu.xast.models.PersForApi;
import sfedu.xast.models.PersInf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersInfDao {
    private Connection connection;

    public PersInfDao(Connection connection) {
        this.connection = connection;
    }

    public void create(PersInf persInf) throws SQLException {
        String sql = "INSERT INTO persinf (name, email) VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persInf.getName());
            ps.setString(2, persInf.getEmail());
            ps.executeUpdate();
        }
    }

    public PersInf read(Long id) throws SQLException {
        String sql = "SELECT * FROM persinf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PersInf(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"));
                }
            }
        }
        return null;
    }

    public void update(PersInf persInf) throws SQLException {
        String sql = "UPDATE persinf SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persInf.getName());
            ps.setString(2, persInf.getEmail());
            ps.setLong(3, persInf.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM persinf WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<PersInf> readAll() throws SQLException {
        List<PersInf> users = new ArrayList<>();
        String sql = "SELECT * FROM persinf";
        try (PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    users.add(new PersInf(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email")));
                }
            }
        return users;
    }
}
