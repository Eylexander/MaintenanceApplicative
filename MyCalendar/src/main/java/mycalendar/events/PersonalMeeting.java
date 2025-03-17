package mycalendar.events;

import mycalendar.Person;

public class PersonalMeeting extends Event {

    public PersonalMeeting(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        super(title, proprietaire, dateDebut, duree);
    }

    @Override
    public String description() {
        return "RDV : " + title.getTitle() + " à " + dateDebut.getDate().toString();
    }
}
