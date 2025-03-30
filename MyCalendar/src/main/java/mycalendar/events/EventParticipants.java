package mycalendar.events;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventParticipants {

    private final List<String> participants;

    @JsonCreator
    public EventParticipants(@JsonProperty("participants") List<String> participants) {
        this.participants = participants;
    }

    public List<String> getParticipants() {
        return participants;
    }
}
