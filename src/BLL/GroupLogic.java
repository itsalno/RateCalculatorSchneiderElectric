package BLL;

import BE.Group;
import DAL.GroupDAO;
import Exceptions.RateCalcException;
import javafx.collections.ObservableList;

import java.util.List;

public class GroupLogic {
    GroupDAO groupDAO=new GroupDAO();

    public void createGroup(Group group) throws RateCalcException {
        groupDAO.createGroup(group);
    }

    public ObservableList<Group> getAllGroups() throws RateCalcException {
       return groupDAO.getAllGroups();
    }
    public Group updateGroupTable(int id) throws RateCalcException {
        return groupDAO.updateGroupTable(id);
    }
    public void deleteGroup(Group group) throws RateCalcException {
        groupDAO.deleteGroup(group);
    }

    public void editGroup(Group group) throws RateCalcException {
        groupDAO.editGroup(group);
    }

    public void applyMultiplierToGroup(int multiplier, int id) throws RateCalcException {
        groupDAO.applyMultiplierToGroup(multiplier, id);
    }
    
}
