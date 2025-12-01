import java.util.*;

/**
 * The class that represents a player in the card game
 */
public class Player {
    private String name;
    private int age;
    private List<Card> hand;

    /**
     * Constructs a player with name, age, and cards in hand
     *
     * @param name Player's name
     * @param age Player's age
     * @param hand Array of cards on player's hand (?)
     * @throws IllegalArgumentException if name is null/empty, or age is invalid, or hand is null
     */
    public Player(String name, int age, Card[] hand) {
        validatePlayerParameters(name, age, hand);
        this.name = name;
        this.age = age;
        this.hand = new ArrayList<>();
        if (hand != null) {
            for (Card card : hand) {
                if (card != null) {
                    this.hand.add(card);
                }
            }
        }
    }

    // updated Section

    /**
     * Get the highest valued card on player's hand
     *
     * @return the most valuable card in player's hand, return null when there is nothing left in player's hand
     */
    public Card getHighestValueCard() {
        //sp case
        if (hand.isEmpty()) {
            return null;
        }
        Card highest = hand.get(0);
        for (Card card : hand) {
            if (card.getValue() > highest.getValue()) {
                highest = card;
            }
        }
        return highest;
    }

    /**
     * Remove the specific card on player's hand
     *
     * @param card card to remove
     * @return if successfully removed the card return true, false otherwise.
     */
    public boolean removeCardFromHand(Card card) {
        return hand.remove(card);
    }

    /**
     * Constructor that initializes player with name and age, empty hand
     *
     * @param name Player's name
     * @param age Player's age
     * @throws IllegalArgumentException if name is null/empty or age is invalid
     */
    public Player(String name, int age) {
        this(name, age, new Card[0]);
    }

    /**
     * Validates a player's params
     *
     * @param name Player's name
     * @param age Player's age
     * @param hand Player's hand of cards
     * @throws IllegalArgumentException if any params above are invalid, message if name is null/empty "Player name cannot be null or empty"; message if hand array is null "Hand array cannot be null"
     */
    private void validatePlayerParameters(String name, int age, Card[] hand) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (hand == null) {
            throw new IllegalArgumentException("Hand array cannot be null");
        }
    }

    /**
     * Returns player's name
     *
     * @return Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns player's age
     *
     * @return Player's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns player's hand as an array of cards
     *
     * @return Player's hand as an array of cards
     */
    public Card[] getHand() {
        return hand.toArray(new Card[0]);
    }

    /**
     * Returns number of cards in player's hand
     *
     * @return Number of cards in player's hand
     */
    public int size() {
        return hand.size();
    }

    /**
     * Draw a card from card deck and add it to player's hand array
     *
     * @param deck The deck to draw cards from
     * @throws IllegalArgumentException if card deck is null, message "Deck cannot be null"
     */
    public void draw(Deck deck) {
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }
        Card drawnCard = deck.draw();
        if (drawnCard != null) {
            hand.add(drawnCard);
        }
    }

    /**
     * Discard a card from hand array to discard pile array
     *
     * @param card The card to discard
     * @param discardPile The discard pile to discard cards to
     * @return true if card was successfully discarded to discarded pile, false otherwise
     * @throws IllegalArgumentException if discardPile is null
     */
    public boolean discardCard(Card card, DiscardPile discardPile) {
        // if card or pile is not provided
        if (card == null) {
            return false;
        }
        if (discardPile == null) {
            throw new IllegalArgumentException("Discard pile cannot be null");
        }
        boolean removed = hand.remove(card);
        if (removed) {
            discardPile.addCard(card);
        }
        return removed;
    }

    /**
     * Returns a card from hand to card deck
     *
     * @param card The card to return
     * @param deck The card deck to return to
     * @return true if card was successfully returned to the card deck, false otherwise
     * @throws IllegalArgumentException if deck is null
     */
    public boolean returnCard(Card card, Deck deck) {
        if (card == null) {
            return false;
        }
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }
        boolean removed = hand.remove(card);
        if (removed) {
            deck.addCard(card);
        }
        return removed;
    }

    /**
     * Returns a string consist of player's name, age, and all cards in hand, if there is nothing in hand,
     * it will return "Empty hand."
     *
     * @return String representation of player with name, age, and hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", ").append(age).append(", ");

        if (hand.isEmpty()) {
            sb.append("Empty hand.");
        } else {
            for (int i = 0; i < hand.size(); i++) {
                sb.append(hand.get(i).toString());
                if (i < hand.size() - 1) {
                    sb.append(", ");
                } else {
                    sb.append(".");
                }
            }
        }

        return sb.toString();
    }
}