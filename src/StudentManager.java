import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/** Console application and student-record manager. */
public class StudentManager {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Path DATA_FILE = Path.of("data", "students.txt");
    private static final Path WEB_PAGE = Path.of("docs", "index.html");

    private final List<Student> students;

    public StudentManager() throws IOException {
        students = loadStudents();
    }

    public static void main(String[] args) {
        try {
            StudentManager manager = new StudentManager();
            if (args.length > 0 && args[0].equals("web")) manager.startWeb(8080);
            else manager.runMenu();
        } catch (IOException ex) {
            System.out.println("Could not start: " + ex.getMessage());
        }
    }

    public List<Student> listStudents() { return new ArrayList<>(students); }

    public Optional<Student> searchStudent(String studentId) {
        return students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
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
        String id = readStudentId("Student ID");
        String name = required("Full name");
        String contact = readContact("Contact number");
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
                    column(student.getStudentId(), 14),
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
        String id = readStudentId("Student ID");
        System.out.println(searchStudent(id).map(Student::displayDetails).orElse("Student ID was not found."));
    }

    private void updateStudentFromInput() throws IOException {
        String id = readStudentId("Student ID to update");
        if (searchStudent(id).isEmpty()) throw new IllegalArgumentException("Student ID was not found.");
        updateStudent(id, required("New full name"), readContact("New contact number"), readYearLevel(),
                required("New course name"), required("New department name"));
        System.out.println("Student updated.");
    }

    private void deleteStudentFromInput() throws IOException {
        deleteStudent(readStudentId("Student ID to delete"));
        System.out.println("Student deleted.");
    }

    private String required(String fieldName) {
        String value = prompt(fieldName + ": ").trim();
        if (value.isEmpty()) throw new IllegalArgumentException(fieldName + " cannot be empty.");
        return value;
    }

    private String readStudentId(String fieldName) { return toStudentId(required(fieldName)); }

    private String readContact(String fieldName) { return toContact(required(fieldName)); }

    private int readYearLevel() { return toYear(prompt("Year level (1-4): ").trim()); }

    private static String toStudentId(String value) {
        if (!value.matches("\\d{3}-\\d{5}")) throw new IllegalArgumentException("Student ID must look like 000-00000.");
        return value;
    }

    private static String toContact(String value) {
        if (!value.matches("09\\d{9}")) throw new IllegalArgumentException("Contact number must be 11 digits starting with 09, like 09000000000.");
        return value;
    }

    private static int toYear(String value) {
        try {
            int year = Integer.parseInt(value);
            if (year < 1 || year > 4) throw new IllegalArgumentException("Year level must be from 1 to 4.");
            return year;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Year level must be a whole number from 1 to 4.");
        }
    }

    // ---- Web mode: java -cp out StudentManager web ----
    private void startWeb(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/", this::handleWeb);
        server.start();
        System.out.println("Student records are at http://localhost:" + port + "  (press Ctrl+C to stop)");
    }

    private void handleWeb(HttpExchange x) throws IOException {
        String path = x.getRequestURI().getPath(), method = x.getRequestMethod();
        String origin = x.getRequestHeaders().getFirst("Origin");
        String json = "application/json; charset=utf-8";
        String type = json, body;
        int status = 200;
        try {
            if (origin != null && !origin.equals("http://localhost:" + x.getLocalAddress().getPort())) {
                status = 403; body = "{\"error\":\"Blocked.\"}";
            } else if (path.equals("/") && method.equals("GET")) {
                type = "text/html; charset=utf-8";
                body = Files.readString(WEB_PAGE, StandardCharsets.UTF_8);
            } else if (path.equals("/api/students") && method.equals("GET")) {
                List<String> items = new ArrayList<>();
                for (Student s : students) items.add(toJson(s));
                body = "[" + String.join(",", items) + "]";
            } else if (path.equals("/api/students") && method.equals("POST")) {
                Map<String, String> f = form(x);
                String id = toStudentId(field(f, "id", "Student ID"));
                String name = field(f, "name", "Full name");
                String contact = toContact(field(f, "contact", "Contact number"));
                int year = toYear(f.getOrDefault("year", "").trim());
                String course = field(f, "course", "Course name"), dept = field(f, "dept", "Department name");
                Student student;
                if ("Regular".equals(f.get("type"))) {
                    student = new RegularStudent(id, name, contact, year, course, dept, field(f, "extra", "Admission date"));
                } else if ("Transferee".equals(f.get("type"))) {
                    student = new TransfereeStudent(id, name, contact, year, course, dept, field(f, "extra", "Previous school"));
                } else {
                    throw new IllegalArgumentException("Choose Regular or Transferee.");
                }
                addStudent(student);
                status = 201;
                body = toJson(student);
            } else if (path.startsWith("/api/students/") && (method.equals("PUT") || method.equals("DELETE"))) {
                String id = toStudentId(path.substring("/api/students/".length()));
                if (method.equals("PUT")) {
                    Map<String, String> f = form(x);
                    updateStudent(id, field(f, "name", "Full name"),
                            toContact(field(f, "contact", "Contact number")),
                            toYear(f.getOrDefault("year", "").trim()),
                            field(f, "course", "Course name"), field(f, "dept", "Department name"));
                    body = toJson(searchStudent(id).get());
                } else {
                    deleteStudent(id);
                    body = "{\"ok\":true}";
                }
            } else {
                status = 404; body = "{\"error\":\"Not found.\"}";
            }
        } catch (IllegalArgumentException ex) {
            status = 400; type = json; body = "{\"error\":" + q(ex.getMessage()) + "}";
        } catch (IOException ex) {
            status = 500; type = json; body = "{\"error\":" + q(ex.getMessage()) + "}";
        }
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        x.getResponseHeaders().set("Content-Type", type);
        x.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = x.getResponseBody()) { out.write(bytes); }
    }

    private static Map<String, String> form(HttpExchange x) throws IOException {
        Map<String, String> fields = new HashMap<>();
        String raw = new String(x.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        for (String pair : raw.split("&")) {
            if (pair.isEmpty()) continue;
            String[] kv = pair.split("=", 2);
            fields.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                    kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "");
        }
        return fields;
    }

    private static String field(Map<String, String> f, String key, String label) {
        String value = f.getOrDefault(key, "").trim();
        if (value.isEmpty()) throw new IllegalArgumentException(label + " cannot be empty.");
        return value;
    }

    private static String toJson(Student s) {
        String extra = s instanceof RegularStudent
                ? ((RegularStudent) s).getAdmissionDate() : ((TransfereeStudent) s).getPreviousSchool();
        return "{\"type\":" + q(s.getStudentType()) + ",\"id\":" + q(s.getStudentId()) + ",\"name\":" + q(s.getName())
                + ",\"contact\":" + q(s.getContactNumber()) + ",\"year\":" + s.getYearLevel()
                + ",\"course\":" + q(s.getCourseName()) + ",\"dept\":" + q(s.getDepartmentName())
                + ",\"extra\":" + q(extra) + "}";
    }

    private static String q(String value) {
        StringBuilder out = new StringBuilder("\"");
        for (char ch : String.valueOf(value).toCharArray()) {
            if (ch == '"' || ch == '\\') out.append('\\').append(ch);
            else if (ch < 0x20) out.append(String.format("\\u%04x", (int) ch));
            else out.append(ch);
        }
        return out.append('"').toString();
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
                String studentId = fields.get(1);
                String contactNumber = fields.get(3);
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
            String[] fields = {student.getStudentType(), student.getStudentId(), student.getName(),
                    student.getContactNumber(), String.valueOf(student.getYearLevel()),
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