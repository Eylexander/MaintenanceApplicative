package mycalendar;

public class Person {
    
    public String firstName;
    public String lastName;

    public Person(String pseudo) {
        this.firstName = pseudo;
        this.lastName = "";
    }

    public Person(String firstName, String lastName) {
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
