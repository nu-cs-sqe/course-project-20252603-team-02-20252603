package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 5;
  private static final int TOP_OF_DRAW_PILE = 0;

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
    if (card.getType() == CardType.EXPLODING_KITTEN) {
      handleExplodingKitten(card, currentPlayer);
      return;
    }
    currentPlayer.addCard(card);
    completeOneTurn();
  }

  private void handleExplodingKitten(Card explodingKitten, Player currentPlayer) {
    if (currentPlayer.hasDefuse()) {
      Card defuse = new Card(CardType.DEFUSE);
      currentPlayer.removeCard(defuse);
      deck.discardCard(defuse);
      deck.addToDrawPile(explodingKitten, TOP_OF_DRAW_PILE);
      completeOneTurn();
      return;
    }
    currentPlayer.die();
    checkWinner();
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
    
    else if (card.getType() == CardType.ATTACK) {
      playAttack();
    }
    
    else if (card.getType() == CardType.BUBONIC_PLAGUE) {
      playBubonicPlague();
      return Collections.emptyList();
    }

    else if (card.getType() == CardType.DRAW_FROM_BOTTOM) {
      playDrawFromBottom();
      return Collections.emptyList();
    }
    

    return Collections.emptyList();
  }

  public List<Card> playCard(Card card, Player target) {
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

    if (card.getType() == CardType.TARGETED_ATTACK) {
      playTargetedAttack(target);
      return Collections.emptyList();
    }
    return Collections.emptyList();
  }

  public List<Card> playCard(Card card, List<Card> reorderedCards) {
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

    if (card.getType() == CardType.ALTER_FUTURE) {
      playAlterTheFuture(reorderedCards);
      return Collections.emptyList();
    }
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

  public void addToDrawPile(Card card, int position) {
    deck.addToDrawPile(card, position);
  }

  public Card drawFromDeck() {
    return deck.drawCard();
  }

  public Card drawFromBottomOfDeck() {
    return deck.drawFromBottom();
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

  /** Bubonic Plague: Each player excl. the player who
   * played the card loses a random card from their hand **/
  public void playBubonicPlague() {
    Player currentPlayer = getCurrentPlayer();
    for (Player player : players) {
      if (player == currentPlayer || !player.isAlive() || player.getHand().isEmpty()) {
        continue;
      }
      List<Card> hand = player.getHand();
      Card randomCard = hand.get(random.nextInt(hand.size()));
      player.removeCard(randomCard);
      deck.addToDrawPile(randomCard, random.nextInt(deck.getDeck().size() + 1));
    }
    deck.shuffle();
  }
  
  public void playTargetedAttack(Player target) {
    if (target == null) {
      throw new IllegalArgumentException("target cannot be null");
    }
    if (target == getCurrentPlayer()) {
      throw new IllegalArgumentException("cannot target yourself");
    }
    if (!target.isAlive()) {
      throw new IllegalArgumentException("target is not alive");
    }

    currentPlayerIndex = players.indexOf(target);
    getCurrentPlayer().addTurn();
  }
  
  public List<Card> playSeeTheFuture(){
    return deck.peekTopCards();
  }
  
  public void playAttack() {
    moveToNextPlayer();
    getCurrentPlayer().addTurn();
  }

  public void playDrawFromBottom() {
    Card card = deck.drawFromBottom();
    if (card.getType() == CardType.EXPLODING_KITTEN) {
      handleExplodingKitten(card, getCurrentPlayer());
      return;
    }
    getCurrentPlayer().addCard(card);
    completeOneTurn();
  }

  public void playAlterTheFuture(List<Card> reorderedCards) {
    deck.reorderTopCards(reorderedCards);
  }
}
