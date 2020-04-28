/**
 * This class design the whole game and manages it.
 *
 * @author Erfan
 * @version 0.0
 */

import java.util.*;

public class Gameplay {

    Scanner input = new Scanner(System.in);
    //The number of players
    private int playerNumber;
    //The list which hold the players
    private ArrayList<Player> playerList;
    //The current card in the board
    private Card currentCard;
    //The dealer of the game who gives the cards
    private Dealer dealer1;
    //The counter to determine the one who is turn to play
    private int counter;
    //The number of cards a player should get from the dealer
    private int numberOfCards;
    //Determine the direction of the game
    private int clockwiseOrAntiClockwise;
    Random rand;


    /**
     * The constructor of the class starts a new game with the given player numbers
     * @param num the number of players in this game
     */
    public Gameplay(int num){
        rand = new Random();
        dealer1 = new Dealer();
        this.playerNumber = num;
        playerList = new ArrayList<>();
        int i = 0;
        for ( ; i < num  - 1 ; i++) {
            playerList.add(new Player("player" + (i + 1), 0));
            dealer1.getPlayerHands(playerList.get(i).getPlayerHand());
        }
        playerList.add(new Player("player" + num, 1));
        dealer1.getPlayerHands(playerList.get(i).getPlayerHand());
        numberOfCards = 1;
        counter = rand.nextInt(playerNumber);
        clockwiseOrAntiClockwise = 0;
    }


    /**
     * This method would start the game and continue it to the end
     */
    public void startGame(){
        currentCard = dealer1.getTheFirstCard();
        int whoseTurn;
        if ( currentCard.getType() == 12 && ((MovingCards) currentCard).getUsedOnce() != true)
            numberOfCards = 2;
        do {
            showGamePlay();
            whoseTurn = counter % playerList.size();
            if (playerList.get(whoseTurn).getPlayerType() == 0){
                computerPlay(playerList.get(whoseTurn));
            }else{
                humanPlay(playerList.get(whoseTurn));
            }
            counter++;
            if (currentCard.getType() > 9 && currentCard.getType() < 13)
                ((MovingCards) currentCard).setUsedOnce(true);
        }while(!gameIsOver());
    }

    /**
     * This method would change the direction of the game when there is a reverse card
     */
    public void reverseGameDirection(){
        Collections.reverse(this.playerList);
        clockwiseOrAntiClockwise++;
    }

