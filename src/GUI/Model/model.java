package GUI.Model;


import BE.Employee;
import BLL.EmployeeLogic;
import BLL.GroupLogic;
import javafx.collections.ObservableList;

import java.sql.SQLException;





import BE.Group;
import BLL.GroupLogic;

import java.util.List;

public class model {
    private final static model instance = new model();
    private GroupLogic groupLogic;


    EmployeeLogic eLogic= new EmployeeLogic();

    public model() {
        this.groupLogic = new GroupLogic();
    }

    public static model getInstance(){
        return instance;
    }

    public void createTeam(Group group){
        groupLogic.createGroup(group);
    }

    public List<Group> getAllTeams(){
        return groupLogic.getAllGroups();
    }
    public void deleteTeam(String name){
        groupLogic.deleteGroup(name);
    }
    public void updateTeam(Group group){
        groupLogic.editGroup(group);
    }

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
