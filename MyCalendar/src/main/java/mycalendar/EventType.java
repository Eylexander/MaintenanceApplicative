package mycalendar;

public enum EventType {
    RDV_PERSONNEL("RDV_PERSONNEL"),
    REUNION("REUNION"),
    PERIODIQUE("PERIODIQUE");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
