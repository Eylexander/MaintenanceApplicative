package mycalendar.calendar;

import mycalendar.events.*;
import mycalendar.person.Person;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Calendar {

    private CalendarTitle title;
    private final Person owner;
    private List<Event> events;

    public Calendar(CalendarTitle title, Person owner) {
        this.title = title;
        this.owner = owner;
        this.events = new ArrayList<>();
    }

    @JsonCreator
    public Calendar(
            @JsonProperty("title") CalendarTitle title,
            @JsonProperty("owner") Person owner,
            @JsonProperty("events") List<Event> events) {
        this.title = title;
        this.owner = owner;
        this.events = (events != null) ? new ArrayList<>(events) : new ArrayList<>();
    }

    public void ajouterEvent(Event e) {
        for (Event existingEvent : events) {
            if (conflit(existingEvent, e)) {
                throw new IllegalArgumentException("The event conflicts with an existing event.");
            }
        }
        events.add(e);
    }

    public void updateEvent(Event e) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getID() == e.getID()) {
                events.set(i, e);
                break;
            }
        }
    }

    public void supprimerEvent(Event e) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getID() == e.getID()) {
                events.remove(i);
                break;
            }
        }
    }

    public String afficherEvenements() {
        StringBuilder sb = new StringBuilder();
        for (Event e : events) {
            sb.append(e.description()).append("\n");
        }
        return sb.toString();
    }

    public List<Event> eventsDansPeriode(EventDate debut, EventDate fin) {
        return events.stream()
                .filter(e -> e.isWithinPeriod(debut, fin))
                .toList();
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.conflictsWith(e2) || e2.conflictsWith(e1);
    }

    public void setTitle(CalendarTitle title) {
        this.title = title;
    }
    
    public Person getOwner() {
        return owner;
    }

    public CalendarTitle getTitle() {
        return title;
    }

    public List<Event> getEvents() {
        return events;
    }
}
