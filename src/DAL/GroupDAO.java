package DAL;

import BE.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements IGroupDAO {


    @Override
    public void createGroup(Group group) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "INSERT INTO Teams(name) VALUES (?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, group.getName());
                pstmt.execute();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Group> getAllGroups() {
        ObservableList<Group> groups = FXCollections.observableArrayList();
        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT * FROM Teams";
            try (PreparedStatement pstmt = con.prepareStatement(sql);
                 ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    int id = resultSet.getInt("id");
                    int multiplier = resultSet.getInt("multiplier");
                    groups.add(new Group(name, id, multiplier));
                }
                return groups;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGroup(Group group) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "DELETE FROM Teams WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, group.getId());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editGroup(Group group) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "UPDATE Teams SET name = ? WHERE id=?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, group.getName());
                pstmt.setInt(2, group.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void applyMultiplierToGroup(int multiplier, int id) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "UPDATE Teams SET multiplier = ? WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, multiplier);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Group updateGroupTable(int id) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT * FROM Teams WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet resultSet = pstmt.executeQuery();

                Group group = null;
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    int Gid = resultSet.getInt("id");
                    int multiplier = resultSet.getInt("multiplier");
                    group = new Group(name, Gid, multiplier);
                }
                return group;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
