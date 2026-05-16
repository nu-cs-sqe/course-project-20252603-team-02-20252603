package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
