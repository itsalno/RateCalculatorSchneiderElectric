package DAL;

import BE.Group;

import java.util.List;

public interface IGroupDAO {

     void createGroup(Group group);

     List<Group> getAllGroups();

     void deleteGroup(String name);

     void editGroup(Group group);
}
