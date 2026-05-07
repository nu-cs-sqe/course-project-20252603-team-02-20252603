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


}
