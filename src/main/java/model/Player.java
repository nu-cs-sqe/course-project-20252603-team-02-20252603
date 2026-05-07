package model;

public class Player {
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        this.alive = false;
    }
}
