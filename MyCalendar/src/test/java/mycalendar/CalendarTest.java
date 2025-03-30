package mycalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mycalendar.calendar.Calendar;
import mycalendar.calendar.CalendarTitle;
import mycalendar.events.EventTitle;
import mycalendar.person.Person;
import mycalendar.events.PersonalMeeting;
import mycalendar.events.EventDate;
import mycalendar.events.EventDuration;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {

    private Calendar calendar;
    private Person owner;

    @BeforeEach
    public void setUp() {
        owner = new Person("Alice", "password123");
        calendar = new Calendar(new CalendarTitle("Alice's Calendar"), owner);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Alice's Calendar", calendar.getTitle().getTitle());
    }

    @Test
    public void testGetOwner() {
        assertEquals("Alice", calendar.getOwner().getName());
    }

    @Test
    public void testAddEvent() {
        assertEquals(0, calendar.getEvents().size());
        calendar.ajouterEvent(new PersonalMeeting(
            new EventTitle("Meeting"),
            owner,
            new EventDate(java.time.LocalDateTime.of(2025, 3, 17, 10, 0)),
            new EventDuration(30)
        ));
        assertEquals(1, calendar.getEvents().size());
    }

    @Test
    public void testRemoveEvent() {
        PersonalMeeting event = new PersonalMeeting(
            new EventTitle("Meeting"),
            owner,
            new EventDate(java.time.LocalDateTime.of(2025, 3, 17, 10, 0)),
            new EventDuration(30)
        );
        calendar.ajouterEvent(event);
        assertEquals(1, calendar.getEvents().size());
        calendar.supprimerEvent(event);
        assertEquals(0, calendar.getEvents().size());
    }
}
