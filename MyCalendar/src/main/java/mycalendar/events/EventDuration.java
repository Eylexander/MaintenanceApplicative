package mycalendar.events;

public class EventDuration {
    private final int durationMinutes;

    public EventDuration(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
