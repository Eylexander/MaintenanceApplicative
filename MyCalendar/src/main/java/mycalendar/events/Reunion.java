package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mycalendar.person.Person;

public class Reunion extends Event {

    private EventLocation location;
    private EventParticipants participants;

    @JsonCreator
    public Reunion(
            @JsonProperty("id") EventID id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("proprietaire") Person proprietaire,
            @JsonProperty("dateDebut") EventDate dateDebut,
            @JsonProperty("duree") EventDuration duree,
            @JsonProperty("location") EventLocation location,
            @JsonProperty("participants") EventParticipants participants) {
        super(id, title, proprietaire, dateDebut, duree);
        this.location = location;
        this.participants = participants;
    }

    public Reunion(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree,
            EventLocation location, EventParticipants participants) {
        super(title, proprietaire, dateDebut, duree);
        this.location = location;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title.getTitle() + " à " + location.getLocation() + " avec "
                + String.join(", ", participants.getParticipants());
    }

    public EventLocation getLocation() {
        return location;
    }

    public EventParticipants getParticipants() {
        return participants;
    }
}
