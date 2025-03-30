package mycalendar.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    public static void saveToJsonFile(String filePath, Object data) throws IOException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON for serialization: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + filePath + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static <T> T loadFromJsonFile(String filePath, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON from file: " + filePath + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Loads data from a JSON file into an object of the specified generic type.
     * Uses TypeReference to handle generic types like List<T> or Map<K, V>.
     *
     * @param filePath The path to the JSON file.
     * @param typeRef  A TypeReference describing the target type (including generics).
     * @param <T>      The target type.
     * @return An object of type T deserialized from the file.
     * @throws IOException If there's an error reading the file or parsing JSON.
     */
    public static <T> T loadFromJsonFile(String filePath, TypeReference<T> typeRef) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            // Return null or throw a specific exception if the file doesn't exist,
            // depending on desired behavior. Here, we might return null or an empty structure
            // if appropriate, but throwing seems safer for general use.
            // For AuthService, it expects a list, so returning null might cause NPEs later.
            // Throwing allows the caller (AuthService constructor) to handle the missing file case.
             throw new IOException("File not found: " + filePath);
             // Alternatively, could return default value:
             // try { return typeRef.getType().getClass().getDeclaredConstructor().newInstance(); } catch (Exception e) { throw new IOException("File not found and failed to create default instance: " + filePath, e); }
        }
        try {
            return objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON from file: " + filePath + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