    /**
     * This method would play the human player hand
     * @param player the player whose turn is to play
     */
    public void humanPlay(Player player) {
        Card userCard = null;
        boolean flag = false;
        if ( currentCard.getType() < 12 ) {
            for (Card c : player.getPlayerHand())
                if (c.getType() == currentCard.getType() || c.getColor().equals(currentCard.getColor()) || c.getColor().equals("black"))
                    flag = true;
        }else if (currentCard.getType() == 12) {
            if (((MovingCards) currentCard).getUsedOnce() == false)
            for (Card c : player.getPlayerHand())
                if (c.getType() == currentCard.getType())
                    flag = true;
        }else if (currentCard.getType() == 12){
            if ((((MovingCards) currentCard).getUsedOnce() == true)) {
                for (Card c : player.getPlayerHand())
                    if (c.getType() == currentCard.getType() || c.getColor().equals(currentCard.getColor()) || c.getColor().equals("black"))
                        flag = true;
            }
        } else {
            for (Card c : player.getPlayerHand())
                if (c.getType() == currentCard.getType())
                    flag = true;
        }
        if ( flag == false ){
            System.out.println("you don't have any card to play");
            if ( ((WildCards) currentCard).getUsed() == true)
                numberOfCards = 1;
            dealer1.getCard(player.getPlayerHand(), numberOfCards);
            if ( currentCard.getType() == 14)
                ((WildCards) currentCard).setUsed(true);
            numberOfCards = 1;
            return;
        }
        for ( Card c : player.getPlayerHand())
            System.out.println(c.getType() + "      " + c.getColor());
        System.out.println(player.getPlayerName() + " it's your turn!\n Choose your card please: ");
        String card, color;
        do {
            card = input.next();
            color = input.next();
            if (isNumeric(card)){
                userCard = new Card (color, Integer.parseInt(card), Integer.parseInt(card));
            }else{
                switch (card){
                    case "skip" :
                        userCard = new Card (color, 20, 10);
                        break;
                    case "reverse" :
                        userCard = new Card (color, 20, 11);
                        break;
                    case "draw" :
                        userCard = new Card (color, 20, 12);
                        break;
                    case "wild" :
                        userCard = new Card (color, 50, 13);
                        break;
                    case "wild+4" :
                        userCard = new Card (color, 50, 14);
                        break;
                    default:
                        System.out.println("Invalid card!");
                }
            }
        } while (!checkCard(player, userCard));
        for ( Card c : player.getPlayerHand())
            if (c.getColor().equals(userCard.getColor()) && c.getType() == userCard.getType()) {
                player.getPlayerHand().remove(c);
                break;
            }
        if (userCard.getType() < 10) {
            currentCard = userCard;
            return;
        }else if ( userCard.getType() == 10) {
            currentCard = userCard;
            counter++;
            ((MovingCards) currentCard).setUsedOnce(true);
            return;
        }else if (userCard.getType() == 11){
            currentCard = userCard;
            this.reverseGameDirection();
            return;
        }else if (userCard.getType() == 12 ){
            currentCard = userCard;
            if (numberOfCards != 1)
                numberOfCards += 2;
            else
                numberOfCards = 2;
            return;
        }else if (userCard.getType() == 13 ) {
            currentCard = userCard;
            System.out.println("What color do you want to change to: ");
            ((WildCards) userCard).setChangedColor(input.next());
            return;
        }else{
            currentCard = userCard;
            System.out.println("What color do you want to change to: ");
            ((WildCards) userCard).setChangedColor(input.next());
            if (numberOfCards != 1)
                numberOfCards += 4;
            else
                numberOfCards = 4;
            return;
        }
    }


