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
}
