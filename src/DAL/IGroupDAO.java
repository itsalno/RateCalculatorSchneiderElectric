package DAL;

import BE.Group;

import java.util.List;

public interface IGroupDAO {

     void createGroup(Group group);

     List<Group> getAllGroups();

     void deleteGroup(Group group);

     void editGroup(Group group);
}
