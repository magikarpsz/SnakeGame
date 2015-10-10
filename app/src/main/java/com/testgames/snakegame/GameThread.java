package com.testgames.snakegame;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameThread implements Runnable{

    GameScreen gameScreen;
    SurfaceHolder surfaceHolder;
    boolean playing;
    Thread th = null;
    int currentD = 0;
    float x = 200, y = 200, fx = 200, fy = 200;
    int score = 0;
    Random rand;
    Canvas c = null;


    public GameThread(SurfaceHolder surfaceHolder, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        while (playing) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            try {
                c = surfaceHolder.lockCanvas();
                moveSnake();
                gameScreen.drawEntities(c);
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }

    }

    public void pause() {
        playing = false;
        while (true) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        th = null;
    }

    public void resume() {
        playing = true;
        th = new Thread(this);
        th.start();
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void moveSnake() {
            switch (currentD) {
                case 0:
                    y -= 1;
                    collide();
                    break;
                case 1:
                    x += 1;
                    collide();
                    break;
                case 2:
                    y += 1;
                    collide();
                    break;
                case 3:
                    x -= 1;
                    collide();
                    break;
                default:
            }
    }

    public void collide(){
        if(getX() == getFX() && getY() == getFY()){
            score++;
            generateFood();
        }
        else if(x < 0 && currentD == 3){
            pause();
            resume();
        }
        else if(y < 0 && currentD == 0){
            pause();
            resume();
        }
        else if(x > c.getWidth() - 30 && currentD == 1){
            pause();
            resume();
        }
        else if(y > c.getHeight() - 30 && currentD == 2){
            pause();
            resume();
        }
    }

    public void generateFood(){
        rand = new Random();
        fx = 200;
        fy = 200;
    }

    public int getScore(){
        return score;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getFX(){
        return fx;
    }

    public float getFY(){
        return fy;
    }

    public void setCurrentD(int currentD){
        this.currentD = currentD;
    }

}

