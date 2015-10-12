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

public class GameThread extends Thread{

    private GameScreen gameScreen;
    private SurfaceHolder surfaceHolder;
    boolean playing = false;
    Canvas c = null;

    public GameThread(SurfaceHolder surfaceHolder, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.surfaceHolder = surfaceHolder;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    @Override
    public void run() {
        while (playing) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            try {
                c = surfaceHolder.lockCanvas();
                gameScreen.drawEntities(c);
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }

    }

}

