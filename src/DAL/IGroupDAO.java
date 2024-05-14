package DAL;

import BE.Group;
import javafx.collections.ObservableList;

import java.util.List;

public interface IGroupDAO {

     void createGroup(Group group);

     ObservableList<Group> getAllGroups();

     void deleteGroup(Group group);

     void editGroup(Group group);

     public void applyMultiplierToGroup(int multiplier, int id);

     public Group updateGroupTable(int id);
}
