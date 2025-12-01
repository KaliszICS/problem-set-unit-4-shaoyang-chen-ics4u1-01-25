import java.util.*;

/**
 * This class represents a discard pile of cards, all cards are stored in an array list
 *
 * @author Shaoyang Chen
 * @version 114.514
 * @since 1919.8.10
 */
public class DiscardPile {
    private List<Card> discardPile;

    /**
     * Constructor that initializes array list of discard pile with given cards
     *
     * @param cards Array of cards to initialize the discard pile.
     * @throws IllegalArgumentException if cards array is null, message "Cards array cannot be null"
     */
    public DiscardPile(Card[] cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Cards array cannot be null");
        }

        this.discardPile = new ArrayList<>();
        for (Card card : cards) {
            if (card != null) {
                this.discardPile.add(card);
            }
        }
    }

    /**
     * Default constructor that creates an empty discard pile, takes in no params and return nothing
     */
    public DiscardPile() {
        this.discardPile = new ArrayList<>();
    }

    /**
     * Return the discard pile of cards
     *
     * @return The discard pile as an array of cards
     */
    public Card[] getDiscardPile() {
        return discardPile.toArray(new Card[0]);
    }

    /**
     * Return the size of discard pile
     *
     * @return The number of cards in the discard pile
     */
    public int size() {
        return discardPile.size();
    }

    /**
     * Add a card to the discard pile
     *
     * @param card The card to add
     * @throws IllegalArgumentException if card is null, message: "Cannot add null card to discard pile"
     */
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Cannot add null card to discard pile");
        }
        discardPile.add(card);
    }

    /**
     * Removes the specified card from the discard pile
     *
     * @param card The card to remove
     * @return The removed card, or null if card is not found
     */
    public Card removeCard(Card card) {
        if (card == null) {
            return null;
        }

        int index = -1;
        for (int i = 0; i < discardPile.size(); i++) {
            if (discardPile.get(i).equals(card)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            return discardPile.remove(index);
        }
        return null;
    }

    /**
     * Removes all cards from the discard pile and returns them as an array
     *
     * @return Array of all removed cards, or empty array if pile is empty or when no cards are removed
     */
    public Card[] removeAll() {
        Card[] allCards = discardPile.toArray(new Card[0]);
        discardPile.clear();
        return allCards;
    }

    /**
     * @return A string contain of all cards in the discard pile
     */
    @Override
    public String toString() {
        if (discardPile.isEmpty()) {
            return "Empty discard pile";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < discardPile.size(); i++) {
            sb.append(discardPile.get(i).toString());
            if (i < discardPile.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(".");
        return sb.toString();
    }
}