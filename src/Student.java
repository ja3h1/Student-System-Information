import java.io.Serializable;

/** Shared student data and polymorphic display behavior. */
public abstract class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int studentId;
    private String name;
    private int contactNumber;
    private int yearLevel;
    private String courseName;
    private String departmentName;

    protected Student(int studentId, String name, int contactNumber, int yearLevel,
                      String courseName, String departmentName) {
        this.studentId = studentId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.yearLevel = yearLevel;
        this.courseName = courseName;
        this.departmentName = departmentName;
    }

    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getContactNumber() { return contactNumber; }
    public void setContactNumber(int contactNumber) { this.contactNumber = contactNumber; }
    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public abstract String getStudentType();
    public abstract String displayDetails();

    protected String commonDetails() {
        return getStudentType() + " Student | ID: " + studentId + " | Name: " + name
                + " | Contact: " + contactNumber + " | Year: " + yearLevel
                + " | Course: " + courseName + " | Department: " + departmentName;
    }
}
