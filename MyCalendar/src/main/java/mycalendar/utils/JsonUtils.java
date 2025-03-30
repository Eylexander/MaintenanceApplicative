package mycalendar.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import mycalendar.calendar.Calendar;

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

    public static void saveToJsonFile(Object data, String filePath) throws IOException {
        try {
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
            Calendar calendar = objectMapper.readValue(new File(filePath), Calendar.class);
            if (calendar == null) {
                throw new IOException("Failed to load calendar from file: " + filePath);
            }
            if (clazz == Calendar.class) {
                if (clazz.isInstance(calendar)) {
                    return clazz.cast(calendar);
                } else {
                    throw new ClassCastException("Cannot cast Calendar to " + clazz.getName());
                }
            }
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON from file: " + filePath + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}