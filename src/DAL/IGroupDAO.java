package DAL;

import BE.Group;

import java.util.List;

public interface IGroupDAO {

    public void createGroup(Group group);

    public List<Group> getAllGroups();

    public void deleteGroup(int id);

    public void editGroup(Group group);
}
