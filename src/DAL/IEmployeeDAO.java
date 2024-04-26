package DAL;

import BE.Employee;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDAO {

    void create(Employee employee);

    ObservableList<Employee> getAllEmployees();
    void delete(Employee employee);
    void edit(Employee employee) ;
    List<Employee> searchEmployees(String searchText);
}
