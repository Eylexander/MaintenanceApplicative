package mycalendar.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Authentification {
    
    private final String password;

    @JsonCreator
    public Authentification(@JsonProperty("password") String password) {
        this.password = encodePassword(password);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(encodePassword(password));
    }

    private String encodePassword(String password) {
        if (password == null) {
            return "0";
        }
        return String.valueOf(password.hashCode());
    }

    public String getPassword() {
        return password;
    }
}
