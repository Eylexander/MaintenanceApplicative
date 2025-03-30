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
		PeriodicEvent periodicEvent = new PeriodicEvent(new EventTitle("Daily Meeting"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 16, 9, 15)), new EventDuration(30), new EventFrequency(1));
		Event event1 = new PersonalMeeting(new EventTitle("Meeting 1"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
		Event event2 = new PersonalMeeting(new EventTitle("Meeting 2"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 18, 10, 0)), new EventDuration(30));
		manager.ajouterEvent(periodicEvent);
		manager.ajouterEvent(event1);
		manager.ajouterEvent(event2);

		List<Event> events = manager.eventsDansPeriode(new EventDate(LocalDateTime.of(2025, 3, 17, 9, 0)),
				new EventDate(LocalDateTime.of(2025, 3, 17, 11, 0)));

		assertEquals(2, events.size());
		assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Meeting 1")));
		assertTrue(events.stream().anyMatch(e -> e.getTitle().equals("Daily Meeting")));
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

	@Test
	public void testAddCalendar() {
		Calendar calendar2 = new Calendar(new CalendarTitle("John's Second Calendar"), owner);
		manager.addCalendar(calendar2);

		assertEquals(2, manager.getCalendars().size());
		assertTrue(manager.getCalendars().contains(calendar2));

		manager.setCalendar(calendar);
		
		assertEquals(2, manager.getCalendars().size());
	}

	@Test
	public void testAddEventConflict() {
		Event event1 = new PersonalMeeting(new EventTitle("Meeting 1"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
		Event event2 = new PersonalMeeting(new EventTitle("Meeting 2"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 15)), new EventDuration(30));
		manager.ajouterEvent(event1);

		assertThrows(IllegalArgumentException.class, () -> manager.ajouterEvent(event2));
	}

	@Test
	public void testRemoveCalendar() {
		Calendar calendar2 = new Calendar(new CalendarTitle("John's Second Calendar"), owner);
		manager.addCalendar(calendar2);

		assertEquals(2, manager.getCalendars().size());
		assertTrue(manager.getCalendars().contains(calendar2));

		manager.removeCalendar(calendar2);

		assertEquals(1, manager.getCalendars().size());
		assertFalse(manager.getCalendars().contains(calendar2));
	}

	@Test
	public void testSaveCalendar() {
		Event event = new PersonalMeeting(new EventTitle("Dentist"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 17, 10, 0)), new EventDuration(30));
		manager.ajouterEvent(event);

		manager.saveCalendar(calendar);

		Calendar loadedCalendar = manager.loadCalendar();

		assertEquals(calendar.getTitle().getTitle(), loadedCalendar.getTitle().getTitle());
		assertEquals(calendar.getOwner().getName(), loadedCalendar.getOwner().getName());
		assertEquals(calendar.getEvents().size(), loadedCalendar.getEvents().size());

		assertEquals(event.getTitle(), loadedCalendar.getEvents().get(0).getTitle());
		assertEquals(event.getID().getID(), loadedCalendar.getEvents().get(0).getID().getID());

		Event periodicEvent = new PeriodicEvent(new EventTitle("Daily Meeting"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 16, 6, 15)), new EventDuration(30), new EventFrequency(1));
		manager.ajouterEvent(periodicEvent);

		Event reunionEvent = new Reunion(new EventTitle("Weekly Meeting"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 18, 10, 0)), new EventDuration(30), new EventLocation("Office"), new EventParticipants(List.of("Alice", "Bob")));
		manager.ajouterEvent(reunionEvent);

		Event birthdayEvent = new Birthday(new EventTitle("Birthday"), owner,
				new EventDate(LocalDateTime.of(2025, 3, 20, 10, 0)));
		manager.ajouterEvent(birthdayEvent);

		manager.saveCalendar(loadedCalendar);

		Calendar loadedCalendar2 = manager.loadCalendar();

		assertEquals(loadedCalendar.getTitle().getTitle(), loadedCalendar2.getTitle().getTitle());
		assertEquals(loadedCalendar.getOwner().getName(), loadedCalendar2.getOwner().getName());

		assertEquals(loadedCalendar.getEvents().size(), loadedCalendar2.getEvents().size());

		assertEquals(event.getTitle(), loadedCalendar2.getEvents().get(0).getTitle());
		assertEquals(event.getID().getID(), loadedCalendar2.getEvents().get(0).getID().getID());

		assertEquals(periodicEvent.getTitle(), loadedCalendar2.getEvents().get(1).getTitle());
		assertEquals(periodicEvent.getID().getID(), loadedCalendar2.getEvents().get(1).getID().getID());

		assertEquals(reunionEvent.getTitle(), loadedCalendar2.getEvents().get(2).getTitle());
		assertEquals(reunionEvent.getID().getID(), loadedCalendar2.getEvents().get(2).getID().getID());

		assertEquals(birthdayEvent.getTitle(), loadedCalendar2.getEvents().get(3).getTitle());
		assertEquals(birthdayEvent.getID().getID(), loadedCalendar2.getEvents().get(3).getID().getID());
		
	}
}
