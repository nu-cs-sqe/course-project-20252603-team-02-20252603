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
        Deck deck = new Deck(players, new Random(42));
        List<Card> before = new ArrayList<>(deck.getDeck()); // snapshot before
        deck.shuffle();
        List<Card> after = deck.getDeck();

        // same size
        assertEquals(52, after.size());

        // same cards (regardless of order)
        assertTrue(after.containsAll(before));
        assertTrue(before.containsAll(after));

        // different order
        assertNotEquals(before, after);
    }
}
