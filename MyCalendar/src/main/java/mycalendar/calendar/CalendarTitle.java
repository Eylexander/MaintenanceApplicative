package mycalendar.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarTitle {
    private String title;

    @JsonCreator
    public CalendarTitle(@JsonProperty("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
