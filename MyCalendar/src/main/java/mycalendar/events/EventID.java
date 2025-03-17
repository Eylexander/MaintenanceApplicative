package mycalendar.events;

public class EventID {
    
    private final int id;
    
    public EventID() {
        this.id = (int) (System.currentTimeMillis() + Math.abs(this.hashCode()));
    }
    
    public int getID() {
        return id;
    }

}
