package mycalendar.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authentification {
    
    @JsonProperty("password")
    private final String passwordString;

    public Authentification(String passwordString) {
        this.passwordString = encodePassword(passwordString);
    }

    public boolean checkPassword(String passwordString) {
        return this.passwordString.equals(encodePassword(passwordString));
    }

    private String encodePassword(String passwordString) {
        return String.valueOf(passwordString.hashCode());
    }

}
