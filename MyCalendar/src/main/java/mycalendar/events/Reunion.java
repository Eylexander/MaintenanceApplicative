package mycalendar.events;

import mycalendar.person.Person;

public class Reunion extends Event {

    private EventLocation location;
    private EventParticipants participants;

    public Reunion(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree, EventLocation location, EventParticipants participants) {
        super(title, proprietaire, dateDebut, duree);
        this.location = location;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title.getTitle() + " à " + location.getLocation() + " avec " + String.join(", ", participants.getParticipants());
    }

    public EventLocation getLocation() {
        return location;
    }

    public EventParticipants getParticipants() {
        return participants;
    }
}
