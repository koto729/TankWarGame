package com.kwt.tankwargame.game;

import javax.swing.*;

public class TankGame extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        new TankGame();
    }
    public TankGame(){
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1010, 785);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(mp);
    }
}
