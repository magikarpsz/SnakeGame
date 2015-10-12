package com.testgames.snakegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TitleScreen extends Activity implements View.OnClickListener{

    GameScreen gameScreen;
    Button start, pause, credit, up, down, left, right;
    TextView score, title;
    AlertDialog.Builder builder;
    Timer timer;
    TimerTask task;
    Handler timerHandle;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlescreen);

        //Setting up buttons, textview, and playing field
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
        gameScreen.setBackgroundColor(Color.WHITE); //White background at title screen

        //AlertDialog for credit
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Credit");
        builder.setMessage("Snake game version 1. Created 10/8/2015");
        builder.setPositiveButton("OK", null);

        //Hide score, pause, arrow key buttons at title screen.
        //score.setText("Score: " + gameScreen.getScore());
        score.setTextColor(Color.RED);
        score.setVisibility(View.GONE);
        pause.setVisibility(View.GONE);
        up.setVisibility(View.GONE);
        down.setVisibility(View.GONE);
        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);

        //Setup a Timer, TimerTask, and Handler to handle when game is over
        timer = new Timer();
        timerHandle = new Handler();
        task = new TimerTask() {
            @Override
            public void run() {
                timerHandle.post(new Runnable() {
                    @Override
                    public void run() {
                        if(gameScreen != null && gameScreen.gameOn() && gameScreen.lost()){
                            String showScore = getString(R.string.score) + gameScreen.getScore();
                            start.setVisibility(View.VISIBLE);
                            score.setVisibility(View.VISIBLE);
                            credit.setVisibility(View.VISIBLE);
                            up.setVisibility(View.GONE);
                            down.setVisibility(View.GONE);
                            left.setVisibility(View.GONE);
                            right.setVisibility(View.GONE);
                            start.setText(R.string.restartButton);
                            score.setText(showScore);
                        }
                    }
                });
            }
        };

        //Schedule a time to execute the defined task above.
        timer.schedule(task, 1000, 500);

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

    }

    //Show credit (via AlertDialog) when credit button is pressed
    public void credit(View v){
        builder.show();
    }


    public void pause(View v){
        if(gameScreen.getPause()){
            pause.setText("Pause");
        }
        else{
            pause.setText("Resume");
        }
            gameScreen.pause();
    }

    //Starts the game
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

    //Click even for the up, down, left, and right buttons
    public void onClick(View v){
        switch(v.getId()){
            case R.id.up:
                gameScreen.setDirection(0);
                break;
            case R.id.down:
                gameScreen.setDirection(2);
                break;
            case R.id.left:
                gameScreen.setDirection(3);
                break;
            case R.id.right:
                gameScreen.setDirection(1);
                break;
        }
    }

}
