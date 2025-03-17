package mycalendar;

public class Owner {
    
    public String firstName;
    public String lastName;

    public Owner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public String toString() {
        return fullName();
    }

}
