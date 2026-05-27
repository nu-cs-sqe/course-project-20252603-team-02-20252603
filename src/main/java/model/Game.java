package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 5;
  private static final int TWO_CAT_COMBO_SIZE = 2;
  private static final int THREE_CAT_COMBO_SIZE = 3;
  private static final int FIVE_CAT_COMBO_SIZE = 5;

  private final int numberOfPlayers;
  private final Random random;
  private List<Player> players;
  private Deck deck;
  private boolean gameLaunched;
  private boolean gameOver;
  private int currentPlayerIndex;

  public Game(int numberOfPlayers, Random random) {
    this.numberOfPlayers = numberOfPlayers;
    this.random = new Random(random.nextLong());
    this.players = new ArrayList<>();
    this.currentPlayerIndex = 0;
  }

  public void startGame() {
    if (gameLaunched) {
      throw new IllegalStateException("game already started");
    }
    validatePlayerCount();

    players = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(new Player());
    }

    deck = new Deck(players, random);
    initializeTurnOrder();
    gameLaunched = true;
    gameOver = false;
  }

  public void validatePlayerCount() {
    if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
      throw new IllegalArgumentException("invalid player count");
    }
  }

  public void initializeTurnOrder() {
    if (players.isEmpty()) {
      throw new IllegalStateException("cannot initialize turn order without players");
    }
    currentPlayerIndex = 0;
  }

  public Player getCurrentPlayer() {
    if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
      throw new IllegalStateException("current player index out of bounds");
    }
    return players.get(currentPlayerIndex);
  }

  public void runGame() {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (!gameOver) {
      drawCard();
    }
  }

  public void handleTurn() {
    Player currentPlayer = getCurrentPlayer();
    if (!currentPlayer.isAlive() || currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
      return;
    }

    completeOneTurn();
  }

  public void completeOneTurn() {
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeTurn();
    if (currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
    }
  }

  public void drawCard() {
    Player currentPlayer = getCurrentPlayer();
    Card card = deck.drawCard();
    if (card.getType() == CardType.EXPLODING_KITTEN && currentPlayer.hasDefuse()) {
      Card defuse = new Card(CardType.DEFUSE);
      currentPlayer.removeCard(defuse);
      deck.discardCard(defuse);
      deck.addToDrawPile(card, 0);
      completeOneTurn();
      return;
    }
    if (card.getType() == CardType.EXPLODING_KITTEN) {
      currentPlayer.die();
      checkWinner();
      return;
    }
    currentPlayer.addCard(card);
    completeOneTurn();
  }

  public List<Card> playCard(Card card) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isPlayableCard(card)) {
      throw new IllegalArgumentException("card is not playable");
    }
    Player currentPlayer = getCurrentPlayer();
    currentPlayer.removeCard(card);
    deck.discardCard(card);

    if (card.getType() == CardType.SEE_THE_FUTURE) {
      return playSeeTheFuture();
    }
    return Collections.emptyList();
  }

  public List<Card> playCard(List<Card> cards, Player target, CardType named) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isValidCatCombo(cards)) {
      throw new IllegalArgumentException("invalid cat combo");
    }
    playCatCards(cards, target, named);
    return Collections.emptyList();
  }

  private boolean isPlayableCard(Card card) {
    return card != null &&
            card.getType() != CardType.EXPLODING_KITTEN &&
            card.getType() != CardType.DEFUSE;
  }

  public void checkWinner() {
    int alivePlayers = 0;
    for (Player player : players) {
      if (player.isAlive()) {
        alivePlayers++;
      }
    }
    if (alivePlayers > 1) {
      gameOver = false;
    } else if (alivePlayers == 1) {
      gameOver = true;
    } else {
      throw new IllegalStateException("no players alive");
    }
  }

  public Player getNextActivePlayer() {
    for (int i = 1; i < players.size(); i++) {
      Player player = players.get((currentPlayerIndex + i) % players.size());
      if (player.isAlive()) {
        return player;
      }
    }
    gameOver = true;
    return null;
  }

  public void moveToNextPlayer() {
    do {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    } while (!getCurrentPlayer().isAlive());
    if (getCurrentPlayer().getTurnsOwed() == 0) {
      getCurrentPlayer().addTurn();
    }
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  public List<Card> getDrawPile() {
    return deck.getDeck();
  }

  public List<Card> getDiscardPile() {
    return deck.getDiscard();
  }

  public Card takeFromDiscard(CardType type) {
    return deck.takeFromDiscard(type);
  }

  public void addToDrawPile(Card card, int position) {
    deck.addToDrawPile(card, position);
  }

  public Card drawFromDeck() {
    return deck.drawCard();
  }

  public void addToDiscard(Card card) {
    deck.discardCard(card);
  }

  public boolean isGameLaunched() {
    return gameLaunched;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public int getCurrentPlayerIndex() {
    return currentPlayerIndex;
  }

  public List<Card> playSeeTheFuture(){
    return deck.peekTopCards();
  }

  public boolean isCatCard(CardType type) {
    if (type == null) {
      throw new IllegalArgumentException("invalid card");
    }
    return type == CardType.TACOCAT
            || type == CardType.HAIRY_POTATO_CAT
            || type == CardType.RAINBOW_RALPHING_CAT
            || type == CardType.BEARD_CAT
            || type == CardType.CATTERMELON
            || type == CardType.FERAL_CAT;
  }

  public boolean isValidCatCombo(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new IllegalArgumentException("cards cannot be null or empty");
    }

    for (Card c : cards) {
      if (!isCatCard(c.getType())) {
        return false;
      }
    }

    int size = cards.size();

    if (size == TWO_CAT_COMBO_SIZE) {
      CardType a = cards.get(0).getType();
      CardType b = cards.get(1).getType();
      boolean aFeral = a == CardType.FERAL_CAT;
      boolean bFeral = b == CardType.FERAL_CAT;
      if (aFeral || bFeral) {
        return true;
      }
      return a == b;
    } else if (size == THREE_CAT_COMBO_SIZE) {
      long ferals = cards.stream()
              .filter(c -> c.getType() == CardType.FERAL_CAT).count();
      if (ferals == THREE_CAT_COMBO_SIZE) {
        return true;
      }
      List<CardType> realCats = new ArrayList<>();
      for (Card c : cards) {
        if (c.getType() != CardType.FERAL_CAT) {
          realCats.add(c.getType());
        }
      }
      CardType first = realCats.get(0);
      for (CardType t : realCats) {
        if (t != first) {
          return false;
        }
      }
      return true;
    } else if (size == FIVE_CAT_COMBO_SIZE) {
      long distinctTypes = cards.stream()
              .map(Card::getType)
              .distinct()
              .count();
      return distinctTypes == FIVE_CAT_COMBO_SIZE;
    }
    return false;
  }

  public Card playTwoMatchingCats(List<Card> cards, Player target) {
    if (target == null || !target.isAlive()) {
      throw new IllegalArgumentException("target cannot be null or dead");
    }

    Player currentPlayer = getCurrentPlayer();
    if (target == currentPlayer) {
      throw new IllegalArgumentException("cannot target yourself");
    }
    if (!isValidCatCombo(cards) || cards.size() != TWO_CAT_COMBO_SIZE) {
      throw new IllegalArgumentException("invalid two-cat combo");
    }

    List<Card> hand = new ArrayList<>(currentPlayer.getHand());
    for (Card c : cards) {
      if (!hand.remove(c)) {
        throw new IllegalArgumentException("cards not in hand");
      }
    }

    for (Card c : cards) {
      currentPlayer.removeCard(c);
      deck.discardCard(c);
    }
    List<Card> targetHand = target.getHand();
    int index = random.nextInt(targetHand.size());
    Card stolenCard = targetHand.get(index);
    target.removeCard(stolenCard);
    currentPlayer.addCard(stolenCard);
    return stolenCard;
  }

  public boolean playThreeMatchingCats(List<Card> cards, Player target, CardType wantedCard) {
    if (target == null || !target.isAlive()) {
      throw new IllegalArgumentException("target cannot be null or dead");
    }
    Player currentPlayer = getCurrentPlayer();
    if (target == currentPlayer) {
      throw new IllegalArgumentException("cannot target yourself");
    }

    if (wantedCard == null || wantedCard == CardType.EXPLODING_KITTEN) {
      throw new IllegalArgumentException("invalid wanted card type");
    }

    if (!isValidCatCombo(cards) || cards.size() != THREE_CAT_COMBO_SIZE) {
      throw new IllegalArgumentException("invalid three-cat combo");
    }

    List<Card> hand = new ArrayList<>(currentPlayer.getHand());
    for (Card c : cards) {
      if (!hand.remove(c)) {
        throw new IllegalArgumentException("cards not in hand");
      }
    }

    for (Card c : cards) {
      currentPlayer.removeCard(c);
      deck.discardCard(c);
    }

    Card namedCard = new Card(wantedCard);
    List<Card> targetHand = target.getHand();
    // current player gets nothing if target doesn't have the card they ask for
    if (!targetHand.contains(namedCard)) {
      return false;
    }
    target.removeCard(namedCard);
    currentPlayer.addCard(namedCard);
    return true;
  }

  public Card playFiveDifferentCats(List<Card> cards, CardType wantedCard) {
    if (cards == null) {
      throw new IllegalArgumentException("cards cannot be null");
    }

    if (wantedCard == null || wantedCard == CardType.EXPLODING_KITTEN) {
      throw new IllegalArgumentException("invalid wanted card type");
    }

    if (!isValidCatCombo(cards) || cards.size() != FIVE_CAT_COMBO_SIZE) {
      throw new IllegalArgumentException("invalid five-cat combo");
    }

    Player currentPlayer = getCurrentPlayer();
    List<Card> hand = new ArrayList<>(currentPlayer.getHand());
    for (Card c : cards) {
      if (!hand.remove(c)) {
        throw new IllegalArgumentException("cards not in hand");
      }
    }
    Card receivedCard = takeFromDiscard(wantedCard); // throws if not found
    for (Card c : cards) {
      currentPlayer.removeCard(c);
      deck.discardCard(c);
    }
    currentPlayer.addCard(receivedCard);
    return receivedCard;
  }

  public void playCatCards(List<Card> cards, Player target, CardType named) {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
    if (gameOver) {
      throw new IllegalStateException("game is over");
    }
    if (!isValidCatCombo(cards)) {
      throw new IllegalArgumentException("invalid cat combo");
    }
    int size = cards.size();
    if (size == TWO_CAT_COMBO_SIZE) {
      playTwoMatchingCats(cards, target);
    } else if (size == THREE_CAT_COMBO_SIZE) {
      playThreeMatchingCats(cards, target, named);
    } else {
      playFiveDifferentCats(cards, named);
    }
  }

}

