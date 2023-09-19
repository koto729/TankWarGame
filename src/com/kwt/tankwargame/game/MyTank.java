package com.kwt.tankwargame.game;

import java.util.Vector;

public class MyTank extends Tank {
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();


    public MyTank(int x, int y) {
        super(x, y);
    }
    public void shotBullet(){
        switch (getDirection()){
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX(), getY() + 20, 2);
                break;
            case 3:
                shot = new Shot(getX() + 20, getY() + 60, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();

    }


}
