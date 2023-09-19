package com.kwt.tankwargame.game;

import java.io.*;

public class Recorder {
    private static int allEnemyTankNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "C:\\Users\\kowin\\Desktop\\myrecord.txt";


    public static void saveRecord(){
        System.out.println(allEnemyTankNum);
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void getRecord() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
