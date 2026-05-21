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
  private static final int TOO_FEW_PLAYERS = 2;
  private static final int MIN_PLAYERS = 3;
  private static final int VALID_PLAYER_COUNT = 4;
  private static final int MAX_PLAYERS = 5;
  private static final int TOO_MANY_PLAYERS = 6;
  private static final int FIRST_PLAYER_INDEX = 0;
  private static final int SECOND_PLAYER_INDEX = 1;
  private static final int THIRD_PLAYER_INDEX = 2;
  private static final int FOURTH_PLAYER_INDEX = 3;

  @Test
  public void startGameValidPlayerCount() {
    Game game = new Game(VALID_PLAYER_COUNT, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(VALID_PLAYER_COUNT, game.getPlayers().size());
    assertNotNull(game.getDrawPile());
    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameLowerBoundaryPlayerCount() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(MIN_PLAYERS, game.getPlayers().size());
    assertNotNull(game.getDrawPile());
    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameUpperBoundaryPlayerCount() {
    Game game = new Game(MAX_PLAYERS, new Random(RANDOM_SEED));

    game.startGame();

    assertTrue(game.isGameLaunched());
    assertEquals(MAX_PLAYERS, game.getPlayers().size());
    assertNotNull(game.getDrawPile());
    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void startGameTooFewPlayersThrowException() {
    Game game = new Game(TOO_FEW_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.startGame());
  }

  @Test
  public void startGameTooManyPlayersThrowException() {
    Game game = new Game(TOO_MANY_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.startGame());
  }

  @Test
  public void startGameTwiceThrowException() {
    Game game = new Game(VALID_PLAYER_COUNT, new Random(RANDOM_SEED));
    game.startGame();

    assertThrows(IllegalStateException.class, () -> game.startGame());
  }

  @Test
  public void validatePlayerCountThreePlayers() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertDoesNotThrow(() -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountFivePlayers() {
    Game game = new Game(MAX_PLAYERS, new Random(RANDOM_SEED));

    assertDoesNotThrow(() -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountTooFewPlayersThrowException() {
    Game game = new Game(TOO_FEW_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());
  }

  @Test
  public void validatePlayerCountTooManyPlayersThrowException() {
    Game game = new Game(TOO_MANY_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalArgumentException.class, () -> game.validatePlayerCount());
  }

  @Test
  public void initializeTurnOrderThreePlayers() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    game.startGame();

    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertEquals(MIN_PLAYERS, game.getPlayers().size());
  }

  @Test
  public void initializeTurnOrderFourPlayers() {
    Game game = new Game(VALID_PLAYER_COUNT, new Random(RANDOM_SEED));

    game.startGame();

    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertEquals(VALID_PLAYER_COUNT, game.getPlayers().size());
  }

  @Test
  public void initializeTurnOrderFivePlayers() {
    Game game = new Game(MAX_PLAYERS, new Random(RANDOM_SEED));

    game.startGame();

    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertEquals(MAX_PLAYERS, game.getPlayers().size());
  }

  @Test
  public void initializeTurnOrderBeforePlayersExistThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.initializeTurnOrder());
  }

  @Test
  public void getCurrentPlayerFirstPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
  }

  @Test
  public void getCurrentPlayerLastPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(players.get(THIRD_PLAYER_INDEX), game.getCurrentPlayer());
  }

  @Test
  public void getCurrentPlayerIndexOutOfBoundsThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.getCurrentPlayer());
  }

  @Test
  public void runGameBeforeStartGameThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.runGame());
  }

  @Test
  public void runGameWhenGameIsAlreadyOver() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();
    game.checkWinner();

    assertDoesNotThrow(() -> game.runGame());
    assertTrue(game.isGameOver());
  }

  @Test
  public void runGameWhileGameIsNotOver() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    int handSizeBefore = firstPlayer.getHand().size();

    game.runGame();

    assertEquals(handSizeBefore + 1, firstPlayer.getHand().size());
    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertFalse(game.isGameOver());
  }

  @Test
  public void runGameWhenGameBecomesOverDuringTurn() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    List<Player> players = game.getPlayers();
    while (firstPlayer.hasDefuse()) {
      firstPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    players.get(THIRD_PLAYER_INDEX).die();
    game.addToDrawPile(new Card(CardType.EXPLODING_KITTEN), 0);

    game.runGame();

    assertFalse(firstPlayer.isAlive());
    assertTrue(game.isGameOver());
  }

  @Test
  public void handleTurnNormalCurrentPlayerTurn() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();

    game.handleTurn();

    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void handleTurnPlayerOwingTwoTurns() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().addTurn();

    game.handleTurn();

    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
  }

  @Test
  public void handleTurnPlayerOwingZeroTurns() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().removeTurn();

    game.handleTurn();

    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void handleTurnEliminatedCurrentPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().die();

    game.handleTurn();

    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void getNextActivePlayerNextPlayerIsActive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();

    assertEquals(players.get(SECOND_PLAYER_INDEX), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerCurrentPlayerIsDead() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(FIRST_PLAYER_INDEX).die();

    assertEquals(players.get(SECOND_PLAYER_INDEX), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerNextPlayerIsEliminated() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();

    assertEquals(players.get(THIRD_PLAYER_INDEX), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerMultipleDeadPlayersInRow() {
    Game game = new Game(MAX_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();

    assertEquals(players.get(FOURTH_PLAYER_INDEX), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerWrapAroundToFirstPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    assertEquals(players.get(FIRST_PLAYER_INDEX), game.getNextActivePlayer());
  }

  @Test
  public void getNextActivePlayerOnlyOneAlivePlayerLeft() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();

    assertNull(game.getNextActivePlayer());
    assertTrue(game.isGameOver());
  }

  @Test
  public void moveToNextPlayerNormally() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    game.moveToNextPlayer();

    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerFromLastPlayerToFirstPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.moveToNextPlayer();
    game.moveToNextPlayer();

    game.moveToNextPlayer();

    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerPastEliminatedPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();

    game.moveToNextPlayer();

    assertEquals(THIRD_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void moveToNextPlayerWhenNextPlayerHasZeroTurnsOwed() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).removeTurn();

    game.moveToNextPlayer();

    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
  }

  @Test
  public void completeOneTurnOneOwedTurn() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();

    game.completeOneTurn();

    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void completeOneTurnOneOfTwoOwedTurns() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().addTurn();

    game.completeOneTurn();

    assertEquals(1, game.getCurrentPlayer().getTurnsOwed());
    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void completeOneTurnWhenZeroTurnsAreOwedThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    game.getCurrentPlayer().removeTurn();

    assertThrows(IllegalStateException.class, () -> game.completeOneTurn());
  }

  @Test
  public void drawCardNormalCardForCurrentPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    int handSizeBefore = firstPlayer.getHand().size();
    int deckSizeBefore = game.getDrawPile().size();

    game.drawCard();

    assertEquals(handSizeBefore + 1, firstPlayer.getHand().size());
    assertEquals(deckSizeBefore - 1, game.getDrawPile().size());
    assertEquals(0, firstPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void drawCardLastCardFromDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    int handSizeBefore = firstPlayer.getHand().size();
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(new Card(CardType.SKIP), 0);

    game.drawCard();

    assertEquals(handSizeBefore + 1, firstPlayer.getHand().size());
    assertEquals(0, game.getDrawPile().size());
  }

  @Test
  public void drawCardFromEmptyDeckThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    assertThrows(IllegalStateException.class, () -> game.drawCard());
  }

  @Test
  public void drawCardExplodingKittenWithDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    int defuseCountBefore = countCards(firstPlayer, CardType.DEFUSE);
    game.addToDrawPile(explodingKitten, 0);
    int deckSizeBefore = game.getDrawPile().size();

    game.drawCard();

    assertTrue(firstPlayer.isAlive());
    assertEquals(defuseCountBefore - 1, countCards(firstPlayer, CardType.DEFUSE));
    assertFalse(firstPlayer.getHand().contains(explodingKitten));
    assertEquals(deckSizeBefore, game.getDrawPile().size());
    assertEquals(explodingKitten, game.getDrawPile().get(0));
  }

  @Test
  public void drawCardExplodingKittenWithoutDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    while (firstPlayer.hasDefuse()) {
      firstPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    game.addToDrawPile(new Card(CardType.EXPLODING_KITTEN), 0);

    game.drawCard();

    assertFalse(firstPlayer.isAlive());
    assertFalse(game.isGameOver());
  }

  @Test
  public void playCardPlayableCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(skip);
    int handSizeBefore = currentPlayer.getHand().size();
    int discardSizeBefore = game.getDiscardPile().size();

    game.playCard(skip);

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());
    assertFalse(currentPlayer.getHand().contains(skip));
    assertEquals(discardSizeBefore + 1, game.getDiscardPile().size());
    assertEquals(skip, game.getDiscardPile().get(discardSizeBefore));
  }

  @Test
  public void playCardCardNotInHandThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.playCard(new Card(CardType.SKIP)));
  }

  @Test
  public void playCardNonPlayableCardThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Card defuse = new Card(CardType.DEFUSE);
    game.getCurrentPlayer().addCard(defuse);

    assertThrows(IllegalArgumentException.class, () -> game.playCard(defuse));
  }

  @Test
  public void playCardBeforeGameStartsThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    assertThrows(IllegalStateException.class, () -> game.playCard(new Card(CardType.SKIP)));
  }

  @Test
  public void playCardAfterGameIsOverThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();
    game.getNextActivePlayer();

    assertThrows(IllegalStateException.class, () -> game.playCard(new Card(CardType.SKIP)));
  }

  @Test
  public void checkWinnerMoreThanOnePlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    game.checkWinner();

    assertFalse(game.isGameOver());
  }

  @Test
  public void checkWinnerExactlyOnePlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();

    game.checkWinner();

    assertTrue(game.isGameOver());
  }

  @Test
  public void checkWinnerNoPlayersAliveThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(FIRST_PLAYER_INDEX).die();
    players.get(SECOND_PLAYER_INDEX).die();
    players.get(THIRD_PLAYER_INDEX).die();

    assertThrows(IllegalStateException.class, () -> game.checkWinner());
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
