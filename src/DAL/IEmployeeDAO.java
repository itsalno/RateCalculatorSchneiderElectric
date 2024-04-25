package DAL;

import BE.Employee;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IEmployeeDAO {

    void create(Employee employee);

    ObservableList<Employee> getAllEmployees() throws SQLException;
    void delete(Employee employee) throws SQLException;
public void edit(Employee employee) throws  SQLException;
}
