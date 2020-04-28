/**
 * this class design moving cards.
 */

import java.util.Objects;

public class MovingCards extends Card {
    //Show of the card has been used before
    private boolean usedOnce;

    public MovingCards(String color, int score, int type){
        super(color, score, type);
        this.usedOnce = false;
    }

    public void setUsedOnce(boolean bool){
        this.usedOnce = bool;
    }
    public boolean getUsedOnce(){
        return this.usedOnce;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovingCards that = (MovingCards) o;
        return this.getType() == that.getType() && this.getColor() == that.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
