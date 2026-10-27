import java.io.Serializable;

/** Address value object associated with a student. */
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String street;
    private final String city;
    private final String province;

    public Address(String street, String city, String province) {
        this.street = street;
        this.city = city;
        this.province = province;
    }

    @Override public String toString() { return street + ", " + city + ", " + province; }
}
