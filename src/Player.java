/**
 * This class design to create players.
 */

import java.util.ArrayList;
public class Player {
    //The name of the player
    private String playerName;
    //The list of each player cards
    private ArrayList<Card> playerHand;
    //Determine whether it is a human playing or a computer
    private int playerType; //0 for pc and 1 for human
    //The score of each player hand
    private int playerScore;

    /**
     * The constructor would create a new player.
     * @param name The name of the player
     * @param type whether it is a human or computer player
     */
    public Player(String name, int type){
        this.playerName = name;
        this.playerType = type;
        playerHand = new ArrayList<>();
    }

    /**
     * this method would calculate the player score
     */
    public void setPlayerScore(){
        for (int i = 0 ; i < playerHand.size() ; i++)
            this.playerScore += playerHand.get(i).getCardScore();
    }

    /**
     * this method would get the player name
     * @return playerName field
     */
    public String getPlayerName(){
        return this.playerName;
    }

    /**
     * this method would return the player score
     * @return playerScore
     */
    public int getPlayerScore(){
        return this.playerScore;
    }

    /**
     * this method would return the type of the player, human ot computer
     * @return playerType field
     */
    public int getPlayerType(){
        return this.playerType;
    }

    /**
     * this method would return the list of player cards.
     * @return playerHand
     */
    public ArrayList<Card> getPlayerHand(){
        return this.playerHand;
    }



}
