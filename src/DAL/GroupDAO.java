package DAL;

import BE.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT * FROM Teams";
            try (PreparedStatement pstmt = con.prepareStatement(sql);
                 ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    groups.add(new Group(name));
                }
                return groups;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGroup(int id) {
        try (Connection con = dbConnector.getConn()) {
            String sql = "DELETE FROM Teams WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
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
                pstmt.setString(1,group.getName());
                pstmt.setInt(2,group.getId());
                pstmt.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
