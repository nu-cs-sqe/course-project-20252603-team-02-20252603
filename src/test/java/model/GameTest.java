package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
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
  private static final int TURNS_OWED = 2;
  private static final int EXISTING_TURNS = 1;
  private static final int EMPTY_HAND_SIZE = 0;
  private static final int NUM_CARDS_PEEKED = 3;
  private static final int TWO_DEFUSE_CARDS = 2;

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
    int discardSizeBefore = game.getDiscardPile().size();

    game.drawCard();

    assertTrue(firstPlayer.isAlive());
    assertEquals(defuseCountBefore - 1, countCards(firstPlayer, CardType.DEFUSE));
    assertFalse(firstPlayer.getHand().contains(explodingKitten));
    assertEquals(deckSizeBefore, game.getDrawPile().size());
    assertEquals(explodingKitten, game.getDrawPile().get(0));
    assertEquals(discardSizeBefore + 1, game.getDiscardPile().size());
    assertEquals(new Card(CardType.DEFUSE), game.getDiscardPile().get(discardSizeBefore));
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
  public void drawCardExplodingKittenWithoutDefuseEndsGameWithOneOtherAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player firstPlayer = game.getCurrentPlayer();
    List<Player> players = game.getPlayers();
    while (firstPlayer.hasDefuse()) {
      firstPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    players.get(THIRD_PLAYER_INDEX).die();
    game.addToDrawPile(new Card(CardType.EXPLODING_KITTEN), 0);

    game.drawCard();

    assertFalse(firstPlayer.isAlive());
    assertTrue(game.isGameOver());
  }

  @Test
  public void playDrawFromBottomWithManyCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card drawFromBottom = new Card(CardType.DRAW_FROM_BOTTOM);
    Card bottomCard = new Card(CardType.FAVOR);
    currentPlayer.addCard(drawFromBottom);
    game.addToDrawPile(bottomCard, game.getDrawPile().size());
    int handSizeBefore = currentPlayer.getHand().size();
    int drawPileSizeBefore = game.getDrawPile().size();
    int discardSizeBefore = game.getDiscardPile().size();

    game.playCard(drawFromBottom);

    assertEquals(handSizeBefore, currentPlayer.getHand().size());
    assertTrue(currentPlayer.getHand().contains(bottomCard));
    assertFalse(currentPlayer.getHand().contains(drawFromBottom));
    assertEquals(drawPileSizeBefore - 1, game.getDrawPile().size());
    assertEquals(discardSizeBefore + 1, game.getDiscardPile().size());
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
    game.addToDrawPile(onlyCard, 0);

    game.playCard(drawFromBottom);

    assertTrue(currentPlayer.getHand().contains(onlyCard));
    assertEquals(EMPTY_HAND_SIZE, game.getDrawPile().size());
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

    List<Card> result = game.playCard(new Card(CardType.TARGETED_ATTACK), target);

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

    // target player 2 spots ahead
    int targetIndex = (game.getCurrentPlayerIndex() + 2) % game.getPlayers().size();
    Player target = game.getPlayers().get(targetIndex);

    List<Card> result = game.playCard(new Card(CardType.TARGETED_ATTACK), target);

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

    List<Card> result = game.playCard(new Card(CardType.TARGETED_ATTACK), target);

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

    assertThrows(IllegalArgumentException.class, () ->
            game.playCard(new Card(CardType.TARGETED_ATTACK), deadPlayer));
  }

  @Test
  void targetedAttackTargetIsCurrentPlayer() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.TARGETED_ATTACK));

    assertThrows(IllegalArgumentException.class, () ->
            game.playCard(new Card(CardType.TARGETED_ATTACK), currentPlayer));
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
  void alterFutureMoreThanThreeCards() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    currentPlayer.addCard(alterFuture);
    game.addToDrawPile(thirdCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(secondCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(firstCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(thirdCard);
    reorderedCards.add(firstCard);
    reorderedCards.add(secondCard);

    List<Card> result = game.playCard(alterFuture, reorderedCards);
    List<Card> drawPile = game.getDrawPile();

    assertEquals(Collections.emptyList(), result);
    assertEquals(thirdCard, drawPile.get(FIRST_PLAYER_INDEX));
    assertEquals(firstCard, drawPile.get(SECOND_PLAYER_INDEX));
    assertEquals(secondCard, drawPile.get(THIRD_PLAYER_INDEX));
  }

  @Test
  void alterFutureExactlyThreeCards() {
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

    List<Card> result = game.playCard(alterFuture, reorderedCards);
    List<Card> drawPile = game.getDrawPile();

    assertEquals(Collections.emptyList(), result);
    assertEquals(secondCard, drawPile.get(FIRST_PLAYER_INDEX));
    assertEquals(thirdCard, drawPile.get(SECOND_PLAYER_INDEX));
    assertEquals(firstCard, drawPile.get(THIRD_PLAYER_INDEX));
  }

  @Test
  void alterFutureWithOneCard() {
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

    List<Card> result = game.playCard(alterFuture, reorderedCards);

    assertEquals(Collections.emptyList(), result);
    assertEquals(onlyCard, game.getDrawPile().get(FIRST_PLAYER_INDEX));
  }

  @Test
  void alterFutureEmptyDeck() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    currentPlayer.addCard(alterFuture);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }

    assertThrows(IllegalStateException.class, () ->
            game.playCard(alterFuture, Collections.emptyList()));
  }

  @Test
  void alterFutureInvalidOrder() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card wrongCard = new Card(CardType.ATTACK);
    currentPlayer.addCard(alterFuture);
    game.addToDrawPile(thirdCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(secondCard, FIRST_PLAYER_INDEX);
    game.addToDrawPile(firstCard, FIRST_PLAYER_INDEX);
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(wrongCard);
    reorderedCards.add(firstCard);
    reorderedCards.add(secondCard);

    assertThrows(IllegalArgumentException.class, () ->
            game.playCard(alterFuture, reorderedCards));
  }

  @Test
  void curseNextPlayerHasNoDefuse() {
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

    List<Card> result = game.playCard(curse);

    assertEquals(Collections.emptyList(), result);
    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore, game.getDrawPile().size());
  }

  @Test
  void curseNextPlayerHasOneDefuse() {
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

    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + EXISTING_TURNS, game.getDrawPile().size());
  }

  @Test
  void curseNextPlayerHasMultipleDefuses() {
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

    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + TWO_DEFUSE_CARDS, game.getDrawPile().size());
  }

  @Test
  void curseOnlyOneOtherPlayerAlive() {
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

    assertFalse(nextPlayer.hasDefuse());
    assertEquals(drawPileSizeBefore + EXISTING_TURNS, game.getDrawPile().size());
  }
  
  @Test
  void attackMoreThanOneOtherPlayerAlive() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    Player currentPlayer = game.getCurrentPlayer();
    currentPlayer.addCard(new Card(CardType.ATTACK));

    int nextPlayerIndex = (game.getCurrentPlayerIndex() + 1) % game.getPlayers().size();
    Player nextPlayer = game.getPlayers().get(nextPlayerIndex);

    List<Card> result = game.playCard(new Card(CardType.ATTACK));

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

    List<Card> result = game.playCard(new Card(CardType.ATTACK));

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

    List<Card> result = game.playCard(new Card(CardType.ATTACK));

    assertEquals(nextPlayerIndex, game.getCurrentPlayerIndex());
    assertEquals(TURNS_OWED, nextPlayer.getTurnsOwed());
    assertEquals(Collections.emptyList(), result);
  }
}
