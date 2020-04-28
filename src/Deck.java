/**
 * This class design the deck of cards in each game.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Deck {
    //The list of cards in the deck
    private ArrayList<Card> deckOfCards;

    public Deck(){
        deckOfCards = new ArrayList<>();
    }

    /**
     * this method would get the list of cards in the deck
     */
    public ArrayList<Card> getDeckOfCards(){
        return this.deckOfCards;
    }

    /**
     * this method would create a new deck of cards and shuffle it.
     */
    public void generateNewDeck(){
        String[] colorList = new String[]{"red", "green", "blue", "yellow"};
        String[] specials = new String[] {"skip", "reverse", "draw"};
        String[] wild = new String[] {"wild", "wild+4"};
        deckOfCards.add(new normalCards("red", 0, 0));
        deckOfCards.add(new normalCards("green", 0, 0));
        deckOfCards.add(new normalCards("yellow", 0, 0));
        deckOfCards.add(new normalCards("Blue", 0, 0));
        for (int k = 0 ; k < 4 ; k++)
            for ( int j = 0 ; j < 2 ; j++) {
                for (int m = 0 ; m < 4 ; m++)
                    deckOfCards.add(new WildCards("black", 50, 13 + j));
                for (int l = 0; l < 3; l++)
                    deckOfCards.add(new MovingCards(colorList[k], 20, 10 + l));
                for (int i = 1; i < 10; i++) {
                    deckOfCards.add(new normalCards(colorList[k], i, i));
                }
            }
        Collections.shuffle(deckOfCards);
    }

    /**
     * this method would add new card to the deck
     * @param card the card to add
     */
    public void addCard(Card card){
        this.deckOfCards.add(card);
    }

    /**
     * this method would remove the card from the deck
     * @param index the card to remove from the deck
     */
     public void removeCard(int index){
        this.deckOfCards.remove(index);
     }
}
