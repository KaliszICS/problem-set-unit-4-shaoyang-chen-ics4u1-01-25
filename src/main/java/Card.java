public class Card {
    private String name;
    private String suit;
    private int value;
    public Card(String name, String suit, int value){}

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card [name=" + name + ", suit=" + suit + ", value=" + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        if (value != card.value) return false;
        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        return suit != null ? suit.equals(card.suit) : card.suit == null;
    }

}
