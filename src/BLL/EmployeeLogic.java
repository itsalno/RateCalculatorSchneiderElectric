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

    public ObservableList<Employee> getAllEmployees() throws SQLException {
        return eDAO.getAllEmployees();
    }

    public void delete(Employee employee) throws SQLException {
        eDAO.delete(employee);
    }
    public void edit(Employee employee){
        eDAO.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText){
        return eDAO.searchEmployees(searchText);
    }
}
