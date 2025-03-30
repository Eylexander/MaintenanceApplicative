package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventFrequency {

    private final int frequency;

    @JsonCreator
    public EventFrequency(@JsonProperty("frequency") int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }
}
