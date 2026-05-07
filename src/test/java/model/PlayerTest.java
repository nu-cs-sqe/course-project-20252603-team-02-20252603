package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    public void IsAlive_OnAlivePlayer_ExpectTrue() {
        Player player = new Player();
        assertTrue(player.isAlive());
    }
}
