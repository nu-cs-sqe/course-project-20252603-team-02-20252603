package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private boolean alive;
  private List<Card> hand;
  private int turnsOwed;

  public Player() {
    this.alive = true;
    this.hand = new ArrayList<>();
    this.turnsOwed = 1;
  }

  public boolean isAlive() {
    return alive;
  }

  public void die() {
    alive = false;
  }

  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  public void addCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("invalid card");
    }
    hand.add(card);
  }

  public void removeCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("invalid card");
    }
    if (hand.isEmpty()) {
      throw new IllegalStateException("empty hand, cannot remove a card");
    }
    if (!hand.contains(card)) {
      throw new IllegalArgumentException("card not in hand");
    }
    hand.remove(card);
  }

  public boolean hasDefuse() {
    return hand.stream().anyMatch(c -> c.getType() == CardType.DEFUSE);
  }

  public void addTurn() {
    if (turnsOwed < 2) {
      turnsOwed++;
    }
  }

  public void removeTurn() {
    if (turnsOwed == 0) {
      throw new IllegalStateException("player has no turns to remove");
    }
    turnsOwed--;
  }

  public int getTurnsOwed() {
    return turnsOwed;
  }
}
