package mycalendar;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    @Test
    public void testAjouterEvent() {
        CalendarManager manager = new CalendarManager();
        manager.ajouterEvent("RDV_PERSONNEL", "Dentist", "John", LocalDateTime.of(2025, 3, 17, 10, 0), 30, "", "", 0);
        assertEquals(1, manager.events.size());
        assertEquals("Dentist", manager.events.get(0).title);
    }

    @Test
    public void testEventsDansPeriode() {
        CalendarManager manager = new CalendarManager();
        manager.ajouterEvent("RDV_PERSONNEL", "Dentist", "John", LocalDateTime.of(2025, 3, 17, 10, 0), 30, "", "", 0);
        manager.ajouterEvent("REUNION", "Meeting", "John", LocalDateTime.of(2025, 3, 18, 14, 0), 60, "Office", "Alice,Bob", 0);
        List<Event> events = manager.eventsDansPeriode(LocalDateTime.of(2025, 3, 17, 0, 0), LocalDateTime.of(2025, 3, 18, 23, 59));
        assertEquals(2, events.size());
    }

    @Test
    public void testConflit() {
        CalendarManager manager = new CalendarManager();
        Event e1 = new Event("RDV_PERSONNEL", "Dentist", "John", LocalDateTime.of(2025, 3, 17, 10, 0), 30, "", "", 0);
        Event e2 = new Event("RDV_PERSONNEL", "Doctor", "John", LocalDateTime.of(2025, 3, 17, 10, 15), 30, "", "", 0);
        assertTrue(manager.conflit(e1, e2));
    }

    @Test
    public void testAfficherEvenements() {
        CalendarManager manager = new CalendarManager();
        manager.ajouterEvent("RDV_PERSONNEL", "Dentist", "John", LocalDateTime.of(2025, 3, 17, 10, 0), 30, "", "", 0);
        manager.ajouterEvent("REUNION", "Meeting", "John", LocalDateTime.of(2025, 3, 18, 14, 0), 60, "Office", "Alice,Bob", 0);
        manager.afficherEvenements();
    }
}
