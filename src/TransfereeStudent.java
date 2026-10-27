/** A student who transferred from another school. */
public class TransfereeStudent extends Student {
    private static final long serialVersionUID = 1L;
    private String previousSchool;

    public TransfereeStudent(String studentId, String name, String contactNumber, int yearLevel,
                             String status, Address address, Course course, String previousSchool) {
        super(studentId, name, contactNumber, yearLevel, status, address, course);
        this.previousSchool = previousSchool;
    }

    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }
    @Override public String getStudentType() { return "Transferee"; }

    @Override public String displayDetails() {
        return getStudentType() + " Student | " + getStudentId() + " | " + getDetails()
                + " | Year: " + getYearLevel() + " | Status: " + getStatus()
                + " | Course: " + getCourse().getCourseCode()
                + " | Address: " + getAddress() + " | Previous school: " + previousSchool;
    }
}
