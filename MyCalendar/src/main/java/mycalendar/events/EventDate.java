package mycalendar.events;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventDate {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
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
