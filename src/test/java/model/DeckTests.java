package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {
    private List<Player> players;

    @BeforeEach
    public void setUp() {
        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());
        players.add(new Player());
    }

    @Test
    public void ShuffleStandardDeck() {
        int numPlayers = players.size();
        int initialCards = (4 * 11) + 1 + 1; // 4 sets + extra nope + extra see the future
        int defuseCards = 6 - numPlayers;
        int dealtCards = 7 * numPlayers;
        int explodingKittens = 4; // added at end of constructor
        int expectedSize = initialCards + defuseCards - dealtCards + explodingKittens;

        Deck deck = new Deck(players, new Random(42));
        List<Card> before = new ArrayList<>(deck.getDeck()); // snapshot before
        deck.shuffle();
        List<Card> after = deck.getDeck();

        // same size
        assertEquals(expectedSize, after.size());

        // same cards (regardless of order)
        assertTrue(after.containsAll(before));
        assertTrue(before.containsAll(after));

        // different order
        assertNotEquals(before, after);
    }

    @Test
    public void ShuffleSingleCard() {
        Deck deck = new Deck(players, new Random(42));

        // drain deck down to 1 card
        while (deck.getDeck().size() > 1) {
            deck.drawCard();
        }

        List<Card> before = new ArrayList<>(deck.getDeck());

        deck.shuffle();

        List<Card> after = deck.getDeck();

        assertEquals(1, after.size());
        assertEquals(before, after);
    }

    @Test
    public void DrawCardManyCards() {
        Deck deck = new Deck(players, new Random(42));

        int sizeBefore = deck.getDeck().size();
        Card topCard = deck.getDeck().get(0);

        Card drawn = deck.drawCard();

        assertEquals(topCard, drawn);
        assertEquals(sizeBefore - 1, deck.getDeck().size());
    }

    @Test
    public void DrawCardOneCard() {
        Deck deck = new Deck(players, new Random(42));

        while (deck.getDeck().size() > 1) {
            deck.drawCard();
        }

        Card topCard = deck.getDeck().get(0);
        Card drawn = deck.drawCard();

        assertEquals(topCard, drawn);
        assertEquals(0, deck.getDeck().size());
    }

    @Test
    public void DrawCardEmptyDeck() {
        Deck deck = new Deck(players, new Random(42));

        while (!deck.getDeck().isEmpty()) {
            deck.drawCard();
        }

        assertThrows(IllegalStateException.class, () -> deck.drawCard());
    }
}
