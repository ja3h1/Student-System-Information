/** Student admitted through the regular admission process. */
public class RegularStudent extends Student {
    private static final long serialVersionUID = 1L;
    private final String admissionDate;

    public RegularStudent(int studentId, String name, int contactNumber, int yearLevel,
                          String courseName, String departmentName, String admissionDate) {
        super(studentId, name, contactNumber, yearLevel, courseName, departmentName);
        this.admissionDate = admissionDate;
    }

    public String getAdmissionDate() { return admissionDate; }
    @Override public String getStudentType() { return "Regular"; }
    @Override public String displayDetails() { return commonDetails() + " | Admitted: " + admissionDate; }
}
