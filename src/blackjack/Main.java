package blackjack;

import java.util.*;

public class Main {

    private static boolean startNewGame = false;
    private static LinkedHashMap<String, ArrayList<Card>> playerHands = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> playerHandValues = new LinkedHashMap<>();
    private static LinkedHashMap<String, Integer> playerScores = new LinkedHashMap<>();
    private static ArrayList<Card> deck = new ArrayList<>();

    private static void dealCard(String playerName, boolean faceUp) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(deck.size());
        Card dealtCard = deck.get(index);

        dealtCard.setFaceUp(faceUp);
        playerHands.get(playerName).add(dealtCard);
        updateHandValue(playerName);
        deck.remove(index);

    }

    private static void updateHandValue(String playerName) {
        int numberOfHighAces = 0;
        int playerHandValue = 0;
        ArrayList<Card> playerHand = playerHands.get(playerName);
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

        playerHandValues.put(playerName, playerHandValue);
    }

    private static boolean checkBlackjacks(String playerName) {
        return playerHandValues.get(playerName) == 21;
    }

    private static void initGame() {
        // Ask number of players

        Scanner userInput = new Scanner(System.in);
        System.out.println("How many players? (1-4): ");
        int numberOfPlayers = 0;
        while (!(numberOfPlayers > 0 && numberOfPlayers < 5)) {
            try {
                numberOfPlayers = userInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter an accepted input (1-4)!");
                userInput = new Scanner(System.in);
            }
        }
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
        cardValues.put("2",2);
        cardValues.put("3",3);
        cardValues.put("4",4);
        cardValues.put("5",5);
        cardValues.put("6",6);
        cardValues.put("7",7);
        cardValues.put("8",8);
        cardValues.put("9",9);
        cardValues.put("10",10);
        cardValues.put("J",10);
        cardValues.put("Q",10);
        cardValues.put("K",10);

        for(int i = 0; i < 4; i++) {
            for(String suit: new String[]{"\u2663", "\u2666", "\u2764", "\u2660"}) {
                for (Map.Entry<String, Integer> entry : cardValues.entrySet()) {
                    deck.add(new Card(suit, entry.getKey(), entry.getValue(), false));
                }
                deck.add(new Card(suit, "A", 1, true, 11));
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
                dealCard(playerName, false);
            } else {
                dealCard(playerName, true);
            }
            dealCard(playerName, true);
        }


    }

    private static void playerActions() {
        for (Map.Entry<String, Integer> entry: playerHandValues.entrySet()) {
            boolean playerStays = false;
            if (!(entry.getKey() == "Dealer")) {
                if (playerHandValues.get(entry.getKey()) == 21) {
                    System.out.println("Blackjack!");
                }
                while (playerHandValues.get(entry.getKey()) < 21 && !playerStays) {

                    displayCurrentPlayerState(entry.getKey());
                    displayCurrentPlayerState("Dealer");
                    System.out.println(entry.getKey() + "'s turn, current hand value: " + playerHandValues.get(entry.getKey()));
                    System.out.println("Enter (1) to hit, (2) to stay");
                    Scanner scanner = new Scanner(System.in);
                    String playerAction = scanner.next();

                    if(playerAction.equals("1")){
                        dealCard(entry.getKey(), true);
                        if (playerHandValues.get(entry.getKey()) > 21) {
                            System.out.println("Your busted!");
                        }
                    } else if (playerAction.equals("2")) {
                        playerStays = true;
                    } else {
                        System.out.println("Invalid input!");
                    }
                }
            }
        }
    }

    private static void dealerActions() {

        playerHands.get("Dealer").get(0).setFaceUp(true);

        while (playerHandValues.get("Dealer") < 17) {
            dealCard("Dealer", true);
        }

    }

    private static void updateScores() {
        for (Map.Entry<String, Integer> entry: playerHandValues.entrySet()) {
            if (
                    (entry.getValue() < 22 && playerHandValues.get("Dealer") > 21) || 
                    (entry.getValue() < 22 && playerHandValues.get(entry.getKey()) > playerHandValues.get("Dealer"))
            ) {
                playerScores.put(entry.getKey(), playerScores.get(entry.getKey()) + 1);
            }
        }
    }

    private static void showScores() {
        System.out.println("\nFinal scores:");
        for (Map.Entry<String, Integer> entry: playerScores.entrySet()) {
            if(!entry.getKey().equals("Dealer")){
                System.out.println(entry.getKey()+": "+entry.getValue()+" points");
            }
        }
        Scanner input = new Scanner(System.in);
        int newRoundInput = -1;
        System.out.println("\nDo you want to play a new game?\n(0) - No, (1) - Yes");
        while (!(newRoundInput == 0 || newRoundInput == 1)) {
            try {
                newRoundInput = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter an accepted input (0-1)!");
                input = new Scanner(System.in);
            }
        }
        if (newRoundInput == 0) {
            startNewGame = false;
        } else if (newRoundInput == 1) {
            startNewGame = true;
        }
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

    private static void displayCurrentPlayerState(String playerName) {
        List<String> cardNames = new ArrayList<>();
        for (Card card: playerHands.get(playerName)) {
            cardNames.add(card.toString());
        }
        if (playerHandValues.get(playerName)>21 && !playerName.equals("Dealer")) {
            System.out.println(playerName + " (Value: \u001B[31m" + playerHandValues.get(playerName) + "\u001B[0m)");
        } else if(!playerName.equals("Dealer")){
            System.out.println(playerName + " (Value: " + playerHandValues.get(playerName) + ")");
        } else {
            System.out.println(playerName);
        }
        System.out.println("Cards: " + String.join(", ", cardNames) + "\n");
    }

    public static void main(String[] args) {
        int numberOfRounds = 5;
        Scanner nextRoundScanner = new Scanner(System.in);
        do {
            initGame();
            for(int i = 0; i < numberOfRounds; i++) {
                System.out.println("\nRound " + (i + 1) + "!\n");
                initRound();
                if(!checkBlackjacks("Dealer")){
                    playerActions();
                    dealerActions();
                    displayState();
                    updateScores();
                } else {
                    System.out.println("The dealer had a blackjack!");
                }
                System.out.println("Next round! (press enter to continue)");
                nextRoundScanner.nextLine();
            }
            showScores();
        } while (startNewGame);
    }
}
