import java.io.Serializable;

/** Course associated with one or more student records. */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String courseName;
    private final Department department;

    public Course(String courseName, Department department) {
        this.courseName = courseName;
        this.department = department;
    }

    public String getCourseName() { return courseName; }
    public Department getDepartment() { return department; }
}
