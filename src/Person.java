import java.io.Serializable;

/** Shared information for people represented in the system. */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String contactNumber;

    protected Person(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getDetails() {
        return "Name: " + name + " | Contact: " + contactNumber;
    }
}
