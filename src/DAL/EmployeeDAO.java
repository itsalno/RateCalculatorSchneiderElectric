package DAL;

import BE.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO implements IEmployeeDAO {


    @Override
    public void create(Employee employee) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "INSERT INTO Employee (AnnualSalary, OverheadMultiplierPercentage, ConfigurableFixedAnnualAmount, " +
                    "Country, Continent, Team, WorkingHours, UtilizationPercentage, EmployeeType) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, employee.getAnnualSalary());
                stmt.setInt(2, employee.getOverheadMultiPercent());
                stmt.setInt(3, employee.getConfFixedAnnualAmount());
                stmt.setString(4, employee.getCountry());
                stmt.setString(5, employee.getContinent());
                stmt.setString(6, employee.getTeam());
                stmt.setInt(7, employee.getWorkingHours());
                stmt.setInt(8, employee.getUtilizationPercent());
                stmt.setString(9, employee.getEmployeeType());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Employee> getAllEmployees() throws SQLException {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int annualSalary = rs.getInt("AnnualSalary");
                        int overheadMultiPercent = rs.getInt("OverheadMultiplierPercentage");
                        int confFixedAnnualAmount = rs.getInt("ConfigurableFixedAnnualAmount");
                        String country = rs.getString("Country");
                        String continent = rs.getString("Continent");
                        String team = rs.getString("Team");
                        int workingHours = rs.getInt("WorkingHours");
                        int utilizationPercent = rs.getInt("UtilizationPercentage");
                        String employeeType = rs.getString("EmployeeType");

                        Employee employee = new Employee(id, annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                                country, continent, team, workingHours, utilizationPercent, employeeType);
                        employees.add(employee);
                    }
                }
            }
        }

        return employees;
    }

    @Override
    public void delete(Employee employee) throws SQLException {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "DELETE FROM Employee WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, employee.getId());
                stmt.executeUpdate();
            }
        }
    }

    public void edit(Employee employee) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "UPDATE Employee SET AnnualSalary=?, OverheadMultiplierPercentage=?,  ConfigurableFixedAnnualAmount=?, Country=?," +
                    "Continent=?, Team=?, WorkingHours=?, UtilizationPercentage=?, EmployeeType=? WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employee.getAnnualSalary());
                pstmt.setInt(2, employee.getOverheadMultiPercent());
                pstmt.setInt(3, employee.getConfFixedAnnualAmount());
                pstmt.setString(4, employee.getCountry());
                pstmt.setString(5, employee.getContinent());
                pstmt.setString(6, employee.getTeam());
                pstmt.setInt(7, employee.getWorkingHours());
                pstmt.setInt(8, employee.getUtilizationPercent());
                pstmt.setString(9, employee.getEmployeeType());
                pstmt.setInt(10, employee.getId()); // Assuming the employee ID is in the 10th position

                // Execute the update query
                pstmt.executeUpdate();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
