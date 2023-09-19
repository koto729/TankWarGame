package com.kwt.tankwargame.game;

public class Boom {
    int x,y;
    int life = 4;
    boolean isLive = true;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        System.out.println("booom "+ life);
        life--;
        if (life < 0 ){
            isLive = false;
        }
    }

}