    /**
     * This method would check if player can play any cards
     * @param player the player who is playing
     * @param userCard the card which the player choose to play
     * @return  true if it is a possible card to play
     */
    public boolean checkCard(Player player, Card userCard){
        if (userCard == null)
            return false;
        ArrayList<Card> possibleCards = new ArrayList<>();
        if ( currentCard.getType() < 12) {
            for (Card c : player.getPlayerHand()) {
                if (c.getColor().equals(currentCard.getColor()) || c.getType() == currentCard.getType())
                    possibleCards.add(c);
            }
        }else if ( currentCard.getType() == 12){
            for (Card c : player.getPlayerHand()) {
                if (c.getType() == currentCard.getType())
                    possibleCards.add(c);
            }
        }else if ( currentCard.getType() == 13){
            for (Card c : player.getPlayerHand()) {
                if (c.getColor().equals(((WildCards) currentCard).getColor()) || c.getType() == currentCard.getType())
                    possibleCards.add(c);
            }
        }else{
            for (Card c : player.getPlayerHand()) {
                if (c.getType() == currentCard.getType())
                    possibleCards.add(c);
            }
        }
        if (possibleCards.isEmpty()){
            for ( Card c : player.getPlayerHand()){
                if (c.getType() == 13 || c.getType() == 14)
                    possibleCards.add(c);
            }
        }
        for ( Card c : possibleCards)
            if ( c.getType() == userCard.getType() || c.getColor().equals(userCard.getColor()))
                return true;

            System.out.println("Choose another one: ");
            return false;
        }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * This method would play the computer hand
     * @param player the player who is playing now
     */
    public void computerPlay(Player player){
        String[] colorList = new String[]{"red", "green", "blue", "yellow"};
        if (currentCard.getType() < 12){
            for ( Card c : player.getPlayerHand() ){
                if ( c.getColor().equals(currentCard.getColor()) || c.getType() == currentCard.getType()){
                    currentCard = c;
                    player.getPlayerHand().remove(c);
                    if ( currentCard.getType() == 10)
                        counter++;
                    else if (currentCard.getType() == 11)
                        this.reverseGameDirection();
                    return;
                }
            }
        }else if (currentCard.getType() == 12 && ((MovingCards) currentCard).getUsedOnce() == false){
            for ( Card c : player.getPlayerHand() ){
                if ( c.getType() == currentCard.getType()){
                    currentCard = c;
                    player.getPlayerHand().remove(c);
                    if (numberOfCards != 1)
                        numberOfCards += 2;
                    else
                        numberOfCards = 2;
                    return;
                }
            }
        }else if (currentCard.getType() == 12 && ((MovingCards) currentCard).getUsedOnce() == true){
            for ( Card c : player.getPlayerHand() ){
                if ( c.getColor().equals(currentCard.getColor())){
                    currentCard = c;
                    player.getPlayerHand().remove(c);
                    if ( currentCard.getType() == 10) {
                        counter++;
                        ((MovingCards) currentCard).setUsedOnce(true);
                    }
                    else if (currentCard.getType() == 11) {
                        this.reverseGameDirection();
                        ((MovingCards) currentCard).setUsedOnce(true);
                    }
                    return;
                }
            }
        }
        else if (currentCard.getType() == 13 ) {
            for ( Card c : player.getPlayerHand() ){
                if ( c.getColor().equals(((WildCards) currentCard).getChangedColor())){
                    String ccolor = colorList[counter % 4];
                    ((WildCards) c).setChangedColor(ccolor);
                    currentCard = c;
                    player.getPlayerHand().remove(c);
                    return;
                }
            }
        }else{
            for ( Card c : player.getPlayerHand() ){
                if (c.getType() == currentCard.getType()){
                    currentCard = c;
                    player.getPlayerHand().remove(c);
                    if (numberOfCards != 1)
                        numberOfCards += 4;
                    else
                        numberOfCards = 4;
                    String ccolor = colorList[counter % 4];
                    ((WildCards) c).setChangedColor(ccolor);
                    return;
                }
            }
        }
        if ( currentCard.getType() != 12) {
            for (Card c : player.getPlayerHand()) {
                if (c.getType() == 13 || c.getType() == 14) {
                    currentCard = c;
                    if ( currentCard.getType() == 14)
                        numberOfCards = 4;
                    player.getPlayerHand().remove(c);
                    return;
                }
            }
        }
        if ((currentCard.getType() == 12 &&((MovingCards) currentCard).getUsedOnce() == true) || (currentCard.getType() == 14  && ((WildCards) currentCard).getUsed() == true) )
            numberOfCards = 1;
        dealer1.getCard(player.getPlayerHand(), numberOfCards);
        numberOfCards = 1;
        return ;
    }


    /**
     * this method check if the game is over
     * @return true if the game is over
     */
    public boolean gameIsOver(){
        for (Player p : playerList)
            if (p.getPlayerHand().size() == 0) {
                showResult(p);
                return true;
            }
        return false;
    }

    /**
     * This method would show the result of the game in the end of the game
     * @param player
     */
    public void showResult(Player player){
        ArrayList<Integer> scoreBoard = new ArrayList<>();
        for (Player p : playerList)
            scoreBoard.add(p.getPlayerScore());
        Collections.sort(scoreBoard, Collections.reverseOrder());
        System.out.println("The game is over!");
        System.out.println(player.getPlayerName() + " has won the game!\nHere is the scoreboard: \n   Player        Score");
        for (int i = 0 ; i < playerList.size() ; i++){
            int score = scoreBoard.get(i);
            for (Player p : playerList)
                if (p.getPlayerScore() == score)
                    System.out.println("   " + p.getPlayerName() + "        " + p.getPlayerScore());
        }
    }

