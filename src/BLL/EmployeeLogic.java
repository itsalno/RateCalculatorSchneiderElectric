package BLL;

import BE.Employee;
import DAL.EmployeeDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EmployeeLogic {

    EmployeeDAO eDAO=new EmployeeDAO();

    public void create(Employee employee){
        eDAO.create(employee);
    }

    public ObservableList<Employee> getAllEmployees() throws SQLException {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) throws SQLException {
        eDAO.delete(employee);
    }
}
