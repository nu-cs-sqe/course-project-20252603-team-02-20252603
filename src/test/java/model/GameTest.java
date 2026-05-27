package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;
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
  private static final int NUM_CARDS_PEEKED = 3;

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

  @Test
  void seeTheFutureEmptyDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.SEE_THE_FUTURE));

    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    assertThrows(IllegalStateException.class, () ->
            game.playCard(new Card(CardType.SEE_THE_FUTURE)));
  }

  @Test
  void seeTheFutureOneCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.SEE_THE_FUTURE));

    while (game.getDrawPile().size() > 1) {
      game.drawFromDeck();
    }

    List<Card> drawPile = game.getDrawPile();
    Card expectedFirst = drawPile.get(0);

    List<Card> result = game.playCard(new Card(CardType.SEE_THE_FUTURE));

    assertEquals(1, result.size());
    assertEquals(expectedFirst, result.get(0));
  }

  @Test
  void seeTheFutureMoreThanThreeCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.SEE_THE_FUTURE));

    List<Card> drawPile = game.getDrawPile();
    Card expectedFirst = drawPile.get(0);
    Card expectedSecond = drawPile.get(1);
    Card expectedThird = drawPile.get(2);

    List<Card> result = game.playCard(new Card(CardType.SEE_THE_FUTURE));

    assertEquals(NUM_CARDS_PEEKED, result.size());
    assertEquals(expectedFirst, result.get(0));
    assertEquals(expectedSecond, result.get(1));
    assertEquals(expectedThird, result.get(2));
  }

  @Test
  void seeTheFutureExactlyThreeCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.SEE_THE_FUTURE));

    while (game.getDrawPile().size() > NUM_CARDS_PEEKED) {
      game.drawFromDeck();
    }

    List<Card> drawPile = game.getDrawPile();
    Card expectedFirst = drawPile.get(0);
    Card expectedSecond = drawPile.get(1);
    Card expectedThird = drawPile.get(2);

    List<Card> result = game.playCard(new Card(CardType.SEE_THE_FUTURE));

    assertEquals(NUM_CARDS_PEEKED, result.size());
    assertEquals(expectedFirst, result.get(0));
    assertEquals(expectedSecond, result.get(1));
    assertEquals(expectedThird, result.get(2));
  }

  @Test
  public void IsCatCardTypeIsNull() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isCatCard(null);
    });

    assertEquals("invalid card", e.getMessage());
  }

  @Test
  public void IsCatCardTypeIsTacocat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.TACOCAT));
  }

  @Test
  public void IsCatCardTypeIsRainbowRalphingCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.RAINBOW_RALPHING_CAT));
  }

  @Test
  public void IsCatCardTypeIsBeardCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.BEARD_CAT));
  }

  @Test
  public void IsCatCardTypeIsCattermelon() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.CATTERMELON));
  }

  @Test
  public void IsCatCardTypeIsFeralCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.FERAL_CAT));
  }

  @Test
  public void IsCatCardTypeIsAttack() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.ATTACK));
  }

  @Test
  public void IsCatCardTypeIsExplodingKitten() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.EXPLODING_KITTEN));
  }

  @Test
  public void IsCatCardTypeIsDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.DEFUSE));
  }

  @Test
  public void isValidCatCombo_NullList_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isValidCatCombo(null);
    });
    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void isValidCatCombo_EmptyList_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isValidCatCombo(List.of());
    });
    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void isValidCatCombo_OneCard_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatCombo_TwoSameRealCats_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatCombo_TwoDifferentRealCats_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.BEARD_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_OneFeralOneRealCat_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.FERAL_CAT),
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatCombo_TwoFeralCats_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.FERAL_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_OneCatOneNonCat_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.ATTACK)
    )));
  }

  @Test
  public void isValidCatCombo_ThreeSameRealCats_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatCombo_TwoMatchingOneFeral_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_OneRealTwoFerals_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.FERAL_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_ThreeFerals_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.FERAL_CAT),
            new Card(CardType.FERAL_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_ThreeDifferentRealCats_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.CATTERMELON)
    )));
  }

  @Test
  public void isValidCatCombo_TwoDifferentRealCatsOneFeral_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_ThreeCardsIncludingNonCat_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.ATTACK)
    )));
  }

  @Test
  public void isValidCatCombo_FourCards_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatCombo_FiveDistinctRealCats_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.HAIRY_POTATO_CAT),
            new Card(CardType.RAINBOW_RALPHING_CAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.CATTERMELON)
    )));
  }

  @Test
  public void isValidCatCombo_FourDistinctRealCatsOneFeral_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.HAIRY_POTATO_CAT),
            new Card(CardType.RAINBOW_RALPHING_CAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_DuplicateRealCat_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.RAINBOW_RALPHING_CAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.CATTERMELON)
    )));
  }

  @Test
  public void isValidCatCombo_TwoFeralsInFiveCards_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.HAIRY_POTATO_CAT),
            new Card(CardType.RAINBOW_RALPHING_CAT),
            new Card(CardType.FERAL_CAT),
            new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatCombo_FiveCardsIncludingNonCat_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.HAIRY_POTATO_CAT),
            new Card(CardType.RAINBOW_RALPHING_CAT),
            new Card(CardType.BEARD_CAT),
            new Card(CardType.ATTACK)
    )));
  }

  @Test
  public void isValidCatCombo_SixCards_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void playTwoMatchingCats_NullTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), null);
    });

    assertEquals("target cannot be null or dead", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_DeadTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    target.die();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), target);
    });

    assertEquals("target cannot be null or dead", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_SelfTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), currentPlayer);
    });

    assertEquals("cannot target yourself", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_NonMatchingCats_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.BEARD_CAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), target);
    });

    assertEquals("invalid two-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_ThreeCards_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2, cat3), target);
    });

    assertEquals("invalid two-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_CardsNotInHand_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), target);
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_OnlyOneRequiredCardInHand_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playTwoMatchingCats(List.of(cat1, cat2), target);
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playTwoMatchingCats_TargetHasOneCard_ReturnsStolenCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    target.addCard(skip);
    int discardSizeBefore = game.getDiscardPile().size();

    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), target);

    assertEquals(CardType.SKIP, stolen.getType());
    assertTrue(currentPlayer.getHand().contains(stolen));
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertEquals(0, target.getHand().size());
    assertEquals(discardSizeBefore + 2, game.getDiscardPile().size());
  }

  @Test
  public void playTwoMatchingCats_TargetHasTwoCards_ReturnsStolenCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card skip = new Card(CardType.SKIP);
    Card attack = new Card(CardType.ATTACK);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    target.addCard(skip);
    target.addCard(attack);
    int discardSizeBefore = game.getDiscardPile().size();

    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), target);

    assertTrue(stolen.getType() == CardType.SKIP || stolen.getType() == CardType.ATTACK);
    assertTrue(currentPlayer.getHand().contains(stolen));
    assertEquals(1, target.getHand().size());
    assertFalse(target.getHand().contains(stolen));
    assertEquals(discardSizeBefore + 2, game.getDiscardPile().size());
  }

  @Test
  public void playTwoMatchingCats_TargetHasFiveCards_ReturnsStolenCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    target.addCard(new Card(CardType.SKIP));
    target.addCard(new Card(CardType.ATTACK));
    target.addCard(new Card(CardType.SHUFFLE));
    target.addCard(new Card(CardType.FAVOR));
    target.addCard(new Card(CardType.NOPE));

    int discardSizeBefore = game.getDiscardPile().size();

    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), target);

    assertNotNull(stolen);
    assertTrue(currentPlayer.getHand().contains(stolen));
    assertEquals(4, target.getHand().size());
    assertFalse(target.getHand().contains(stolen));
    assertEquals(discardSizeBefore + 2, game.getDiscardPile().size());
  }

  @Test
  public void playThreeMatchingCats_NullTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), null, CardType.FAVOR);
    });

    assertEquals("target cannot be null or dead", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_DeadTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    target.die();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    });

    assertEquals("target cannot be null or dead", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_SelfTarget_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), currentPlayer, CardType.FAVOR);
    });

    assertEquals("cannot target yourself", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_NullNamedCard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, null);
    });

    assertEquals("invalid wanted card type", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_ExplodingKittenNamedCard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.EXPLODING_KITTEN);
    });

    assertEquals("invalid wanted card type", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_NonMatchingCats_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.BEARD_CAT);
    Card cat3 = new Card(CardType.CATTERMELON);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    });

    assertEquals("invalid three-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_TwoCards_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2), target, CardType.FAVOR);
    });

    assertEquals("invalid three-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_CardsNotInHand_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playThreeMatchingCats_TargetHasNoNamedCard_ReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    target.addCard(new Card(CardType.ATTACK));
    int discardSizeBefore = game.getDiscardPile().size();

    boolean result = game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertFalse(result);
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(1, target.getHand().size());
    assertEquals(CardType.ATTACK, target.getHand().get(0).getType());
    assertEquals(discardSizeBefore + 3, game.getDiscardPile().size());
  }

  @Test
  public void playThreeMatchingCats_TargetHasOneNamedCard_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor = new Card(CardType.FAVOR);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    target.addCard(favor);
    int discardSizeBefore = game.getDiscardPile().size();

    boolean result = game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertTrue(result);
    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(0, target.getHand().size());
    assertEquals(discardSizeBefore + 3, game.getDiscardPile().size());
  }

  @Test
  public void playThreeMatchingCats_TargetHasTwoNamedCards_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor1 = new Card(CardType.FAVOR);
    Card favor2 = new Card(CardType.FAVOR);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    target.addCard(favor1);
    target.addCard(favor2);
    int discardSizeBefore = game.getDiscardPile().size();

    boolean result = game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertTrue(result);
    assertEquals(1, countCards(currentPlayer, CardType.FAVOR));
    assertEquals(1, countCards(target, CardType.FAVOR));
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(discardSizeBefore + 3, game.getDiscardPile().size());
  }

  @Test
  public void playThreeMatchingCats_TargetHasNamedCardAndOtherCards_ReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor = new Card(CardType.FAVOR);
    Card attack = new Card(CardType.ATTACK);
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    target.addCard(favor);
    target.addCard(attack);
    target.addCard(skip);
    int discardSizeBefore = game.getDiscardPile().size();

    boolean result = game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertTrue(result);
    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(target.getHand().contains(favor));
    assertTrue(target.getHand().contains(attack));
    assertTrue(target.getHand().contains(skip));
    assertEquals(2, target.getHand().size());
    assertEquals(discardSizeBefore + 3, game.getDiscardPile().size());
  }

  @Test
  public void playFiveDifferentCats_NullCards_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(null, CardType.FAVOR);
    });

    assertEquals("cards cannot be null", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_NullWantedCard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), null);
    });

    assertEquals("invalid wanted card type", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_ExplodingKittenWantedCard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
              CardType.EXPLODING_KITTEN);
    });

    assertEquals("invalid wanted card type", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_DuplicateCatType_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);
    });

    assertEquals("invalid five-cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_ThreeCards_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3), CardType.FAVOR);
    });

    assertEquals("invalid five-cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_CardsNotInHand_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_EmptyDiscardPile_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);

    Exception e = assertThrows(IllegalStateException.class, () -> {
      game.playFiveDifferentCats(
              List.of(cat1, cat2, cat3, cat4, cat5),
              CardType.FAVOR
      );
    });

    assertEquals("discard pile is empty", e.getMessage());
  }

  @Test
  public void playFiveDifferentCats_DiscardHasOneNonMatchingCard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card attack = new Card(CardType.ATTACK);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);
    game.addToDiscard(attack);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5), CardType.FAVOR);
    });

    assertEquals("card type not in discard pile", e.getMessage());
    assertTrue(game.getDiscardPile().contains(attack));
  }

  @Test
  public void playFiveDifferentCats_DiscardHasOneMatchingCard_ReturnsWantedCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);
    game.addToDiscard(favor);

    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
            CardType.FAVOR);

    assertEquals(CardType.FAVOR, result.getType());
    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(game.getDiscardPile().contains(favor));
  }

  @Test
  public void playFiveDifferentCats_DiscardHasTwoMatchingCards_TransfersOneCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor1 = new Card(CardType.FAVOR);
    Card favor2 = new Card(CardType.FAVOR);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);
    game.addToDiscard(favor1);
    game.addToDiscard(favor2);

    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
            CardType.FAVOR);

    assertEquals(CardType.FAVOR, result.getType());
    assertEquals(1, countCards(currentPlayer, CardType.FAVOR));
  }

  @Test
  public void playFiveDifferentCats_DiscardHasWantedCardAmongOtherCards_ReturnsWantedCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card attack = new Card(CardType.ATTACK);
    Card favor = new Card(CardType.FAVOR);
    Card skip = new Card(CardType.SKIP);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);
    game.addToDiscard(attack);
    game.addToDiscard(favor);
    game.addToDiscard(skip);

    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
            CardType.FAVOR);

    assertEquals(CardType.FAVOR, result.getType());
    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(game.getDiscardPile().contains(favor));
    assertTrue(game.getDiscardPile().contains(attack));
    assertTrue(game.getDiscardPile().contains(skip));
  }

  @Test
  public void playCatCards_GameNotLaunched_ThrowsIllegalStateException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    List<Card> cards = List.of(new Card(CardType.TACOCAT), new Card(CardType.TACOCAT));
    Player dummy = new Player();

    Exception e = assertThrows(IllegalStateException.class, () ->
            game.playCatCards(cards, dummy, null));

    assertEquals("game has not started", e.getMessage());
  }

  @Test
  public void playCatCards_GameIsOver_ThrowsIllegalStateException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    List<Player> players = game.getPlayers();
    players.get(1).die();
    players.get(2).die();
    game.checkWinner();
    List<Card> cards = List.of(new Card(CardType.TACOCAT), new Card(CardType.TACOCAT));

    Exception e = assertThrows(IllegalStateException.class, () ->
            game.playCatCards(cards, players.get(0), null));

    assertEquals("game is over", e.getMessage());
  }

  @Test
  public void playCatCards_CardsIsNull_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(1);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
            game.playCatCards(null, target, CardType.FAVOR));

    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void playCatCards_ComboSizeOne_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(1);
    List<Card> cards = List.of(new Card(CardType.TACOCAT));

    Exception e = assertThrows(IllegalArgumentException.class, () ->
            game.playCatCards(cards, target, null));

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playCatCards_ComboSizeFour_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(1);
    List<Card> cards = List.of(
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT),
            new Card(CardType.TACOCAT)
    );

    Exception e = assertThrows(IllegalArgumentException.class, () ->
            game.playCatCards(cards, target, null));

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playCatCards_TwoMatchingCats_TargetHasOneCard_StealsCatd() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(1);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    new ArrayList<>(target.getHand()).forEach(target::removeCard);
    target.addCard(skip);

    game.playCatCards(List.of(cat1, cat2), target, null);

    assertTrue(currentPlayer.getHand().contains(skip));
    assertFalse(target.getHand().contains(skip));
    assertTrue(game.getDiscardPile().contains(cat1));
    assertTrue(game.getDiscardPile().contains(cat2));
  }

  @Test
  public void playCatCards_ThreeMatchingCats_TargetHasNamedCard_TransfersCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(1);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card favor = new Card(CardType.FAVOR);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    new ArrayList<>(target.getHand())
            .stream()
            .filter(c -> c.getType() == CardType.FAVOR)
            .forEach(target::removeCard);

    target.addCard(favor);

    game.playCatCards(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(target.getHand().contains(favor));
    assertTrue(game.getDiscardPile().contains(cat1));
    assertTrue(game.getDiscardPile().contains(cat2));
    assertTrue(game.getDiscardPile().contains(cat3));
  }

  @Test
  public void playCatCards_ThreeMatchingCats_TargetLacksNamedCard_NothingTransferred() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(1);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);

    new ArrayList<>(target.getHand())
            .stream()
            .filter(c -> c.getType() == CardType.FAVOR)
            .forEach(target::removeCard);

    game.playCatCards(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertFalse(currentPlayer.getHand().contains(new Card(CardType.FAVOR)));
    assertTrue(game.getDiscardPile().contains(cat1));
    assertTrue(game.getDiscardPile().contains(cat2));
    assertTrue(game.getDiscardPile().contains(cat3));
  }

  @Test
  public void playCatCards_FiveDifferentCats_NamedCardInDiscard_TransfersCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);
    Card favor = new Card(CardType.FAVOR);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);
    game.addToDiscard(favor);

    game.playCatCards(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);

    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(game.getDiscardPile().contains(favor));
  }

  @Test
  public void playCatCards_FiveDifferentCats_NamedCardNotInDiscard_ThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);
    currentPlayer.addCard(cat5);

    Exception e = assertThrows(IllegalStateException.class, () ->
            game.playCatCards(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR));

    assertEquals("discard pile is empty", e.getMessage());
  }

}
