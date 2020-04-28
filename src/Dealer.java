/**
 * this class design the dealer of the game
 */

import java.util.ArrayList;

public class Dealer {
    //The deck of cards each dealer has
    private Deck deck1;

    public Dealer(){
        deck1 = new Deck();
        deck1.generateNewDeck();
    }

    /**
     * get the players their card. For each player 7 cards
     * @param hand The list of player cards
     */
    public void getPlayerHands(ArrayList<Card> hand){
        for ( int i = 0 ; i < 7 ; i++){
            int max = deck1.getDeckOfCards().size();
            int rand = (int) (Math.random() * max);
            Card card = deck1.getDeckOfCards().get(rand);
            hand.add(card);
            deck1.removeCard(rand);
        }
    }

    /**
     * this method would get the player new cards
     * @param hand  the list of player cards
     * @param count the number of cards should give to the player
     */
    public void getCard(ArrayList<Card> hand, int count){
        for ( int i = 0 ; i < count ; i++) {
            hand.add(deck1.getDeckOfCards().get(0));
            deck1.removeCard(0);
        }
    }

    /**
     * this method would add the old played cards to the deck
     * @param card
     */
    public void addToDeck(Card card){
        deck1.getDeckOfCards().add(card);
    }

    /**
     * this method would choose the first card in the game to play base on that
     * @return
     */
    public Card getTheFirstCard(){
        int i = 0;
        while (deck1.getDeckOfCards().get(i).getType() > 12){
            i++;
        }
        Card card = deck1.getDeckOfCards().get(i);
        deck1.removeCard(i);
        return card;
    }
}
