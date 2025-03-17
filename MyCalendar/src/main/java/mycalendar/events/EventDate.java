package mycalendar.events;

import java.time.LocalDateTime;

public class EventDate {
    private final LocalDateTime date;

    public EventDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isAfter(EventDate debut) {
        return date.isAfter(debut.date);
	}

    public boolean isBefore(EventDate fin) {
        return date.isBefore(fin.date);
    }

    public LocalDateTime getDate() {
        return date;
    }
}
