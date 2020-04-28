/**
 * The class to design normal cards without any features
 */

import java.util.Objects;

public class normalCards extends Card {


    public normalCards(String color, int score, int type){
        super(color, score, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        normalCards that = (normalCards) o;
        return this.getCardScore() == that.getCardScore() && this.getColor() == that.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCardScore());
    }
}
