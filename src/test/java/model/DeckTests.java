package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTests {
  private List<Player> players;
  private static final int RANDOM_SEED = 42;
  private static final int STANDARD_CARD_COUNT = 4;
  private static final int STANDARD_CARD_TYPES = 11;
  private static final int SPECIAL_CARD_TYPES = 5;
  private static final int SPECIAL_CARD_COUNT = 3;
  private static final int CAT_CARD_COUNT = 2;
  private static final int CAT_CARD_TYPES = 4;
  private static final int SEE_THE_FUTURE_CARD_COUNT = 3;
  private static final int DEFUSE_CARD_COUNT = 6;
  private static final int STARTING_HAND_SIZE = 7;
  private static final int INITIAL_DISCARD_COUNT = 10;
  private static final int PEEK_CARD_COUNT = 3;

  @BeforeEach
  public void setUp() {
    players = new ArrayList<>();
    players.add(new Player());
    players.add(new Player());
    players.add(new Player());
  }

  /* Shuffle Tests*/
  @Test
  public void shuffleStandardDeck() {
    int numPlayers = players.size();
    // 4 sets + 3 sets + 2 sets + singles
    int initialCards = (STANDARD_CARD_COUNT * STANDARD_CARD_TYPES)
        + (SPECIAL_CARD_COUNT * SPECIAL_CARD_TYPES)
        + (CAT_CARD_COUNT * CAT_CARD_TYPES)
        + SEE_THE_FUTURE_CARD_COUNT;
    int defuseCards = DEFUSE_CARD_COUNT - numPlayers;
    int dealtCards = STARTING_HAND_SIZE * numPlayers;
    int explodingKittens = numPlayers - 1;
    int expectedSize = initialCards + defuseCards - dealtCards + explodingKittens;

    Deck deck = new Deck(players, new Random(RANDOM_SEED));
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
  public void shuffleSingleCard() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

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
  public void shuffleEmptyDeck() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    while (!deck.getDeck().isEmpty()) {
      deck.drawCard();
    }

    assertDoesNotThrow(() -> deck.shuffle());
    assertEquals(0, deck.getDeck().size());
  }

  /* Draw Card Tests */
  @Test
  public void drawCardManyCards() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    int sizeBefore = deck.getDeck().size();
    Card topCard = deck.getDeck().get(0);

    Card drawn = deck.drawCard();

    assertEquals(topCard, drawn);
    assertEquals(sizeBefore - 1, deck.getDeck().size());
  }

  @Test
  public void drawCardOneCard() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    while (deck.getDeck().size() > 1) {
      deck.drawCard();
    }

    Card topCard = deck.getDeck().get(0);
    Card drawn = deck.drawCard();

    assertEquals(topCard, drawn);
    assertEquals(0, deck.getDeck().size());
  }

  @Test
  public void drawCardEmptyDeck() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    while (!deck.getDeck().isEmpty()) {
      deck.drawCard();
    }

    assertThrows(IllegalStateException.class, () -> deck.drawCard());
  }

  /* Discard Card Tests */
  @Test
  public void discardCardEmptyPile() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    Card drawn = deck.drawCard();
    deck.discardCard(drawn);

    assertEquals(1, deck.getDiscard().size());
    assertEquals(drawn, deck.getDiscard().get(0));
  }

  @Test
  public void discardCardNonEmptyPile() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    for (int i = 0; i < INITIAL_DISCARD_COUNT; i++) {
      deck.discardCard(deck.drawCard());
    }

    Card drawn = deck.drawCard();
    deck.discardCard(drawn);

    assertEquals(INITIAL_DISCARD_COUNT + 1, deck.getDiscard().size());
    assertEquals(drawn, deck.getDiscard().get(INITIAL_DISCARD_COUNT));
  }

  /* Add to Draw Pile Tests */
  @Test
  public void addToDrawPileTop() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
    Card card = new Card(CardType.EXPLODING_KITTEN);

    int sizeBefore = deck.getDeck().size();
    deck.addToDrawPile(card, 0);

    assertEquals(sizeBefore + 1, deck.getDeck().size());
    assertEquals(card, deck.getDeck().get(0));
    assertEquals(card, deck.drawCard());
  }

  @Test
  public void addToDrawPileBottom() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
    Card card = new Card(CardType.EXPLODING_KITTEN);

    int sizeBefore = deck.getDeck().size();
    deck.addToDrawPile(card, sizeBefore);

    assertEquals(sizeBefore + 1, deck.getDeck().size());
    assertEquals(card, deck.getDeck().get(sizeBefore));
  }

  @Test
  public void addToDrawPileMiddle() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
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
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
    Card card = new Card(CardType.EXPLODING_KITTEN);

    while (!deck.getDeck().isEmpty()) {
      deck.drawCard();
    }

    deck.addToDrawPile(card, 0);

    assertEquals(1, deck.getDeck().size());
    assertEquals(card, deck.getDeck().get(0));
  }

  @Test
  public void addToDrawPileOutOfBounds() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
    Card card = new Card(CardType.EXPLODING_KITTEN);

    int outOfBounds = deck.getDeck().size() + 1;

    assertThrows(IllegalArgumentException.class, () -> deck.addToDrawPile(card, outOfBounds));
  }

  @Test
  public void addToDrawPileNegativePosition() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));
    Card card = new Card(CardType.EXPLODING_KITTEN);

    assertThrows(IllegalArgumentException.class, () -> deck.addToDrawPile(card, -1));
  }

  /* Peek Top Cards Tests */
  @Test
  public void peekTopCardsMoreThanThree() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    int sizeBefore = deck.getDeck().size();
    List<Card> top3 = deck.getDeck().subList(0, PEEK_CARD_COUNT);

    List<Card> peeked = deck.peekTopCards();

    assertEquals(PEEK_CARD_COUNT, peeked.size());
    assertEquals(top3, peeked);
    assertEquals(sizeBefore, deck.getDeck().size()); // deck unchanged
  }

  @Test
  public void peekTopCardsExactlyThree() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    while (deck.getDeck().size() > PEEK_CARD_COUNT) {
      deck.drawCard();
    }

    List<Card> top3 = deck.getDeck().subList(0, PEEK_CARD_COUNT);

    List<Card> peeked = deck.peekTopCards();

    assertEquals(PEEK_CARD_COUNT, peeked.size());
    assertEquals(top3, peeked);
    assertEquals(PEEK_CARD_COUNT, deck.getDeck().size()); // deck unchanged
  }

  @Test
  public void peekTopCardsFewerThanThree() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

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
  public void peekTopCardsEmptyDeck() {
    Deck deck = new Deck(players, new Random(RANDOM_SEED));

    while (!deck.getDeck().isEmpty()) {
      deck.drawCard();
    }

    assertThrows(IllegalStateException.class, () -> deck.peekTopCards());
  }
}
