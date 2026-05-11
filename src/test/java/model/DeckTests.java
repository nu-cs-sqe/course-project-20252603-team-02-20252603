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

    /* Shuffle Tests*/
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
    public void ShuffleEmptyDeck() {
        Deck deck = new Deck(players, new Random(42));

        while (!deck.getDeck().isEmpty()) {
            deck.drawCard();
        }

        assertDoesNotThrow(() -> deck.shuffle());
        assertEquals(0, deck.getDeck().size());
    }

    /* Draw Card Tests */
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

    /* Discard Card Tests */
    @Test
    public void DiscardCardEmptyPile() {
        Deck deck = new Deck(players, new Random(42));

        Card drawn = deck.drawCard();
        deck.discardCard(drawn);

        assertEquals(1, deck.getDiscard().size());
        assertEquals(drawn, deck.getDiscard().get(0));
    }

    @Test
    public void DiscardCardNonEmptyPile() {
        Deck deck = new Deck(players, new Random(42));

        for (int i = 0; i < 10; i++) {
            deck.discardCard(deck.drawCard());
        }

        Card drawn = deck.drawCard();
        deck.discardCard(drawn);

        assertEquals(11, deck.getDiscard().size());
        assertEquals(drawn, deck.getDiscard().get(10));
    }

    /* Add to Draw Pile Tests */
    @Test
    public void AddToDrawPileTop() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        int sizeBefore = deck.getDeck().size();
        deck.addToDrawPile(card, 0);

        assertEquals(sizeBefore + 1, deck.getDeck().size());
        assertEquals(card, deck.getDeck().get(0));
        assertEquals(card, deck.drawCard());
    }

    @Test
    public void AddToDrawPileBottom() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        int sizeBefore = deck.getDeck().size();
        deck.addToDrawPile(card, sizeBefore);

        assertEquals(sizeBefore + 1, deck.getDeck().size());
        assertEquals(card, deck.getDeck().get(sizeBefore));
    }

    /* Peek Top Cards Tests */
}