    /**
     * This would show the game play in each round
     */
    public void showGamePlay(){
        System.out.println("\n\n\n============================================================");
        for (Player p : playerList){
            if ( p.getPlayerType() == 0){
                System.out.print(p.getPlayerName() + "           ");
            }
        }
        System.out.println();
        for (Player p : playerList){
            if ( p.getPlayerType() == 0){
                System.out.print("Score: "+ p.getPlayerScore() + "          ");
            }
        }
        System.out.println();
        for (Player p : playerList){
            if ( p.getPlayerType() == 0){
                System.out.print("Cards: " + p.getPlayerHand().size() +  "          ");
            }
        }
        if (clockwiseOrAntiClockwise % 2 == 0)
            System.out.println("\n\n        clockwise");
        else
            System.out.println("\n\n        anticlockwise");
        if (currentCard.getColor().equals("red"))
            System.out.print(Color.RED);
        else if (currentCard.getColor().equals("green"))
            System.out.print(Color.GREEN);
        else if (currentCard.getColor().equals("yellow"))
            System.out.print(Color.YELLOW);
        else if (currentCard.getColor().equals("blue"))
            System.out.print(Color.BLUE);
        System.out.println("   =================");
        for (int i  = 0 ; i < 3 ; i++)
            System.out.println("  |                 |");
        if ( currentCard.getType() < 10){
            System.out.println("  |        " + currentCard.getType() + "        |");
        }else if (currentCard.getType() == 10)
            System.out.println("  |      Skip       |");
        else if ( currentCard.getType() == 11)
            System.out.println("  |    Reverse      |");
        else if (currentCard.getType() == 12)
            System.out.println("  |      Draw       |");
        else if (currentCard.getType() == 13) {
            System.out.println("  |      Wild       |");
            System.out.println("  |     " + ((WildCards) currentCard).getChangedColor() + "      |");
        }
        else if (currentCard.getType() == 14) {
            System.out.println("  |     Wild+4      |");
            System.out.println("  |     "+((WildCards) currentCard).getChangedColor()+ "      |");
        }
        for (int i  = 0 ; i < 3 ; i++)
            System.out.println("  |                 |");
        System.out.println("   =================\n\n");
        System.out.print(Color.RESET);
        for (Player p : playerList)
            if (p.getPlayerType() == 1)
                System.out.println("        " + p.getPlayerName() + "\n" + "        " + "Score: " + p.getPlayerScore() + "\n" + "        Cards: " + p.getPlayerHand().size());
}

    enum Color {
        //Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m"),    // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"),   // BLACK
        RED_BOLD("\033[1;31m"),     // RED
        GREEN_BOLD("\033[1;32m"),   // GREEN
        YELLOW_BOLD("\033[1;33m"),  // YELLOW
        BLUE_BOLD("\033[1;34m"),    // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"),    // CYAN
        WHITE_BOLD("\033[1;37m"),   // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),     // BLACK
        RED_UNDERLINED("\033[4;31m"),       // RED
        GREEN_UNDERLINED("\033[4;32m"),     // GREEN
        YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
        BLUE_UNDERLINED("\033[4;34m"),      // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"),      // CYAN
        WHITE_UNDERLINED("\033[4;37m"),     // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"),   // BLACK
        RED_BACKGROUND("\033[41m"),     // RED
        GREEN_BACKGROUND("\033[42m"),   // GREEN
        YELLOW_BACKGROUND("\033[43m"),  // YELLOW
        BLUE_BACKGROUND("\033[44m"),    // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"),    // CYAN
        WHITE_BACKGROUND("\033[47m"),   // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),     // BLACK
        RED_BRIGHT("\033[0;91m"),       // RED
        GREEN_BRIGHT("\033[0;92m"),     // GREEN
        YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
        BLUE_BRIGHT("\033[0;94m"),      // BLUE
        MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
        CYAN_BRIGHT("\033[0;96m"),      // CYAN
        WHITE_BRIGHT("\033[0;97m"),     // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"),      // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

}
