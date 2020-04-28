/**
 * this class design wild cards in the game
 */
public class WildCards extends Card {

    private String changedColor;
    private boolean used;

    public WildCards(String color, int score, int type) {
        super(color, score, type);
        used = false;
    }

    /**
     * return the color chosen after use the wild card
     * @param color
     */
    public void setChangedColor(String color) {
        this.changedColor = color;
    }

    public String getChangedColor() {
        return this.changedColor;
    }

    public void setUsed(boolean bool){
        this.used = bool;
    }

    public boolean getUsed(){
        return this.used;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WildCards wildCard = (WildCards) o;
        return this.getType() == wildCard.getType();
    }
}
