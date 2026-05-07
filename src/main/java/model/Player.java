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
        this.alive = false;
    }

    public List<Card> getHand() {
        return this.hand;
    }
}
