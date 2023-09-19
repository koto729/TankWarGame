package com.kwt.tankwargame.game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        this.setSize(1310, 780);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(mp);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Open");
                Recorder.getRecord();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveRecord();
                System.exit(0);
            }
        });
    }
}
