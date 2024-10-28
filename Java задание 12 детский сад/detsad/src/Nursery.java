import java.util.ArrayList;
import java.util.List;

class Nursery {
    private final List<Group> groups;

    public Nursery () {
        groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }
    public void printGroups() {
        if (groups.isEmpty()) {
            System.out.println("Нет доступных групп.");
            return;
        }
        for (Group group : groups) {
            System.out.println(group);
        }
    }

    public Group findGroupName(String name) {
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

}
