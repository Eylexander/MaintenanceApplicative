package mycalendar;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import mycalendar.events.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    @Test
    public void testAjouterEvent() {
        Person owner = new Person("John", "Doe");
        CalendarManager manager = new CalendarManager(owner);
        Event event = new PersonalMeeting(new EventTitle("Dentist"), owner, new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        manager.ajouterEvent(event);
        assertEquals(1, manager.events.size());
        assertEquals("Dentist", manager.events.get(0).getTitle().getTitle());
    }

    @Test
    public void testModifTitre() {
        Person owner = new Person("John", "Doe");
        CalendarManager manager = new CalendarManager(owner);
        Event event = new PersonalMeeting(new EventTitle("Dentist"), owner, new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        manager.ajouterEvent(event);
        assertEquals(1, manager.events.size());
        assertEquals("Dentist", manager.events.get(0).getTitle().getTitle());
        event.getTitle().setTitle("Doctor");
        manager.updateEvent(event);
        assertEquals("Doctor", manager.events.get(0).getTitle().getTitle());
    }
}
