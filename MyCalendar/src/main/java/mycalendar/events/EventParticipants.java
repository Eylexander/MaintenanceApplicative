package mycalendar.events;

import java.util.List;

public class EventParticipants {
    private final List<String> participants;

    public EventParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<String> getParticipants() {
        return participants;
    }
}
