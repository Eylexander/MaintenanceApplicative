package mycalendar.person;

public class Person {
    
    private final String name;
    private final Authentification authentification;

    public Person(String pseudo, String password) {
        this.name = pseudo;
        this.authentification = new Authentification(password);
    }

    public boolean checkPassword(String password) {
        return this.authentification.checkPassword(password);
    }

    public String toString() {
        return this.name;
    }

}
