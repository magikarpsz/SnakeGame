package com.testgames.snakegame;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    GameThread gameThread;
    GameEngine gameEngine;
    GestureDetector gestureDetector;
    Bitmap snakeTile, foodTile;
    Paint snake, food, paint;

    public GameScreen(Context context, AttributeSet attrs){
        super(context, attrs);

        //Create Holder for canvas painting, GameThread object, tile object and call paintTile method to create
        //the snake and food.
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        gestureDetector = new GestureDetector(context, new SwipeListener());
        snakeTile = BitmapFactory.decodeResource(getResources(), R.drawable.snake);
        foodTile = BitmapFactory.decodeResource(getResources(), R.drawable.snake_food);
        paintTiles();
    }

    //Painting tiles to create snake and food
    public void paintTiles(){
        snake = new Paint();
        food = new Paint();
        snake.setColorFilter(new LightingColorFilter(Color.BLUE, 1));
        food.setColorFilter(new LightingColorFilter(Color.GREEN, 1));

        //Creating paint object to print the score on the screen.
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
    }

    //When the drawing surface is created, execute game thread to start the game.
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(gameThread.getState() == Thread.State.TERMINATED){
            gameThread = new GameThread(getHolder(), this);
            gameThread.setPlaying(true);
            gameThread.start();
        }
        else{
            gameThread.setPlaying(true);
            gameThread.start();
        }

        //Create GameEngine object and pass in the width and height or the canvas and the tile Bitmap.
        //Initialize the snake.
        gameEngine = new GameEngine(getWidth(), getHeight(), snakeTile);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //When surface is destroyed, end all threads.
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setPlaying(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Draw snake, food, and score onto the canvas.
    public void drawEntities(Canvas c){
        c.drawColor(Color.WHITE);
        if(gameEngine != null){
            c.drawBitmap(snakeTile, gameEngine.getSnake()[0], gameEngine.getSnake()[1], null);
            c.drawBitmap(foodTile, gameEngine.getFood()[0], gameEngine.getFood()[1], null);
            c.drawText("Score: " + gameEngine.getScore(), 50, 50, paint);
        }

    }

    public void startGame(){
        gameEngine.setPlaying(true);
        gameEngine.initSnake();
    }

    public boolean gameOn(){
        return (gameEngine != null);
    }

    public int getScore(){
        return gameEngine.getScore();
    }

    public boolean lost(){
        return gameEngine.lost();
    }

    public void pause(){
        gameEngine.pause();
    }

    public boolean getPause(){
        return gameEngine.getPause();
    }

    public void setDirection(int direction){
        gameEngine.setDirection(direction);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return false;
    }
}

