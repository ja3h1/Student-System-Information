/** Student who transferred from another school. */
public class TransfereeStudent extends Student {
    private static final long serialVersionUID = 1L;
    private final String previousSchool;

    public TransfereeStudent(int studentId, String name, int contactNumber, int yearLevel,
                             String courseName, String departmentName, String previousSchool) {
        super(studentId, name, contactNumber, yearLevel, courseName, departmentName);
        this.previousSchool = previousSchool;
    }

    public String getPreviousSchool() { return previousSchool; }
    @Override public String getStudentType() { return "Transferee"; }
    @Override public String displayDetails() { return commonDetails() + " | Previous school: " + previousSchool; }
}
