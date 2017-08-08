package blackjack;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {

    private static boolean startNewGame = false;
    private static LinkedHashMap<String, ArrayList<Card>> playerHands = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer[]> playerHandValues = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> playerScores = new LinkedHashMap<>();
    private static ArrayList<Card> deck = new ArrayList<>();

    private static Card dealCard(boolean faceUp) {
        // Deal a card, and remove it form the deck
        Card dealtCard;

        return dealtCard;
    }

    private static void checkBlackjacks() {
        // Check if each player has a blackjack and determine what happens in each scenario
    }

    private static void initGame() {
        // Ask number of players
        // Set player information
        // Create deck
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
