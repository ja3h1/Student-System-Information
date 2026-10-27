import java.io.Serializable;

/** Common student information. Concrete student types provide their own details. */
public abstract class Student extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String studentId;
    private int yearLevel;
    private String status;
    private Address address;
    private Course course;

    protected Student(String studentId, String name, String contactNumber, int yearLevel,
                      String status, Address address, Course course) {
        super(name, contactNumber);
        this.studentId = studentId;
        this.yearLevel = yearLevel;
        this.status = status;
        this.address = address;
        this.course = course;
    }

    public String getStudentId() { return studentId; }
    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public abstract String getStudentType();
    public abstract String displayDetails();
}
