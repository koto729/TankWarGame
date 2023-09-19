package com.kwt.tankwargame.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
    MyTank myTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Boom boom = null;
    Image boomImage = null;

    int enemyTankSize = 8;

    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("TimesRoman", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("Accumulated Kills", 1020,30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum()+" ", 1080,100);
    }

    public MyPanel() {
        myTank = new MyTank(500,500);
        myTank.setSpeed(5);
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            enemyTank.setDirection(3);
            Shot shot = new Shot(enemyTank.getX() + 20 , enemyTank.getY()+60, enemyTank.getDirection());
            enemyTank.shots.add(shot);
            new Thread(shot).start();
            new Thread(enemyTank).start();
            enemyTanks.add(enemyTank);
        }
        boomImage = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/boom.png"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        g.drawImage(boomImage, 100, 100, 60, 60, this);
        g.fillRect(0, 0, 1000, 750);
        drawMyBullet(100, 100, g);

        if (myTank != null && myTank.isLive){
            for (int i = 0; i < myTank.shots.size(); i++) {
                Shot shot = myTank.shots.get(i);
                if (shot.isLive) {
                    System.out.println(myTank.shots.size()+" : x = "+shot.getX()+" y = "+shot.getY());
                    drawMyBullet(shot.getX(), shot.getY(), g);
                }else {
                    myTank.shots.remove(shot);
                }
            }

            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }



        if(boom != null && boom.isLive){
            g.drawImage(boomImage, boom.x, boom.y, 60, 60, this);
            boom.lifeDown();
        }


        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        drawEnemyBullet(shot.getX(), shot.getY(), g);
                        //g.fill3DRect(shot.getX(),shot.getY(), 4, 4, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

    }

    /**
     *
     * @param x  tank x coordinate on the top left side.
     * @param y  tank y coordinate on the top left side.
     * @param g
     * @param direction  direction
     * @param type type of tank
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type){
        switch (type){
            case 0: // my tank
                g.setColor(Color.cyan);
                break;
            case 1: // enemy
                g.setColor(Color.red);
                break;
        }
        switch (direction){
            case 0:
                //draw the tank shape when face to top
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1:
                //draw the tank shape when face to right
                g.fill3DRect(x, y, 60,10,false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                //draw the tank shape when face to left
                g.fill3DRect(x, y, 60,10,false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x , y + 20);
                break;
            case 3:
                //draw the tank shape when face to down
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y +60, x + 20, y+30);
                break;
            default:
                System.out.println("nothing");
        }
    }

    public void hit(Shot s, Tank tank){
        switch (tank.getDirection()){
            case 0:
            case 3:
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 40
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    System.out.println("NAME " + tank.getX());
                    enemyTanks.remove(tank);
                    boom = new Boom(tank.getX(), tank.getY());
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                        System.out.println(Recorder.getAllEnemyTankNum());
                    }
                }
            case 1:
            case 2:
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 60
                        && s.getY() > tank.getY() && s.getY() < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    System.out.println("NAME " + tank.getX());
                    enemyTanks.remove(tank);
                    boom = new Boom(tank.getX(), tank.getY());
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                        System.out.println(Recorder.getAllEnemyTankNum());
                    }
                }
        }
    }

    public void drawMyBullet(int x, int y, Graphics g){
        g.fill3DRect(x,y, 4, 4, false);
        g.setColor(Color.cyan);
    }

    public void drawEnemyBullet(int x, int y, Graphics g){
        g.fill3DRect(x,y, 4, 4, false);
        g.setColor(Color.red);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && myTank.getY() > 0) {
            myTank.setDirection(0);
            myTank.moveup();
        }
        if(e.getKeyCode() == KeyEvent.VK_D && myTank.getX() + 60 < 1000) {
            myTank.setDirection(1);
            myTank.moveright();
        }
        if(e.getKeyCode() == KeyEvent.VK_A && myTank.getX() > 0) {
            myTank.setDirection(2);
            myTank.moveleft();
        }
        if(e.getKeyCode() == KeyEvent.VK_S && myTank.getY() + 60 < 750) {
            myTank.setDirection(3);
            myTank.movedown();
        }
        if (e.getKeyCode() == KeyEvent.VK_J && myTank.isLive){
            //if (myTank.shot == null || !myTank.shot.isLive){
            System.out.println("Fire!!!");
            myTank.shotBullet();
            //}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //check EnemyTank
            if (myTank.shot != null && myTank.shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < myTank.shots.size(); j++) {
                        Shot shot = myTank.shots.get(j);
                        System.out.println("------SHOT---: Name = " + Thread.currentThread().getName() + " x =  "
                                + myTank.shot.getX() + " y = " + myTank.shot.getY());
                        hit(shot, enemyTank);
                    }
                }
            }
            //check MyTank
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        Shot shot = enemyTank.shots.get(j);
                        hit(shot, myTank);
                    }
                }
            }
            if (!myTank.isLive){
                //myTank = null;
            }
            this.repaint();
        }
    }
}
