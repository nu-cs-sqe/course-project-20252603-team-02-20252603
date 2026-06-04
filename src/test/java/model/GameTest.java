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

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GameTest {
  private static final int MIN_PLAYERS = 3;
  private static final int VALID_PLAYER_COUNT = 4;
  private static final int MAX_PLAYERS = 5;
  private static final int RANDOM_SEED = 42;

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
    game.drawCard();

    assertEquals(1, game.getCurrentPlayerIndex());

    verify(mockPlayer1, mockPlayer2, mockPlayer3, mockDeck, mockCard);
  }
}