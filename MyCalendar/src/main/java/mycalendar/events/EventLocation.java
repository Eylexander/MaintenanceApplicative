package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventLocation {

    private final String location;

    @JsonCreator
    public EventLocation(@JsonProperty("location") String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
