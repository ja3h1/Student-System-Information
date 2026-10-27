import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Reads and writes student records using Java object serialization. */
public class FileHandler {
    private final Path filePath;

    public FileHandler(Path filePath) { this.filePath = filePath; }

    public void save(List<Student> students) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) Files.createDirectories(parent);
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            output.writeObject(new ArrayList<>(students));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Student> load() throws IOException, ClassNotFoundException {
        if (!Files.exists(filePath)) return new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath))) {
            Object data = input.readObject();
            if (!(data instanceof List<?>)) throw new IOException("Saved data has an unexpected format.");
            return (List<Student>) data;
        }
    }
}
