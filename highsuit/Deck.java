package highsuit;

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {

                int value = (i < 9) ? i + 2 : (i < 12 ? 10 : 11);
                cards.add(new Card(suit, ranks[i], value));
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.remove(0);
    }
}