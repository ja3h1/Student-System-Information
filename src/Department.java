import java.io.Serializable;

/** Department offering a course. */
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String departmentCode;
    private final String departmentName;

    public Department(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() { return departmentCode; }
    public String getDepartmentName() { return departmentName; }
}
