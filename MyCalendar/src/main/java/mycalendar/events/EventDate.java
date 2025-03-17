package mycalendar.events;

import java.time.LocalDateTime;

public class EventDate {
    private final LocalDateTime date;

    public EventDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
