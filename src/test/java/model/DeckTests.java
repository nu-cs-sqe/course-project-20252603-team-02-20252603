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

    @Test
    public void AddToDrawPileMiddle() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        int sizeBefore = deck.getDeck().size();
        int position = sizeBefore / 2;

        Card cardBefore = deck.getDeck().get(position - 1);
        Card cardAfter = deck.getDeck().get(position);

        deck.addToDrawPile(card, position);

        assertEquals(sizeBefore + 1, deck.getDeck().size());
        assertEquals(card, deck.getDeck().get(position));
        assertEquals(cardBefore, deck.getDeck().get(position - 1));
        assertEquals(cardAfter, deck.getDeck().get(position + 1));
    }

    @Test
    public void testAddToDrawPileEmpty() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        while (!deck.getDeck().isEmpty()) {
            deck.drawCard();
        }

        deck.addToDrawPile(card, 0);

        assertEquals(1, deck.getDeck().size());
        assertEquals(card, deck.getDeck().get(0));
    }

    @Test
    public void AddToDrawPileOutOfBounds() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        int outOfBounds = deck.getDeck().size() + 1;

        assertThrows(IllegalArgumentException.class, () -> deck.addToDrawPile(card, outOfBounds));
    }

    @Test
    public void AddToDrawPileNegativePosition() {
        Deck deck = new Deck(players, new Random(42));
        Card card = new Card(CardType.EXPLODING_KITTEN);

        assertThrows(IllegalArgumentException.class, () -> deck.addToDrawPile(card, -1));
    }

    /* Peek Top Cards Tests */
    @Test
    public void PeekTopCardsMoreThanThree() {
        Deck deck = new Deck(players, new Random(42));

        int sizeBefore = deck.getDeck().size();
        List<Card> top3 = deck.getDeck().subList(0, 3);

        List<Card> peeked = deck.peekTopCards();

        assertEquals(3, peeked.size());
        assertEquals(top3, peeked);
        assertEquals(sizeBefore, deck.getDeck().size()); // deck unchanged
    }

    @Test
    public void PeekTopCardsExactlyThree() {
        Deck deck = new Deck(players, new Random(42));

        while (deck.getDeck().size() > 3) {
            deck.drawCard();
        }

        List<Card> top3 = deck.getDeck().subList(0, 3);

        List<Card> peeked = deck.peekTopCards();

        assertEquals(3, peeked.size());
        assertEquals(top3, peeked);
        assertEquals(3, deck.getDeck().size()); // deck unchanged
    }

    @Test
    public void PeekTopCardsFewerThanThree() {
        Deck deck = new Deck(players, new Random(42));

        while (deck.getDeck().size() > 2) {
            deck.drawCard();
        }

        List<Card> top2 = deck.getDeck().subList(0, 2);

        List<Card> peeked = deck.peekTopCards();

        assertEquals(2, peeked.size());
        assertEquals(top2, peeked);
        assertEquals(2, deck.getDeck().size()); // deck unchanged
    }

    @Test
    public void PeekTopCardsEmptyDeck() {
        Deck deck = new Deck(players, new Random(42));

        while (!deck.getDeck().isEmpty()) {
            deck.drawCard();
        }

        assertThrows(IllegalStateException.class, () -> deck.peekTopCards());
    }
}
