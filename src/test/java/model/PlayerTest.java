package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void IsAlive_OnAlivePlayer_ExpectTrue() {
        Player player = new Player();
        assertTrue(player.isAlive());
    }

    @Test
    public void IsAlive_OnDeadPlayer_ExpectFalse() {
        Player player = new Player();
        player.die();
        assertFalse(player.isAlive());
    }

    @Test
    public void Die_OnAlivePlayer() {
        Player player = new Player();
        player.die();
        assertFalse(player.isAlive());
    }

    @Test
    public void Die_OnDeadPlayer() {
        Player player = new Player();
        player.die();
        player.die();
        assertFalse(player.isAlive());
    }

    @Test
    public void GetHand_EmptyHand() {
        Player player = new Player();
        assertEquals(List.of(), player.getHand());
    }

    @Test
    public void GetHand_OneCard() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        player.addCard(defuse);
        assertEquals(List.of(defuse), player.getHand());
    }

    @Test
    public void GetHand_MultipleCards() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(defuse);
        player.addCard(explodingKitten);
        assertEquals(List.of(defuse, explodingKitten), player.getHand());
    }

    @Test
    public void GetHand_DuplicateCards() {
        Player player = new Player();
        Card attack1 = new Card(CardType.ATTACK);
        Card attack2 = new Card(CardType.ATTACK);
        player.addCard(attack1);
        player.addCard(attack2);
        assertEquals(List.of(attack1, attack2), player.getHand());
    }

    @Test
    public void AddCard_ToEmptyHand() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        player.addCard(defuse);
        assertEquals(List.of(defuse), player.getHand());
    }

    @Test
    public void AddCard_ToOneCardHand() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        Card skip = new Card(CardType.SKIP);
        player.addCard(defuse);
        player.addCard(skip);
        assertEquals(List.of(defuse, skip), player.getHand());
    }

    @Test
    public void AddCard_ToMultipleCardHand() {
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
    public void AddCard_ToHandWithDuplicates() {
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
    public void AddCard_ResultingHandHasDuplicates() {
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
    public void AddCard_NullCard_ThrowException() {
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
    public void RemoveCard_OneCardHand() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        player.addCard(defuse);

        player.removeCard(defuse);
        assertEquals(List.of(), player.getHand());
    }

    @Test
    public void RemoveCard_MultipleCardHand() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);
        Card exploding = new Card(CardType.EXPLODING_KITTEN);
        player.addCard(defuse);
        player.addCard(exploding);

        player.removeCard(exploding);
        assertEquals(List.of(defuse), player.getHand());
    }

    @Test
    public void RemoveCard_HandHasDuplicates() {
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
    public void RemoveCard_ResultingHandHasDuplicates() {
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
    public void RemoveCard_EmptyHand_ThrowException() {
        Player player = new Player();
        Card defuse = new Card(CardType.DEFUSE);

        IllegalStateException e = assertThrows(
                IllegalStateException.class,
                () -> player.removeCard(defuse)
        );
        assertEquals("empty hand, cannot remove a card", e.getMessage());
    }

    @Test
    public void RemoveCard_CardNotInHand_ThrowException() {
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
    public void RemoveCard_NullCard_ThrowException() {
        Player player = new Player();
        player.addCard(new Card(CardType.DEFUSE));
        player.addCard(new Card(CardType.ATTACK));

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> player.removeCard(null)
        );
        assertEquals("invalid card", e.getMessage());
    }

}
