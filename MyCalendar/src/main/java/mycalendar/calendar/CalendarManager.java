package mycalendar.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mycalendar.events.Event;
import mycalendar.events.EventDate;
import mycalendar.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class CalendarManager {

    private static final String DATA_FILE = "./data/calendar_data.json";

    private List<Calendar> calendars = new ArrayList<>();
    private Calendar lastCalendar;

    public CalendarManager() {
        this.calendars = new ArrayList<>();
        this.lastCalendar = null;
    }

    public CalendarManager(Calendar initialCalendar) {
        this.calendars = new ArrayList<>();
        if (initialCalendar != null) {
            this.calendars.add(initialCalendar);
            this.lastCalendar = initialCalendar;
        } else {
            this.lastCalendar = null;
        }
    }

    public void addCalendar(Calendar calendarToAdd) {
        if (calendarToAdd != null && !this.calendars.contains(calendarToAdd)) {
            this.calendars.add(calendarToAdd);
            this.lastCalendar = calendarToAdd;
        }
    }

    public void removeCalendar(Calendar calendarToRemove) {
        this.calendars.remove(calendarToRemove);
        if (lastCalendar == calendarToRemove) {
            lastCalendar = calendars.isEmpty() ? null : calendars.get(0);
        }
    }

    public void saveCalendars() {
        try {
            JsonUtils.saveToJsonFile(DATA_FILE, this.calendars);
        } catch (IOException e) {
            System.err.println("Error saving calendars: " + e.getMessage());
        }
    }

    public void loadCalendars() {
        try {
            List<Calendar> loaded = JsonUtils.loadFromJsonFile(DATA_FILE, new TypeReference<List<Calendar>>() {
            });
            if (loaded != null) {
                this.calendars = loaded;
                this.lastCalendar = calendars.isEmpty() ? null : calendars.get(0);
            } else {
                this.calendars = new ArrayList<>();
                this.lastCalendar = null;
            }
        } catch (IOException e) {
            System.err.println("Could not load calendars from " + DATA_FILE + ": " + e.getMessage()
                    + ". Starting with empty list.");
            this.calendars = new ArrayList<>();
            this.lastCalendar = null;
        }
    }

    public void setActiveCalendar(Calendar calendar) {
        if (this.calendars.contains(calendar)) {
            this.lastCalendar = calendar;
        } else {
            System.err.println("Cannot set active calendar: Calendar not managed by this manager.");
        }
    }

    public Calendar getActiveCalendar() {
        if (lastCalendar != null && !calendars.contains(lastCalendar)) {
            lastCalendar = calendars.isEmpty() ? null : calendars.get(0);
        }
        return lastCalendar;
    }

    public List<Calendar> getAllCalendars() {
        return calendars;
    }

    public List<Event> getActiveCalendarEvents() {
        return (lastCalendar != null) ? lastCalendar.getEvents() : new ArrayList<>();
    }

    public void ajouterEvent(Event e) {
        if (lastCalendar != null) {
            lastCalendar.ajouterEvent(e);
            saveCalendars();
        } else {
            System.err.println("Cannot add event: No active calendar selected.");
        }
    }

    public void updateEvent(Event e) {
        if (lastCalendar != null) {
            lastCalendar.updateEvent(e);
            saveCalendars();
        } else {
            System.err.println("Cannot update event: No active calendar selected.");
        }
    }

    public void supprimerEvent(Event e) {
        if (lastCalendar != null) {
            lastCalendar.supprimerEvent(e);
            saveCalendars();
        } else {
            System.err.println("Cannot delete event: No active calendar selected.");
        }
    }

    public void afficherEvenements() {
        if (lastCalendar != null) {
            System.out.println(lastCalendar.afficherEvenements());
        } else {
            System.err.println("Cannot display events: No active calendar selected.");
        }
    }

    public List<Event> eventsDansPeriode(EventDate debut, EventDate fin) {
        return (lastCalendar != null) ? lastCalendar.eventsDansPeriode(debut, fin) : new ArrayList<>();
    }

    public boolean conflit(Event e1, Event e2) {
        return (lastCalendar != null) ? lastCalendar.conflit(e1, e2) : false;
    }

}
