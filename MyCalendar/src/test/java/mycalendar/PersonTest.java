package mycalendar;

import org.junit.jupiter.api.Test;
import mycalendar.person.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testPasswordVerification() {
        // Create a user with a password
        Person user = new Person("Alice", "securePassword123");

        // Verify the password
        assertTrue(user.checkPassword("securePassword123"));
        assertFalse(user.checkPassword("wrongPassword"));
    }

    @Test
    public void testNullPassword() {
        // Create a user with a null password
        Person user = new Person("Bob", null);

        // Verify the null password
        assertTrue(user.checkPassword(null));
        assertFalse(user.checkPassword("notNullPassword"));
    }

    @Test
    public void testGetName() {
        // Create a user and verify the name
        Person user = new Person("Charlie", "password");
        assertEquals("Charlie", user.getName());
    }
}
