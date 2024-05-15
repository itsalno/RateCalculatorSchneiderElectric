package BLL;

import BE.Group;
import DAL.GroupDAO;
import javafx.collections.ObservableList;

import java.util.List;

public class GroupLogic {
    GroupDAO groupDAO=new GroupDAO();

    public void createGroup(Group group){
        groupDAO.createGroup(group);
    }

    public ObservableList<Group> getAllGroups(){
       return groupDAO.getAllGroups();
    }
    public Group updateGroupTable(int id){
        return groupDAO.updateGroupTable(id);
    }
    public void deleteGroup(Group group){
        groupDAO.deleteGroup(group);
    }

    public void editGroup(Group group){
        groupDAO.editGroup(group);
    }

    public void applyMultiplierToGroup(int multiplier, int id){
        groupDAO.applyMultiplierToGroup(multiplier, id);
    }

}
