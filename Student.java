public class Student extends Person {
    private String id;
    private String branch;
    private String password;

    public Student(String id, String name, String branch, String password) {
        super(name);
        this.id = id;
        this.branch = branch;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Overriding the abstract method from Person class
    @Override
    public void getDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Branch: " + getBranch());
    }

    // Custom method to format the data for saving to a file
    public String toFileString() {
        return id + "," + getName() + "," + branch + "," + password;
    }
}
