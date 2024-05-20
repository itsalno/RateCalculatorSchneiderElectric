package DAL;

import BE.Multiplier;
import Exceptions.RateCalcException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MultiplierDAO implements IMultiplierDAO {
    @Override
    public void createMulti(Multiplier multiplier) throws RateCalcException {
        try (Connection con = dbConnector.getConn()) {
            String sql = "INSERT INTO Multipliers(Type, Percentage) VALUES (?,?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, multiplier.getType());
                pstmt.setInt(2 ,multiplier.getPerc());
                pstmt.execute();
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public ArrayList<Multiplier> getAllMultis() throws RateCalcException{
        ArrayList<Multiplier> multis = new ArrayList<>();
        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT * FROM Multipliers";
            try (PreparedStatement pstmt = con.prepareStatement(sql);
                 ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("Type");
                    int percentage = resultSet.getInt("Percentage");
                    int id = resultSet.getInt("ID");
                    multis.add(new Multiplier(type,percentage, id));
                }
                return multis;
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public void deleteMulti(int id) throws RateCalcException {
        try (Connection con = dbConnector.getConn()) {
            String sql = "DELETE FROM Multipliers WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();

        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public void editMulti(Multiplier multiplier) throws RateCalcException {
        try (Connection con = dbConnector.getConn()) {
            String sql = "UPDATE Teams SET Type = ? WHERE Percentage=?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, multiplier.getType());
                pstmt.setInt(2,multiplier.getPerc());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }


}
