package mycalendar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mycalendar.person.Person;
import mycalendar.utils.JsonUtils;

public class AuthService {

    private static final String USERS_FILE_PATH = "data/users.json";
    private List<Person> users;

    public AuthService() {
        try {
            this.users = loadUsers();
        } catch (IOException e) {
            System.err.println("Failed to load users from " + USERS_FILE_PATH + ": " + e.getMessage());
            System.err.println("Initializing with default users.");
            this.users = new ArrayList<>(); // Initialize with empty list on error
        }

        // Ensure users list is not null before checking if empty
        if (this.users == null) {
             this.users = new ArrayList<>();
        }

        if (this.users.isEmpty()) {
            System.out.println("No users found or failed to load. Creating default users.");
            this.users.add(new Person("Roger", "Chat"));
            this.users.add(new Person("Pierre", "KiRouhl"));
            saveUsers();
        }
    }

    private List<Person> loadUsers() throws IOException {
        // Use TypeReference to specify the generic type List<Person>
        return JsonUtils.loadFromJsonFile(USERS_FILE_PATH, new TypeReference<List<Person>>() {});
    }

    private void saveUsers() {
        try {
            JsonUtils.saveToJsonFile(USERS_FILE_PATH, this.users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public Optional<Person> findUser(String username) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(username))
                .findFirst();
    }

    /**
     * Attempts to log in a user.
     * 
     * @param username The username.
     * @param password The password.
     * @return An Optional containing the Person if login is successful, otherwise
     *         empty.
     */
    public Optional<Person> login(String username, String password) {
        return findUser(username)
                .filter(user -> user.checkPassword(password));
    }

    /**
     * Attempts to sign up a new user.
     * 
     * @param username The desired username.
     * @param password The desired password.
     * @return An Optional containing the newly created Person if signup is
     *         successful (e.g., username not taken), otherwise empty.
     */
    public Optional<Person> signup(String username, String password) {
        if (findUser(username).isPresent()) {
            System.err.println("Username '" + username + "' is already taken.");
            return Optional.empty();
        }
        Person newUser = new Person(username, password);
        this.users.add(newUser);
        saveUsers();
        return Optional.of(newUser);
    }
}
