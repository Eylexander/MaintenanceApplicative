package mycalendar.events;

import mycalendar.person.Person;

public class PersonalMeeting extends Event {

    private EventDuration duree;

    public PersonalMeeting(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        super(title, proprietaire, dateDebut);
        this.duree = duree;
    }

    @Override
    public String description() {
        return "RDV : " + title.getTitle() + " à " + dateDebut.getDate().toString();
    }

    public EventDuration getDuree() {
        return duree;
    }

    public void setDuree(EventDuration duree) {
        this.duree = duree;
    }
}
