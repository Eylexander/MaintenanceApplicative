package mycalendar.events;

import mycalendar.Person;

public abstract class Event {
    protected static EventID ID_STRING;
    protected EventTitle title;
    protected Person proprietaire;
    protected EventDate dateDebut;
    protected EventDuration duree;

    public Event(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        Event.ID_STRING = new EventID();
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.duree = duree;
    }

    public abstract String description();

    public EventID getID() {
        return ID_STRING;
    }

    public EventTitle getTitle() {
        return title;
    }

    public Person getProprietaire() {
        return proprietaire;
    }

    public EventDate getDateDebut() {
        return dateDebut;
    }

    public EventDuration getDuree() {
        return duree;
    }
}
