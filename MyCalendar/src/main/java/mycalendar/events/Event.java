package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import mycalendar.person.Person;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Birthday.class, name = "Birthday"),
    @JsonSubTypes.Type(value = PersonalMeeting.class, name = "PersonalMeeting"),
    @JsonSubTypes.Type(value = Reunion.class, name = "Reunion"),
    @JsonSubTypes.Type(value = PeriodicEvent.class, name = "PeriodicEvent")
})
public abstract class Event {

    protected EventID eventID;
    protected EventTitle title;
    protected Person proprietaire;
    protected EventDuration duree;
    protected EventDate dateDebut;

    public Event(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        this.eventID = new EventID();
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.duree = duree;
    }

    public Event(EventID eventID, EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        this.eventID = eventID;
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.duree = duree;
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

    public EventDate whenFin() {
        return new EventDate(dateDebut.getDate().plusMinutes(duree.getDurationMinutes()));
    }

    public boolean conflictsWith(Event e2) {
        return this.isWithinPeriod(e2.dateDebut, e2.whenFin()) || e2.isWithinPeriod(this.dateDebut, this.whenFin());
    }

    public abstract String description();

    public EventID getID() {
        return this.eventID;
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

    public EventDuration getDuree() {
        return duree;
    }

    public void setTitle(EventTitle title) {
        this.title = title;
    }

    public void setDateDebut(EventDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDuree(EventDuration duree) {
        this.duree = duree;
    }
}
