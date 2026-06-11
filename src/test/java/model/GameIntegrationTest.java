package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class GameIntegrationTest {
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
  private static final int TURNS_OWED = 2;
  private static final int EXISTING_TURNS = 1;
  private static final int EMPTY_HAND_SIZE = 0;
  private static final int NUM_CARDS_PEEKED = 3;
  private static final int THREE_CARDS = 3;
  private static final int FOUR_CARDS = 4;
  private static final int FIVE_CARDS = 5;

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

    game.drawCard(0);

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

    game.drawCard(0);

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

    assertThrows(IllegalStateException.class, () -> game.drawCard(0));
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

    game.drawCard(0);

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

    game.drawCard(0);

    assertFalse(firstPlayer.isAlive());
    assertFalse(game.isGameOver());
  }

  @Test
  public void playDrawFromBottomWithManyCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card drawFromBottom = new Card(CardType.DRAW_FROM_BOTTOM);
    Card topCard = new Card(CardType.SKIP);
    Card bottomCard = new Card(CardType.FAVOR);
    currentPlayer.addCard(drawFromBottom);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(topCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(bottomCard, game.getDrawPile().size());
    int discardSizeBefore = game.getDiscardPile().size();

    game.playCard(drawFromBottom);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertTrue(currentPlayer.getHand().contains(bottomCard));
    assertFalse(currentPlayer.getHand().contains(drawFromBottom));
    assertEquals(topCard, game.getDrawPile().get(FIRST_PLAYER_INDEX));
    assertEquals(discardSizeBefore + EXISTING_TURNS, game.getDiscardPile().size());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void playDrawFromBottomWithOneCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card drawFromBottom = new Card(CardType.DRAW_FROM_BOTTOM);
    Card onlyCard = new Card(CardType.FAVOR);
    currentPlayer.addCard(drawFromBottom);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(onlyCard, FIRST_PLAYER_INDEX);

    game.playCard(drawFromBottom);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertTrue(currentPlayer.getHand().contains(onlyCard));
    assertEquals(EMPTY_HAND_SIZE, game.getDrawPile().size());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void playDrawFromBottomWithEmptyDeckThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    assertThrows(IllegalStateException.class, () -> game.playDrawFromBottom());
  }

  @Test
  public void alterFutureMoreThanThreeCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card fourthCard = new Card(CardType.SKIP);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(fourthCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(thirdCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(secondCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(firstCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(thirdCard);
    reorderedCards.add(firstCard);
    reorderedCards.add(secondCard);

    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();
    List<Card> drawPile = game.getDrawPile();

    assertEquals(Collections.emptyList(), result);
    assertEquals(thirdCard, drawPile.get(FIRST_PLAYER_INDEX));
    assertEquals(firstCard, drawPile.get(SECOND_PLAYER_INDEX));
    assertEquals(secondCard, drawPile.get(THIRD_PLAYER_INDEX));
    assertEquals(fourthCard, drawPile.get(FOURTH_PLAYER_INDEX));
  }

  @Test
  public void alterFutureExactlyThreeCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(thirdCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(secondCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(firstCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(secondCard);
    reorderedCards.add(thirdCard);
    reorderedCards.add(firstCard);

    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();
    List<Card> drawPile = game.getDrawPile();

    assertEquals(Collections.emptyList(), result);
    assertEquals(secondCard, drawPile.get(FIRST_PLAYER_INDEX));
    assertEquals(thirdCard, drawPile.get(SECOND_PLAYER_INDEX));
    assertEquals(firstCard, drawPile.get(THIRD_PLAYER_INDEX));
  }

  @Test
  public void alterFutureWithOneCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card onlyCard = new Card(CardType.FAVOR);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(onlyCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(onlyCard);

    game.playCard(alterFuture, reorderedCards);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertEquals(onlyCard, game.getDrawPile().get(FIRST_PLAYER_INDEX));
  }

  @Test
  public void alterFutureEmptyDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    assertThrows(IllegalStateException.class, () -> {
      game.playCard(alterFuture, Collections.emptyList());
      game.resolvePendingAction();
    });
  }

  @Test
  public void alterFutureInvalidOrder() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card wrongCard = new Card(CardType.ATTACK);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    game.addToDrawPile(thirdCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(secondCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(firstCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(wrongCard);
    reorderedCards.add(firstCard);
    reorderedCards.add(secondCard);

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(alterFuture, reorderedCards);
      game.resolvePendingAction();
    });
  }

  @Test
  public void curseNextPlayerHasNoDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player nextPlayer = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card curse = new Card(CardType.CURSE);
    currentPlayer.addCard(curse);
    while (nextPlayer.hasDefuse()) {
      nextPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    int drawPileSizeBefore = game.getDrawPile().size();
    int discardSizeBefore = game.getDiscardPile().size();

    game.playCard(curse);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore, game.getDrawPile().size());
    assertEquals(discardSizeBefore + EXISTING_TURNS, game.getDiscardPile().size());
  }

  @Test
  public void curseNextPlayerHasOneDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player nextPlayer = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card curse = new Card(CardType.CURSE);
    currentPlayer.addCard(curse);
    while (nextPlayer.hasDefuse()) {
      nextPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    nextPlayer.addCard(new Card(CardType.DEFUSE));
    int drawPileSizeBefore = game.getDrawPile().size();

    game.playCard(curse);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + EXISTING_TURNS, game.getDrawPile().size());
  }

  @Test
  public void curseNextPlayerHasMultipleDefuses() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player nextPlayer = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card curse = new Card(CardType.CURSE);
    currentPlayer.addCard(curse);
    while (nextPlayer.hasDefuse()) {
      nextPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    nextPlayer.addCard(new Card(CardType.DEFUSE));
    nextPlayer.addCard(new Card(CardType.DEFUSE));
    int drawPileSizeBefore = game.getDrawPile().size();

    game.playCard(curse);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + TURNS_OWED, game.getDrawPile().size());
  }

  @Test
  public void curseOnlyOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player nextPlayer = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Player deadPlayer = game.getPlayers().get(THIRD_PLAYER_INDEX);
    Card curse = new Card(CardType.CURSE);
    currentPlayer.addCard(curse);
    deadPlayer.die();
    while (nextPlayer.hasDefuse()) {
      nextPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    nextPlayer.addCard(new Card(CardType.DEFUSE));
    int drawPileSizeBefore = game.getDrawPile().size();

    game.playCard(curse);
    List<Card> result = game.resolvePendingAction();

    assertEquals(Collections.emptyList(), result);
    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + EXISTING_TURNS, game.getDrawPile().size());
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
  public void playSkipCardNotInHandThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SKIP));
    }
    Card skip = new Card(CardType.SKIP);

    assertThrows(IllegalArgumentException.class, () -> game.playCard(skip));
  }

  @Test
  public void playSkipCardAppearsInDiscardPile() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    // Remove any existing skips from hand
    while (currentPlayer.getHand().contains(new Card(CardType.SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SKIP));
    }

    // Add our skip
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(skip);
    assertTrue(currentPlayer.getHand().contains(skip));

    // Verify no skips in discard before
    assertFalse(game.getDiscardPile().contains(new Card(CardType.SKIP)));

    game.playCard(skip);

    // Verify skip is now in discard and removed from hand
    assertTrue(game.getDiscardPile().contains(skip));
    assertFalse(currentPlayer.getHand().contains(new Card(CardType.SKIP)));
  }

  @Test
  public void playSkipWithOneTurnOwed() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SKIP));
    }
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(skip);

    game.playCard(skip);
    game.resolvePendingAction();

    assertEquals(0, currentPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void playSkipWithTwoTurnsOwed() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SKIP));
    }
    Card skip = new Card(CardType.SKIP);
    currentPlayer.addCard(skip);
    currentPlayer.addTurn();

    game.playCard(skip);
    game.resolvePendingAction();

    assertEquals(1, currentPlayer.getTurnsOwed());
    assertEquals(FIRST_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void playSuperSkipCardNotInHandThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SUPER_SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SUPER_SKIP));
    }
    Card superSkip = new Card(CardType.SUPER_SKIP);

    assertThrows(IllegalArgumentException.class, () -> game.playCard(superSkip));
  }

  @Test
  public void playSuperSkipCardAppearsInDiscardPile() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SUPER_SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SUPER_SKIP));
    }
    Card superSkip = new Card(CardType.SUPER_SKIP);
    currentPlayer.addCard(superSkip);

    assertFalse(game.getDiscardPile().contains(new Card(CardType.SUPER_SKIP)));

    game.playCard(superSkip);

    assertTrue(game.getDiscardPile().contains(superSkip));
    assertFalse(currentPlayer.getHand().contains(new Card(CardType.SUPER_SKIP)));
  }

  @Test
  public void playSuperSkipWithOneTurnOwed() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SUPER_SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SUPER_SKIP));
    }
    Card superSkip = new Card(CardType.SUPER_SKIP);
    currentPlayer.addCard(superSkip);

    game.playCard(superSkip);
    game.resolvePendingAction();

    assertEquals(0, currentPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  public void playSuperSkipWithTwoTurnsOwed() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.SUPER_SKIP))) {
      currentPlayer.removeCard(new Card(CardType.SUPER_SKIP));
    }
    Card superSkip = new Card(CardType.SUPER_SKIP);
    currentPlayer.addCard(superSkip);
    currentPlayer.addTurn();

    game.playCard(superSkip);
    game.resolvePendingAction();

    assertEquals(0, currentPlayer.getTurnsOwed());
    assertEquals(SECOND_PLAYER_INDEX, game.getCurrentPlayerIndex());
  }

  @Test
  void bubonicPlagueAllOtherPlayersHaveCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.BUBONIC_PLAGUE));

    List<Player> players = game.getPlayers();
    int currentIndex = game.getCurrentPlayerIndex();

    List<Integer> otherHandSizesBefore = new ArrayList<>();
    for (int i = 0; i < players.size(); i++) {
      if (i != currentIndex) {
        otherHandSizesBefore.add(players.get(i).getHand().size());
      }
    }
    int currentHandSizeBefore = currentPlayer.getHand().size();

    game.playCard(new Card(CardType.BUBONIC_PLAGUE));
    game.resolvePendingAction();

    // current player hand unchanged (minus the played card)
    assertEquals(currentHandSizeBefore - 1, game.getCurrentPlayer().getHand().size());

    // each other player lost exactly one card
    int otherIndex = 0;
    for (int i = 0; i < game.getPlayers().size(); i++) {
      if (i != currentIndex) {
        assertEquals(otherHandSizesBefore.get(otherIndex) - 1,
            game.getPlayers().get(i).getHand().size());
        otherIndex++;
      }
    }
  }

  @Test
  void bubonicPlagueOneOtherPlayerHasNoCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.BUBONIC_PLAGUE));

    int currentIndex = game.getCurrentPlayerIndex();
    int emptyPlayerIndex = (currentIndex + 1) % game.getPlayers().size();
    int otherPlayerIndex = (currentIndex + 2) % game.getPlayers().size();

    Player emptyPlayer = game.getPlayers().get(emptyPlayerIndex);
    Player otherPlayer = game.getPlayers().get(otherPlayerIndex);

    // drain one player's hand
    List<Card> hand = new ArrayList<>(emptyPlayer.getHand());
    for (Card card : hand) {
      emptyPlayer.removeCard(card);
    }

    int otherHandSizeBefore = otherPlayer.getHand().size();

    game.playCard(new Card(CardType.BUBONIC_PLAGUE));
    game.resolvePendingAction();

    // empty player still has no cards
    assertEquals(EMPTY_HAND_SIZE, emptyPlayer.getHand().size());
    // other player lost one card
    assertEquals(otherHandSizeBefore - 1, otherPlayer.getHand().size());
  }

  @Test
  void bubonicPlagueAllOtherPlayersHaveNoCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.BUBONIC_PLAGUE));

    int currentIndex = game.getCurrentPlayerIndex();
    int drawPileSizeBefore = game.getDrawPile().size();

    // drain all other players' hands
    for (int i = 0; i < game.getPlayers().size(); i++) {
      if (i != currentIndex) {
        Player player = game.getPlayers().get(i);
        List<Card> hand = new ArrayList<>(player.getHand());
        for (Card card : hand) {
          player.removeCard(card);
        }
      }
    }

    game.playCard(new Card(CardType.BUBONIC_PLAGUE));
    game.resolvePendingAction();

    // draw pile size unchanged (no cards moved)
    assertEquals(drawPileSizeBefore, game.getDrawPile().size());
  }

  @Test
  void bubonicPlagueExactlyOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.BUBONIC_PLAGUE));

    int currentIndex = game.getCurrentPlayerIndex();
    int alivePlayerIndex = (currentIndex + 1) % game.getPlayers().size();
    int deadPlayerIndex = (currentIndex + 2) % game.getPlayers().size();

    Player alivePlayer = game.getPlayers().get(alivePlayerIndex);
    game.getPlayers().get(deadPlayerIndex).die();

    int aliveHandSizeBefore = alivePlayer.getHand().size();

    game.playCard(new Card(CardType.BUBONIC_PLAGUE));
    game.resolvePendingAction();

    assertEquals(aliveHandSizeBefore - 1, alivePlayer.getHand().size());
  }

  @Test
  void bubonicPlagueCurrentPlayerNotAffected() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.BUBONIC_PLAGUE));

    int currentHandSizeBefore = currentPlayer.getHand().size();

    game.playCard(new Card(CardType.BUBONIC_PLAGUE));

    // minus 1 for the played card itself, no additional cards removed
    assertEquals(currentHandSizeBefore - 1, game.getCurrentPlayer().getHand().size());
  }

  @Test
  void targetedAttackTargetIsNextPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player target = game.getPlayers().get(nextPlayerIndex);

    game.playCard(new Card(CardType.TARGETED_ATTACK), target);
    List<Card> result = game.resolvePendingAction();

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, target.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void targetedAttackTargetIsNotNextPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    int targetIndex = (game.getCurrentPlayerIndex() + 2) % game.getPlayers().size();
    Player target = game.getPlayers().get(targetIndex);

    game.playCard(new Card(CardType.TARGETED_ATTACK), target);
    List<Card> result = game.resolvePendingAction();

    assertEquals(targetIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, target.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void targetedAttackOnlyOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    List<Player> players = game.getPlayers();
    players.get(THIRD_PLAYER_INDEX).die();

    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player target = game.getPlayers().get(nextPlayerIndex);

    game.playCard(new Card(CardType.TARGETED_ATTACK), target);

    List<Card> result = game.resolvePendingAction();

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, target.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void targetedAttackTargetIsDeadPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    Player deadPlayer = game.getPlayers().get(THIRD_PLAYER_INDEX);
    deadPlayer.die();

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(new Card(CardType.TARGETED_ATTACK), deadPlayer);
      game.resolvePendingAction();
    });
  }

  @Test
  void targetedAttackTargetIsCurrentPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(new Card(CardType.TARGETED_ATTACK), currentPlayer);
      game.resolvePendingAction();
    });
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

    assertThrows(IllegalStateException.class, () -> {
      game.playCard(new Card(CardType.SEE_THE_FUTURE));
      game.resolvePendingAction();
    });
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

    game.playCard(new Card(CardType.SEE_THE_FUTURE));
    List<Card> result = game.resolvePendingAction();

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

    game.playCard(new Card(CardType.SEE_THE_FUTURE));
    List<Card> result = game.resolvePendingAction();

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

    game.playCard(new Card(CardType.SEE_THE_FUTURE));
    List<Card> result = game.resolvePendingAction();

    assertEquals(NUM_CARDS_PEEKED, result.size());
    assertEquals(expectedFirst, result.get(0));
    assertEquals(expectedSecond, result.get(1));
    assertEquals(expectedThird, result.get(2));
  }

  @Test
  void shuffleEmptyDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card shuffleCard = new Card(CardType.SHUFFLE);
    currentPlayer.addCard(shuffleCard);

    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    game.playCard(shuffleCard);
    List<Card> result = game.resolvePendingAction();

    assertTrue(result.isEmpty());
  }

  @Test
  public void playNosyCardNotInHandThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.NOSY))) {
      currentPlayer.removeCard(new Card(CardType.NOSY));
    }

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(SECOND_PLAYER_INDEX));
  }

  @Test
  public void playNosyOnNegativeIndexThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card nosy = new Card(CardType.NOSY);
    currentPlayer.addCard(nosy);

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(-1));
  }

  @Test
  public void playNosyOnTooLargeIndexThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card nosy = new Card(CardType.NOSY);
    currentPlayer.addCard(nosy);

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(MIN_PLAYERS));
  }

  @Test
  public void playNosyOnSelfThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card nosy = new Card(CardType.NOSY);
    currentPlayer.addCard(nosy);

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(FIRST_PLAYER_INDEX));
  }

  @Test
  public void playNosyOnDeadPlayerThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card nosy = new Card(CardType.NOSY);
    currentPlayer.addCard(nosy);
    List<Player> players = game.getPlayers();
    players.get(SECOND_PLAYER_INDEX).die();

    assertThrows(IllegalArgumentException.class, () -> game.playNosy(SECOND_PLAYER_INDEX));
  }

  @Test
  public void playNosyOnValidTarget() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.getHand().contains(new Card(CardType.NOSY))) {
      currentPlayer.removeCard(new Card(CardType.NOSY));
    }
    Card nosy = new Card(CardType.NOSY);
    currentPlayer.addCard(nosy);
    List<Player> players = game.getPlayers();
    List<Card> targetHand = players.get(SECOND_PLAYER_INDEX).getHand();

    List<Card> result = game.playNosy(SECOND_PLAYER_INDEX);

    assertEquals(targetHand, result);
  }

  @Test
  public void defuseWithoutDefuseCardThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    while (currentPlayer.hasDefuse()) {
      currentPlayer.removeCard(new Card(CardType.DEFUSE));
    }

    assertThrows(IllegalStateException.class, () -> game.defuse(0));
  }

  @Test
  public void defuseWithNegativePositionThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    assertThrows(IllegalArgumentException.class, () -> game.defuse(-1));
  }

  @Test
  public void defuseWithPositionTooLargeThrowException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    int drawPileSize = game.getDrawPile().size();

    assertThrows(IllegalArgumentException.class, () -> game.defuse(drawPileSize + 1));
  }

  private void assertDefusePlacesKittenAtPosition(Game game, int position) {
    int drawPileSizeBefore = game.getDrawPile().size();

    game.defuse(position);

    assertEquals(CardType.EXPLODING_KITTEN, game.getDrawPile().get(position).getType());
    assertEquals(drawPileSizeBefore + 1, game.getDrawPile().size());
  }

  @Test
  public void defuseWithPositionZero() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    assertDefusePlacesKittenAtPosition(game, 0);
  }

  @Test
  public void defuseWithPositionAtBottom() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    assertDefusePlacesKittenAtPosition(game, game.getDrawPile().size());

  }

  @Test
  public void defuseWithPositionInMiddle() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    assertDefusePlacesKittenAtPosition(game, game.getDrawPile().size() / 2);
  }

  @Test
  void playSwapEmptyDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    currentPlayer.addCard(swapCard);

    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    int handSizeBefore = currentPlayer.getHand().size();

    game.playCard(swapCard);

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());
    assertTrue(game.getDrawPile().isEmpty());
  }

  @Test
  void shuffleDeckWithOneElement() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card shuffleCard = new Card(CardType.SHUFFLE);
    currentPlayer.addCard(shuffleCard);

    while (game.getDrawPile().size() > 1) {
      game.drawFromDeck();
    }

    game.playCard(shuffleCard);
    List<Card> result = game.resolvePendingAction();

    assertEquals(1, result.size());
  }

  @Test
  void playSwapOneCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    currentPlayer.addCard(swapCard);

    while (game.getDrawPile().size() > 1) {
      game.drawFromDeck();
    }

    Card onlyCard = game.getDrawPile().get(0);
    int handSizeBefore = currentPlayer.getHand().size();

    game.playCard(swapCard);
    game.resolvePendingAction();

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());
    assertEquals(1, game.getDrawPile().size());
    assertEquals(onlyCard, game.getDrawPile().get(0));
  }

  @Test
  void shuffleDeckWithMoreThanOneElement() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card shuffleCard = new Card(CardType.SHUFFLE);
    currentPlayer.addCard(shuffleCard);

    while (game.getDrawPile().size() > NUM_CARDS_PEEKED) {
      game.drawFromDeck();
    }

    game.playCard(shuffleCard);
    List<Card> result = game.resolvePendingAction();

    assertEquals(NUM_CARDS_PEEKED, result.size());
  }

  @Test
  void playSwapTwoCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    currentPlayer.addCard(swapCard);

    while (game.getDrawPile().size() > 2) {
      game.drawFromDeck();
    }

    Card originalTop = game.getDrawPile().get(0);
    Card originalBottom = game.getDrawPile().get(1);
    int handSizeBefore = currentPlayer.getHand().size();

    game.playCard(swapCard);
    game.resolvePendingAction();

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());
    assertEquals(2, game.getDrawPile().size());
    assertEquals(originalBottom, game.getDrawPile().get(0));
    assertEquals(originalTop, game.getDrawPile().get(1));
  }

  @Test
  void playSwapMoreThanTwoCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card swapCard = new Card(CardType.SWAP_TOP_BOTTOM);
    currentPlayer.addCard(swapCard);

    int deckSizeBefore = game.getDrawPile().size();
    Card originalTop = game.getDrawPile().get(0);
    Card originalBottom = game.getDrawPile().get(deckSizeBefore - 1);
    int handSizeBefore = currentPlayer.getHand().size();

    game.playCard(swapCard);
    game.resolvePendingAction();

    assertEquals(handSizeBefore - 1, currentPlayer.getHand().size());

    assertEquals(deckSizeBefore, game.getDrawPile().size());
    assertEquals(originalBottom, game.getDrawPile().get(0));
    assertEquals(originalTop, game.getDrawPile().get(deckSizeBefore - 1));
  }

  public void isCatCardTypeIsNull() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isCatCard(null);
    });

    assertEquals("invalid card", e.getMessage());
  }

  @Test
  public void isCatCardTypeIsTacocat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.TACOCAT));
  }

  @Test
  public void isCatCardTypeIsRainbowRalphingCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.RAINBOW_RALPHING_CAT));
  }

  @Test
  public void isCatCardTypeIsBeardCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.BEARD_CAT));
  }

  @Test
  public void isCatCardTypeIsCattermelon() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.CATTERMELON));
  }

  @Test
  public void isCatCardTypeIsFeralCat() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isCatCard(CardType.FERAL_CAT));
  }

  @Test
  public void isCatCardTypeIsAttack() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.ATTACK));
  }

  @Test
  public void isCatCardTypeIsExplodingKitten() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.EXPLODING_KITTEN));
  }

  @Test
  public void isCatCardTypeIsDefuse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isCatCard(CardType.DEFUSE));
  }

  @Test
  public void isValidCatComboNullListThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isValidCatCombo(null);
    });
    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void isValidCatComboEmptyListThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.isValidCatCombo(List.of());
    });
    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void isValidCatComboOneCardReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatComboTwoSameRealCatsReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatComboTwoDifferentRealCatsReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.BEARD_CAT)
    )));
  }

  @Test
  public void isValidCatComboOneFeralOneRealCatReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.FERAL_CAT),
        new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatComboTwoFeralCatsReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.FERAL_CAT),
        new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatComboOneCatOneNonCatReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.ATTACK)
    )));
  }

  @Test
  public void isValidCatComboThreeSameRealCatsReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatComboTwoMatchingOneFeralReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatComboOneRealTwoFeralsReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.FERAL_CAT),
        new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatComboThreeFeralsReturnsTrue() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertTrue(game.isValidCatCombo(List.of(
        new Card(CardType.FERAL_CAT),
        new Card(CardType.FERAL_CAT),
        new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatComboThreeDifferentRealCatsReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.BEARD_CAT),
        new Card(CardType.CATTERMELON)
    )));
  }

  @Test
  public void isValidCatComboTwoDifferentRealCatsOneFeralReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.BEARD_CAT),
        new Card(CardType.FERAL_CAT)
    )));
  }

  @Test
  public void isValidCatComboThreeCardsIncludingNonCatReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.ATTACK)
    )));
  }

  @Test
  public void isValidCatComboFourCardsReturnsFalse() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    assertFalse(game.isValidCatCombo(List.of(
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT),
        new Card(CardType.TACOCAT)
    )));
  }

  @Test
  public void isValidCatComboFiveDistinctRealCatsReturnsTrue() {
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
  public void isValidCatComboFourDistinctRealCatsOneFeralReturnsTrue() {
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
  public void isValidCatComboDuplicateRealCatReturnsFalse() {
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
  public void isValidCatComboTwoFeralsInFiveCardsReturnsFalse() {
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
  public void isValidCatComboFiveCardsIncludingNonCatReturnsFalse() {
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
  public void isValidCatComboSixCardsReturnsFalse() {
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
  public void playTwoMatchingCatsNullTargetThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2), null, CardType.TACOCAT);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 2-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsDeadTargetThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2), target, CardType.TACOCAT);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 2-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsSelfTargetThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2), currentPlayer, CardType.TACOCAT);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 2-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsNonMatchingCatsThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.BEARD_CAT);
    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2), target, CardType.TACOCAT);
      game.resolvePendingAction();
    });

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsThreeCardsThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), target, null);
      game.resolvePendingAction();
    });

    assertEquals("invalid wanted card type for 3-cat combo", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsCardsNotInHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2), target, CardType.BEARD_CAT);
      game.resolvePendingAction();
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsOnlyOneRequiredCardInHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    currentPlayer.addCard(cat1);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2), target, CardType.DEFUSE);
      game.resolvePendingAction();
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playTwoMatchingCatsTargetHasOneCardReturnsStolenCard() {
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


    game.playCard(List.of(cat1, cat2), target, null);

    game.resolvePendingAction();

    assertTrue(currentPlayer.getHand().contains(skip));

    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));

    assertEquals(0, target.getHand().size());
  }

  @Test
  public void playTwoMatchingCatsTargetHasTwoCardsReturnsStolenCard() {
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

    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), target);

    assertTrue(stolen.getType() == CardType.SKIP || stolen.getType() == CardType.ATTACK);
    assertTrue(currentPlayer.getHand().contains(stolen));
    assertEquals(1, target.getHand().size());
    assertFalse(target.getHand().contains(stolen));
  }

  @Test
  public void playTwoMatchingCatsTargetHasFiveCardsReturnsStolenCard() {
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


    Card stolen = game.playTwoMatchingCats(List.of(cat1, cat2), target);

    assertNotNull(stolen);
    assertTrue(currentPlayer.getHand().contains(stolen));
    assertEquals(FOUR_CARDS, target.getHand().size());
    assertFalse(target.getHand().contains(stolen));
  }

  @Test
  public void playThreeMatchingCatsNullTargetThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), null, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 3-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsDeadTargetThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 3-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsSelfTargetThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), currentPlayer, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 3-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsNullNamedCardThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), target, null);
      game.resolvePendingAction();
    });

    assertEquals("invalid wanted card type for 3-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsExplodingKittenNamedCardThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), target, CardType.EXPLODING_KITTEN);
      game.resolvePendingAction();
    });

    assertEquals("invalid wanted card type for 3-cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsNonMatchingCatsThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playFourMatchingCatsTwoCardsThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);
    Card cat4 = new Card(CardType.TACOCAT);

    currentPlayer.addCard(cat1);
    currentPlayer.addCard(cat2);
    currentPlayer.addCard(cat3);
    currentPlayer.addCard(cat4);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2, cat3, cat4), target, null);
    });

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsCardsNotInHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);
    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    Card cat3 = new Card(CardType.TACOCAT);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsTargetHasNoNamedCardReturnsFalse() {
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

    game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    game.resolvePendingAction();

    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(1, target.getHand().size());
    assertEquals(CardType.ATTACK, target.getHand().get(0).getType());
    assertEquals(discardSizeBefore + THREE_CARDS, game.getDiscardPile().size());
  }

  @Test
  public void playNekoCardsIsNullThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(null));

    assertEquals("cards cannot be null", e.getMessage());
  }

  @Test
  public void playNekoCardsIsEmptyThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of()));

    assertEquals("must play exactly 3 neko cards", e.getMessage());
  }

  @Test
  public void playNekoOneCardThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card neko = new Card(CardType.NEKO);
    currentPlayer.addCard(neko);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());
  }

  @Test
  public void playNekoTwoCardsThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());
  }

  @Test
  public void playNekoThreeNekosGameOver() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    List<Player> players = game.getPlayers();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);
    currentPlayer.addCard(neko3);

    game.playNeko(List.of(neko1, neko2, neko3));

    assertTrue(game.isGameOver());
    assertTrue(currentPlayer.isAlive());
    assertFalse(players.get(SECOND_PLAYER_INDEX).isAlive());
    assertFalse(players.get(THIRD_PLAYER_INDEX).isAlive());
    assertTrue(game.getDiscardPile().contains(neko1));
    assertTrue(game.getDiscardPile().contains(neko2));
    assertTrue(game.getDiscardPile().contains(neko3));
  }

  @Test
  public void playNekoFourCardsThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);
    Card neko4 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);
    currentPlayer.addCard(neko3);
    currentPlayer.addCard(neko4);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, neko3, neko4)));

    assertEquals("must play exactly 3 neko cards", e.getMessage());
  }

  @Test
  public void playNekoOneCardIsNullThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);

    List<Card> cards = new java.util.ArrayList<>();
    cards.add(neko1);
    cards.add(null);
    cards.add(neko2);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(cards));

    assertEquals("all cards must be neko cards", e.getMessage());
  }

  @Test
  public void playNekoOneCardIsNotNekoThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card attack = new Card(CardType.ATTACK);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);
    currentPlayer.addCard(attack);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, attack)));

    assertEquals("all cards must be neko cards", e.getMessage());
  }

  @Test
  public void playNekoThreeNekosOnlyTwoInHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playNeko(List.of(neko1, neko2, neko3)));

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playNekoOneOpponentAlreadyDeadRemainingOpponentDies() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    List<Player> players = game.getPlayers();

    players.get(SECOND_PLAYER_INDEX).die();

    Card neko1 = new Card(CardType.NEKO);
    Card neko2 = new Card(CardType.NEKO);
    Card neko3 = new Card(CardType.NEKO);
    currentPlayer.addCard(neko1);
    currentPlayer.addCard(neko2);
    currentPlayer.addCard(neko3);

    game.playNeko(List.of(neko1, neko2, neko3));

    assertTrue(game.isGameOver());
    assertTrue(currentPlayer.isAlive());
    assertFalse(players.get(THIRD_PLAYER_INDEX).isAlive());
  }

  @Test
  public void playFavorTargetIsDeadThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    target.die();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(target, new Card(CardType.SKIP)));

    assertEquals("invalid target", e.getMessage());
  }

  @Test
  public void playFavorTargetIsCurrentPlayerThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(currentPlayer, new Card(CardType.SKIP)));

    assertEquals("cannot target yourself", e.getMessage());
  }

  @Test
  public void playFavorGivenIsNullThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(target, null));

    assertEquals("given card cannot be null", e.getMessage());
  }

  @Test
  public void playFavorTargetHasEmptyHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(target, new Card(CardType.SKIP)));

    assertEquals("target does not have that card", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsTargetHasOneNamedCardReturnsTrue() {
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

    game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    game.resolvePendingAction();

    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(0, target.getHand().size());
  }

  @Test
  public void playFavorTargetHasOneCardGivenDoesNotMatchThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }
    target.addCard(new Card(CardType.ATTACK));

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(target, new Card(CardType.SKIP)));

    assertEquals("target does not have that card", e.getMessage());
  }

  @Test
  public void playThreeMatchingCatsTargetHasTwoNamedCardsReturnsTrue() {
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

    game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    game.resolvePendingAction();

    assertEquals(1, countCards(currentPlayer, CardType.FAVOR));
    assertEquals(1, countCards(target, CardType.FAVOR));
    assertFalse(currentPlayer.getHand().contains(cat1));
    assertFalse(currentPlayer.getHand().contains(cat2));
    assertFalse(currentPlayer.getHand().contains(cat3));
    assertEquals(discardSizeBefore + THREE_CARDS, game.getDiscardPile().size());
  }

  @Test
  public void playThreeMatchingCatsTargetHasNamedCardAndOtherCardsReturnsTrue() {
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

    boolean result = game.playThreeMatchingCats(List.of(cat1, cat2, cat3), target, CardType.FAVOR);

    assertTrue(result);
    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(target.getHand().contains(favor));
    assertTrue(target.getHand().contains(attack));
    assertTrue(target.getHand().contains(skip));
    assertEquals(2, target.getHand().size());
  }

  @Test
  public void playFavorTargetHasOneMatchingCardTransfersCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card skip = new Card(CardType.SKIP);
    target.addCard(skip);

    game.playFavor(target, skip);

    assertTrue(currentPlayer.getHand().contains(skip));
    assertFalse(target.getHand().contains(skip));
    assertTrue(target.getHand().isEmpty());
  }

  @Test
  public void playFiveDifferentCatsNullCardsThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(null, null, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsNullWantedCardThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null, null);
      game.resolvePendingAction();
    });

    assertEquals("invalid wanted card type for 5-cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsExplodingKittenWantedCardThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null,
          CardType.EXPLODING_KITTEN);
      game.resolvePendingAction();
    });

    assertEquals("invalid wanted card type for 5-cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsDuplicateCatTypeThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);
    });

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsThreeCardsThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3), null, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("invalid target for 3-cat combo", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsCardsNotInHandThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
    Card cat4 = new Card(CardType.BEARD_CAT);
    Card cat5 = new Card(CardType.CATTERMELON);

    Exception e = assertThrows(IllegalArgumentException.class, () -> {
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("cards not in hand", e.getMessage());
  }

  @Test
  public void playFiveDifferentCatsEmptyDiscardPileThrowsIllegalArgumentException() {
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
  public void playFiveDifferentCatsDiscardHasOneNonMatchingCardThrowsIllegalArgumentException() {
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

//  @Test
//  public void playFiveDifferentCatsDiscardHasOneMatchingCardReturnsWantedCard() {
//    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
//    game.startGame();
//    Player currentPlayer = game.getCurrentPlayer();
//
//    Card cat1 = new Card(CardType.TACOCAT);
//    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
//    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
//    Card cat4 = new Card(CardType.BEARD_CAT);
//    Card cat5 = new Card(CardType.CATTERMELON);
//    Card favor = new Card(CardType.FAVOR);
//
//    currentPlayer.addCard(cat1);
//    currentPlayer.addCard(cat2);
//    currentPlayer.addCard(cat3);
//    currentPlayer.addCard(cat4);
//    currentPlayer.addCard(cat5);
//    game.addToDiscard(favor);
//
//    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
//        CardType.FAVOR);
//
//    assertEquals(CardType.FAVOR, result.getType());
//    assertTrue(currentPlayer.getHand().contains(favor));
//    assertFalse(game.getDiscardPile().contains(favor));
//  }

//  @Test
//  public void playFiveDifferentCatsDiscardHasTwoMatchingCardsTransfersOneCard() {
//    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
//    game.startGame();
//    Player currentPlayer = game.getCurrentPlayer();
//
//    Card cat1 = new Card(CardType.TACOCAT);
//    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
//    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
//    Card cat4 = new Card(CardType.BEARD_CAT);
//    Card cat5 = new Card(CardType.CATTERMELON);
//    Card favor1 = new Card(CardType.FAVOR);
//    Card favor2 = new Card(CardType.FAVOR);
//
//    currentPlayer.addCard(cat1);
//    currentPlayer.addCard(cat2);
//    currentPlayer.addCard(cat3);
//    currentPlayer.addCard(cat4);
//    currentPlayer.addCard(cat5);
//    game.addToDiscard(favor1);
//    game.addToDiscard(favor2);
//
//    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
//        CardType.FAVOR);
//
//    assertEquals(CardType.FAVOR, result.getType());
//    assertEquals(1, countCards(currentPlayer, CardType.FAVOR));
//  }

//  @Test
//  public void playFiveDifferentCatsDiscardHasWantedCardAmongOtherCardsReturnsWantedCard() {
//    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
//    game.startGame();
//    Player currentPlayer = game.getCurrentPlayer();
//
//    Card cat1 = new Card(CardType.TACOCAT);
//    Card cat2 = new Card(CardType.HAIRY_POTATO_CAT);
//    Card cat3 = new Card(CardType.RAINBOW_RALPHING_CAT);
//    Card cat4 = new Card(CardType.BEARD_CAT);
//    Card cat5 = new Card(CardType.CATTERMELON);
//    Card attack = new Card(CardType.ATTACK);
//    Card favor = new Card(CardType.FAVOR);
//    Card skip = new Card(CardType.SKIP);
//
//    currentPlayer.addCard(cat1);
//    currentPlayer.addCard(cat2);
//    currentPlayer.addCard(cat3);
//    currentPlayer.addCard(cat4);
//    currentPlayer.addCard(cat5);
//    game.addToDiscard(attack);
//    game.addToDiscard(favor);
//    game.addToDiscard(skip);
//
//    Card result = game.playFiveDifferentCats(List.of(cat1, cat2, cat3, cat4, cat5),
//        CardType.FAVOR);
//
//    assertEquals(CardType.FAVOR, result.getType());
//    assertTrue(currentPlayer.getHand().contains(favor));
//    assertFalse(game.getDiscardPile().contains(favor));
//    assertTrue(game.getDiscardPile().contains(attack));
//    assertTrue(game.getDiscardPile().contains(skip));
//  }

  @Test
  public void playCatCardsGameNotLaunchedThrowsIllegalStateException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    List<Card> cards = List.of(new Card(CardType.TACOCAT), new Card(CardType.TACOCAT));
    Player dummy = new Player();

    Exception e = assertThrows(IllegalStateException.class, () ->
        game.playCatCards(cards, dummy, null));

    assertEquals("game has not started", e.getMessage());
  }

  @Test
  public void playCatCardsGameIsOverThrowsIllegalStateException() {
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
  public void playCatCardsCardsIsNullThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(1);

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCatCards(null, target, CardType.FAVOR));

    assertEquals("cards cannot be null or empty", e.getMessage());
  }

  @Test
  public void playCatCardsComboSizeOneThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player target = game.getPlayers().get(1);
    List<Card> cards = List.of(new Card(CardType.TACOCAT));

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playCatCards(cards, target, null));

    assertEquals("invalid cat combo", e.getMessage());
  }

  @Test
  public void playCatCardsComboSizeFourThrowsIllegalArgumentException() {
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
  public void playCatCardsTwoMatchingCatsTargetHasOneCardStealsCatd() {
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

    game.playCard(List.of(cat1, cat2), target, null);
    game.resolvePendingAction();

    assertTrue(currentPlayer.getHand().contains(skip));
    assertFalse(target.getHand().contains(skip));
    assertTrue(game.getDiscardPile().contains(cat1));
    assertTrue(game.getDiscardPile().contains(cat2));
  }

  @Test
  public void playCatCardsThreeMatchingCatsTargetHasNamedCardTransfersCard() {
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

    game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
    game.resolvePendingAction();

    assertTrue(currentPlayer.getHand().contains(favor));
    assertFalse(target.getHand().contains(favor));
    assertTrue(game.getDiscardPile().contains(cat1));
    assertTrue(game.getDiscardPile().contains(cat2));
    assertTrue(game.getDiscardPile().contains(cat3));
  }

//  @Test
//  public void playCatCardsThreeMatchingCatsTargetLacksNamedCardNothingTransferred() {
//    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
//    game.startGame();
//    Player currentPlayer = game.getCurrentPlayer();
//    Player target = game.getPlayers().get(1);
//
//    Card cat1 = new Card(CardType.TACOCAT);
//    Card cat2 = new Card(CardType.TACOCAT);
//    Card cat3 = new Card(CardType.TACOCAT);
//    currentPlayer.addCard(cat1);
//    currentPlayer.addCard(cat2);
//    currentPlayer.addCard(cat3);
//
//    new ArrayList<>(target.getHand())
//        .stream()
//        .filter(c -> c.getType() == CardType.FAVOR)
//        .forEach(target::removeCard);
//
//    game.playCard(List.of(cat1, cat2, cat3), target, CardType.FAVOR);
//    game.resolvePendingAction();
//
//    assertFalse(currentPlayer.getHand().contains(new Card(CardType.FAVOR)));
//    assertTrue(game.getDiscardPile().contains(cat1));
//    assertTrue(game.getDiscardPile().contains(cat2));
//    assertTrue(game.getDiscardPile().contains(cat3));
//  }

  @Test
  public void playCatCardsFiveDifferentCatsNamedCardInDiscardTransfersCard() {
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
  public void playCatCardsFiveDifferentCatsNamedCardNotInDiscardThrowsIllegalArgumentException() {
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
      game.playCard(List.of(cat1, cat2, cat3, cat4, cat5), null, CardType.FAVOR);
      game.resolvePendingAction();
    });

    assertEquals("card type not in discard pile", e.getMessage());
  }

  @Test
  public void playFavorTargetHasTwoCardsGivenMatchesOneTransfersCorrectCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card skip = new Card(CardType.SKIP);
    Card attack = new Card(CardType.ATTACK);

    target.addCard(skip);
    target.addCard(attack);

    game.playFavor(target, skip);

    assertTrue(currentPlayer.getHand().contains(skip));
    assertFalse(target.getHand().contains(skip));
    assertTrue(target.getHand().contains(attack));
    assertEquals(1, target.getHand().size());
  }

  @Test
  public void playFavorTargetHasTwoCopiesOfGivenTransfersExactlyOne() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Player target = game.getPlayers().get(SECOND_PLAYER_INDEX);

    while (!target.getHand().isEmpty()) {
      target.removeCard(target.getHand().get(0));
    }

    Card skip1 = new Card(CardType.SKIP);
    Card skip2 = new Card(CardType.SKIP);

    target.addCard(skip1);
    target.addCard(skip2);

    game.playFavor(target, skip1);

    assertEquals(1, countCards(currentPlayer, CardType.SKIP));
    assertEquals(1, countCards(target, CardType.SKIP));
  }

  @Test
  public void playFavorTargetIsNullThrowsIllegalArgumentException() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Exception e = assertThrows(IllegalArgumentException.class, () ->
        game.playFavor(null, new Card(CardType.SKIP)));

    assertEquals("invalid target", e.getMessage());
  }

  @Test
  void attackMoreThanOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.ATTACK));

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player nextPlayer = game.getPlayers().get(nextPlayerIndex);

    game.playCard(new Card(CardType.ATTACK));
    List<Card> result = game.resolvePendingAction();

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, nextPlayer.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void attackExactlyOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    // kill all players except current and next
    List<Player> players = game.getPlayers();
    players.get(THIRD_PLAYER_INDEX).die();

    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.ATTACK));

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player nextPlayer = game.getPlayers().get(nextPlayerIndex);

    game.playCard(new Card(CardType.ATTACK));
    List<Card> result = game.resolvePendingAction();

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, nextPlayer.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void attackCurrentPlayerOwesOneTurn() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.ATTACK));

    assertEquals(EXISTING_TURNS, currentPlayer.getTurnsOwed());

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player nextPlayer = game.getPlayers().get(nextPlayerIndex);

    game.playCard(new Card(CardType.ATTACK));
    List<Card> result = game.resolvePendingAction();

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, nextPlayer.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  public void testPlayBlessingCard() {
    Player currentPlayerMock = EasyMock.createMock(Player.class);
    Player targetPlayerMock = EasyMock.createMock(Player.class);

    Player dummyPlayer1 = EasyMock.createMock(Player.class);
    Player dummyPlayer2 = EasyMock.createMock(Player.class);

    Deck deckMock = EasyMock.createMock(Deck.class);
    Card blessingCardMock = EasyMock.createMock(Card.class);

    EasyMock.expect(blessingCardMock.getType()).andReturn(CardType.BLESSING).anyTimes();
    EasyMock.expect(currentPlayerMock.isAlive()).andReturn(true).anyTimes();
    EasyMock.expect(targetPlayerMock.isAlive()).andReturn(true).anyTimes();

    EasyMock.expect(dummyPlayer1.isAlive()).andReturn(true).anyTimes();
    EasyMock.expect(dummyPlayer2.isAlive()).andReturn(true).anyTimes();

    currentPlayerMock.removeCard(blessingCardMock);
    deckMock.discardCard(blessingCardMock);
    targetPlayerMock.removeTurn();

    EasyMock.replay(
            currentPlayerMock, targetPlayerMock, dummyPlayer1, dummyPlayer2,
            deckMock, blessingCardMock
    );

    List<Player> mockPlayers = Arrays.asList(
            currentPlayerMock, targetPlayerMock, dummyPlayer1, dummyPlayer2
    );

    Game game = new Game(mockPlayers, deckMock, new Random());

    game.startGame();

    game.playCard(blessingCardMock, targetPlayerMock);
    game.resolvePendingAction();

    EasyMock.verify(
            currentPlayerMock, targetPlayerMock, dummyPlayer1, dummyPlayer2,
            deckMock, blessingCardMock
    );
  }

  @Test
  public void testPlaySkipIsNopedFailsToSkip() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player player1 = game.getCurrentPlayer();
    Player player2 = game.getPlayers().get((game.getCurrentPlayerIndex() + 1) % MIN_PLAYERS);

    Card skipCard = new Card(CardType.SKIP);
    player1.addCard(skipCard);

    Card nopeCard = new Card(CardType.NOPE);
    player2.addCard(nopeCard);

    game.playCard(skipCard);

    game.playNope(player2, nopeCard);

    game.resolvePendingAction();

    assertFalse(player1.getHand().contains(skipCard), "Skip card consumed");
    assertFalse(player2.getHand().contains(nopeCard), "Nope card consumed");
    assertTrue(game.getDiscardPile().contains(skipCard), "Skip in discard");
    assertTrue(game.getDiscardPile().contains(nopeCard), "Nope in discard");

    assertEquals(player1, game.getCurrentPlayer(),
            "The Skip was Noped, so it should still be Player 1's turn.");
  }

  @Test
  void nopeWhenNoActionPending() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player player = game.getCurrentPlayer();
    Card nopeCard = new Card(CardType.NOPE);
    player.addCard(nopeCard);

    assertThrows(IllegalStateException.class, () ->
            game.playNope(player, nopeCard), "Cannot play Nope when pendingAction is NONE");
  }

  @Test
  void nopeWithCardNotInHand() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player player = game.getCurrentPlayer();
    Card skipCard = new Card(CardType.SKIP);
    player.addCard(skipCard);

    game.playCard(skipCard);

    Card phantomNope = new Card(CardType.NOPE);

    assertThrows(IllegalArgumentException.class, () ->
            game.playNope(player, phantomNope), "Cannot play a card not in player's hand");
  }

  @Test
  void passNonNopeCard() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player player = game.getCurrentPlayer();
    Player noper = game.getPlayers().get((game.getCurrentPlayerIndex() + 1) % MIN_PLAYERS);

    Card skipCard = new Card(CardType.SKIP);
    player.addCard(skipCard);
    game.playCard(skipCard);

    Card attackCard = new Card(CardType.ATTACK);
    noper.addCard(attackCard);

    assertThrows(IllegalArgumentException.class, () ->
            game.playNope(noper, attackCard), "Card must be of type NOPE");
  }

  @Test
  void nopeByThirdPartyPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player attacker = game.getCurrentPlayer();
    Player target = game.getPlayers().get(1);
    Player thirdParty = game.getPlayers().get(2); // The player entirely uninvolved

    Card attackCard = new Card(CardType.TARGETED_ATTACK);
    attacker.addCard(attackCard);

    Card nopeCard = new Card(CardType.NOPE);
    thirdParty.addCard(nopeCard);

    game.playCard(attackCard, target);

    game.playNope(thirdParty, nopeCard);
    game.resolvePendingAction();

    assertFalse(thirdParty.getHand().contains(nopeCard));
    assertTrue(game.getDiscardPile().contains(nopeCard));
    assertEquals(attacker, game.getCurrentPlayer(),
            "Attack was cancelled, so turn doesn't pass to target");
  }

  @Test
  void nopeOnCatCombo() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();

    Player attacker = game.getCurrentPlayer();
    Player victim = game.getPlayers().get(1);
    Player noper = game.getPlayers().get(2);

    int initialAttackerHandSize = attacker.getHand().size();
    int initialDiscardSize = game.getDiscardPile().size();

    Card cat1 = new Card(CardType.TACOCAT);
    Card cat2 = new Card(CardType.TACOCAT);
    attacker.addCard(cat1);
    attacker.addCard(cat2);

    Card victimCard = new Card(CardType.DEFUSE);
    victim.addCard(victimCard);
    int expectedVictimHandSize = victim.getHand().size();

    Card nope = new Card(CardType.NOPE);
    noper.addCard(nope);

    game.playCard(List.of(cat1, cat2), victim, null);

    game.playNope(noper, nope);
    game.resolvePendingAction();

    assertEquals(initialAttackerHandSize, attacker.getHand().size(),
            "Attacker loses combo cards");

    assertEquals(initialDiscardSize + MIN_PLAYERS, game.getDiscardPile().size(),
            "Combo and Nope cards go to discard");

    assertEquals(expectedVictimHandSize, victim.getHand().size(), "Victim was not robbed");
    assertTrue(victim.getHand().contains(victimCard), "Victim still has their specific card");
  }
}