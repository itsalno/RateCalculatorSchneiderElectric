package GUI.Model;

import BE.Employee;
import BLL.EmployeeLogic;
import BLL.GroupLogic;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class model {
    EmployeeLogic eLogic= new EmployeeLogic();
    GroupLogic gLogic=new GroupLogic();

    public void createEmployee(Employee employee){
        eLogic.create(employee);
    }
    public ObservableList<Employee> getAllEmployees() throws SQLException {
        return eLogic.getAllEmployees();
    }

    public void deleteEmployee(Employee employee) throws SQLException {
        eLogic.delete(employee);
    }

}
