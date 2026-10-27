import java.io.Serializable;

/** Department offering a course. */
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String departmentName;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() { return departmentName; }
}
