package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    void testCardCreation() {
        Card defuseCard = new Card(CardType.DEFUSE);

        assertEquals(CardType.DEFUSE, defuseCard.getType());
    }

    @Test
    void testMoreCardCreations() {
        Card explodingKittenCard = new Card(CardType.EXPLODING_KITTEN);
        assertEquals(CardType.EXPLODING_KITTEN, explodingKittenCard.getType());

        Card nopeCard = new Card(CardType.NOPE);
        assertEquals(CardType.NOPE, nopeCard.getType());

        Card shuffleCard = new Card(CardType.SHUFFLE);
        assertEquals(CardType.SHUFFLE, shuffleCard.getType());

        Card tacocatCard = new Card(CardType.TACOCAT);
        assertEquals(CardType.TACOCAT, tacocatCard.getType());
    }

    @Test
    void testCardEquality() {
        Card beardCat1 = new Card(CardType.BEARD_CAT);
        Card beardCat2 = new Card(CardType.BEARD_CAT);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);

        assertEquals(beardCat1, beardCat2);
        assertNotEquals(beardCat1, explodingKitten);
    }

    @Test
    void testCardToString() {
        Card card = new Card(CardType.ALTER_THE_FUTURE);
        String result = card.toString();

        assertEquals("ALTER_THE_FUTURE CARD", result);
    }
}
