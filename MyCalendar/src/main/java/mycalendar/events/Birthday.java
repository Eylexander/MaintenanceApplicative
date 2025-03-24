package mycalendar.events;

import mycalendar.person.Person;

public class Birthday extends Event {

    public Birthday(EventTitle title, Person proprietaire, EventDate dateDebut) {
        super(title, proprietaire, dateDebut);
    }

    @Override
    public String description() {
        return "Anniversaire de " + title.getTitle() + " le " + dateDebut.getDate().toString();
    }
    
}
