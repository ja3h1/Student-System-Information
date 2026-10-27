import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Console application and student-record manager. */
public class StudentManager {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Path DATA_FILE = Path.of("data", "students.txt");

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

    public Optional<Student> searchStudent(int studentId) {
        return students.stream().filter(s -> s.getStudentId() == studentId).findFirst();
    }

    public void addStudent(Student student) throws IOException {
        if (searchStudent(student.getStudentId()).isPresent()) {
            throw new IllegalArgumentException("A student with that ID already exists.");
        }
        students.add(student);
        saveStudents();
    }

    public void updateStudent(int studentId, String name, int contactNumber, int yearLevel,
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

    public void deleteStudent(int studentId) throws IOException {
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
        int id = readPositiveInteger("Student ID");
        String name = required("Full name");
        int contact = readPositiveInteger("Contact number");
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
        String format = "%-14s | %-24s | %-12s | %-6s | %-14s | %-24s | %-24s%n";
        System.out.printf(format, "STUDENT ID", "NAME", "TYPE", "YEAR", "CONTACT", "COURSE", "DEPARTMENT");
        System.out.println("----------------+--------------------------+--------------+--------+----------------+--------------------------+--------------------------");
        for (Student student : students) {
            System.out.printf(format,
                    column(String.valueOf(student.getStudentId()), 14),
                    column(student.getName(), 24),
                    column(student.getStudentType(), 12),
                    student.getYearLevel(),
                    student.getContactNumber(),
                    column(student.getCourseName(), 24),
                    column(student.getDepartmentName(), 24));
        }
    }

    private String column(String value, int width) {
        if (value == null) return "";
        if (value.length() <= width) return value;
        return value.substring(0, width - 3) + "...";
    }

    private void showStudent() {
        int id = readPositiveInteger("Student ID");
        System.out.println(searchStudent(id).map(Student::displayDetails).orElse("Student ID was not found."));
    }

    private void updateStudentFromInput() throws IOException {
        int id = readPositiveInteger("Student ID to update");
        if (searchStudent(id).isEmpty()) throw new IllegalArgumentException("Student ID was not found.");
        updateStudent(id, required("New full name"), readPositiveInteger("New contact number"), readYearLevel(),
                required("New course name"), required("New department name"));
        System.out.println("Student updated.");
    }

    private void deleteStudentFromInput() throws IOException {
        deleteStudent(readPositiveInteger("Student ID to delete"));
        System.out.println("Student deleted.");
    }

    private String required(String fieldName) {
        String value = prompt(fieldName + ": ").trim();
        if (value.isEmpty()) throw new IllegalArgumentException(fieldName + " cannot be empty.");
        return value;
    }

    private int readPositiveInteger(String fieldName) {
        String value = required(fieldName);
        try {
            int number = Integer.parseInt(value);
            if (number <= 0) throw new IllegalArgumentException(fieldName + " must be a positive integer.");
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " must be a positive whole number within the int range.");
        }
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
        List<Student> loaded = new ArrayList<>();
        int lineNumber = 0;
        for (String line : Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8)) {
            lineNumber++;
            if (line.trim().isEmpty()) continue;
            List<String> fields = splitRecord(line);
            if (fields.size() != 8) throw new IOException("Invalid record on line " + lineNumber + ".");
            try {
                int studentId = Integer.parseInt(fields.get(1));
                int contactNumber = Integer.parseInt(fields.get(3));
                int year = Integer.parseInt(fields.get(4));
                if (year < 1 || year > 4) throw new IOException("Invalid year level on line " + lineNumber + ".");
                String type = fields.get(0);
                if (type.equals("Regular")) {
                    loaded.add(new RegularStudent(studentId, fields.get(2), contactNumber, year,
                            fields.get(5), fields.get(6), fields.get(7)));
                } else if (type.equals("Transferee")) {
                    loaded.add(new TransfereeStudent(studentId, fields.get(2), contactNumber, year,
                            fields.get(5), fields.get(6), fields.get(7)));
                } else {
                    throw new IOException("Unknown student type on line " + lineNumber + ".");
                }
            } catch (NumberFormatException ex) {
                throw new IOException("Invalid integer field on line " + lineNumber + ".", ex);
            }
        }
        return loaded;
    }

    private void saveStudents() throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        List<String> lines = new ArrayList<>();
        for (Student student : students) {
            String subtypeValue = student instanceof RegularStudent
                    ? ((RegularStudent) student).getAdmissionDate()
                    : ((TransfereeStudent) student).getPreviousSchool();
            String[] fields = {student.getStudentType(), String.valueOf(student.getStudentId()), student.getName(),
                    String.valueOf(student.getContactNumber()), String.valueOf(student.getYearLevel()),
                    student.getCourseName(), student.getDepartmentName(), subtypeValue};
            List<String> encodedFields = new ArrayList<>();
            for (String field : fields) encodedFields.add(encodeField(field));
            lines.add(String.join("|", encodedFields));
        }
        Files.write(DATA_FILE, lines, StandardCharsets.UTF_8);
    }

    private String encodeField(String value) {
        StringBuilder encoded = new StringBuilder();
        for (char ch : value.toCharArray()) {
            switch (ch) {
                case '\\': encoded.append("\\\\"); break;
                case '|': encoded.append("\\|"); break;
                case '\n': encoded.append("\\n"); break;
                case '\r': encoded.append("\\r"); break;
                case '\t': encoded.append("\\t"); break;
                default: encoded.append(ch);
            }
        }
        return encoded.toString();
    }

    private List<String> splitRecord(String line) throws IOException {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean escaped = false;
        for (char ch : line.toCharArray()) {
            if (escaped) {
                switch (ch) {
                    case '\\': field.append('\\'); break;
                    case '|': field.append('|'); break;
                    case 'n': field.append('\n'); break;
                    case 'r': field.append('\r'); break;
                    case 't': field.append('\t'); break;
                    default: throw new IOException("Invalid escape in saved student data.");
                }
                escaped = false;
            } else if (ch == '\\') {
                escaped = true;
            } else if (ch == '|') {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(ch);
            }
        }
        if (escaped) throw new IOException("Incomplete escape in saved student data.");
        fields.add(field.toString());
        return fields;
    }
}
