package com.cir3.minesweeper.gameobject;

import java.util.concurrent.ThreadLocalRandom;

public class Point {

    private int x;
    private int y;

    public Point(){

    }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

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

    public Point randomPoint(int n, int m){
        int randomx = ThreadLocalRandom.current().nextInt(0, n );
        int randomy = ThreadLocalRandom.current().nextInt(0, m );

        return new Point(randomx, randomy);

    }

    public boolean isInbound(int maxX, int maxY){

        if (this.x < 0  || this.x >= maxX){
            return false;
        }else if (this.y < 0 || this.y >= maxY){
            return false;
        }
        return true;
    }
}
