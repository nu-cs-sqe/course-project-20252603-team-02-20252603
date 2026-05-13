package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @ParameterizedTest
    @EnumSource(CardType.class)
    void testValidCardsCreation(CardType type) {
        Card defuseCard = new Card(type);

        assertEquals(type, defuseCard.getType());
    }

    @Test
    void testInvalidCardCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(null);
        });
    }
}
