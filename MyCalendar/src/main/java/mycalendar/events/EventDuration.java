package mycalendar.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventDuration {
    private final int durationMinutes;

    @JsonCreator
    public EventDuration(@JsonProperty("durationMinutes") int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
