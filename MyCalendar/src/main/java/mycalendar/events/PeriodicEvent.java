package mycalendar.events;

import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mycalendar.person.Person;

public class PeriodicEvent extends Event {

    private EventFrequency frequency;

    @JsonCreator
    public PeriodicEvent(
            @JsonProperty("id") EventID id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("proprietaire") Person proprietaire,
            @JsonProperty("dateDebut") EventDate dateDebut,
            @JsonProperty("duree") EventDuration duree,
            @JsonProperty("frequency") EventFrequency frequency) {
        super(id, title, proprietaire, dateDebut, duree);
        this.frequency = frequency;
    }

    public PeriodicEvent(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree,
            EventFrequency frequency) {
        super(title, proprietaire, dateDebut, duree);
        this.frequency = frequency;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.getTitle() + " tous les " + frequency.getFrequency() + " jours";
    }

@Override
public boolean isWithinPeriod(EventDate debut, EventDate fin) {
    var occurrenceDate = dateDebut.getDate();
    while (!occurrenceDate.isAfter(fin.getDate())) {
        if (!occurrenceDate.isBefore(debut.getDate())) {
            return true;
        }
        occurrenceDate = occurrenceDate.plusDays(frequency.getFrequency());
    }
    return false;
}

    @Override
    public boolean conflictsWith(Event event) {
        return false;
    }

    public EventFrequency getFrequency() {
        return frequency;
    }

    public void setDuree(EventDuration duree) {
        this.duree = duree;
    }

    public void setFrequency(EventFrequency frequency) {
        this.frequency = frequency;
    }
}
