package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
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
    if (numberOfPlayers < 3 || numberOfPlayers > 5) {
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
    return players.get(currentPlayerIndex);
  }

  public void runGame() {
    if (!gameLaunched) {
      throw new IllegalStateException("game has not started");
    }
  }

  public void handleTurn() {
    Player currentPlayer = getCurrentPlayer();
    if (!currentPlayer.isAlive() || currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
      return;
    }

    currentPlayer.removeTurn();
    if (currentPlayer.getTurnsOwed() == 0) {
      moveToNextPlayer();
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

  public Deck getDeck() {
    return deck;
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
}
