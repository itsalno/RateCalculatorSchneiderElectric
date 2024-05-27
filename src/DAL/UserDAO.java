package DAL;

import BE.Group;
import BE.User;
import Exceptions.RateCalcException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAO implements IUserDAO{
    
    @Override
    public List<User> getAllUsers() throws RateCalcException{
        
            List<User> users = new LinkedList<>();

            try (Connection conn = dbConnector.getConn()) {
                String sql = "SELECT * FROM Users";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String username = rs.getString("username");
                            String password = rs.getString("password");

                            User user = new User(username, password);
                            users.add(user);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RateCalcException("Problems with the database or database connection",e);
            }

            return users;
        }
}

