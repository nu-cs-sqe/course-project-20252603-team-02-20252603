package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameStepDefinitions {
  private static final int RANDOM_SEED = 42;
  private static final int MIN_PLAYERS = 3;
  private static final int FIRST_CARD_INDEX = 0;
  private static final int SECOND_CARD_INDEX = 1;
  private static final int THIRD_CARD_INDEX = 2;
  private static final int FOURTH_CARD_INDEX = 3;
  private static final int SECOND_PLAYER_INDEX = 1;

  private Game game;
  private Player currentPlayer;
  private Player nextPlayer;
  private Card alterFutureCard;
  private Card curseCard;
  private List<Card> actionResult;

  @Given("a started game with an empty draw pile")
  public void aStartedGameWithAnEmptyDrawPile() {
    game = new Game(MIN_PLAYERS, new Random(RANDOM_SEED));
    game.startGame();
    currentPlayer = game.getCurrentPlayer();
    nextPlayer = game.getPlayers().get(SECOND_PLAYER_INDEX);
    while (!game.getDrawPile().isEmpty()) {
      game.drawFromDeck();
    }
  }

  @Given("the current player has an Alter Future card")
  public void theCurrentPlayerHasAnAlterFutureCard() {
    alterFutureCard = new Card(CardType.ALTER_FUTURE);
    currentPlayer.addCard(alterFutureCard);
  }

  @Given("the current player has a Curse card")
  public void theCurrentPlayerHasACurseCard() {
    curseCard = new Card(CardType.CURSE);
    currentPlayer.addCard(curseCard);
  }

  @Given("the draw pile contains {word}, {word}, {word}, {word} from top to bottom")
  public void theDrawPileContainsCardsFromTopToBottom(String firstType, String secondType,
                                                     String thirdType, String fourthType) {
    game.addToDrawPile(new Card(toCardType(fourthType)), FIRST_CARD_INDEX);
    game.addToDrawPile(new Card(toCardType(thirdType)), FIRST_CARD_INDEX);
    game.addToDrawPile(new Card(toCardType(secondType)), FIRST_CARD_INDEX);
    game.addToDrawPile(new Card(toCardType(firstType)), FIRST_CARD_INDEX);
  }

  @Given("the next player has {int} Defuse cards")
  public void theNextPlayerHasDefuseCards(int defuseCount) {
    while (nextPlayer.hasDefuse()) {
      nextPlayer.removeCard(new Card(CardType.DEFUSE));
    }
    for (int i = 0; i < defuseCount; i++) {
      nextPlayer.addCard(new Card(CardType.DEFUSE));
    }
  }

  @When("the current player plays Alter Future with order {word}, {word}, {word}")
  public void theCurrentPlayerPlaysAlterFutureWithOrder(String firstType, String secondType,
                                                        String thirdType) {
    List<Card> reorderedCards = new ArrayList<>();
    reorderedCards.add(new Card(toCardType(firstType)));
    reorderedCards.add(new Card(toCardType(secondType)));
    reorderedCards.add(new Card(toCardType(thirdType)));
    game.playCard(alterFutureCard, reorderedCards);
  }

  @When("the current player plays Curse")
  public void theCurrentPlayerPlaysCurse() {
    game.playCard(curseCard);
  }

  @When("the pending action resolves")
  public void thePendingActionResolves() {
    actionResult = game.resolvePendingAction();
  }

  @Then("the action returns no cards")
  public void theActionReturnsNoCards() {
    assertEquals(Collections.emptyList(), actionResult);
  }

  @Then("the draw pile starts with {word}, {word}, {word}, {word}")
  public void theDrawPileStartsWith(String firstType, String secondType, String thirdType,
                                    String fourthType) {
    List<Card> drawPile = game.getDrawPile();
    assertEquals(toCardType(firstType), drawPile.get(FIRST_CARD_INDEX).getType());
    assertEquals(toCardType(secondType), drawPile.get(SECOND_CARD_INDEX).getType());
    assertEquals(toCardType(thirdType), drawPile.get(THIRD_CARD_INDEX).getType());
    assertEquals(toCardType(fourthType), drawPile.get(FOURTH_CARD_INDEX).getType());
  }

  @Then("the next player has no Defuse cards")
  public void theNextPlayerHasNoDefuseCards() {
    assertFalse(nextPlayer.hasDefuse());
  }

  @Then("the draw pile has {int} cards")
  public void theDrawPileHasCards(int expectedSize) {
    assertEquals(expectedSize, game.getDrawPile().size());
  }

  @Then("the discard pile contains {word}")
  public void theDiscardPileContains(String cardType) {
    CardType expectedType = toCardType(cardType);
    assertTrue(game.getDiscardPile().stream()
            .anyMatch(card -> card.getType() == expectedType));
  }

  private CardType toCardType(String cardType) {
    return CardType.valueOf(cardType);
  }
}
