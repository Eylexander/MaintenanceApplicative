package mycalendar;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import mycalendar.events.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

    @Test
    public void testAjouterEvent() {
        Person owner = new Person("John", "password");
        CalendarManager manager = new CalendarManager(owner);
        Event event = new PersonalMeeting(new EventTitle("Dentist"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        manager.ajouterEvent(event);
        assertEquals(1, manager.calendar.getEvents().size());
        assertEquals("Dentist", manager.calendar.getEvents().get(0).getTitle().getTitle());
        assertEquals("RDV : Dentist à 2025-03-17T10:00", manager.calendar.getEvents().get(0).description());
    }

    @Test
    public void testModifTitre() {
        Person owner = new Person("John", "password");
        CalendarManager manager = new CalendarManager(owner);
        Event event = new PersonalMeeting(new EventTitle("Dentist"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        manager.ajouterEvent(event);
        assertEquals(1, manager.calendar.getEvents().size());
        assertEquals("Dentist", manager.calendar.getEvents().get(0).getTitle().getTitle());
        event.setTitle(new EventTitle("Doctor"));
        manager.updateEvent(event);
        assertEquals("Doctor", manager.calendar.getEvents().get(0).getTitle().getTitle());
    }

    @Test
    public void testEventsDansPeriode() {
        Person owner = new Person("John", "password");
        CalendarManager manager = new CalendarManager(owner);
        Event event1 = new PersonalMeeting(new EventTitle("Meeting 1"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        Event event2 = new PersonalMeeting(new EventTitle("Meeting 2"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 18, 10, 0)), new EventDuration(30));
        PeriodicEvent periodicEvent = new PeriodicEvent(new EventTitle("Weekly Meeting"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 16, 9, 0)), new EventDuration(60), new EventFrequency(1));
        manager.ajouterEvent(event1);
        manager.ajouterEvent(event2);
        manager.ajouterEvent(periodicEvent);

        List<Event> events = manager.eventsDansPeriode(new EventDate(LocalDateTime.of(2025, 3, 17, 9, 0)),
                new EventDate(LocalDateTime.of(2025, 3, 17, 11, 0)));
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> e.getTitle().getTitle().equals("Meeting 1")));
        assertTrue(events.stream().anyMatch(e -> e.getTitle().getTitle().equals("Weekly Meeting")));
    }

    @Test
    public void testConflit() {
        Person owner = new Person("John", "password");
        CalendarManager manager = new CalendarManager(owner);
        Event event1 = new PersonalMeeting(new EventTitle("Meeting 1"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
        Event event2 = new PersonalMeeting(new EventTitle("Meeting 2"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 10, 15)), new EventDuration(30));
        Event event3 = new PersonalMeeting(new EventTitle("Meeting 3"), owner,
                new EventDate(LocalDateTime.of(2025, 3, 17, 11, 0)), new EventDuration(30));

        assertTrue(manager.conflit(event1, event2));
        assertFalse(manager.conflit(event1, event3));
    }
}
