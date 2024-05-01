package BLL;

import BE.Employee;
import DAL.EmployeeDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class EmployeeLogic {

    EmployeeDAO eDAO=new EmployeeDAO();

    public void create(Employee employee){
        eDAO.create(employee);
    }

    public ObservableList<Employee> getAllEmployees() {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) {
        eDAO.delete(employee);
    }
    public void edit(Employee employee){
        eDAO.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText){
        return eDAO.searchEmployees(searchText);
    }

    public void updateRate(Employee employee) {
        double hourlyRate = employee.calculateHourlyRate();
        double dailyRate = employee.calculateDailyRate(8); // Assuming an 8-hour workday.

        // Here, you'd convert these to strings if your database fields are VARCHAR or similar,
        // otherwise, you should change your database fields to be of a DECIMAL type.
        employee.setHourlyRate(String.format("%.2f", hourlyRate));
        employee.setDailyRate(String.format("%.2f", dailyRate));

        // Then, you might want to update the employee object in the database with these new rates.
        eDAO.edit(employee);
    }
}
