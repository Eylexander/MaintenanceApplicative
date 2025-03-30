package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mycalendar.person.Person;

public class PersonalMeeting extends Event {

    @JsonCreator
    public PersonalMeeting(
            @JsonProperty("id") EventID id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("proprietaire") Person proprietaire,
            @JsonProperty("dateDebut") EventDate dateDebut,
            @JsonProperty("duree") EventDuration duree) {
        super(id, title, proprietaire, dateDebut, duree);
    }

    public PersonalMeeting(EventTitle title, Person proprietaire, EventDate dateDebut, EventDuration duree) {
        super(title, proprietaire, dateDebut, duree);
    }

    @Override
    public String description() {
        return "RDV : " + title.getTitle() + " à " + dateDebut.getDate().toString();
    }
}
