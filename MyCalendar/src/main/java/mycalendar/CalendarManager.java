package mycalendar;
import mycalendar.events.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mycalendar.events.Event;

public class CalendarManager {
    public List<Event> events;
    private final Person owner;

    public CalendarManager(Person owner) {
        this.owner = owner;
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(Event e) {
        events.add(e);
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e instanceof PeriodicEvent) {
                PeriodicEvent periodicEvent = (PeriodicEvent) e;
                LocalDateTime temp = periodicEvent.getDateDebut().getDate();
                while (temp.isBefore(fin)) {
                    if (!temp.isBefore(debut)) {
                        result.add(e);
                        break;
                    }
                    temp = temp.plusDays(periodicEvent.getFrequency().getFrequencyDays());
                }
            } else if (!e.getDateDebut().getDate().isBefore(debut) && !e.getDateDebut().getDate().isAfter(fin)) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        LocalDateTime fin1 = e1.getDateDebut().getDate().plusMinutes(e1.getDuree().getDurationMinutes());
        LocalDateTime fin2 = e2.getDateDebut().getDate().plusMinutes(e2.getDuree().getDurationMinutes());

        if (e1 instanceof PeriodicEvent || e2 instanceof PeriodicEvent) {
            return false; // Simplification abusive
        }

        if (e1.getDateDebut().getDate().isBefore(fin2) && fin1.isAfter(e2.getDateDebut().getDate())) {
            return true;
        }
        return false;
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}
