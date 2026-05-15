package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void isAliveOnAlivePlayerExpectTrue() {
    Player player = new Player();
    assertTrue(player.isAlive());
  }

  @Test
  public void isAliveOnDeadPlayerExpectFalse() {
    Player player = new Player();
    player.die();
    assertFalse(player.isAlive());
  }

  @Test
  public void dieOnAlivePlayer() {
    Player player = new Player();
    player.die();
    assertFalse(player.isAlive());
  }

  @Test
  public void dieOnDeadPlayer() {
    Player player = new Player();
    player.die();
    player.die();
    assertFalse(player.isAlive());
  }

  @Test
  public void getHandEmptyHand() {
    Player player = new Player();
    assertEquals(List.of(), player.getHand());
  }

  @Test
  public void getHandOneCard() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    player.addCard(defuse);
    assertEquals(List.of(defuse), player.getHand());
  }

  @Test
  public void getHandMultipleCards() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
    player.addCard(defuse);
    player.addCard(explodingKitten);
    assertEquals(List.of(defuse, explodingKitten), player.getHand());
  }

  @Test
  public void getHandDuplicateCards() {
    Player player = new Player();
    Card attack1 = new Card(CardType.ATTACK);
    Card attack2 = new Card(CardType.ATTACK);
    player.addCard(attack1);
    player.addCard(attack2);
    assertEquals(List.of(attack1, attack2), player.getHand());
  }

  @Test
  public void addCardToEmptyHand() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    player.addCard(defuse);
    assertEquals(List.of(defuse), player.getHand());
  }

  @Test
  public void addCardToOneCardHand() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    Card skip = new Card(CardType.SKIP);
    player.addCard(defuse);
    player.addCard(skip);
    assertEquals(List.of(defuse, skip), player.getHand());
  }

  @Test
  public void addCardToMultipleCardHand() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    Card exploding = new Card(CardType.EXPLODING_KITTEN);
    Card attack = new Card(CardType.ATTACK);
    player.addCard(defuse);
    player.addCard(exploding);
    player.addCard(attack);
    assertEquals(List.of(defuse, exploding, attack), player.getHand());
  }

  @Test
  public void addCardToHandWithDuplicates() {
    Player player = new Player();
    Card attack1 = new Card(CardType.ATTACK);
    Card attack2 = new Card(CardType.ATTACK);
    Card defuse = new Card(CardType.DEFUSE);
    player.addCard(attack1);
    player.addCard(attack2);
    player.addCard(defuse);
    assertEquals(List.of(attack1, attack2, defuse), player.getHand());
  }

  @Test
  public void addCardResultingHandHasDuplicates() {
    Player player = new Player();
    Card defuse1 = new Card(CardType.DEFUSE);
    Card exploding = new Card(CardType.EXPLODING_KITTEN);
    Card defuse2 = new Card(CardType.DEFUSE);
    player.addCard(defuse1);
    player.addCard(exploding);
    player.addCard(defuse2);
    assertEquals(List.of(defuse1, exploding, defuse2), player.getHand());
  }

  @Test
  public void addCardNullCardThrowException() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    player.addCard(defuse);

    IllegalArgumentException e = assertThrows(
        IllegalArgumentException.class,
        () -> player.addCard(null)
    );
    assertEquals("invalid card", e.getMessage());
  }

  @Test
  public void removeCardOneCardHand() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    player.addCard(defuse);

    player.removeCard(defuse);
    assertEquals(List.of(), player.getHand());
  }

  @Test
  public void removeCardMultipleCardHand() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);
    Card exploding = new Card(CardType.EXPLODING_KITTEN);
    player.addCard(defuse);
    player.addCard(exploding);

    player.removeCard(exploding);
    assertEquals(List.of(defuse), player.getHand());
  }

  @Test
  public void removeCardHandHasDuplicates() {
    // [ATTACK, ATTACK, SKIP] remove ATTACK → [ATTACK, SKIP]
    Player player = new Player();
    Card attack1 = new Card(CardType.ATTACK);
    Card attack2 = new Card(CardType.ATTACK);
    Card skip = new Card(CardType.SKIP);
    player.addCard(attack1);
    player.addCard(attack2);
    player.addCard(skip);

    player.removeCard(attack1);
    assertEquals(List.of(attack2, skip), player.getHand());
  }

  @Test
  public void removeCardResultingHandHasDuplicates() {
    // [ATTACK, ATTACK, SKIP] remove SKIP → [ATTACK, ATTACK]
    Player player = new Player();
    Card attack1 = new Card(CardType.ATTACK);
    Card attack2 = new Card(CardType.ATTACK);
    Card skip = new Card(CardType.SKIP);
    player.addCard(attack1);
    player.addCard(attack2);
    player.addCard(skip);
    player.removeCard(skip);
    assertEquals(List.of(attack1, attack2), player.getHand());
  }

  @Test
  public void removeCardEmptyHandThrowException() {
    Player player = new Player();
    Card defuse = new Card(CardType.DEFUSE);

    IllegalStateException e = assertThrows(
        IllegalStateException.class,
        () -> player.removeCard(defuse)
    );
    assertEquals("empty hand, cannot remove a card", e.getMessage());
  }

  @Test
  public void removeCardCardNotInHandThrowException() {
    Player player = new Player();
    player.addCard(new Card(CardType.DEFUSE));
    player.addCard(new Card(CardType.ATTACK));

    IllegalArgumentException e = assertThrows(
        IllegalArgumentException.class,
        () -> player.removeCard(new Card(CardType.SKIP))
    );
    assertEquals("card not in hand", e.getMessage());
  }

  @Test
  public void removeCardNullCardThrowException() {
    Player player = new Player();
    player.addCard(new Card(CardType.DEFUSE));
    player.addCard(new Card(CardType.ATTACK));

    IllegalArgumentException e = assertThrows(
        IllegalArgumentException.class,
        () -> player.removeCard(null)
    );
    assertEquals("invalid card", e.getMessage());
  }

  @Test
  public void hasDefuseEmptyHandExpectFalse() {
    Player player = new Player();
    assertFalse(player.hasDefuse());
  }

  @Test
  public void hasDefuseOneDefuseInHandExpectTrue() {
    Player player = new Player();
    player.addCard(new Card(CardType.DEFUSE));
    assertTrue(player.hasDefuse());
  }

  @Test
  public void hasDefuseDefuseInMultipleCardsHandExpectTrue() {
    Player player = new Player();
    player.addCard(new Card(CardType.ATTACK));
    player.addCard(new Card(CardType.DEFUSE));
    assertTrue(player.hasDefuse());
  }

  @Test
  public void hasDefuseNoDefuseInMultipleCardsHandExpectFalse() {
    Player player = new Player();
    player.addCard(new Card(CardType.EXPLODING_KITTEN));
    player.addCard(new Card(CardType.SKIP));
    player.addCard(new Card(CardType.SKIP));
    assertFalse(player.hasDefuse());
  }

  @Test
  public void hasDefuseMultipleDefuseCards() {
    Player player = new Player();
    player.addCard(new Card(CardType.DEFUSE));
    player.addCard(new Card(CardType.SKIP));
    player.addCard(new Card(CardType.DEFUSE));
    assertTrue(player.hasDefuse());
  }

  @Test
  public void addTurnFromZero() {
    Player player = new Player();
    // turnsOwed defaults to 1 at construction, must bring it down to 0 for this test
    player.removeTurn();
    player.addTurn();
    assertEquals(1, player.getTurnsOwed());
  }

  @Test
  public void addTurnFromOne() {
    Player player = new Player();
    player.addTurn();
    assertEquals(2, player.getTurnsOwed());
  }

  @Test
  public void addTurnFromTwoNoChange() {
    Player player = new Player();
    player.addTurn();
    player.addTurn();
    assertEquals(2, player.getTurnsOwed());
  }

  @Test
  public void addTurnFromThreeStayAtTwo() {
    Player player = new Player();
    player.addTurn();
    player.addTurn();
    player.addTurn();
    assertEquals(2, player.getTurnsOwed());
  }

  @Test
  public void removeTurnFromZeroThrowException() {
    Player player = new Player();
    player.removeTurn();

    IllegalStateException e = assertThrows(
        IllegalStateException.class,
        player::removeTurn
    );
    assertEquals("player has no turns to remove", e.getMessage());
  }

  @Test
  public void removeTurnFromOne() {
    Player player = new Player();
    player.removeTurn();
    assertEquals(0, player.getTurnsOwed());
  }

  @Test
  public void removeTurnFromTwo() {
    Player player = new Player();
    player.addTurn();

    player.removeTurn();
    assertEquals(1, player.getTurnsOwed());
  }

  @Test
  public void getTurnsOwedExpectZero() {
    Player player = new Player();
    player.removeTurn();
    assertEquals(0, player.getTurnsOwed());
  }

  @Test
  public void getTurnsOwedExpectOne() {
    Player player = new Player();
    assertEquals(1, player.getTurnsOwed());
  }

  @Test
  public void getTurnsOwedExpectTwo() {
    Player player = new Player();
    player.addTurn();
    assertEquals(2, player.getTurnsOwed());
  }

}
