package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GameTest {
  private static final int RANDOM_SEED = 42;

  @Test
  public void startGameValidPlayerCount() {
    Game game = new Game(4, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(4, game.getPlayers().size());
    assertNotNull(game.getDeck());
    assertEquals(0, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameLowerBoundaryPlayerCount() {
    Game game = new Game(3, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(3, game.getPlayers().size());
    assertNotNull(game.getDeck());
    assertEquals(0, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameUpperBoundaryPlayerCount() {
    Game game = new Game(5, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(5, game.getPlayers().size());
    assertNotNull(game.getDeck());
    assertEquals(0, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameTooFewPlayersThrowException() {
    Game game = new Game(2, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.startGame());
  }

  @Test
  public void startGameTooManyPlayersThrowException() {
    Game game = new Game(6, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.startGame());
  }

  @Test
  public void startGameTwiceThrowException() {
    Game game = new Game(4, new Random(RANDOM_SEED));
    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.startGame());
  }

  @Test
  public void validatePlayerCountThreePlayers() {
    Game game = new Game(3, new Random(RANDOM_SEED));

    assertDoesNotThrow(() -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountFivePlayers() {
    Game game = new Game(5, new Random(RANDOM_SEED));

    assertDoesNotThrow(() -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountTooFewPlayersThrowException() {
    Game game = new Game(2, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountTooManyPlayersThrowException() {
    Game game = new Game(6, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());
  }

  @Test
  public void initializeTurnOrderThreePlayers() {
    Game game = new Game(3, new Random(RANDOM_SEED));

    game.startGame();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(3, game.getPlayers().size());
  }

  @Test
  public void initializeTurnOrderFivePlayers() {
    Game game = new Game(5, new Random(RANDOM_SEED));

    game.startGame();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(5, game.getPlayers().size());
  }

  @Test
  public void initializeTurnOrderBeforePlayersExistThrowException() {
    Game game = new Game(3, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.initializeTurnOrder());
  }

  @Test
  public void getCurrentPlayerFirstPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();

    assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
  }

  @Test
  public void runGameBeforeStartGameThrowException() {
    Game game = new Game(3, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.runGame());
  }

  @Test
  public void handleTurnNormalCurrentPlayerTurn() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();

    game.handleTurn();

    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void handleTurnPlayerOwingTwoTurns() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().addTurn();

    game.handleTurn();

    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
  }

  @Test
  public void handleTurnPlayerOwingZeroTurns() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().removeTurn();

    game.handleTurn();

    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void handleTurnEliminatedCurrentPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().die();

    game.handleTurn();

    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void getNextActivePlayerNextPlayerIsActive() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();

    assertEquals(players.get(1), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerCurrentPlayerIsDead() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(0).die();

    assertEquals(players.get(1), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerNextPlayerIsEliminated() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).die();

    assertEquals(players.get(2), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerMultipleDeadPlayersInRow() {
    Game game = new Game(5, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).die();
    players.get(2).die();

    assertEquals(players.get(3), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerWrapAroundToFirstPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(players.get(0), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerOnlyOneAlivePlayerLeft() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).die();
    players.get(2).die();

    assertNull(game.getNextActivePlayer());
    assertTrue(game.isGameOver());
  }

  @Test
  public void moveToNextPlayerNormally() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();

    game.moveToNextPlayer();

    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerFromLastPlayerToFirstPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    game.moveToNextPlayer();

    assertEquals(0, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerPastEliminatedPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).die();

    game.moveToNextPlayer();

    assertEquals(2, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerWhenNextPlayerHasZeroTurnsOwed() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).removeTurn();

    game.moveToNextPlayer();

    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
  }

  @Test
  public void completeOneTurnOneOwedTurn() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();

    game.completeOneTurn();

    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void completeOneTurnOneOfTwoOwedTurns() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().addTurn();

    game.completeOneTurn();

    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
    assertEquals(0, game.getCurrentPlayerIndex());
  }

  @Test
  public void completeOneTurnWhenZeroTurnsAreOwedThrowException() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().removeTurn();

    assertThrows(IllegalStateException.class, () -> game.completeOneTurn());
  }

  @Test
  public void drawCardNormalCardForCurrentPlayer() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    int handSizeBefore = firstPlayer.getHand().size();
    int deckSizeBefore = game.getDeck().getDeck().size();

    game.drawCard();

    assertEquals(handSizeBefore + 1, firstPlayer.getHand().size());
    assertEquals(deckSizeBefore - 1, game.getDeck().getDeck().size());
    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void drawCardLastCardFromDeck() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    int handSizeBefore = firstPlayer.getHand().size();
    while (!game.getDeck().getDeck().isEmpty()) {
      game.getDeck().drawCard();
    }
    game.getDeck().addToDrawPile(new Card(CardType.SKIP), 0);

    game.drawCard();

    assertEquals(handSizeBefore + 1, firstPlayer.getHand().size());
    assertEquals(0, game.getDeck().getDeck().size());
  }

  @Test
  public void drawCardFromEmptyDeckThrowException() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    while (!game.getDeck().getDeck().isEmpty()) {
      game.getDeck().drawCard();
    }

    assertThrows(IllegalStateException.class, () -> game.drawCard());
  }

  @Test
  public void drawCardExplodingKittenWithDefuse() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    int defuseCountBefore = countCards(firstPlayer, CardType.DEFUSE);
    game.getDeck().addToDrawPile(explodingKitten, 0);
    int deckSizeBefore = game.getDeck().getDeck().size();

    game.drawCard();

    assertTrue(firstPlayer.isAlive());
    assertEquals(defuseCountBefore - 1, countCards(firstPlayer, CardType.DEFUSE));
    assertFalse(firstPlayer.getHand().contains(explodingKitten));
    assertEquals(deckSizeBefore, game.getDeck().getDeck().size());
    assertEquals(explodingKitten, game.getDeck().getDeck().get(0));
  }

  @Test
  public void drawCardExplodingKittenWithoutDefuse() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    while (firstPlayer.hasDefuse()) {
      firstPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    game.getDeck().addToDrawPile(new Card(CardType.EXPLODING_KITTEN), 0);

    game.drawCard();

    assertFalse(firstPlayer.isAlive());
    assertFalse(game.isGameOver());
  }

  @Test
  public void playCardPlayableCard() {
    Game game = new Game(3, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(skip);
    int handSizeBefore = currentPlayer.getHand().size();
    int discardSizeBefore = game.getDeck().getDiscard().size();

    game.playCard(skip);

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());
    assertFalse(currentPlayer.getHand().contains(skip));
    assertEquals(discardSizeBefore + 1, game.getDeck().getDiscard().size());
    assertEquals(skip, game.getDeck().getDiscard().get(discardSizeBefore));
  }

  private int countCards(Player player, CardType cardType) {
    int count = 0;
    for (Card card : player.getHand()) {
      if (card.getType() == cardType) {
        count++;
      }
    }
    return count;
  }
}
