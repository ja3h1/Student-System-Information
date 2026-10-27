/** A student admitted through the regular admission process. */
public class RegularStudent extends Student {
    private static final long serialVersionUID = 1L;
    private String admissionDate;

    public RegularStudent(String studentId, String name, String contactNumber, int yearLevel,
                          String status, Address address, Course course, String admissionDate) {
        super(studentId, name, contactNumber, yearLevel, status, address, course);
        this.admissionDate = admissionDate;
    }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }
    @Override public String getStudentType() { return "Regular"; }

    @Override public String displayDetails() {
        return getStudentType() + " Student | " + getStudentId() + " | " + getDetails()
                + " | Year: " + getYearLevel() + " | Status: " + getStatus()
                + " | Course: " + getCourse().getCourseCode()
                + " | Address: " + getAddress() + " | Admitted: " + admissionDate;
    }
}
