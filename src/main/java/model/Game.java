package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 5;

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

    if (card.getType() == CardType.NOPE) {
      throw new IllegalArgumentException("Cannot play a Nope card when no action is pending.");
    }

    currentPlayer.removeCard(card);
    deck.discardCard(card);

    boolean isNoped = false;
    boolean nopePlayedThisRound = true;
    Scanner scanner = new Scanner(System.in, "UTF-8");

    System.out.println("Player " + currentPlayerIndex + " played " + card.getType() + "\n");

    while (nopePlayedThisRound) {
      nopePlayedThisRound = false;

      for (int i = 0; i < players.size(); i++) {
        Player p = players.get(i);

        if (!p.isAlive()) {
          continue;
        }

        Card nopeCardToPlay = null;
        for (Card c : p.getHand()) {
          if (c.getType() == CardType.NOPE) {
            nopeCardToPlay = c;
            break;
          }
        }

        if (nopeCardToPlay != null) {
          System.out.print("Player " + i + ", play your NOPE card? (y/n): ");

          String input = scanner.nextLine().trim().toLowerCase();

          if (input.equals("y")) {
            p.removeCard(nopeCardToPlay);
            deck.discardCard(nopeCardToPlay);

            isNoped = !isNoped;
            nopePlayedThisRound = true;

            System.out.println("Player " + i + " played a NOPE!\n");
            break;
          }
        }
      }
    }

    if (isNoped) {
      return Collections.emptyList();
    }

    if (card.getType() == CardType.SEE_THE_FUTURE) {
      return playSeeTheFuture();
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
}
