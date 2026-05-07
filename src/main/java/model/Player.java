package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private boolean alive;
    private List<Card> hand;

    public Player() {
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("invalid card");
        }
        hand.add(card);
    }

    public void removeCard(Card card) {
        if (hand.isEmpty()) {
            throw new IllegalStateException("empty hand, cannot remove a card");
        }
        hand.remove(card);
    }
}
