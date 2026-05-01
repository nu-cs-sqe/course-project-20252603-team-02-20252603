package model;

public class Card {
    private final CardType type;

    public Card(CardType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Card card = (Card) obj;
        return type == card.type;
    }

    public CardType getType() {
        return type;
    }
}
