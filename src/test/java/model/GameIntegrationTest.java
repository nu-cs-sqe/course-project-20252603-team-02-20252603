package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GameIntegrationTest {
  private static final int RANDOM_SEED = 42;
  private static final int MIN_PLAYERS = 3;
  private static final int FIRST_CARD_INDEX = 0;
  private static final int SECOND_CARD_INDEX = 1;
  private static final int THIRD_CARD_INDEX = 2;
  private static final int FOURTH_CARD_INDEX = 3;

  @Test
  public void givenAlterFutureOrderWhenActionResolvesThenDeckTopCardsAreReordered() {
    Game game = givenStartedGameWithEmptyDrawPile();
    Player currentPlayer = game.getCurrentPlayer();
    Card alterFuture = new Card(CardType.ALTER_FUTURE);
    Card firstCard = new Card(CardType.FAVOR);
    Card secondCard = new Card(CardType.SHUFFLE);
    Card thirdCard = new Card(CardType.NOPE);
    Card fourthCard = new Card(CardType.SKIP);
    givenCurrentPlayerHasCard(currentPlayer, alterFuture);
    givenDrawPileHasCards(game, firstCard, secondCard, thirdCard, fourthCard);

    whenCurrentPlayerPlaysAlterFuture(game, alterFuture, thirdCard, firstCard, secondCard);
    List<Card> result = whenPendingActionResolves(game);

    thenActionReturnsNoCards(result);
    thenDrawPileStartsWith(game, thirdCard, firstCard, secondCard, fourthCard);
    thenDiscardPileContains(game, alterFuture);
  }

  private Game givenStartedGameWithEmptyDrawPile() {
    Game game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
    return game;
  }

  private void givenCurrentPlayerHasCard(Player player, Card card) {
    player.addCard(card);
  }

  private void givenDrawPileHasCards(Game game, Card firstCard, Card secondCard,
                                     Card thirdCard, Card fourthCard) {
    game.addToDrawPile(fourthCard, FIRST_CARD_INDEX);
    game.addToDrawPile(thirdCard, FIRST_CARD_INDEX);
    game.addToDrawPile(secondCard, FIRST_CARD_INDEX);
    game.addToDrawPile(firstCard, FIRST_CARD_INDEX);
  }

  private void whenCurrentPlayerPlaysAlterFuture(Game game, Card alterFuture,
                                                 Card firstCard, Card secondCard, Card thirdCard) {
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(firstCard);
    reorderedCards.add(secondCard);
    reorderedCards.add(thirdCard);
    game.playCard(alterFuture, reorderedCards);
  }

  private List<Card> whenPendingActionResolves(Game game) {
    return game.resolvePendingAction();
  }

  private void thenActionReturnsNoCards(List<Card> result) {
    assertEquals(Collections.emptyList(), result);
  }

  private void thenDrawPileStartsWith(Game game, Card firstCard, Card secondCard,
                                      Card thirdCard, Card fourthCard) {
    List<Card> drawPile = game.getDrawPile();
    assertEquals(firstCard, drawPile.get(FIRST_CARD_INDEX));
    assertEquals(secondCard, drawPile.get(SECOND_CARD_INDEX));
    assertEquals(thirdCard, drawPile.get(THIRD_CARD_INDEX));
    assertEquals(fourthCard, drawPile.get(FOURTH_CARD_INDEX));
  }

  private void thenDiscardPileContains(Game game, Card card) {
    assertTrue(game.getDiscardPile().contains(card));
  }
}
