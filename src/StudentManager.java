import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Owns the student collection and its record-management operations. */
public class StudentManager {
    private final List<Student> students;
    private final FileHandler fileHandler;

    public StudentManager(FileHandler fileHandler) throws IOException {
        this.fileHandler = fileHandler;
        try {
            this.students = fileHandler.load();
        } catch (ClassNotFoundException ex) {
            throw new IOException("Saved student data is incompatible with this application.", ex);
        }
    }

    public List<Student> listStudents() { return new ArrayList<>(students); }

    public Optional<Student> searchStudent(String studentId) {
        return students.stream().filter(s -> s.getStudentId().equalsIgnoreCase(studentId)).findFirst();
    }

    public void addStudent(Student student) throws IOException {
        if (searchStudent(student.getStudentId()).isPresent()) {
            throw new IllegalArgumentException("A student with that ID already exists.");
        }
        students.add(student);
        save();
    }

    public void updateStudent(String studentId, String name, String contact, int year,
                              String status, Address address, Course course) throws IOException {
        Student student = searchStudent(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student ID was not found."));
        student.setName(name);
        student.setContactNumber(contact);
        student.setYearLevel(year);
        student.setStatus(status);
        student.setAddress(address);
        student.setCourse(course);
        save();
    }

    public void deleteStudent(String studentId) throws IOException {
        Student student = searchStudent(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student ID was not found."));
        students.remove(student);
        save();
    }

    private void save() throws IOException { fileHandler.save(students); }
}
