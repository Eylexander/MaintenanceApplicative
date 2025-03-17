package mycalendar;

import mycalendar.events.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mycalendar.events.Event;

public class CalendarManager {

    public Calendar calendar;
    private final Person owner;

    public CalendarManager(Person owner) {
        this.owner = owner;
        this.calendar = new Calendar();
    }

    public void ajouterEvent(Event e) {
        calendar.ajouterEvent(e);
    }

    public void updateEvent(Event e) {
        calendar.updateEvent(e);
    }

    public void supprimerEvent(Event e) {
        calendar.supprimerEvent(e);
    }

    public void afficherEvenements() {
        System.out.println(calendar.afficherEvenements());
    }

    public List<Event> eventsDansPeriode(EventDate debut, EventDate fin) {
        return calendar.getEvents().stream()
                .filter(e -> e.isWithinPeriod(debut, fin))
                .collect(Collectors.toList());
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.conflictsWith(e2) || e2.conflictsWith(e1);
    }

    public Person getOwner() {
        return owner;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
