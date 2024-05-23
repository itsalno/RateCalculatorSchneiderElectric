package DAL;

import BE.Employee;
import BE.Group;
import Exceptions.RateCalcException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {

    @Override
    public void create(Employee employee) throws RateCalcException {
        if (employee.getTeams().size() > 2) {
            throw new IllegalArgumentException("An employee cannot be part of more than two teams.");
        }
        try (Connection conn = dbConnector.getConn()) {
            String sql = "INSERT INTO Employee (AnnualSalary, OverheadMultiplierPercentage, ConfigurableFixedAnnualAmount, " +
                    "Country, Continent, WorkingHours, UtilizationPercentage, EmployeeType, AnnualSalaryUSD, " +
                    "ConfigurableFixedAnnualAmountUSD, Name, HourlyRate, DailyRate ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, employee.getAnnualSalary());
                stmt.setInt(2, employee.getOverheadMultiPercent());
                stmt.setDouble(3, employee.getConfFixedAnnualAmount());
                stmt.setString(4, employee.getCountry());
                stmt.setString(5, employee.getContinent());
                stmt.setInt(6, employee.getWorkingHours());
                stmt.setInt(7, employee.getUtilizationPercent());
                stmt.setString(8, employee.getEmployeeType());
                stmt.setDouble(9, employee.getAnnualSalaryUSD());
                stmt.setDouble(10, employee.getConfFixedAnnualAmountUSD());
                stmt.setString(11, employee.getFullName());
                stmt.setFloat(12, employee.calculateHourlyRate());
                stmt.setFloat(13, employee.calculateDailyRate(employee.getWorkingHours()));

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int employeeId = generatedKeys.getInt(1);
                        insertEmployeeTeams(employeeId, employee.getTeams());
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    private void insertEmployeeTeams(int employeeId, List<Group> teams) throws RateCalcException {
        String sql = "INSERT INTO EmployeeTeams (employee_id, team_id) VALUES (?, ?)";
        try (Connection conn = dbConnector.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Group team : teams) {
                stmt.setInt(1, employeeId);
                stmt.setInt(2, team.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public ObservableList<Employee> getAllEmployees() throws RateCalcException {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String fullname = rs.getString("Name");
                        double annualSalary = rs.getDouble("AnnualSalary");
                        int overheadMultiPercent = rs.getInt("OverheadMultiplierPercentage");
                        double confFixedAnnualAmount = rs.getDouble("ConfigurableFixedAnnualAmount");
                        String country = rs.getString("Country");
                        String continent = rs.getString("Continent");
                        int workingHours = rs.getInt("WorkingHours");
                        int utilizationPercent = rs.getInt("UtilizationPercentage");
                        String employeeType = rs.getString("EmployeeType");
                        float hourlyRate = rs.getFloat("HourlyRate");
                        float dailyRate = rs.getFloat("DailyRate");

                        List<Group> teams = getTeamsByEmployeeId(id);

                        Employee employee = new Employee(id, 0, fullname, annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                                country, continent, teams, workingHours, utilizationPercent, employeeType, hourlyRate, dailyRate);
                        employees.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }

        return employees;
    }

    private List<Group> getTeamsByEmployeeId(int employeeId) throws RateCalcException {
        List<Group> teams = new ArrayList<>();
        String sql = "SELECT t.id, t.name, t.multiplier FROM Teams t " +
                "INNER JOIN EmployeeTeams et ON t.id = et.team_id " +
                "WHERE et.employee_id = ?";
        try (Connection conn = dbConnector.getConn(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int multiplier = rs.getInt("multiplier");
                    Group team = new Group(name, id, multiplier);
                    teams.add(team);
                }
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
        return teams;
    }

    @Override
    public void delete(Employee employee) throws RateCalcException {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "DELETE FROM EmployeeTeams WHERE employee_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, employee.getId());
                stmt.executeUpdate();
            }

            sql = "DELETE FROM Employee WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, employee.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public void edit(Employee employee) throws RateCalcException {
        if (employee.getTeams().size() > 2) {
            throw new IllegalArgumentException("An employee cannot be part of more than two teams.");
        }

        try (Connection conn = dbConnector.getConn()) {
            String sql = "UPDATE Employee SET Name=?, AnnualSalary=?, OverheadMultiplierPercentage=?, ConfigurableFixedAnnualAmount=?, Country=?, " +
                    "Continent=?, WorkingHours=?, UtilizationPercentage=?, EmployeeType=?, AnnualSalaryUSD=?, ConfigurableFixedAnnualAmountUSD=?, HourlyRate = ?, DailyRate = ? WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, employee.getFullName());
                stmt.setDouble(2, employee.getAnnualSalary());
                stmt.setInt(3, employee.getOverheadMultiPercent());
                stmt.setDouble(4, employee.getConfFixedAnnualAmount());
                stmt.setString(5, employee.getCountry());
                stmt.setString(6, employee.getContinent());
                stmt.setInt(7, employee.getWorkingHours());
                stmt.setInt(8, employee.getUtilizationPercent());
                stmt.setString(9, employee.getEmployeeType());
                stmt.setDouble(10, employee.getAnnualSalaryUSD());
                stmt.setDouble(11, employee.getConfFixedAnnualAmountUSD());
                stmt.setFloat(12, employee.calculateHourlyRate());
                stmt.setFloat(13, employee.calculateDailyRate(employee.getWorkingHours()));
                stmt.setInt(14, employee.getId());

                stmt.executeUpdate();

                updateEmployeeTeams(employee);
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    private void updateEmployeeTeams(Employee employee) throws RateCalcException {
        String sqlDelete = "DELETE FROM EmployeeTeams WHERE employee_id = ?";
        String sqlInsert = "INSERT INTO EmployeeTeams (employee_id, team_id) VALUES (?, ?)";
        try (Connection conn = dbConnector.getConn(); PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete); PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
            conn.setAutoCommit(false);

            stmtDelete.setInt(1, employee.getId());
            stmtDelete.executeUpdate();

            for (Group team : employee.getTeams()) {
                stmtInsert.setInt(1, employee.getId());
                stmtInsert.setInt(2, team.getId());
                stmtInsert.addBatch();
            }

            stmtInsert.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public List<Employee> searchEmployees(String searchText) throws RateCalcException {
        List<Employee> matchingEmployees = new ArrayList<>();
        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee WHERE Country LIKE ? OR Continent LIKE ? OR Name LIKE ? OR Team LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            pstmt.setString(3, "%" + searchText + "%");
            pstmt.setString(4, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullname = rs.getString("Name");
                double annualSalary = rs.getDouble("AnnualSalary");
                int overheadMultiPercent = rs.getInt("OverheadMultiplierPercentage");
                double confFixedAnnualAmount = rs.getDouble("ConfigurableFixedAnnualAmount");
                String country = rs.getString("Country");
                String continent = rs.getString("Continent");
                int workingHours = rs.getInt("WorkingHours");
                int utilizationPercent = rs.getInt("UtilizationPercentage");
                String employeeType = rs.getString("EmployeeType");
                float hourlyRate = rs.getFloat("HourlyRate");
                float dailyRate = rs.getFloat("DailyRate");

                List<Group> teams = getTeamsByEmployeeId(id);

                Employee employee = new Employee(id, 0, fullname, annualSalary, overheadMultiPercent, confFixedAnnualAmount,
                        country, continent, teams, workingHours, utilizationPercent, employeeType, hourlyRate, dailyRate);
                matchingEmployees.add(employee);
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
        return matchingEmployees;
    }

    @Override
    public int getAnuallSalaryUSD(Employee e) throws RateCalcException {
        try (Connection conn = dbConnector.getConn()) {
            int AnnualSalaryUSD = 0;
            String sql = "SELECT AnnualSalaryUSD FROM Employee WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, e.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                AnnualSalaryUSD = rs.getInt("AnnualSalaryUSD");
            }
            return AnnualSalaryUSD;
        } catch (SQLException ex) {
            throw new RateCalcException("Problems with the database or database connection",ex);
        }
    }

    @Override
    public int getConFixAmountUSD(Employee e) throws RateCalcException {
        int ConfigurableFixedAnnualAmountUSD = 0;
        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT ConfigurableFixedAnnualAmountUSD FROM Employee WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, e.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ConfigurableFixedAnnualAmountUSD = rs.getInt("ConfigurableFixedAnnualAmountUSD");
            }
            return ConfigurableFixedAnnualAmountUSD;
        } catch (SQLException ex) {
            throw new RateCalcException("Problems with the database or database connection",ex);
        }
    }

    @Override
    public void removeTeamFromEmployee(int id, int tId) throws RateCalcException {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "DELETE FROM EmployeeTeams WHERE employee_id = ? AND team_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setInt(2, tId);
                pstmt.executeUpdate();
            }

            sql = "UPDATE Employee SET Team=?, TeamId=NULL WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "None");
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws RateCalcException {
        try (Connection conn = dbConnector.getConn()) {
            String sql = "SELECT * FROM Employee WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        List<Group> teams = getTeamsByEmployeeId(id);

                        Employee employee = new Employee(
                                rs.getInt("id"),
                                rs.getInt("TeamId"),
                                rs.getString("Name"),
                                rs.getDouble("AnnualSalary"),
                                rs.getInt("OverheadMultiplierPercentage"),
                                rs.getDouble("ConfigurableFixedAnnualAmount"),
                                rs.getString("Country"),
                                rs.getString("Continent"),
                                teams,
                                rs.getInt("WorkingHours"),
                                rs.getInt("UtilizationPercentage"),
                                rs.getString("EmployeeType"),
                                rs.getFloat("HourlyRate"),
                                rs.getFloat("DailyRate")
                        );
                        return employee;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RateCalcException("Problems with the database or database connection",e);
        }
        return null;
    }
}