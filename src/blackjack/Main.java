package blackjack;

import java.util.*;


public class Main {

    private static boolean startNewGame = false;
    private static LinkedHashMap<String, ArrayList<Card>> playerHands = new LinkedHashMap<>();
    private static LinkedHashMap<String, ArrayList<Integer>> playerHandValues = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> playerScores = new LinkedHashMap<>();
    private static ArrayList<Card> deck = new ArrayList<>();

    private static Card dealCard(boolean faceUp) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(deck.size());
        Card dealtCard = deck.get(index);

        dealtCard.setFaceUp(faceUp);
        deck.remove(index);

        return dealtCard;
    }

    private static boolean checkBlackjacks(String playerName) {
        return playerHandValues.get(playerName).contains(21);
    }

    private static void initGame() {
        // Ask number of players

        Scanner userInput = new Scanner(System.in);
        System.out.println("How many players? (1-4): ");
        int numberOfPlayers = userInput.nextInt();
        if (numberOfPlayers < 1) {
            numberOfPlayers = 1;
        } else if (numberOfPlayers > 4) {
            numberOfPlayers = 4;
        }

        // Set player information

        playerHands.clear();
        playerHandValues.clear();
        playerScores.clear();
        for (int i=0; i < numberOfPlayers; i++) {
            playerHands.put("Player "+(i+1), new ArrayList<Card>());
            playerHandValues.put("Player "+(i+1), new ArrayList<Integer>());
            playerScores.put("Player "+(i+1), 0);
        }
        playerHands.put("Dealer", new ArrayList<Card>());
        playerHandValues.put("Dealer", new ArrayList<Integer>());

        // Create deck

        Map<String, Integer> cardValues = new HashMap<>();
        cardValues.put("Two",2);
        cardValues.put("Three",3);
        cardValues.put("Four",4);
        cardValues.put("Five",5);
        cardValues.put("Six",6);
        cardValues.put("Seven",7);
        cardValues.put("Eight",8);
        cardValues.put("Nine",9);
        cardValues.put("Ten",10);
        cardValues.put("Jumbo",10);
        cardValues.put("Queen",10);
        cardValues.put("King",10);

        for(int i = 0; i < 4; i++) {
            for(String suit: new String[]{"Clubs", "Diamonds", "Heards", "Spades"}) {
                for (Map.Entry<String, Integer> entry : cardValues.entrySet()) {
                    deck.add(new Card(suit, entry.getKey(), entry.getValue(), false));
                }
                deck.add(new Card(suit, "Ace", 1, true, 10));
            }
        }
    }

    private static void initRound() {
        // Deal cards to players
        checkBlackjacks();
    }

    private static void playerActions() {
        // Allow each player to take action
    }

    private static void dealerActions() {
        // Do the dealer actions
    }

    private static void updateScores() {
        // Update player scores
    }

    private static void showScores() {
        // Show the final player scores
        // Ask for new game
    }

    public static void main(String[] args) {
        int numberOfRounds = 5;
        do {
            initGame();
            for(int i = 0; i < numberOfRounds; i++) {
                initRound();
                playerActions();
                dealerActions();
                updateScores();
            }
            showScores();
        } while (startNewGame);
    }
}

/*
Dealer: [Z] fjslf, ????
Player 1: [X] fjsdljfs, fjslfjsl
Player 2: [Y] fjlsfj, guslfjs

Player 1's turn:
*/
