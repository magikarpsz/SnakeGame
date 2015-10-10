package com.testgames.snakegame;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class GameScreen extends SurfaceView{

    SurfaceHolder holder;
    Bitmap player, food;
    GameThread gameThread;
    GameEngine gameEngine;
    float sx, sy;
    Random rand;
    Paint paint;

    public GameScreen(Context context, AttributeSet attrs){
        super(context, attrs);
        holder = getHolder();
        gameThread = new GameThread(holder, this);
        player = BitmapFactory.decodeResource(getResources(), R.drawable.snake);
        food = BitmapFactory.decodeResource(getResources(), R.drawable.snake_food);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
    }

    public void drawEntities(Canvas c){
        c.drawARGB(255, 255, 255, 255);
        c.drawBitmap(player, gameThread.getX(), gameThread.getY(), null);
        c.drawBitmap(food, gameThread.getFX(), gameThread.getFY(), null);
        c.drawText("Score: " + gameThread.score, 50, 50, paint);
    }

    public void startGame(){
        gameThread.setPlaying(true);
        gameThread.resume();
    }

    public void restartGame(){

    }

    public void pauseGame(){
        gameThread.pause();
    }

    public int getPlayerWidth(){
        return player.getWidth();
    }

    public int getPlayerHeight(){
        return player.getHeight();
    }

    public int getFoodWidth(){
        return food.getWidth();
    }

    public int getFoodHeight(){
        return food.getHeight();
    }

}

