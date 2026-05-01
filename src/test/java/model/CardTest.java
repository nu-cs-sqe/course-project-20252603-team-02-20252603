package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    void testCardCreation() {
        Card defuseCard = new Card(CardType.DEFUSE);

        assertEquals(CardType.DEFUSE, defuseCard.getType());
    }
}
