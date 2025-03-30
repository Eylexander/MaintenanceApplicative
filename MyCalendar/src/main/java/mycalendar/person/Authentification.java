package mycalendar.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authentification {

    @JsonProperty("password")
private final String password;

    @JsonCreator
    public Authentification(@JsonProperty("password") String password) {
        if (isEncoded(password)) {
            this.password = password;
        } else {
            this.password = encodePassword(password);
        }
    }

    private boolean isEncoded(String password) {
        return password != null && password.matches("^[a-fA-F0-9]{64}$");
    }

    public boolean checkPassword(String password) {
        return this.password.equals(encodePassword(password));
    }

    private String encodePassword(String password) {
        if (password == null) {
            return "0";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }

    public String getPassword() {
        return password;
    }
}
