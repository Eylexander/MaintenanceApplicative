package mycalendar;

import mycalendar.events.*;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    public List<Event> events;

    public Calendar() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(Event e) {
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

    public List<Event> getEvents() {
        return events;
    }
}
