package model;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  private static final int MIN_PLAYERS = 3;
  private static final int MAX_PLAYERS = 5;
  private static final int TOO_FEW_PLAYERS = 2;
  private static final int TOO_MANY_PLAYERS = 6;


  @Test
  public void startGameValidPlayerCount() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4),
        mockDeck,
        new Random(42)
    );

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertFalse(game.isGameOver());
    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockDeck);
  }

  @Test
  public void startGameUpperBoundaryPlayerCount() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Player mockPlayer5 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5),
        mockDeck,
        new Random(42)
    );

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertFalse(game.isGameOver());
    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);
  }
}