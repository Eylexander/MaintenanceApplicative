package mycalendar.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Person {
    
    @JsonProperty("name")
    private final String name;

    @JsonUnwrapped
    private final Authentification authentification;

    @JsonCreator
    public Person(
            @JsonProperty("name") String pseudo,
            @JsonProperty("password") String password) {
        this.name = pseudo;
        this.authentification = new Authentification(password);
    }

    public boolean checkPassword(String password) {
        return this.authentification.checkPassword(password);
    }

    public String getName() {
        return this.name;
    }

}
