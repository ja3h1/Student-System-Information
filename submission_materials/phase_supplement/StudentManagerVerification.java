import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Standalone verification harness for the existing SIMS implementation.
 * Run from an isolated working directory so its data/students.txt does not
 * affect the project or any user records.
 */
public class StudentManagerVerification {
    private static int checks;

    public static void main(String[] args) throws Exception {
        checkValidator("toStudentId", "000-12345", true, "valid student ID accepted");
        checkValidator("toStudentId", "00-12345", false, "malformed student ID rejected");
        checkValidator("toContact", "09123456789", true, "valid contact accepted");
        checkValidator("toContact", "08123456789", false, "contact without 09 prefix rejected");
        checkValidator("toContact", "0912345", false, "short contact rejected");
        checkValidator("toYear", "1", true, "year 1 accepted");
        checkValidator("toYear", "4", true, "year 4 accepted");
        checkValidator("toYear", "0", false, "year 0 rejected");
        checkValidator("toYear", "5", false, "year 5 rejected");
        checkValidator("toYear", "abc", false, "non-numeric year rejected");

        Path dataFile = Path.of("data", "students.txt");
        if (Files.exists(dataFile)) throw new AssertionError("Harness must run in a clean isolated directory.");

        StudentManager manager = new StudentManager();
        check(manager.listStudents().isEmpty(), "starts with an empty record list");

        String specialName = "Ana|Mae\\Santos\nJr";
        RegularStudent regular = new RegularStudent("001-00001", specialName, "09123456789", 1,
                "Computer Science", "Computing", "2026-06-01");
        TransfereeStudent transferee = new TransfereeStudent("002-00002", "Ben Cruz", "09987654321", 2,
                "Information Systems", "Computing", "North High");
        manager.addStudent(regular);
        manager.addStudent(transferee);
        check(manager.listStudents().size() == 2, "adds both student subtypes");
        check(manager.searchStudent("001-00001").orElseThrow() instanceof RegularStudent,
                "search returns the regular student");
        check(manager.searchStudent("002-00002").orElseThrow() instanceof TransfereeStudent,
                "search returns the transferee student");
        check(manager.searchStudent("999-99999").isEmpty(), "search reports an absent ID as empty");
        expectIllegalArgument(() -> manager.addStudent(regular), "duplicate student ID rejected");

        manager.updateStudent("001-00001", "Ana Santos", "09111222333", 3, "Software Engineering", "Engineering");
        Student updated = manager.searchStudent("001-00001").orElseThrow();
        check(updated.getName().equals("Ana Santos") && updated.getYearLevel() == 3
                && updated.getCourseName().equals("Software Engineering"), "updates common student fields");

        StudentManager reloaded = new StudentManager();
        check(reloaded.listStudents().size() == 2, "records reload from the saved file");
        check(reloaded.searchStudent("001-00001").orElseThrow().getName().equals("Ana Santos"),
                "updated values survive reload");
        check(reloaded.searchStudent("002-00002").orElseThrow() instanceof TransfereeStudent,
                "transferee subtype survives reload");
        // Add a separate record to verify delimiter, backslash, and newline escaping on save/load.
        RegularStudent escaped = new RegularStudent("003-00003", specialName, "09123456789", 1,
                "Course|Name", "Dept\\Name", "2026-06-01");
        reloaded.addStudent(escaped);
        StudentManager escapedReload = new StudentManager();
        Student escapedRead = escapedReload.searchStudent("003-00003").orElseThrow();
        check(escapedRead.getName().equals(specialName), "pipe, backslash, and newline survive save/load");
        check(escapedRead.getCourseName().equals("Course|Name") && escapedRead.getDepartmentName().equals("Dept\\Name"),
                "escaped course and department values survive save/load");

        escapedReload.deleteStudent("002-00002");
        check(escapedReload.searchStudent("002-00002").isEmpty(), "deletes an existing record");
        expectIllegalArgument(() -> escapedReload.deleteStudent("999-99999"), "deleting an absent record is rejected");
        StudentManager afterDelete = new StudentManager();
        check(afterDelete.searchStudent("002-00002").isEmpty(), "deletion survives reload");
        expectIllegalArgument(() -> afterDelete.updateStudent("999-99999", "X", "09123456789", 1, "C", "D"),
                "updating an absent record is rejected");

        System.out.println("PASS: " + checks + " verification checks passed.");
    }

    private static void checkValidator(String methodName, String value, boolean valid, String label) throws Exception {
        Method method = StudentManager.class.getDeclaredMethod(methodName, String.class);
        method.setAccessible(true);
        boolean accepted;
        try {
            method.invoke(null, value);
            accepted = true;
        } catch (InvocationTargetException ex) {
            if (!(ex.getCause() instanceof IllegalArgumentException)) throw ex;
            accepted = false;
        }
        check(accepted == valid, label);
    }

    private static void expectIllegalArgument(ThrowingAction action, String label) throws Exception {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            check(true, label);
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException: " + label);
    }

    private static void check(boolean condition, String label) {
        checks++;
        if (!condition) throw new AssertionError("Check failed: " + label);
        System.out.println("PASS: " + label);
    }

    @FunctionalInterface
    private interface ThrowingAction { void run() throws Exception; }
}
