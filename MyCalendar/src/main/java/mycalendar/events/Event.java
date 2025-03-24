package mycalendar.events;

import mycalendar.person.Person;

public abstract class Event {
    protected static EventID ID_STRING;
    protected EventTitle title;
    protected Person proprietaire;
    protected EventDate dateDebut;

    public Event(EventTitle title, Person proprietaire, EventDate dateDebut) {
        Event.ID_STRING = new EventID();
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
    }

    public boolean isBefore(EventDate date) {
        return dateDebut.isBefore(date);
    }

    public boolean isAfter(EventDate date) {
        return dateDebut.isAfter(date);
    }

    public boolean isWithinPeriod(EventDate debut, EventDate fin) {
        return this.isAfter(debut) && this.isBefore(fin);
    }

    public EventDate getFin() {
        return new EventDate(dateDebut.getDate().plusMinutes(duree.getDurationMinutes()));
    }

    public boolean conflictsWith(Event e2) {
        return this.isWithinPeriod(e2.dateDebut, e2.getFin()) || e2.isWithinPeriod(this.dateDebut, this.getFin());
    }

    public abstract String description();

    public EventID getID() {
        return ID_STRING;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public Person getProprietaire() {
        return proprietaire;
    }

    public EventDate getDateDebut() {
        return dateDebut;
    }

    public void setTitle(EventTitle title) {
        this.title = title;
    }

    public void setDateDebut(EventDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
