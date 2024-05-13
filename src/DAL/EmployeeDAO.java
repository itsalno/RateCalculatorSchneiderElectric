package DAL;

import BE.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {


    @Override
    public void create(Employee employee) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "INSERT INTO Employee (AnnualSalary, OverheadMultiplierPercentage, ConfigurableFixedAnnualAmount, " +
                    "Country, Continent, Team, WorkingHours, UtilizationPercentage, EmployeeType, AnnualSalaryUSD, ConfigurableFixedAnnualAmountUSD,Name ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, employee.getAnnualSalary());
                stmt.setInt(2, employee.getOverheadMultiPercent());
                stmt.setDouble(3, employee.getConfFixedAnnualAmount());
                stmt.setString(4, employee.getCountry());
                stmt.setString(5, employee.getContinent());
                stmt.setString(6, employee.getTeam());
                stmt.setInt(7, employee.getWorkingHours());
                stmt.setInt(8, employee.getUtilizationPercent());
                stmt.setString(9, employee.getEmployeeType());
                stmt.setDouble(10, employee.getAnnualSalaryUSD());
                stmt.setDouble(11, employee.getConfFixedAnnualAmountUSD());
                stmt.setString(12, employee.getFullName());


                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String fullname=rs.getString("Name");
                        int annualSalary = rs.getInt("AnnualSalary");
                        int overheadMultiPercent = rs.getInt("OverheadMultiplierPercentage");
                        int confFixedAnnualAmount = rs.getInt("ConfigurableFixedAnnualAmount");
                        String country = rs.getString("Country");
                        String continent = rs.getString("Continent");
                        String team = rs.getString("Team");
                        int workingHours = rs.getInt("WorkingHours");
                        int utilizationPercent = rs.getInt("UtilizationPercentage");
                        String employeeType = rs.getString("EmployeeType");

                        Employee employee = new Employee(id,fullname, annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                                country, continent, team, workingHours, utilizationPercent, employeeType);
                        employees.add(employee);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    @Override
    public void delete(Employee employee) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "DELETE FROM Employee WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, employee.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(Employee employee) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "UPDATE Employee SET Name=?,AnnualSalary=?, OverheadMultiplierPercentage=?,  ConfigurableFixedAnnualAmount=?, Country=?," +
                    "Continent=?, Team=?, WorkingHours=?, UtilizationPercentage=?, EmployeeType=?, AnnualSalaryUSD=?, ConfigurableFixedAnnualAmountUSD=? WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1,employee.getFullName());
                pstmt.setDouble(2, employee.getAnnualSalary());
                pstmt.setInt(3, employee.getOverheadMultiPercent());
                pstmt.setDouble(4, employee.getConfFixedAnnualAmount());
                pstmt.setString(5, employee.getCountry());
                pstmt.setString(6, employee.getContinent());
                pstmt.setString(7, employee.getTeam());
                pstmt.setInt(8, employee.getWorkingHours());
                pstmt.setInt(9, employee.getUtilizationPercent());
                pstmt.setString(10, employee.getEmployeeType());
                pstmt.setDouble(11, employee.getAnnualSalaryUSD());
                pstmt.setDouble(12, employee.getConfFixedAnnualAmountUSD());
                pstmt.setInt(13, employee.getId());

                pstmt.executeUpdate();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> searchEmployees(String searchText) {
        List<Employee> matchingEmployees = new ArrayList<>();
        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee WHERE Country LIKE ? OR Continent LIKE ? OR Team LIKE ? OR Name LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            pstmt.setString(3, "%" + searchText + "%");
            pstmt.setString(4, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setCountry(rs.getString("Country"));
                employee.setFullName(rs.getString("Name"));
                employee.setConfFixedAnnualAmount(rs.getInt("ConfigurableFixedAnnualAmount"));
                employee.setTeam(rs.getString("Team"));
                employee.setWorkingHours(rs.getInt("WorkingHours"));
                employee.setUtilizationPercent(rs.getInt("UtilizationPercentage"));
                employee.setContinent(rs.getString("Continent"));
                employee.setEmployeeType(rs.getString("EmployeeType"));
                employee.setAnnualSalary(rs.getInt("AnnualSalary"));
                employee.setOverheadMultiPercent(rs.getInt("OverheadMultiplierPercentage"));
                matchingEmployees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matchingEmployees;
    }

    @Override
    public int getAnuallSalaryUSD(Employee e) {
        try (Connection con = dbConnector.getConn()) {
            int AnnualSalaryUSD = 0;
            String sql = "SELECT AnnualSalaryUSD FROM Employee WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, e.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AnnualSalaryUSD = rs.getInt("AnnualSalaryUSD");
            }
            return AnnualSalaryUSD;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public int getConFixAmountUSD(Employee e) {
        int ConfigurableFixedAnnualAmountUSD = 0;

        try (Connection con = dbConnector.getConn()) {
            String sql = "SELECT ConfigurableFixedAnnualAmountUSD FROM Employee WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, e.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ConfigurableFixedAnnualAmountUSD = rs.getInt("ConfigurableFixedAnnualAmountUSD");
            }
            return ConfigurableFixedAnnualAmountUSD;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeTeamFromEmployee(int id) {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "UPDATE Employee SET Team=? WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "None");
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
