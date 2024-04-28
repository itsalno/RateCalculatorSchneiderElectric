package BLL;

import BE.Group;
import DAL.GroupDAO;

import java.util.List;

public class GroupLogic {
    GroupDAO groupDAO=new GroupDAO();

    public void createGroup(Group group){
        groupDAO.createGroup(group);
    }

    public List<Group> getAllGroups(){
       return groupDAO.getAllGroups();
    }

    public void deleteGroup(Group group){
        groupDAO.deleteGroup(group);
    }

    public void editGroup(Group group){
        groupDAO.editGroup(group);
    }
}
