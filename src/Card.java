/**
 * This class is the super class of the cards and different types of cards extends it.
 */
public class Card {
    //The color of the card
    private final String cardColor;
    //The score of the card
    private final int cardScore;
    //The type of the card
    private final int type;
 //   public enum Color { Red, Green, Blue, Yellow }

    public Card(String color, int score, int type){
        this.cardColor = color;
        this.cardScore = score;
        this.type = type;
    }

    public int getCardScore(){
        return this.cardScore;
    }

    public String getColor(){
        return this.cardColor;
    }

    public int getType(){
        return this.type;
    }
}
