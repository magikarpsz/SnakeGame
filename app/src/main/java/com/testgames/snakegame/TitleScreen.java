package com.testgames.snakegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TitleScreen extends Activity {

    GameScreen gameScreen;
    Button start, pause, credit, up, down, left, right;
    TextView score, title;
    public static AlertDialog.Builder builder;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlescreen);
        start = (Button) findViewById(R.id.startButton);
        pause = (Button) findViewById(R.id.pauseButton);
        credit = (Button) findViewById(R.id.creditButton);
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right= (Button) findViewById(R.id.right);
        score = (TextView) findViewById(R.id.score);
        title = (TextView) findViewById(R.id.title);
        gameScreen = (GameScreen) findViewById(R.id.playField);
        gameScreen.setBackgroundColor(Color.WHITE);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Credit");
        builder.setMessage("Snake game version 1. Created 10/8/2015");
        builder.setPositiveButton("OK", null);

        score.setText("Score: " + gameScreen.gameThread.getScore());
        score.setTextColor(Color.RED);
        score.setVisibility(View.GONE);
        pause.setVisibility(View.GONE);
        up.setVisibility(View.GONE);
        down.setVisibility(View.GONE);
        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
    }

    public void credit(View v){
        builder.show();
    }

    public void pause(View v){
        gameScreen.pauseGame();
        start.setVisibility(View.VISIBLE);
        pause.setVisibility(View.GONE);
        score.setVisibility(View.VISIBLE);
        start.setText("Continue");
    }

    public void start(View v){
        gameScreen.startGame();
        gameScreen.setBackgroundColor(Color.TRANSPARENT);
        start.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        score.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
        up.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
    }

    public void up(View v){
        gameScreen.gameThread.setCurrentD(0);
    }

    public void down(View v){
        gameScreen.gameThread.setCurrentD(2);
    }

    public void left(View v){
        gameScreen.gameThread.setCurrentD(3);
    }

    public void right(View v){
        gameScreen.gameThread.setCurrentD(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameScreen.pauseGame();
    }
}
