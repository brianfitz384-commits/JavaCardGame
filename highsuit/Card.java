package highsuit;

public class Card {
    private final String suit;
    private final String rank;
    private final int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() { return suit; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        String symbol = "";
        switch (suit.toLowerCase()) {
            case "hearts": symbol = "♥"; break;
            case "diamonds": symbol = "♦"; break;
            case "clubs": symbol = "♣"; break;
            case "spades": symbol = "♠"; break;
        }
        return rank + symbol;
    }
}