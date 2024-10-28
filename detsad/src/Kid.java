
class Kid {
    private final String fullName;
    private final String gender;
    private final int age;

    public Kid(String name, String gender, int age) {
        this.fullName = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + ", пол: " + gender + ", возраст:" + age ;
    }

    public String getFullName() {
        return fullName;
    }
}

