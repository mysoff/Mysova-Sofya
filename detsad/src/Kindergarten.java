import java.util.ArrayList;
import java.util.List;

class Kindergarten {
    private List<Group> groups;

    public Kindergarten() {
        groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void printGroups() {
        for (Group group : groups) {
            System.out.println(group);
            for (Child child : group.getChildren()) {
                System.out.println("  " + child);
            }
        }
    }

    public Group findGroupByName(String name) {
        for (Group group : groups) {
            if (group.toString().contains(name)) {
                return group;
            }
        }
        return null;
    }
}
