package mycalendar.events;

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

    public EventFrequency getFrequency() {
        return frequency;
    }
}
