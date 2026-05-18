package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
  private List<Card> deck = new ArrayList<Card>();
  private List<Card> discard = new ArrayList<Card>();
  private int numPlayers = 0;
  private Random random;
  private static final int STANDARD_CARD_COUNT = 4;
  private static final int SPECIAL_CARD_COUNT = 3;
  private static final int SEE_THE_FUTURE_CARD_COUNT = 3;
  private static final int DEFUSE_CARD_COUNT = 6;
  private static final int STARTING_HAND_SIZE = 7;

  public Deck(List<Player> players, Random random) {
    this.numPlayers = players.size();
    this.random = new Random(random.nextLong());

    // set up deck
    for (int i = 0; i < STANDARD_CARD_COUNT; i++) { // 4 of each
      deck.add(new Card(CardType.FAVOR));
      deck.add(new Card(CardType.SHUFFLE));
      deck.add(new Card(CardType.NOPE));
      deck.add(new Card(CardType.BEARD_CAT));
      deck.add(new Card(CardType.CATTERMELON));
      deck.add(new Card(CardType.HAIRY_POTATO_CAT));
      deck.add(new Card(CardType.TACOCAT));
      deck.add(new Card(CardType.RAINBOW_RALPHING_CAT));
      deck.add(new Card(CardType.DRAW_FROM_BOTTOM));
      deck.add(new Card(CardType.ALTER_FUTURE));
      deck.add(new Card(CardType.FERAL_CAT));

    }
    for (int i = 0; i < SPECIAL_CARD_COUNT; i++) { // 3 of each
      deck.add(new Card(CardType.ATTACK));
      deck.add(new Card(CardType.TARGETED_ATTACK));
      deck.add(new Card(CardType.SEE_THE_FUTURE));
      deck.add(new Card(CardType.SWAP_TOP_BOTTOM));
      deck.add(new Card(CardType.NEKO));
    }
    for (int i = 0; i < 2; i++) { // 2 of each
      deck.add(new Card(CardType.BUBONIC_PLAGUE));
      deck.add(new Card(CardType.BLESSING));
      deck.add(new Card(CardType.CURSE));
      deck.add(new Card(CardType.NOSY));
    }

    deck.add(new Card(CardType.SUPER_SKIP));
    deck.add(new Card(CardType.NOPE)); // 5th nope card
    deck.add(new Card(CardType.SKIP)); // 5th skip card

    for (int i = 0; i < DEFUSE_CARD_COUNT - numPlayers; i++) { // extra defuse cards
      deck.add(new Card(CardType.DEFUSE));
    }

    // shuffle deck
    shuffle();

    // deal hands of 7 cards to each player
    for (int i = 0; i < STARTING_HAND_SIZE; i++) {
      for (Player p : players) {
        p.addCard(deck.get(0));
        deck.remove(0);
      }
    }

    // give each a defuse card
    for (Player p : players) {
      p.addCard(new Card(CardType.DEFUSE));
    }

    // put exploding kittens into deck
    for (int i = 0; i < numPlayers - 1; i++) {
      deck.add(new Card(CardType.EXPLODING_KITTEN));
    }
  }

  /* Shuffle the deck */
  public void shuffle() {
    Collections.shuffle(deck, random);
  }

  /* Draw from draw pile */
  public Card drawCard() {
    if (deck.isEmpty()) {
      throw new IllegalStateException("Draw pile is empty");
    }
    return deck.remove(0);
  }

  /* Add card to discard pile */
  public void discardCard(Card card) {
    discard.add(card);
  }

  /* Add card back to draw pile */
  public void addToDrawPile(Card card, int position) {
    if (position < 0 || position > deck.size()) {
      throw new IllegalArgumentException("Invalid position");
    }
    deck.add(position, card);
  }

  /* Peek at top 3 cards */
  public List<Card> peekTopCards() {
    if (deck.isEmpty()) {
      throw new IllegalStateException("Draw pile is empty");
    }
    int count = Math.min(SEE_THE_FUTURE_CARD_COUNT, deck.size());
    return new ArrayList<>(deck.subList(0, count));
  }

  /* Getters */
  public List<Card> getDeck() {
    return new ArrayList<>(deck);
  }

  public List<Card> getDiscard() {
    return new ArrayList<>(discard);
  }
}
