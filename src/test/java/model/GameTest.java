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
  public void drawCardEmptyDeckKillsCurrentPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockDeck.drawCard()).andThrow(new IllegalStateException("Draw pile is empty")).once();
    mockPlayer1.die();
    expect(mockPlayer1.isAlive()).andReturn(false).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer3.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.drawCard(0);

    assertFalse(game.isGameOver());

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
    expect(mockPlayer1.hasDefuse()).andReturn(true).times(2);
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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.playCard(seeTheFutureCard);
    List<Card> result = game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

    assertEquals(0, game.getCurrentPlayerIndex());
    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsCardsNotInHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(Collections.emptyList()).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCard(List.of(cat1, cat2), mockPlayer2, null));

    assertEquals("cards not in hand", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsOnlyOneRequiredCardInHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCard(List.of(cat1, cat2), mockPlayer2, null));

    assertEquals("cards not in hand", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsCardsNotInHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(Collections.emptyList()).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCard(List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR));

    assertEquals("cards not in hand", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsCardsNotInHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand()).andReturn(Collections.emptyList()).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);
    });

    assertEquals("cards not in hand", e.getMessage());

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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

    assertThrows(IllegalStateException.class, () -> {
      game.playCard(seeTheFutureCard);
      game.resolvePendingAction();
    });

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
    game.playCard(seeTheFutureCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(seeTheFutureCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(seeTheFutureCard);
    List<Card> result = game.resolvePendingAction();

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

    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
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
    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
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
    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
    expect(mockDeck.getDeck()).andReturn(List.of()).once();
    mockPlayer1.removeCard(new Card(CardType.DEFUSE));
    mockDeck.discardCard(new Card(CardType.DEFUSE));
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

    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
    expect(mockDeck.getDeck()).andReturn(List.of(topCard)).once();
    mockPlayer1.removeCard(new Card(CardType.DEFUSE));
    mockDeck.discardCard(new Card(CardType.DEFUSE));
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
    expect(mockPlayer1.hasDefuse()).andReturn(true).once();
    expect(mockDeck.getDeck()).andReturn(List.of(topCard, bottomCard)).once();
    mockPlayer1.removeCard(new Card(CardType.DEFUSE));
    mockDeck.discardCard(new Card(CardType.DEFUSE));
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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.resolvePendingAction();

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
    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();

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

    Game game = new Game(List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck, new Random(RANDOM_SEED));

    game.startGame();
    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();

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

    assertThrows(IllegalStateException.class, () -> {
      game.playCard(alterFuture, Collections.emptyList());
      game.resolvePendingAction();
    });

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

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(alterFuture, reorderedCards);
      game.resolvePendingAction();
    });

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
    game.playCard(curseCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(curseCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(curseCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(curseCard);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(drawFromBottom);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(drawFromBottom);
    List<Card> result = game.resolvePendingAction();

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

    assertThrows(IllegalStateException.class, () -> {
      game.playDrawFromBottom();
      game.resolvePendingAction();
    });

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
    game.playCard(targetedAttack, mockPlayer2);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(targetedAttack, mockPlayer3);
    List<Card> result = game.resolvePendingAction();

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
    game.playCard(targetedAttack, mockPlayer2);
    List<Card> result = game.resolvePendingAction();

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

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(targetedAttack, mockPlayer3);
      game.resolvePendingAction();
    });

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void targetedAttackTargetIsCurrentPlayer() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card targetedAttack = new Card(CardType.TARGETED_ATTACK);

    mockPlayer1.removeCard(targetedAttack);
    expectLastCall().andThrow(new IllegalArgumentException("cannot target yourself")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(targetedAttack, mockPlayer1);
      game.resolvePendingAction();
    });

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNosyOnNegativeIndexThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card nosyCard = new Card(CardType.NOSY);
    expect(mockPlayer1.getHand()).andReturn(List.of(nosyCard)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(-1));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNosyOnTooLargeIndexThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card nosyCard = new Card(CardType.NOSY);
    expect(mockPlayer1.getHand()).andReturn(List.of(nosyCard)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(MIN_PLAYERS));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNosyOnSelfThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card nosyCard = new Card(CardType.NOSY);
    expect(mockPlayer1.getHand()).andReturn(List.of(nosyCard)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(0));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNosyOnDeadPlayerThrowException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card nosyCard = new Card(CardType.NOSY);
    expect(mockPlayer1.getHand()).andReturn(List.of(nosyCard)).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(1));

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNosyOnValidTarget() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card nosyCard = new Card(CardType.NOSY);
    Card targetCard = new Card(CardType.SKIP);
    List<Card> targetHand = List.of(targetCard);

    expect(mockPlayer1.getHand()).andReturn(List.of(nosyCard)).anyTimes();
    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(targetHand).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    List<Card> result = game.playNosy(1);

    assertEquals(targetHand, result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsNullTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playTwoMatchingCats(List.of(cat1, cat2), null));

    assertEquals("target cannot be null or dead", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsDeadTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playTwoMatchingCats(List.of(cat1, cat2), mockPlayer2));

    assertEquals("target cannot be null or dead", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsSelfTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playTwoMatchingCats(List.of(cat1, cat2), mockPlayer1));

    assertEquals("cannot target yourself", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsNonMatchingCatsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.BEARD_CAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playTwoMatchingCats(List.of(cat1, cat2), mockPlayer2));

    assertEquals("invalid two-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsThreeCardsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playTwoMatchingCats(List.of(cat1, cat2, cat3), mockPlayer2));

    assertEquals("invalid two-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playTwoMatchingCatsTargetHasOneCardReturnsStolenCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card skip = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(skip)).anyTimes();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer2.removeCard(skip);
    mockPlayer1.addCard(skip);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), mockPlayer2);

    assertEquals(skip, stolen);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playThreeMatchingCatsNullTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2, cat3), null, CardType.FAVOR));

    assertEquals("target cannot be null or dead", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsDeadTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR));

    assertEquals("target cannot be null or dead", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsSelfTargetThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer1.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2, cat3), mockPlayer1, CardType.FAVOR));

    assertEquals("cannot target yourself", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsNullNamedCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2, cat3), mockPlayer2, null));

    assertEquals("invalid wanted card type", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsExplodingKittenNamedCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(
            List.of(cat1, cat2, cat3), mockPlayer2, CardType.EXPLODING_KITTEN));

    assertEquals("invalid wanted card type", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsNonMatchingCatsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.BEARD_CAT);
    Card cat3 = new Card(CardType.CATTERMELON);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR));

    assertEquals("invalid three-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsTwoCardsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playThreeMatchingCats(List.of(cat1, cat2), mockPlayer2, CardType.FAVOR));

    assertEquals("invalid three-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsTargetHasNoNamedCardReturnsFalse() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2, cat3)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(new Card(CardType.ATTACK))).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    boolean result = game.playThreeMatchingCats(
        List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR);

    assertFalse(result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playThreeMatchingCatsTargetHasOneNamedCardReturnsTrue() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2, cat3)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(favor)).anyTimes();
    mockPlayer2.removeCard(favor);
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    boolean result = game.playThreeMatchingCats(
        List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR);

    assertTrue(result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoCardsIsNullThrowsIllegalArgumentException() {
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

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(null));

    assertEquals("cards cannot be null", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoCardsIsEmptyThrowsIllegalArgumentException() {
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

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of()));

    assertEquals("must play exactly 3 neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoOneCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko = new Card(CardType.NEKO);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoTwoCardsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoFourCardsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);
    Card neko4 = new Card(CardType.NEKO);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, neko3, neko4)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoOneCardIsNullThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);

    List<Card> cards = new java.util.ArrayList<>();
    cards.add(neko1);
    cards.add(null);
    cards.add(neko2);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(cards));

    assertEquals("all cards must be neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoOneCardIsNotNekoThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card attack = new Card(CardType.ATTACK);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, attack)));

    assertEquals("all cards must be neko cards", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoThreeNekosOnlyTwoInHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);

    expect(mockPlayer1.getHand()).andReturn(List.of(neko1, neko2)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, neko3)));

    assertEquals("cards not in hand", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoThreeNekosGameOver() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);

    expect(mockPlayer1.getHand()).andReturn(List.of(neko1, neko2, neko3)).anyTimes();
    mockPlayer1.removeCard(neko1);
    mockPlayer1.removeCard(neko2);
    mockPlayer1.removeCard(neko3);
    mockDeck.discardCard(neko1);
    mockDeck.discardCard(neko2);
    mockDeck.discardCard(neko3);
    mockPlayer2.die();
    mockPlayer3.die();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playNeko(List.of(neko1, neko2, neko3));

    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playNekoOneOpponentAlreadyDeadRemainingOpponentDies() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);

    expect(mockPlayer1.getHand()).andReturn(List.of(neko1, neko2, neko3)).anyTimes();
    mockPlayer1.removeCard(neko1);
    mockPlayer1.removeCard(neko2);
    mockPlayer1.removeCard(neko3);
    mockDeck.discardCard(neko1);
    mockDeck.discardCard(neko2);
    mockDeck.discardCard(neko3);
    mockPlayer2.die();
    mockPlayer3.die();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playNeko(List.of(neko1, neko2, neko3));

    assertTrue(game.isGameOver());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetIsDeadThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(false).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(mockPlayer2, new Card(CardType.SKIP)));

    assertEquals("invalid target", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetIsCurrentPlayerThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer1.isAlive()).andReturn(true).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(mockPlayer1, new Card(CardType.SKIP)));

    assertEquals("cannot target yourself", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetIsNullThrowsIllegalArgumentException() {
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

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(null, new Card(CardType.SKIP)));

    assertEquals("invalid target", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorGivenIsNullThrowsIllegalArgumentException() {
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

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(mockPlayer2, null));

    assertEquals("given card cannot be null", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetHasEmptyHandThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(Collections.emptyList()).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(mockPlayer2, new Card(CardType.SKIP)));

    assertEquals("target does not have that card", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetHasOneCardGivenDoesNotMatchThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card attack = new Card(CardType.ATTACK);
    Card skip = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(attack)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(mockPlayer2, skip));

    assertEquals("target does not have that card", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetHasOneMatchingCardTransfersCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skip = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(skip)).anyTimes();
    mockPlayer2.removeCard(skip);
    mockPlayer1.addCard(skip);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playFavor(mockPlayer2, skip);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetHasTwoCardsGivenMatchesOneTransfersCorrectCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skip = new Card(CardType.SKIP);
    Card attack = new Card(CardType.ATTACK);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(skip, attack)).anyTimes();
    mockPlayer2.removeCard(skip);
    mockPlayer1.addCard(skip);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playFavor(mockPlayer2, skip);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFavorTargetHasTwoCopiesOfGivenTransfersExactlyOne() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card skip1 = new Card(CardType.SKIP);
    Card skip2 = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(skip1, skip2)).anyTimes();
    mockPlayer2.removeCard(skip1);
    mockPlayer1.addCard(skip1);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playFavor(mockPlayer2, skip1);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsNullCardsThrowsIllegalArgumentException() {
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

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFiveDifferentCats(null, CardType.FAVOR));

    assertEquals("cards cannot be null", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsNullWantedCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), null));

    assertEquals("invalid wanted card type", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsExplodingKittenWantedCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFiveDifferentCats(
            List.of(cat1, cat2, cat3, cat4, cat5), CardType.EXPLODING_KITTEN));

    assertEquals("invalid wanted card type", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsDuplicateCatTypeThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);
    });

    assertEquals("invalid five-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsThreeCardsThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2, cat3)).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3), CardType.FAVOR);
    });

    assertEquals("invalid five-cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsEmptyDiscardPileThrowsIllegalStateException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR))
        .andThrow(new IllegalStateException("discard pile is empty")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalStateException.class, () ->
        game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR));

    assertEquals("discard pile is empty", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsDiscardHasOneNonMatchingCardThrowsIllegalArgumentException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR))
        .andThrow(new IllegalArgumentException("card type not in discard pile")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR));

    assertEquals("card type not in discard pile", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsDiscardHasOneMatchingCardReturnsWantedCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR)).andReturn(favor).once();
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Card result = game.playFiveDifferentCats(
        List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);

    assertEquals(favor, result);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsDiscardHasTwoMatchingCardsTransfersOneCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR)).andReturn(favor).once();
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Card result = game.playFiveDifferentCats(
        List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);

    assertEquals(CardType.FAVOR, result.getType());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playFiveDifferentCatsDiscardHasWantedCardAmongOtherCardsReturnsWantedCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR)).andReturn(favor).once();
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Card result = game.playFiveDifferentCats(
        List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);

    assertEquals(CardType.FAVOR, result.getType());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsGameNotLaunchedThrowsIllegalStateException() {
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

    List<Card> cards = List.of(new Card(CardType.TACOCAT), new Card(CardType.TACOCAT));

    Exception e = assertThrows(IllegalStateException.class, () ->
        game.playCatCards(cards, mockPlayer2, null));

    assertEquals("game has not started", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsGameIsOverThrowsIllegalStateException() {
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

    List<Card> cards = List.of(new Card(CardType.TACOCAT), new Card(CardType.TACOCAT));

    Exception e = assertThrows(IllegalStateException.class, () ->
        game.playCatCards(cards, mockPlayer3, null));

    assertEquals("game is over", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsCardsIsNullThrowsIllegalArgumentException() {
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

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCatCards(null, mockPlayer2, CardType.FAVOR));

    assertEquals("cards cannot be null or empty", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsComboSizeOneThrowsIllegalArgumentException() {
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

    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCatCards(List.of(new Card(CardType.TACOCAT)), mockPlayer2, null));

    assertEquals("invalid cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsComboSizeFourThrowsIllegalArgumentException() {
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

    game.startGame();

    List<Card> fourCats = List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT)
    );

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCatCards(fourCats, mockPlayer2, null));

    assertEquals("invalid cat combo", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsTwoMatchingCatsTargetHasOneCardStealsCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);
    Random mockRandom = createMock(Random.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card skip = new Card(CardType.SKIP);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(skip)).anyTimes();
    expect(mockRandom.nextInt(1)).andReturn(0).once();
    mockPlayer2.removeCard(skip);
    mockPlayer1.addCard(skip);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        mockRandom
    );

    game.startGame();
    game.playCatCards(List.of(cat1, cat2), mockPlayer2, null);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockRandom);
  }

  @Test
  public void playCatCardsThreeMatchingCatsTargetHasNamedCardTransfersCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2, cat3)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(favor)).anyTimes();
    mockPlayer2.removeCard(favor);
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCatCards(List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsThreeMatchingCatsTargetLacksNamedCardNothingTransferred() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    expect(mockPlayer2.isAlive()).andReturn(true).anyTimes();
    expect(mockPlayer1.getHand()).andReturn(List.of(cat1, cat2, cat3)).anyTimes();
    expect(mockPlayer2.getHand()).andReturn(List.of(new Card(CardType.ATTACK))).anyTimes();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCatCards(List.of(cat1, cat2, cat3), mockPlayer2, CardType.FAVOR);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsFiveDifferentCatsNamedCardInDiscardTransfersCard() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR)).andReturn(favor).once();
    mockPlayer1.addCard(favor);

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();
    game.playCatCards(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }

  @Test
  public void playCatCardsFiveDifferentCatsNamedCardNotInDiscardThrowsIllegalStateException() {
    Player mockPlayer1 = createMock(Player.class);
    Player mockPlayer2 = createMock(Player.class);
    Player mockPlayer3 = createMock(Player.class);
    Deck mockDeck = createMock(Deck.class);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    expect(mockPlayer1.getHand())
        .andReturn(List.of(cat1, cat2, cat3, cat4, cat5)).anyTimes();
    expect(mockDeck.takeFromDiscard(CardType.FAVOR))
        .andThrow(new IllegalStateException("discard pile is empty")).once();

    replay(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);

    Game game = new Game(
        List.of(mockPlayer1, mockPlayer2, mockPlayer3),
        mockDeck,
        new Random(RANDOM_SEED)
    );

    game.startGame();

    Exception e = assertThrows(IllegalStateException.class, () ->
        game.playCatCards(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR));

    assertEquals("discard pile is empty", e.getMessage());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck);
  }
}
