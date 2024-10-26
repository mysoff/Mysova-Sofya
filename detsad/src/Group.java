import java.util.ArrayList;
import java.util.List;

class Group {
    private String name;
    private int number;
    private List<Child> children;

    public Group(String name, int number) {
        this.name = name;
        this.number = number;
        this.children = new ArrayList<>();
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public void removeChild(Child child) {
        children.remove(child);
    }

    public List<Child> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Группа: " + name + ", Номер: " + number + ", Дети: " + children.size();
    }
}