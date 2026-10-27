import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

/** Console entry point for the Student Information Management System. */
public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final InputValidator VALIDATOR = new InputValidator();
    private static StudentManager manager;

    public static void main(String[] args) {
        try {
            manager = new StudentManager(new FileHandler(Path.of("data", "students.dat")));
            runMenu();
        } catch (IOException ex) {
            System.out.println("Could not load or save student data: " + ex.getMessage());
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n=== Student Information Management System ===");
            System.out.println("1. Add student\n2. List students\n3. Search student\n4. Update student\n5. Delete student\n0. Exit");
            String choice = prompt("Choose an option: ");
            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": listStudents(); break;
                    case "3": searchStudent(); break;
                    case "4": updateStudent(); break;
                    case "5": deleteStudent(); break;
                    case "0": System.out.println("Goodbye."); return;
                    default: System.out.println("Choose a menu option from 0 to 5.");
                }
            } catch (IllegalArgumentException | IOException ex) {
                System.out.println("Action could not be completed: " + ex.getMessage());
            }
        }
    }

    private static void addStudent() throws IOException {
        String type = prompt("Student type (1 Regular, 2 Transferee): ");
        String id = VALIDATOR.requireText(prompt("Student ID: "), "Student ID");
        String name = VALIDATOR.requireText(prompt("Full name: "), "Name");
        String contact = VALIDATOR.requireText(prompt("Contact number: "), "Contact number");
        int year = VALIDATOR.requireYearLevel(prompt("Year level (1-4): "));
        Address address = readAddress();
        Course course = readCourse();
        Student student;
        if (type.equals("1")) {
            student = new RegularStudent(id, name, contact, year, address, course,
                    VALIDATOR.requireText(prompt("Admission date: "), "Admission date"));
        } else if (type.equals("2")) {
            student = new TransfereeStudent(id, name, contact, year, address, course,
                    VALIDATOR.requireText(prompt("Previous school: "), "Previous school"));
        } else {
            throw new IllegalArgumentException("Choose 1 for Regular or 2 for Transferee.");
        }
        manager.addStudent(student);
        System.out.println("Student saved.");
    }

    private static void listStudents() {
        if (manager.listStudents().isEmpty()) { System.out.println("No student records yet."); return; }
        manager.listStudents().forEach(s -> System.out.println(s.displayDetails()));
    }

    private static void searchStudent() {
        Optional<Student> found = manager.searchStudent(prompt("Student ID: ").trim());
        System.out.println(found.map(Student::displayDetails).orElse("Student ID was not found."));
    }

    private static void updateStudent() throws IOException {
        String id = VALIDATOR.requireText(prompt("Student ID to update: "), "Student ID");
        Student existing = manager.searchStudent(id)
                .orElseThrow(() -> new IllegalArgumentException("Student ID was not found."));
        manager.updateStudent(id,
                VALIDATOR.requireText(prompt("New full name: "), "Name"),
                VALIDATOR.requireText(prompt("New contact number: "), "Contact number"),
                VALIDATOR.requireYearLevel(prompt("New year level (1-4): ")),
                readAddress(), readCourse());
        System.out.println(existing.getStudentId() + " updated.");
    }

    private static void deleteStudent() throws IOException {
        manager.deleteStudent(VALIDATOR.requireText(prompt("Student ID to delete: "), "Student ID"));
        System.out.println("Student deleted.");
    }

    private static Address readAddress() {
        return new Address(VALIDATOR.requireText(prompt("Street: "), "Street"),
                VALIDATOR.requireText(prompt("City: "), "City"),
                VALIDATOR.requireText(prompt("Province: "), "Province"));
    }

    private static Course readCourse() {
        String departmentName = VALIDATOR.requireText(prompt("Department name: "), "Department name");
        Department department = new Department(departmentName);
        return new Course(VALIDATOR.requireText(prompt("Course name: "), "Course name"), department);
    }

    private static String prompt(String message) {
        System.out.print(message);
        return INPUT.nextLine();
    }
}
