package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @EnumSource(CardType.class)
    void testEquals_SameCardType_ReturnsTrue(CardType type) {
        Card card1 = new Card(type);
        Card card2 = new Card(type);

        assertTrue(card1.equals(card2));
    }

//    @ParameterizedTest
//    @CsvSource({
//            "DEFUSE, ATTACK",
//            "ATTACK, SKIP",
//            "SKIP, FAVOR",
//            "FAVOR, SHUFFLE",
//            "SHUFFLE, NOPE",
//            "NOPE, SEE_THE_FUTURE",
//            "SEE_THE_FUTURE, TACOCAT",
//            "TACOCAT, HAIRY_POTATO_CAT",
//            "HAIRY_POTATO_CAT, RAINBOW_RALPHING_CAT",
//            "RAINBOW_RALPHING_CAT, BEARD_CAT",
//            "BEARD_CAT, CATTERMELON",
//            "CATTERMELON, DEFUSE"
//    })
//    void testEquals_DifferentCardTypes_ReturnsFalse(CardType type1, CardType type2) {
//        Card card1 = new Card(type1);
//        Card card2 = new Card(type2);
//
//        assertFalse(card1.equals(card2));
//    }
}
