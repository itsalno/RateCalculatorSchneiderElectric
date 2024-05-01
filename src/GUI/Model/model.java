package GUI.Model;


import BE.Employee;
import BE.Multiplier;
import BLL.EmployeeLogic;
import BLL.GroupLogic;
import BLL.MultiplierLogic;
import GUI.Controllers.MSController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;





import BE.Group;
import BLL.GroupLogic;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class model {
    private final static model instance = new model();
    private GroupLogic groupLogic;

    private MultiplierLogic multiplierLogic = new MultiplierLogic();

    private EmployeeLogic eLogic= new EmployeeLogic();

    public model() {
        this.groupLogic = new GroupLogic();
    }

    public static model getInstance(){
        return instance;
    }




    //TEAM OPERATIONS

    public void createTeam(Group group){
        groupLogic.createGroup(group);
    }

    public List<Group> getAllTeams(){
        return groupLogic.getAllGroups();
    }
    public void deleteTeam(Group group){
        groupLogic.deleteGroup(group);
    }
    public void updateTeam(Group group){
        groupLogic.editGroup(group);
    }



    //EMPLOYEE OPERATIONS
    public void createEmployee(Employee employee){
        eLogic.create(employee);
    }
    public ObservableList<Employee> getAllEmployees() {
        return eLogic.getAllEmployees();
    }

    public void deleteEmployee(Employee employee) {
        eLogic.delete(employee);
    }

    public void editEmployee(Employee employee) {
        eLogic.edit(employee);
    }

    public List<Employee> searchEmployees(String searchText){
        return eLogic.searchEmployees(searchText);
    }


    //MULTIPLIER OPERATIONS

    public void createMulti(Multiplier multiplier){multiplierLogic.createMulti(multiplier);}

    public ArrayList<Multiplier> getAllMultis(){
        return multiplierLogic.getAllMultis();
    }

    public void deleteMulti(int id){multiplierLogic.deleteMulti(id);}

    public void editMulti(Multiplier multiplier){multiplierLogic.editMultiplier(multiplier);}

////////////////////////////////////////

    public void updateTable(TableView<Multiplier> multiTable){
        ObservableList<Multiplier> multis = FXCollections.observableArrayList();
        multis.setAll(model.getInstance().getAllMultis());
        multiTable.setItems(multis);
    }


}
