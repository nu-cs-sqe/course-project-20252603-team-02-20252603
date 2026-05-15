package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
