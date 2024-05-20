package DAL;

import BE.Group;
import Exceptions.RateCalcException;
import javafx.collections.ObservableList;

import java.util.List;

public interface IGroupDAO {

     void createGroup(Group group) throws RateCalcException;

     ObservableList<Group> getAllGroups() throws RateCalcException;

     void deleteGroup(Group group) throws RateCalcException;

     void editGroup(Group group) throws RateCalcException;

     public void applyMultiplierToGroup(int multiplier, int id) throws RateCalcException;

     public Group updateGroupTable(int id) throws RateCalcException;
}
