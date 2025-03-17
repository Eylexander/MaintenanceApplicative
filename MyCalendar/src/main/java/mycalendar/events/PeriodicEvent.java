package mycalendar.events;

import java.time.temporal.ChronoUnit;

import mycalendar.Person;

public class PeriodicEvent extends Event {
    private EventFrequency frequency;

    public PeriodicEvent(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree, EventFrequency frequency) {
        super(title, proprietaire, dateDebut, duree);
        this.frequency = frequency;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.getTitle() + " tous les " + frequency.getFrequencyDays() + " jours";
    }

    @Override
    public boolean isWithinPeriod(EventDate debut, EventDate fin) {
        return dateDebut.getDate().until(fin.getDate(), ChronoUnit.DAYS) % frequency.getFrequencyDays() == 0;
    }

    @Override
    public boolean conflictsWith(Event event) {
        return false;
    }

    public EventFrequency getFrequency() {
        return frequency;
    }
}
