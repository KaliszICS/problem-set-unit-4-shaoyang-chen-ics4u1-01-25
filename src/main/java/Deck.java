import java.util.*;

/**
 * This class represents a deck of playing cards.
 * This class includes proper exception handling.
 *
 * @author Shaoyang Chen
 * @version 114.514
 * @since 1919.8.10
 */
public class Deck {
    private List<Card> cards;
    private Random random;

    /**
     * Constructor that take in an array of cards
     *
     * @param cards Array of cards to initialize
     * @throws IllegalArgumentException if provided cards array is null
     */

    //Overloading Constructor Deck

    public Deck(Card[] cards) {
        validateDeckInitialization(cards);

        this.cards = new ArrayList<>();
        this.random = new Random();

        for (Card card : cards) {
            if (card != null) {
                this.cards.add(card);
            }
        }
    }

    /**
     * Default constructor which creates an unshuffled standard deck
     *
     * @throws RuntimeException if failed to initialize standard deck, and will provide error message.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        this.random = new Random();

        try {
            initializeStandardDeck();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize standard deck: " + e.getMessage(), e);
        }
    }

    /**
     * Validates the deck initialization params
     *
     * @param cards Cards array to validate
     * @throws IllegalArgumentException if cards array is null
     */
    private void validateDeckInitialization(Card[] cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Cards array is a null array!!!");
        }
    }

    /**
     * Initializes a standard deck with size of 52 cards.
     *
     * @throws RuntimeException if failed to create card, and will provide message "Failed to create card: {nameOfCard} of {suitOfCard} {Error Message}"
     * @throws IllegalStateException if created deck have wrong amount of cards
     */
    private void initializeStandardDeck() {
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] names = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        // create in order of suits
        // for (int i = 0; i < suits.length; i++) {
        //    String suit = suits[i];
        //    for(i){...}
        // }

        for (String suit : suits) {
            // create 13 for each
            for (int i = 0; i < names.length; i++) {
                try {
                    cards.add(new Card(names[i], suit, values[i]));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Failed to create card: " + names[i] + " of " + suit, e);
                }
            }
        }

        if (cards.size() != 52) {
            throw new IllegalStateException("Standard deck should have 52 cards, but has: " + cards.size());
        }
    }

    /**
     * Returns the number of the cards in deck.
     *
     * @return The number of cards currently in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Remove and return the top card from the deck
     *
     * @return The top card, or null if deck is empty after previous move
     * @throws IllegalStateException if deck is in an invalid state when drawing cards
     */
    public Card draw() {
        validateDeckState();

        if (cards.isEmpty()) {
            return null;
        }

        try {
            return cards.remove(cards.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Deck state corrupted during draw operation", e);
        }
    }

    /**
     * Shuffles the deck using Fisher-Yates shuffle algorithm
     *
     * @throws IllegalStateException if deck is empty or in invalid state, message: Shuffle operation failed
     */
    public void shuffle() {
        validateDeckState();

        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot shuffle an empty deck");
        }

        try {

            //Knuth Shuffle / Fisher-Yates Shuffle
            //Starting from the last element of the array,
            //randomly select a position (including the current position) to swap,
            //and then process forward step by step.

            for (int i = cards.size() - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                Card temp = cards.get(i);
                cards.set(i, cards.get(j));
                cards.set(j, temp);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Shuffle operation failed", e);
        }
    }

    /**
     * Adds a (1) card to the deck
     *
     * @param card The card to add
     * @throws IllegalArgumentException if card is null, message: "Cannot add null card to deck"
     */
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Cannot add null card to deck");
        }
        cards.add(card);
    }

    /**
     * Adds all cards from the provided array to the deck, then shuffles
     *
     * @param cards Array of cards to add
     * @throws IllegalArgumentException if cards array is null, message: "Cards array cannot be null"
     * @throws IllegalStateException if reshuffle process failed, message: "Reshuffle operation failed"
     */
    public void reshuffle(Card[] cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Cards array cannot be null");
        }

        try {
            for (Card card : cards) {
                if (card != null) {
                    addCard(card);
                }
            }
            shuffle();
        } catch (Exception e) {
            throw new IllegalStateException("Reshuffle operation failed", e);
        }
    }

    /**
     * Validates that the deck is in a usable or correct state
     *
     * @throws IllegalStateException if deck is null or somehow corrupted, message: "Deck has not been properly initialized"
     */
    private void validateDeckState() {
        if (cards == null) {
            throw new IllegalStateException("Deck has not been properly initialized");
        }
    }

    /**
     * @return String of how many card is currently in the deck in "Deck with {deckSize} cards" format
     */

    @Override
    public String toString() {
        return "Deck with " + size() + " cards";
    }
}