/** Central input checks used by the console interface. */
public class InputValidator {
    public String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public int requireYearLevel(String value) {
        try {
            int year = Integer.parseInt(value.trim());
            if (year < 1 || year > 4) throw new IllegalArgumentException("Year level must be from 1 to 4.");
            return year;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Year level must be a whole number.");
        }
    }
}
