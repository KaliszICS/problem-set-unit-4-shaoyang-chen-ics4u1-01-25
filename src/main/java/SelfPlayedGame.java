import java.util.Scanner;

/**
 * Represents a self-played card game where two players compete by playing
 * their highest value cards over 5 rounds, only username is inputted by the user
 *
 * @author Shaoyang Chen
 * @version 114.514
 * @since 1919.8.10
 */
public class SelfPlayedGame {
    private Deck deck;
    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;

    /**
     * Constructs a new SelfPlayedGame with two players.
     * Initializes a deck, and shuffles it, and then creates players with default age (1), and sets initial scores to zero.
     *
     * @param player1Name the name of the first player
     * @param player2Name the name of the second player
     */
    public SelfPlayedGame(String player1Name, String player2Name) {
        deck = new Deck();
        deck.shuffle();
        player1 = new Player(player1Name, 1);
        player2 = new Player(player2Name, 1);
        player1Score = 0;
        player2Score = 0;
    }

    /**
     * Draw 5 cards to each player from the deck.
     * If the deck doesn't have enough cards, the remaining draws will be null cards, and won't be added to players' hands.
     */
    public void drawCards() {
        for (int i = 0; i < 5; i++) {
            player1.draw(deck);
            player2.draw(deck);
        }
    }

    /**
     * Plays one round of the game.
     * Each player plays their highest value card, and the player with the higher valued card wins the round and earns a point.
     * If they tied, no points will be given to any players.
     *
     * @param round the current round number (for display purposes)
     */
    public void playRound(int round) {
        System.out.println("\n--- Round " + round + " ---");
        Card player1Card = player1.getHighestValueCard();
        player1.removeCardFromHand(player1Card);
        Card player2Card = player2.getHighestValueCard();
        player2.removeCardFromHand(player2Card);
        System.out.println(player1.getName() + " plays: " + player1Card + " (Value: " + player1Card.getValue() + ")");
        System.out.println(player2.getName() + " plays: " + player2Card + " (Value: " + player2Card.getValue() + ")");
        if (player1Card.getValue() > player2Card.getValue()) {
            player1Score++;
            System.out.println(player1.getName() + " wins this round!");
        } else if (player2Card.getValue() > player1Card.getValue()) {
            player2Score++;
            System.out.println(player2.getName() + " wins this round!");
        } else {
            System.out.println("It's a tie! No points awarded.");
        }
        System.out.println("Current Score: " + player1.getName() + " " + player1Score + " - " +
                player2.getName() + " " + player2Score);
    }

    /**
     * Displays the final results for scores on both sides.
     * Shows each player's total score and displays the winner's name or a tie.
     */
    public void displayResults() {
        System.out.println("\n****** FINAL RESULTS ******");
        System.out.println(player1.getName() + ": " + player1Score + " points");
        System.out.println(player2.getName() + ": " + player2Score + " points");
        if (player1Score > player2Score) {
            System.out.println(player1.getName() + " WINS THE GAME!");
        } else if (player2Score > player1Score) {
            System.out.println(player2.getName() + " WINS THE GAME!");
        } else {
            System.out.println("THE GAME IS A TIE!");
        }
    }

    /**
     * Runs the complete card game.
     * Draw cards, plays 5 rounds, and displays the final results.
     */
    public void playGame() {
        System.out.println("Starting High Card Game!");
        System.out.println("Players: " + player1.getName() + " vs " + player2.getName());
        drawCards();
        System.out.println("\nCards have been dealt:");
        System.out.println(player1.getName() + "'s hand: " + player1);
        System.out.println(player2.getName() + "'s hand: " + player2);
        for (int round = 1; round <= 5; round++) {
            playRound(round);
        }
        displayResults();
    }

    /**
     * The main class for this game, starting with prompt the user for player names, create a game, and start the game.
     * If a player name is empty, a default name will be assigned.
     *
     * @throws IllegalArgumentException if player names are null or empty
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Welcome to High Card Game!");
            System.out.print("Enter 1P name: ");
            String player1Name = scanner.nextLine();
            if (player1Name == null || player1Name.isEmpty()) {
                throw new IllegalArgumentException("1P name cannot be null or empty");
            }
            System.out.print("Enter 2P name: ");
            String player2Name = scanner.nextLine();
            if (player2Name == null || player2Name.isEmpty()) {
                throw new IllegalArgumentException("2P name cannot be null or empty");
            }
            SelfPlayedGame game = new SelfPlayedGame(player1Name, player2Name);
            game.playGame();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Game terminated due to invalid input.");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nGame session ended.");
        }
    }
}