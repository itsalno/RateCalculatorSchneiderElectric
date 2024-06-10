package BLL;

import BE.Group;
import DAL.GroupDAO;
import Exceptions.RateCalcException;


import java.util.List;

public class GroupLogic {
    private GroupDAO groupDAO=new GroupDAO();

    public void createGroup(Group group) throws RateCalcException {
        groupDAO.createGroup(group);
    }

    public List<Group> getAllGroups() throws RateCalcException {
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
