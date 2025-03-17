package mycalendar;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    @Test
    public void testAjouterEvent() {
        Owner owner = new Owner("John", "Doe");
        CalendarManager manager = new CalendarManager(owner);
        Event event = new Event("RDV_PERSONNEL", "Dentist", "John", LocalDateTime.of(2025, 3, 17, 10, 0), 30, "", "", 0);
        manager.ajouterEvent(event);
        assertEquals(1, manager.events.size());
        assertEquals("Dentist", manager.events.get(0).title);
    }
}
