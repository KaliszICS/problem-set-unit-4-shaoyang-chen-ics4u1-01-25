import java.util.*;

/**
 * This class defines a card, it only provides getters since user cannot change card's details.
 * This class includes proper exception handling.
 *
 * @author Shaoyang Chen
 * @version 114.514
 * @since 1919.8.10
 */

public class Card {
    private String name;
    private String suit;
    private int value;


    /**
     * Constructs a new card with specified card info.
     *
     * @param name name or number of this card
     * @param suit suit of the card
     * @param value value of the card / worth of the card
     */

    public Card(String name, String suit, int value){
        //verify
        validateCardParameters(name, suit, value);
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    /**
     * Validates if a card is valid. Must provide all info of a card to validate
     *
     * @param name name or number of this card
     * @param suit suit of the card
     * @param value value of the card / worth of the card
     * @throws IllegalArgumentException if name of card is empty or void with message "Illegal input for card name (cannot be empty / null)"
     * @throws IllegalArgumentException if suit of card is empty or void with message "Illegal input for card suit (cannot be empty / null)"
     * @throws IllegalArgumentException if value of card is below zero with message "Illegal input for value (value cannot be negative), entered value: {provided value}"
     */

    private void validateCardParameters(String name, String suit, int value) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Illegal input for card name cannot be null or empty");
        }

        if (suit == null || suit.trim().isEmpty()) {
            throw new IllegalArgumentException("Illegal input for card suit cannot be null or empty");
        }

        if (value < 0) {
            throw new IllegalArgumentException("Illegal input for value (value cannot be negative), entered value: " + value);
        }
// Should be added?
//        // Additional validation for realistic card values
//        if (value > 20) {
//            throw new IllegalArgumentException("Card value seems unrealistic: " + value);
//        }
    }

    /**
     * Returns the name of the card.
     *
     * @return the name of the card
     */

    public String getName() {
        return name;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */

    public String getSuit() {
        return suit;
    }

    /**
     * Returns the value of the card.
     *
     * @return the value of the card
     */

    public int getValue() {
        return value;
    }

    /**
     * @return String of the card in "{name} of {suit}" format
     */

    @Override
    public String toString() {
        return name + " of " + suit;
    }

    /**
     * Check if two cards are the same based on name, suit and value
     * @param o Object to compare with
     * @return return true if name, suit and value are the same, return false if any of them are unmatched.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        if (value != card.value) return false;
        // ( true/false  ?      {run if true}      :  {run if false}  ) 三元运算符
        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        return suit != null ? suit.equals(card.suit) : card.suit == null;
    }

}
