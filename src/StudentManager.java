import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Console application and student-record manager. */
public class StudentManager {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Path DATA_FILE = Path.of("data", "student_records_v2.dat");

    private final List<Student> students;

    public StudentManager() throws IOException {
        students = loadStudents();
    }

    public static void main(String[] args) {
        try {
            new StudentManager().runMenu();
        } catch (IOException ex) {
            System.out.println("Could not open student records: " + ex.getMessage());
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
        saveStudents();
    }

    public void updateStudent(String studentId, String name, String contactNumber, int yearLevel,
                              String courseName, String departmentName) throws IOException {
        Student student = searchStudent(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student ID was not found."));
        student.setName(name);
        student.setContactNumber(contactNumber);
        student.setYearLevel(yearLevel);
        student.setCourseName(courseName);
        student.setDepartmentName(departmentName);
        saveStudents();
    }

    public void deleteStudent(String studentId) throws IOException {
        Student student = searchStudent(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student ID was not found."));
        students.remove(student);
        saveStudents();
    }

    private void runMenu() {
        while (true) {
            System.out.println("\n=== Student Information Management System ===");
            System.out.println("1. Add student\n2. List students\n3. Search student\n4. Update student\n5. Delete student\n0. Exit");
            try {
                switch (prompt("Choose an option: ").trim()) {
                    case "1": addStudentFromInput(); break;
                    case "2": showStudents(); break;
                    case "3": showStudent(); break;
                    case "4": updateStudentFromInput(); break;
                    case "5": deleteStudentFromInput(); break;
                    case "0": System.out.println("Goodbye."); return;
                    default: System.out.println("Choose a menu option from 0 to 5.");
                }
            } catch (IllegalArgumentException | IOException ex) {
                System.out.println("Action could not be completed: " + ex.getMessage());
            }
        }
    }

    private void addStudentFromInput() throws IOException {
        String type = prompt("Student type (1 Regular, 2 Transferee): ").trim();
        String id = required("Student ID");
        String name = required("Full name");
        String contact = required("Contact number");
        int year = readYearLevel();
        String course = required("Course name");
        String department = required("Department name");
        Student student;
        if (type.equals("1")) {
            student = new RegularStudent(id, name, contact, year, course, department, required("Admission date"));
        } else if (type.equals("2")) {
            student = new TransfereeStudent(id, name, contact, year, course, department, required("Previous school"));
        } else {
            throw new IllegalArgumentException("Choose 1 for Regular or 2 for Transferee.");
        }
        addStudent(student);
        System.out.println("Student saved.");
    }

    private void showStudents() {
        if (students.isEmpty()) { System.out.println("No student records yet."); return; }
        students.forEach(student -> System.out.println(student.displayDetails()));
    }

    private void showStudent() {
        String id = required("Student ID");
        System.out.println(searchStudent(id).map(Student::displayDetails).orElse("Student ID was not found."));
    }

    private void updateStudentFromInput() throws IOException {
        String id = required("Student ID to update");
        if (searchStudent(id).isEmpty()) throw new IllegalArgumentException("Student ID was not found.");
        updateStudent(id, required("New full name"), required("New contact number"), readYearLevel(),
                required("New course name"), required("New department name"));
        System.out.println("Student updated.");
    }

    private void deleteStudentFromInput() throws IOException {
        deleteStudent(required("Student ID to delete"));
        System.out.println("Student deleted.");
    }

    private String required(String fieldName) {
        String value = prompt(fieldName + ": ").trim();
        if (value.isEmpty()) throw new IllegalArgumentException(fieldName + " cannot be empty.");
        return value;
    }

    private int readYearLevel() {
        String value = prompt("Year level (1-4): ").trim();
        try {
            int year = Integer.parseInt(value);
            if (year < 1 || year > 4) throw new IllegalArgumentException("Year level must be from 1 to 4.");
            return year;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Year level must be a whole number from 1 to 4.");
        }
    }

    private String prompt(String message) {
        System.out.print(message);
        return INPUT.nextLine();
    }

    private List<Student> loadStudents() throws IOException {
        if (!Files.exists(DATA_FILE)) return new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(DATA_FILE))) {
            Object saved = input.readObject();
            if (!(saved instanceof List<?>)) throw new IOException("Saved records have an unexpected format.");
            List<Student> loaded = new ArrayList<>();
            for (Object item : (List<?>) saved) {
                if (!(item instanceof Student)) throw new IOException("Saved data contains an unknown record type.");
                loaded.add((Student) item);
            }
            return loaded;
        } catch (ClassNotFoundException ex) {
            throw new IOException("Saved records are incompatible with this version.", ex);
        }
    }

    private void saveStudents() throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(DATA_FILE))) {
            output.writeObject(new ArrayList<>(students));
        }
    }
}
