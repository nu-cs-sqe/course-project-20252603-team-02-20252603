package model;

import java.util.Objects;

public class Card {
  private final CardType type;

  public Card(CardType type) {
    this.type = Objects.requireNonNull(type, "CardType cannot be null");
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

  @Override
  public int hashCode() {
    return type.hashCode();
  }

  public CardType getType() {
    return type;
  }
}
