package blackjack;

import java.util.*;

public class Main {

    private static boolean startNewGame = false;
    private static LinkedHashMap<String, ArrayList<Card>> playerHands = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> playerHandValues = new LinkedHashMap<>();
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

    private static int updateHandValue(String playerName) {
        int numberOfHighAces = 0;
        ArrayList<Card> playerHand = playerHands.get(playerName);
        int playerHandValue = 0;
        for (Card card : playerHand) {
            if (card.isAce()) {
                if (playerHandValue + card.getValue2() < 22) {
                    playerHandValue += card.getValue2();
                    numberOfHighAces++;
                } else {
                    playerHandValue += card.getValue1();
                }
            } else {
                playerHandValue += card.getValue1();
            }
        }
        while (playerHandValue > 21 && numberOfHighAces > 0) {
            playerHandValue -= 10; // Change an ace 11 to an ace 1
            numberOfHighAces--;
        }

        return playerHandValue;
    }

    private static boolean checkBlackjacks(String playerName) {
        return playerHandValues.get(playerName) == 21;
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

        playerScores.clear();

        for (int i=0; i < numberOfPlayers; i++) {
            playerScores.put("Player "+(i+1), 0);
        }

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
        cardValues.put("Jack",10);
        cardValues.put("Queen",10);
        cardValues.put("King",10);

        for(int i = 0; i < 4; i++) {
            for(String suit: new String[]{"Clubs", "Diamonds", "Hearts", "Spades"}) {
                for (Map.Entry<String, Integer> entry : cardValues.entrySet()) {
                    deck.add(new Card(suit, entry.getKey(), entry.getValue(), false));
                }
                deck.add(new Card(suit, "Ace", 1, true, 11));
            }
        }
    }

    private static void initRound() {
        // Deal cards to players

        playerHands.clear();
        playerHandValues.clear();

        for (int i=0; i < playerScores.size(); i++) {
            playerHands.put("Player " + (i + 1), new ArrayList<Card>());
            playerHandValues.put("Player " + (i + 1), new Integer(0));
        }
        playerHands.put("Dealer", new ArrayList<Card>());
        playerHandValues.put("Dealer", new Integer(0));

        for (Map.Entry<String, ArrayList<Card>> playerHand : playerHands.entrySet()) {
            String playerName = playerHand.getKey();
            if (playerName == "Dealer") {
                playerHand.getValue().add(dealCard(false));
            } else {
                playerHand.getValue().add(dealCard(true));
            }
            playerHand.getValue().add(dealCard(true));
            playerHandValues.put(playerName, updateHandValue(playerName));
        }


    }

    private static void playerActions() {
        // Allow each player to take action
    }

    private static void dealerActions() {

        playerHands.get("Dealer").get(0).setFaceUp(true);

        while (playerHandValues.get("Dealer") < 17) {
            playerHands.get("Dealer").add(dealCard(true));
            playerHandValues.put("Dealer", updateHandValue("Dealer"));
        }

    }

    private static void updateScores() {
        for (Map.Entry<String, Integer> entry: playerHandValues.entrySet()) {
            if (
                    (entry.getValue() < 22 && playerHandValues.get("Dealer") > 21) || 
                    (entry.getValue() < 22 && playerHandValues.get(entry.getKey()) > playerHandValues.get("Dealer"))
            ) {
                playerScores.put(entry.getKey(), playerScores.get(entry.getKey() + 1));
            }
        }
    }

    private static void showScores() {
        // Show the final player scores
        // Ask for new game
    }

    private static void displayState() {
        List<String> cardNames = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Card>> entry: playerHands.entrySet()) {
            for (Card card: playerHands.get(entry.getKey())) {
                cardNames.add(card.toString());
            }
            if (playerHandValues.get(entry.getKey())>21) {
                System.out.println(entry.getKey() + " (Value: \u001B[31m" + playerHandValues.get(entry.getKey()) + "\u001B[0m)");
            } else {
            System.out.println(entry.getKey() + " (Value: " + playerHandValues.get(entry.getKey()) + ")");
            }
            System.out.println("Cards: " + String.join(", ", cardNames)+"\n");
            cardNames.clear();
        }
    }

    public static void main(String[] args) {
        int numberOfRounds = 5;
        do {
            initGame();
            for(int i = 0; i < numberOfRounds; i++) {
                initRound();
                System.out.println(deck.size());
                if(!checkBlackjacks("Dealer")){
                    playerActions();
                    dealerActions();
                    displayState();
                    updateScores();
                } else {
                    // TODO: handle dealer blackjack
                }
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
