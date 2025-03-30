package mycalendar.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mycalendar.events.Event;
import mycalendar.events.EventDate;
import mycalendar.utils.JsonUtils;

public class CalendarManager {

    private static final String DATA_FILE = "./data/calendar_data.json";

    private List<Calendar> calendar = new ArrayList<>();
    private Calendar lastCalendar;

    public CalendarManager(Calendar calendar) {
        this.calendar.add(calendar);
        this.lastCalendar = calendar;
    }

    public void ajouterEvent(Event e) {
        lastCalendar.ajouterEvent(e);
    }

    public void updateEvent(Event e) {
        lastCalendar.updateEvent(e);
    }

    public void supprimerEvent(Event e) {
        lastCalendar.supprimerEvent(e);
    }

    public void afficherEvenements() {
        System.out.println(lastCalendar.afficherEvenements());
    }

    public List<Event> eventsDansPeriode(EventDate debut, EventDate fin) {
        return lastCalendar.eventsDansPeriode(debut, fin);
    }

    public boolean conflit(Event e1, Event e2) {
        return lastCalendar.conflit(e1, e2);
    }

    public void addCalendar(Calendar calendar) {
        if (!this.calendar.contains(calendar)) {
            this.calendar.add(calendar);
        }
    }

    public void removeCalendar(Calendar calendar) {
        this.calendar.remove(calendar);
    }

    public void saveCalendar(Calendar calendar) {
        try {
            JsonUtils.saveToJsonFile(calendar, DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Calendar loadCalendar() {
        try {
            Calendar calendar = JsonUtils.loadFromJsonFile(DATA_FILE, Calendar.class);
            this.addCalendar(calendar);
            this.lastCalendar = calendar;
            return calendar;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setCalendar(Calendar calendar) {
        addCalendar(calendar);
        this.lastCalendar = calendar;
    }

    public Calendar getCalendar() {
        return lastCalendar;
    }

    public List<Calendar> getCalendars() {
        return calendar;
    }

    public List<Event> getEvents() {
        return lastCalendar.getEvents();
    }
}
