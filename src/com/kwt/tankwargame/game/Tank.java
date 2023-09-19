package com.kwt.tankwargame.game;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 2;
    boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveup() {
        y -= speed;
    }
    public void moveright() {
        x += speed;
    }
    public void movedown() {
        y += speed;
    }
    public void moveleft() {
        x -= speed;
    }

    ;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
