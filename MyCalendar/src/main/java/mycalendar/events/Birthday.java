package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mycalendar.person.Person;

public class Birthday extends Event {

    @JsonCreator
    public Birthday(
            @JsonProperty("id") EventID id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("proprietaire") Person proprietaire,
            @JsonProperty("dateDebut") EventDate dateDebut) {
        super(id, title, proprietaire, dateDebut, new EventDuration(0));
    }

    public Birthday(EventTitle title, Person proprietaire, EventDate dateDebut) {
        super(title, proprietaire, dateDebut, new EventDuration(0));
    }

    @Override
    public String description() {
        return "Anniversaire de " + title.getTitle() + " le " + dateDebut.getDate().toString();
    }
    
}
