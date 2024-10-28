import java.util.ArrayList;
import java.util.List;

class Group {
    private final String name;
    private final int number;
    private final List<Kid> kids;

    public Group(String name, int number) {
        this.name = name;
        this.number = number;
        this.kids = new ArrayList<>();
    }

    public void addKid (Kid kid) {
        kids.add(kid);
    }

    public void removeKid (Kid kid) {
        kids.remove(kid);
    }

    public List<Kid> getKids () {
        return kids;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Группа: " + name + " номер: " + number + ", дети: " + kids;
    }
}

