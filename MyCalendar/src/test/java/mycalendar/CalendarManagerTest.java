package mycalendar;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mycalendar.events.*;
import mycalendar.calendar.*;
import mycalendar.person.Person;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {

	private Person owner;
	private Calendar calendar;
	private CalendarManager manager;

	@BeforeEach
	public void setUp() {
		owner = new Person("John", "password");
		calendar = new Calendar(new CalendarTitle("John's Calendar"), owner);
		manager = new CalendarManager(calendar);
	}

	@Test
	public void testAjouterEvent() {
		Event event = new PersonalMeeting(new EventTitle("Dentist"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
		manager.ajouterEvent(event);

		assertEquals(1, manager.getEvents().size());
		assertEquals("Dentist", manager.getEvents().get(0).getTitle());
		assertEquals("RDV : Dentist à 2025-03-17T10:00", manager.getEvents().get(0).description());
	}

	@Test
	public void testModifTitre() {
		Event event = new PersonalMeeting(new EventTitle("Dentist"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
		manager.ajouterEvent(event);

		assertEquals(1, manager.getEvents().size());
		assertEquals("Dentist", manager.getEvents().get(0).getTitle());

		event.setTitle(new EventTitle("Doctor"));
		manager.updateEvent(event);

		assertEquals("Doctor", manager.getEvents().get(0).getTitle());
	}

	@Test
	public void testEventsDansPeriode() {
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
		assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Meeting 1")));
		assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Weekly Meeting")));
	}

	@Test
	public void testConflit() {
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
