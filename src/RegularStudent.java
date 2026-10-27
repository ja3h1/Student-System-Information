/** A student admitted through the regular admission process. */
public class RegularStudent extends Student {
    private static final long serialVersionUID = 1L;
    private String admissionDate;

    public RegularStudent(String studentId, String name, String contactNumber, int yearLevel,
                          Address address, Course course, String admissionDate) {
        super(studentId, name, contactNumber, yearLevel, address, course);
        this.admissionDate = admissionDate;
    }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }
    @Override public String getStudentType() { return "Regular"; }

    @Override public String displayDetails() {
        return getStudentType() + " Student | " + getStudentId() + " | " + getDetails()
                + " | Year: " + getYearLevel()
                + " | Course: " + getCourse().getCourseName() + " (" + getCourse().getDepartment().getDepartmentName() + ")"
                + " | Address: " + getAddress() + " | Admitted: " + admissionDate;
    }
}
