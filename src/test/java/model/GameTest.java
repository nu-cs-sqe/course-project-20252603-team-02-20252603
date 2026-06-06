package model;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class GameTest {
  private static final int MIN_PLAYERS = 3;
  private static final int VALID_PLAYER_COUNT = 4;
  private static final int MAX_PLAYERS = 5;
  private static final int RANDOM_SEED = 42;
  private static final int SEE_THE_FUTURE_CARD_COUNT = 3;

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
        new Random(RANDOM_SEED)
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
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertFalse(game.isGameOver());
    assertEquals(MAX_PLAYERS, game.getPlayers().size());
    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);
  }

  @Test
  public void startGameLowerBoundaryPlayerCount() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(MIN_PLAYERS, game.getPlayers().size());
    assertFalse(game.isGameOver());
    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void startGameTooFewPlayersThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalArgumentException.class, () -> game.startGame());

    verify(mockPlayer1, mockPlayer2, mockDeck);
  }

  @Test
  public void startGameTooManyPlayersThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Player mockPlayer5 = createMock(Player.class);
    Player mockPlayer6 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalArgumentException.class, () -> game.startGame());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6, mockDeck);
  }

  @Test
  public void startGameTwiceThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.startGame());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void validatePlayerCountThreePlayers() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );
    assertEquals(MIN_PLAYERS, game.getPlayers().size());
    assertDoesNotThrow(() -> game.validatePlayerCount());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void validatePlayerCountFivePlayers() {
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
        new Random(RANDOM_SEED)
    );
    assertEquals(MAX_PLAYERS, game.getPlayers().size());
    assertDoesNotThrow(() -> game.validatePlayerCount());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);
  }

  @Test
  public void validatePlayerCountTooFewPlayersThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());

    verify(mockPlayer1, mockPlayer2, mockDeck);
  }

  @Test
  public void validatePlayerCountTooManyPlayersThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Player mockPlayer5 = createMock(Player.class);
    Player mockPlayer6 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockPlayer6, mockDeck);
  }

  @Test
  public void initializeTurnOrderThreePlayers() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.initializeTurnOrder();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(MIN_PLAYERS, game.getPlayers().size());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void initializeTurnOrderFourPlayers() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.initializeTurnOrder();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(VALID_PLAYER_COUNT, game.getPlayers().size());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockDeck);
  }

  @Test
  public void initializeTurnOrderFivePlayers() {
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
        new Random(RANDOM_SEED)
    );

    game.initializeTurnOrder();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(MAX_PLAYERS, game.getPlayers().size());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);
  }

  @Test
  public void initializeTurnOrderBeforePlayersExistThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.initializeTurnOrder());
  }

  @Test
  public void getCurrentPlayerLastPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getTurnsOwed()).andReturn(1).anyTimes();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(mockPlayer3, game.getCurrentPlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getCurrentPlayerFirstPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertEquals(mockPlayer1, game.getCurrentPlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getCurrentPlayerIndexOutOfBoundsThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.getCurrentPlayer());
  }

  @Test
  public void runGameWhileGameIsNotOver() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockCard.getType()).andReturn(CardType.SKIP).anyTimes();
    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    mockPlayer1.addCard(mockCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getTurnsOwed()).andReturn(1).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.runGame();

    assertFalse(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void runGameWhenGameIsAlreadyOver() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.checkWinner();

    assertTrue(game.isGameOver());

    game.runGame();

    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void runGameBeforeStartGameThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalStateException.class, () -> game.runGame());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void runGameWhenGameBecomesOverDuringTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockCard.getType()).andReturn(CardType.EXPLODING_KITTEN).anyTimes();
    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    expect(mockPlayer1.hasDefuse()).andReturn(false).once();
    mockPlayer1.die();
    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.runGame();

    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void handleTurnNormalCurrentPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.handleTurn();

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void handleTurnPlayerOwingTwoTurns() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getTurnsOwed()).andReturn(2).once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.handleTurn();

    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void handleTurnPlayerOwingZeroTurns() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.handleTurn();

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void handleTurnEliminatedCurrentPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.handleTurn();

    assertEquals(1, game.getCurrentPlayerIndex()); // skipped dead player, now on player 2

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getNextActivePlayerNextIsActive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertEquals(mockPlayer2, game.getNextActivePlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getNextActivePlayerCurrentPlayerIsDead() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertEquals(mockPlayer2, game.getNextActivePlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getNextActivePlayerWrapAroundToFirstPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(mockPlayer1, game.getNextActivePlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getNextActivePlayerMultipleDeadPlayersInARow() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Player mockPlayer4 = createMock(Player.class);
    Player mockPlayer5 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer4.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertEquals(mockPlayer4, game.getNextActivePlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5, mockDeck);
  }

  @Test
  public void getNextActivePlayerNextPlayerIsEliminated() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertEquals(mockPlayer3, game.getNextActivePlayer());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void getNextActivePlayerOnlyOneAliveLeft() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertNull(game.getNextActivePlayer());
    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void moveToNextPlayerNormally() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void moveToNextPlayerFromLastToFirst() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getTurnsOwed()).andReturn(1).anyTimes();
    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(0, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void moveToNextPlayerPastEliminatedPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();

    assertEquals(2, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void moveToNextPlayerNextHasZeroTurnsOwed() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(0).once();
    mockPlayer2.addTurn();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.moveToNextPlayer();

    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(1, game.getPlayers().get(1).getTurnsOwed());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void completeOneTurnOneOwed() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.completeOneTurn();

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void completeOneTurnTwoOwed() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).times(2);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.completeOneTurn();

    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(1, game.getPlayers().get(0).getTurnsOwed());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void completeOneTurnZeroOwedThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    mockPlayer1.removeTurn();
    expectLastCall().andThrow(new IllegalStateException("player has no turns to remove"));

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    assertThrows(IllegalStateException.class, () -> game.completeOneTurn());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void drawCardNormalCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    expect(mockCard.getType()).andReturn(CardType.SKIP).anyTimes();
    mockPlayer1.addCard(mockCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.drawCard(0);

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void drawCardLastCardInDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    expect(mockCard.getType()).andReturn(CardType.SKIP).anyTimes();
    mockPlayer1.addCard(mockCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    expect(mockDeck.getDeck()).andReturn(List.of()).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.drawCard(0);

    assertEquals(1, game.getCurrentPlayerIndex());
    assertTrue(game.getDrawPile().isEmpty());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void drawCardEmptyDeckThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockDeck.drawCard()).andThrow(new IllegalStateException("Draw pile is empty")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.drawCard(0));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void drawCardExplodingKittenWithDefuse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    expect(mockCard.getType()).andReturn(CardType.EXPLODING_KITTEN).anyTimes();
    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
    mockPlayer1.removeCard(new Card(CardType.DEFUSE));
    mockDeck.discardCard(new Card(CardType.DEFUSE));
    expect(mockDeck.getDeck()).andReturn(List.of()).once(); // bounds check in defuse()
    mockDeck.addToDrawPile(new Card(CardType.EXPLODING_KITTEN), 0);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    expect(mockDeck.getDeck()).andReturn(List.of(new Card(CardType.EXPLODING_KITTEN))).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.drawCard(0);

    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(CardType.EXPLODING_KITTEN, game.getDrawPile().get(0).getType());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void drawCardExplodingKittenWithoutDefuse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Card mockCard = createMock(Card.class);

    expect(mockCard.getType()).andReturn(CardType.EXPLODING_KITTEN).anyTimes();
    expect(mockDeck.drawCard()).andReturn(mockCard).once();
    expect(mockPlayer1.hasDefuse()).andReturn(false).once();
    mockPlayer1.die();
    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.drawCard(0);

    assertFalse(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }

  @Test
  public void playCardPlayableCardSkip() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);

    mockPlayer1.removeCard(skipCard);
    mockDeck.discardCard(skipCard);
    expectLastCall().once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(skipCard);

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardCardNotInHandThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);

    mockPlayer1.removeCard(skipCard);
    expectLastCall().andThrow(new IllegalArgumentException("card not in hand")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playCard(skipCard));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardNonPlayableCardThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    assertThrows(IllegalArgumentException.class, () -> game.playCard(explodingKitten));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardBeforeGameStartThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    Card skipCard = new Card(CardType.SKIP);
    assertThrows(IllegalStateException.class, () -> game.playCard(skipCard));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardAfterGameOverThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.checkWinner();

    Card skipCard = new Card(CardType.SKIP);
    assertThrows(IllegalStateException.class, () -> game.playCard(skipCard));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSkipAdvancesTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(skipCard);
    mockDeck.discardCard(skipCard);
    expectLastCall().once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(skipCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSuperSkipOneOwedAdvancesTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card superSkipCard = new Card(CardType.SUPER_SKIP);
    mockPlayer1.removeCard(superSkipCard);
    mockDeck.discardCard(superSkipCard);
    expectLastCall().once();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(superSkipCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSuperSkipTwoOwedAdvancesTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card superSkipCard = new Card(CardType.SUPER_SKIP);
    mockPlayer1.removeCard(superSkipCard);
    mockDeck.discardCard(superSkipCard);
    expectLastCall().once();
    expect(mockPlayer1.getTurnsOwed()).andReturn(2).once();
    mockPlayer1.removeTurn();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(superSkipCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSeeTheFutureReturnsTopThreeCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card seeTheFutureCard = new Card(CardType.SEE_THE_FUTURE);
    Card topCard1 = new Card(CardType.SKIP);
    Card topCard2 = new Card(CardType.ATTACK);
    Card topCard3 = new Card(CardType.NOPE);
    mockPlayer1.removeCard(seeTheFutureCard);
    mockDeck.discardCard(seeTheFutureCard);
    expectLastCall().once();
    expect(mockDeck.peekTopCards()).andReturn(List.of(topCard1, topCard2, topCard3)).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(seeTheFutureCard);

    assertEquals(List.of(topCard1, topCard2, topCard3), result);
    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSwapTopBottomSwapsDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    mockPlayer1.removeCard(swapCard);
    mockDeck.discardCard(swapCard);
    expectLastCall().once();
    mockDeck.swapTopBottomCards();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(swapCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardAttackMovesToNextPlayerWithExtraTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card attackCard = new Card(CardType.ATTACK);
    mockPlayer1.removeCard(attackCard);
    mockDeck.discardCard(attackCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    mockPlayer2.addTurn();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(attackCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardDrawFromBottomAddsCardAndAdvancesTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card drawFromBottomCard = new Card(CardType.DRAW_FROM_BOTTOM);
    Card bottomCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(drawFromBottomCard);
    mockDeck.discardCard(drawFromBottomCard);
    expectLastCall().once();
    expect(mockDeck.drawFromBottom()).andReturn(bottomCard).once();
    mockPlayer1.addCard(bottomCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(drawFromBottomCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardCurseNextPlayerLosesAllDefuse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card curseCard = new Card(CardType.CURSE);
    Card defuseCard = new Card(CardType.DEFUSE);
    mockPlayer1.removeCard(curseCard);
    mockDeck.discardCard(curseCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.hasDefuse()).andReturn(true).once();
    mockPlayer2.removeCard(defuseCard);
    mockDeck.addToDrawPile(defuseCard, 0);
    expect(mockPlayer2.hasDefuse()).andReturn(false).once();
    mockDeck.shuffle();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(curseCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void checkWinnerMoreThanOnePlayerAlive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.checkWinner();

    assertFalse(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void checkWinnerExactlyOnePlayerAlive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.checkWinner();

    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void checkWinnerNoPlayersAliveThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.checkWinner());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSkipCardNotInHandThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(skipCard);
    expectLastCall().andThrow(new IllegalArgumentException("card not in hand")).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playCard(skipCard));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSkipCardAppearsInDiscardPile() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(skipCard);
    mockDeck.discardCard(skipCard);
    expectLastCall().once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(skipCard);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSkipCardOneOwedAdvancesTurn() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(skipCard);
    mockDeck.discardCard(skipCard);
    expectLastCall().once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(skipCard);

    assertEquals(1, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSkipCardTwoOwedRemainsCurrentPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skipCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(skipCard);
    mockDeck.discardCard(skipCard);
    expectLastCall().once();
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(1).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(skipCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playBubonicPlagueAllOtherPlayersHaveCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card card2 = new Card(CardType.SKIP);
    Card card3 = new Card(CardType.ATTACK);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(card2)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer2.removeCard(card2);
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockDeck.addToDrawPile(card2, 0);

    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getHand()).andReturn(List.of(card3)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer3.removeCard(card3);
    expect(mockDeck.getDeck()).andReturn(List.of(card2)).once();
    expect(mockRandom.nextInt(2)).andReturn(1).once();
    mockDeck.addToDrawPile(card3, 1);

    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playBubonicPlague();

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playBubonicPlagueOnePlayerHasNoCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card card3 = new Card(CardType.ATTACK);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of()).once();

    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getHand()).andReturn(List.of(card3)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer3.removeCard(card3);
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockDeck.addToDrawPile(card3, 0);

    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playBubonicPlague();

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playBubonicPlagueAllOtherPlayersHaveNoCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of()).once();

    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getHand()).andReturn(List.of()).once();

    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playBubonicPlague();

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playBubonicPlagueOneOtherPlayerAlive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card card2 = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(card2)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer2.removeCard(card2);
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockDeck.addToDrawPile(card2, 0);

    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();

    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playBubonicPlague();

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playBubonicPlagueCurrentPlayerUnaffected() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card card2 = new Card(CardType.SKIP);
    Card card3 = new Card(CardType.ATTACK);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(card2)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer2.removeCard(card2);
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockDeck.addToDrawPile(card2, 0);

    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.getHand()).andReturn(List.of(card3)).times(2);
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer3.removeCard(card3);
    expect(mockDeck.getDeck()).andReturn(List.of(card2)).once();
    expect(mockRandom.nextInt(2)).andReturn(1).once();
    mockDeck.addToDrawPile(card3, 1);

    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playBubonicPlague();

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playSeeTheFutureEmptyDeckThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card seeTheFutureCard = new Card(CardType.SEE_THE_FUTURE);
    mockPlayer1.removeCard(seeTheFutureCard);
    mockDeck.discardCard(seeTheFutureCard);
    expectLastCall().once();
    expect(mockDeck.peekTopCards())
        .andThrow(new IllegalStateException("Draw pile is empty"))
        .once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.playCard(seeTheFutureCard));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSeeTheFutureOneDeckCardReturnsOne() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card seeTheFutureCard = new Card(CardType.SEE_THE_FUTURE);
    Card topCard = new Card(CardType.SKIP);
    mockPlayer1.removeCard(seeTheFutureCard);
    mockDeck.discardCard(seeTheFutureCard);
    expectLastCall().once();
    expect(mockDeck.peekTopCards()).andReturn(List.of(topCard)).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(seeTheFutureCard);

    assertEquals(1, result.size());
    assertEquals(topCard, result.get(0));
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSeeTheFutureExactlyThreeDeckCardsReturnsThree() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card seeTheFutureCard = new Card(CardType.SEE_THE_FUTURE);
    Card topCard1 = new Card(CardType.SKIP);
    Card topCard2 = new Card(CardType.ATTACK);
    Card topCard3 = new Card(CardType.NOPE);
    mockPlayer1.removeCard(seeTheFutureCard);
    mockDeck.discardCard(seeTheFutureCard);
    expectLastCall().once();
    expect(mockDeck.peekTopCards()).andReturn(List.of(topCard1, topCard2, topCard3)).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(seeTheFutureCard);

    assertEquals(SEE_THE_FUTURE_CARD_COUNT, result.size());
    assertEquals(List.of(topCard1, topCard2, topCard3), result);
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playSeeTheFutureMoreThanThreeDeckCardsReturnsThree() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card seeTheFutureCard = new Card(CardType.SEE_THE_FUTURE);
    Card topCard1 = new Card(CardType.SKIP);
    Card topCard2 = new Card(CardType.ATTACK);
    Card topCard3 = new Card(CardType.NOPE);
    mockPlayer1.removeCard(seeTheFutureCard);
    mockDeck.discardCard(seeTheFutureCard);
    expectLastCall().once();
    expect(mockDeck.peekTopCards()).andReturn(List.of(topCard1, topCard2, topCard3)).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(seeTheFutureCard);

    assertEquals(SEE_THE_FUTURE_CARD_COUNT, result.size());
    assertEquals(List.of(topCard1, topCard2, topCard3), result);
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void defuseNegativePositionThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.defuse(-1));

    verify(mockPlayer1, mockPlayer2, mockPlayer3);
  }

  @Test
  public void defusePositionTooLargeThrowsException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card topCard = new Card(CardType.SKIP);
    expect(mockDeck.getDeck()).andReturn(List.of(topCard)).once();
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.defuse(2));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void defusePositionZeroInsertsAtTop() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    mockDeck.addToDrawPile(explodingKitten, 0);
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.defuse(0);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void defusePositionEqualToSizeInsertsAtBottom() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card topCard = new Card(CardType.SKIP);
    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    expect(mockDeck.getDeck()).andReturn(List.of(topCard)).once();
    mockDeck.addToDrawPile(explodingKitten, 1);
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.defuse(1);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void defuseMiddlePositionInsertsAtMiddle() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card topCard = new Card(CardType.SKIP);
    Card bottomCard = new Card(CardType.ATTACK);
    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    expect(mockDeck.getDeck()).andReturn(List.of(topCard, bottomCard)).once();
    mockDeck.addToDrawPile(explodingKitten, 1);
    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.defuse(1);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSwapTopBottomEmptyDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    mockPlayer1.removeCard(swapCard);
    mockDeck.discardCard(swapCard);
    expectLastCall().once();
    mockDeck.swapTopBottomCards();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(swapCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSwapTopBottomOneCardInDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    mockPlayer1.removeCard(swapCard);
    mockDeck.discardCard(swapCard);
    expectLastCall().once();
    mockDeck.swapTopBottomCards();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(swapCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSwapTopBottomTwoCardsInDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    mockPlayer1.removeCard(swapCard);
    mockDeck.discardCard(swapCard);
    expectLastCall().once();
    mockDeck.swapTopBottomCards();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(swapCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCardSwapTopBottomMoreThanTwoCardsInDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    mockPlayer1.removeCard(swapCard);
    mockDeck.discardCard(swapCard);
    expectLastCall().once();
    mockDeck.swapTopBottomCards();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCard(swapCard);

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @ParameterizedTest
  @CsvSource({
      "TACOCAT, true",
      "RAINBOW_RALPHING_CAT, true",
      "BEARD_CAT, true",
      "CATTERMELON, true",
      "FERAL_CAT, true",
      "ATTACK, false",
      "EXPLODING_KITTEN, false",
      "DEFUSE, false"
  })
  public void isCatCard(CardType type, boolean expected) {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertEquals(expected, game.isCatCard(type));
  }

  @Test
  public void isCatCardTypeIsNull() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isCatCard(null);
    });

    assertEquals("invalid card", e.getMessage());
  }

  @Test
  public void isValidCatComboNullListThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.isValidCatCombo(null));

    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void isValidCatComboEmptyListThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.isValidCatCombo(List.of()));

    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  static Stream<Arguments> isValidCatComboProvider() {
    return Stream.of(
        Arguments.of(List.of(
                new Card(CardType.TACOCAT)),
            false),
        // TC91: 2 same real cats
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.BEARD_CAT)),
            false),
        Arguments.of(List.of(
                new Card(CardType.FERAL_CAT),
                new Card(CardType.TACOCAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.FERAL_CAT),
                new Card(CardType.FERAL_CAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.ATTACK)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.FERAL_CAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.FERAL_CAT),
                new Card(CardType.FERAL_CAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.FERAL_CAT),
                new Card(CardType.FERAL_CAT),
                new Card(CardType.FERAL_CAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.CATTERMELON)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.FERAL_CAT)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.ATTACK)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.CATTERMELON)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.FERAL_CAT)),
            true),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.CATTERMELON)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.FERAL_CAT),
                new Card(CardType.FERAL_CAT)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.HAIRY_POTATO_CAT),
                new Card(CardType.RAINBOW_RALPHING_CAT),
                new Card(CardType.BEARD_CAT),
                new Card(CardType.ATTACK)),
            false),
        Arguments.of(List.of(
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT),
                new Card(CardType.TACOCAT)),
            false)
    );
  }

  @ParameterizedTest
  @MethodSource("isValidCatComboProvider")
  public void isValidCatCombo(List<Card> cards, boolean expected) {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertEquals(expected, game.isValidCatCombo(cards));
  }

  @Test
  public void alterFutureMoreThanThreeCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card fourthCard = new Card(CardType.SKIP);

    List<Card> reorderedCards = List.of(thirdCard, firstCard, secondCard);

    mockPlayer1.removeCard(alterFuture);
    mockDeck.discardCard(alterFuture);
    expectLastCall().once();
    mockDeck.reorderTopCards(reorderedCards);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
            List.of(mockPlayer1, mockPlayer2, mockPlayer3),
            mockDeck,
            new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(alterFuture, reorderedCards);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void alterFutureExactlyThreeCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);

    List<Card> reorderedCards = List.of(secondCard, thirdCard, firstCard);

    mockPlayer1.removeCard(alterFuture);
    mockDeck.discardCard(alterFuture);
    expectLastCall().once();
    mockDeck.reorderTopCards(reorderedCards);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(List.of(mockPlayer1, mockPlayer2, mockPlayer3), mockDeck, new Random(RANDOM_SEED));

    game.startGame();
    List<Card> result = game.playCard(alterFuture, reorderedCards);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void alterFutureWithOneCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card onlyCard = new Card(CardType.FAVOR);

    List<Card> reorderedCards = List.of(onlyCard);

    mockPlayer1.removeCard(alterFuture);
    mockDeck.discardCard(alterFuture);
    expectLastCall().once();
    mockDeck.reorderTopCards(reorderedCards);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
          List.of(mockPlayer1, mockPlayer2, mockPlayer3),
          mockDeck,
          new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(alterFuture, reorderedCards);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void alterFutureEmptyDeck() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card alterFuture = new Card(CardType.ALTER_FUTURE);

    mockPlayer1.removeCard(alterFuture);
    mockDeck.discardCard(alterFuture);
    expectLastCall().once();
    mockDeck.reorderTopCards(Collections.emptyList());
    expectLastCall().andThrow(new IllegalStateException("deck is empty")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () ->
        game.playCard(alterFuture, Collections.emptyList()));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void alterFutureInvalidOrder() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card wrongCard = new Card(CardType.ATTACK);

    List<Card> reorderedCards = List.of(wrongCard, firstCard, secondCard);

    mockPlayer1.removeCard(alterFuture);
    mockDeck.discardCard(alterFuture);
    expectLastCall().once();
    mockDeck.reorderTopCards(reorderedCards);
    expectLastCall().andThrow(new IllegalArgumentException("invalid reorder")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () ->
        game.playCard(alterFuture, reorderedCards));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void curseNextPlayerHasNoDefuse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card curseCard = new Card(CardType.CURSE);

    mockPlayer1.removeCard(curseCard);
    mockDeck.discardCard(curseCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.hasDefuse()).andReturn(false).once();
    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(curseCard);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void curseNextPlayerHasOneDefuse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card curseCard = new Card(CardType.CURSE);
    Card defuseCard = new Card(CardType.DEFUSE);

    mockPlayer1.removeCard(curseCard);
    mockDeck.discardCard(curseCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.hasDefuse()).andReturn(true).once();
    mockPlayer2.removeCard(defuseCard);
    mockDeck.addToDrawPile(defuseCard, 0);
    expect(mockPlayer2.hasDefuse()).andReturn(false).once();
    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(curseCard);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void curseNextPlayerHasMultipleDefuses() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card curseCard = new Card(CardType.CURSE);
    Card defuseCard = new Card(CardType.DEFUSE);

    mockPlayer1.removeCard(curseCard);
    mockDeck.discardCard(curseCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.hasDefuse()).andReturn(true).once();
    mockPlayer2.removeCard(defuseCard);
    mockDeck.addToDrawPile(defuseCard, 0);
    expect(mockPlayer2.hasDefuse()).andReturn(true).once();
    mockPlayer2.removeCard(defuseCard);
    mockDeck.addToDrawPile(defuseCard, 0);
    expect(mockPlayer2.hasDefuse()).andReturn(false).once();
    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(curseCard);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void curseOnlyOneOtherPlayerAlive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card curseCard = new Card(CardType.CURSE);
    Card defuseCard = new Card(CardType.DEFUSE);

    mockPlayer1.removeCard(curseCard);
    mockDeck.discardCard(curseCard);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.hasDefuse()).andReturn(true).once();
    mockPlayer2.removeCard(defuseCard);
    mockDeck.addToDrawPile(defuseCard, 0);
    expect(mockPlayer2.hasDefuse()).andReturn(false).once();
    mockDeck.shuffle();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(curseCard);

    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playDrawFromBottomWithManyCards() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card drawFromBottom = new Card(CardType.DRAW_FROM_BOTTOM);
    Card bottomCard = new Card(CardType.FAVOR);

    mockPlayer1.removeCard(drawFromBottom);
    mockDeck.discardCard(drawFromBottom);
    expectLastCall().once();
    expect(mockDeck.drawFromBottom()).andReturn(bottomCard).once();
    mockPlayer1.addCard(bottomCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(drawFromBottom);

    assertEquals(Collections.emptyList(), result);
    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playDrawFromBottomWithOneCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card drawFromBottom = new Card(CardType.DRAW_FROM_BOTTOM);
    Card onlyCard = new Card(CardType.FAVOR);

    mockPlayer1.removeCard(drawFromBottom);
    mockDeck.discardCard(drawFromBottom);
    expectLastCall().once();
    expect(mockDeck.drawFromBottom()).andReturn(onlyCard).once();
    mockPlayer1.addCard(onlyCard);
    mockPlayer1.removeTurn();
    expect(mockPlayer1.getTurnsOwed()).andReturn(0).once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getTurnsOwed()).andReturn(1).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(drawFromBottom);

    assertEquals(Collections.emptyList(), result);
    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playDrawFromBottomWithEmptyDeckThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockDeck.drawFromBottom())
        .andThrow(new IllegalStateException("Draw pile is empty"))
        .once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.playDrawFromBottom());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void targetedAttackTargetIsNextPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card targetedAttack = new Card(CardType.TARGETED_ATTACK);

    mockPlayer1.removeCard(targetedAttack);
    mockDeck.discardCard(targetedAttack);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    mockPlayer2.addTurn();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(targetedAttack, mockPlayer2);

    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void targetedAttackTargetIsNotNextPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card targetedAttack = new Card(CardType.TARGETED_ATTACK);

    mockPlayer1.removeCard(targetedAttack);
    mockDeck.discardCard(targetedAttack);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();
    mockPlayer3.addTurn();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(targetedAttack, mockPlayer3);

    assertEquals(2, game.getCurrentPlayerIndex());
    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void targetedAttackOnlyOneOtherPlayerAlive() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card targetedAttack = new Card(CardType.TARGETED_ATTACK);

    mockPlayer1.removeCard(targetedAttack);
    mockDeck.discardCard(targetedAttack);
    expectLastCall().once();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    mockPlayer2.addTurn();
    expect(mockPlayer3.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playCard(targetedAttack, mockPlayer2);

    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(Collections.emptyList(), result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void targetedAttackTargetIsDeadPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card targetedAttack = new Card(CardType.TARGETED_ATTACK);

    mockPlayer1.removeCard(targetedAttack);
    expectLastCall().andThrow(new IllegalArgumentException("target is dead")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () ->
        game.playCard(targetedAttack, mockPlayer3));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }
}