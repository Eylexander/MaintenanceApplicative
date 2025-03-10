package trivia;

public class Player {
    private final String name;
    private int place, purse;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.place = 1;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPurse() {
        return purse;
    }

    public void addPurse() {
        this.purse++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
}
