/** Student admitted through the regular admission process. */
public class RegularStudent extends Student {
    private final String admissionDate;

    public RegularStudent(String studentId, String name, String contactNumber, int yearLevel,
                          String courseName, String departmentName, String admissionDate) {
        super(studentId, name, contactNumber, yearLevel, courseName, departmentName);
        this.admissionDate = admissionDate;
    }

    public String getAdmissionDate() { return admissionDate; }
    @Override public String getStudentType() { return "Regular"; }
    @Override public String displayDetails() { return commonDetails() + " | Admitted: " + admissionDate; }
}
