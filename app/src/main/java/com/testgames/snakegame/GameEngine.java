package com.testgames.snakegame;

import android.graphics.Bitmap;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine{

    private int width, height, tileWidth, tileHeight, currentD, score;
    boolean playing, lost, paused;
    float x, y;
    //private ArrayList<Float> snake;
    private static ArrayList<Integer> direction;
    private float[] food, snake;
    private Timer snakeMover;
    private TimerTask task;
    private Handler timerHandle;
    private SwipeListener swipeListener;

    public GameEngine(int width, int height, Bitmap tile){
        this.width = width;
        this.height = height;
        //snake = new ArrayList<>();
        direction = new ArrayList<>();
        tileWidth = tile.getWidth();
        tileHeight = tile.getHeight();
        timerHandle = new Handler();
        //swipeListener = new SwipeListener(this);
        food = new float[2];
        snake = new float[2];
        direction.add(0);
        x = y = 0;
        playing = false;
        generateFood();
    }

    //Create snake
    public void initSnake(){
        snake[0] = (tileWidth * 20);
        snake[1] = (tileHeight * 20);
        updateTimer(10000 / (height / tileHeight));
        lost = false;
        score = 0;

    }

    //Checks for collision
    public void checkCollision(){
        //Food eating
        if(snake[0] == food[0] && snake[1] == food[1]){
            score++;
            generateFood();
            updateTimer((10000/(height/tileHeight))/((score/3.0)+1));
        }

        //Checks if snake hits the wall.
        if(x < (tileWidth/1.3) || x > width - (tileWidth * 1.3) || y < (tileHeight/2) || y > height - (tileHeight * 1.1)){
            playing = false;
            lost = true;
            currentD = 0;
        }
    }

    public void updateTimer(double d){
        if(snakeMover != null){
            snakeMover.cancel();
        }

        snakeMover = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                timerHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        if(playing){
                            moveSnake();
                        }
                    }
                });
            }
        };

        snakeMover.schedule(task, 0, (long) d);
    }

    public void generateFood(){
        //Calculate maximum number of blocks that can fit on the canvas
        int t;
        int maxHorizontal = width/tileWidth;
        int maxVertical = height/tileHeight;
        maxHorizontal -= 3;
        maxVertical -= 7;

        //Generate random X coordinate for food
        t = (int) ((Math.random()*10000)%maxHorizontal);
        t += 2;
        food[0] = t*tileWidth;

        //Generate random Y coordinate for food
        t = (int) ((Math.random()*10000)%maxVertical);
        t += 6;
        food[1] = t*tileHeight;
    }

    public static void setDirection(int d){
        direction.add(Integer.valueOf(d));
    }

    public void moveSnake(){
        if(direction.size() > 0){
            currentD = direction.remove(0);
        }
        switch (currentD){
            //Going up
            case 0:
                snake[1]-=10;
                x = snake[0];
                y = snake[1];
                break;

            //Going right
            case 1:
                snake[0]+=10;
                x = snake[0];
                y = snake[1];
                break;

            //Going down
            case 2:
                snake[1]+=10;
                x = snake[0];
                y = snake[1];
                break;

            //Going left
            case 3:
                snake[0]-=10;
                x = snake[0];
                y = snake[1];
                break;
        }
        checkCollision();
    }

    public float[] getFood(){
        return food;
    }

    /*public ArrayList<Float> getSnake(){
        return snake;
    }*/

    public float[] getSnake(){
        return snake;
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }

    public boolean getPlaying(){
        return playing;
    }

    public int getScore(){
        return score;
    }

    public boolean lost(){
        return lost;
    }

    public boolean getPause(){
        return paused;
    }

    public void pause(){
        if(!paused){
            snakeMover.cancel();
            paused = true;
        }
        else{
            paused = false;
            updateTimer((10000/(height/tileHeight))/((score/3.0)+1));
        }
    }


}
