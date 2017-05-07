package com.example.baby.firstgame;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

/**
 * Created by baby on 24.04.17.
 */

public class GameActivity extends Activity implements View.OnClickListener, Runnable{
    private boolean isRunning;
    private int round;
    private int points;
    private int hearts;
    private int collectedHearts;
    private int time;

    private Random randomGenerator = new Random();
    private Handler handler = new Handler();
    private float scale;
    private ViewGroup gameView;

    private static final long MAXAGE_MS = 2000;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        gameView = (ViewGroup)findViewById(R.id.GAMEVIEW);
        startGame();

    }


    private void startGame(){
        isRunning = true;
        round = 0;
        points = 0;
        startRound();
    }

    private void startRound() {
        round++;
        hearts = round * 10;
        collectedHearts = 0;
        time = 60;
        reloadScreen();
        handler.postDelayed(this,1000);
    }

    private void reloadScreen() {
        TextView tvPoints = (TextView) findViewById(R.id.points);
        tvPoints.setText(Integer.toString(points));

        TextView tvRound = (TextView) findViewById(R.id.round);
        tvRound.setText(Integer.toString(round));

        TextView tvHits = (TextView) findViewById(R.id.hits);
        tvHits.setText(Integer.toString(collectedHearts));

        TextView tvTime = (TextView) findViewById(R.id.time);
        tvTime.setText(Integer.toString(time));

        scale = getResources().getDisplayMetrics().density;
    }

    public void countDown() {
        time--;
        float ranomNumber = randomGenerator.nextFloat();

        if (ranomNumber < hearts * 1.5f / 60) {
            showHeart();
        }

        double possibility = hearts * 1.5;
        if (possibility > 1){
            showHeart();
            if (ranomNumber < possibility - 1){
                showHeart();
            }
        }else{
            if (ranomNumber < possibility){
                showHeart();
            }
        }
        removeHeart();
        reloadScreen();
        if(!checkGameover()){
           if(!checkRoundEnd())
               handler.postDelayed(this,1000);
        }

    }

    private boolean checkRoundEnd() {
        if( collectedHearts >= hearts){
            startRound();
            return true;
        }
        return false;
    }


    private boolean checkGameover() {
        if (time == 0 && collectedHearts < hearts){
            gameOver();
            return true;
        }
        return false;
    }

    private void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
        isRunning = false;
    }


    private void removeHeart() {
        int nummer = 0;
        while (nummer < gameView.getChildCount() ){
            ImageView heartView = (ImageView) gameView.getChildAt(nummer);
            Date creationTime = (Date) heartView.getTag(R.id.creationTime);
            long age = (new Date()).getTime() - creationTime.getTime();

            if (age > MAXAGE_MS){
                gameView.removeView(heartView);
            }else{
                nummer++;
            }
        }


    }

    private void showHeart() {
        int width = gameView.getWidth();
        int height = gameView.getHeight();

        int heartWidth = (int)Math.round(scale *50);
        int heartHeight = (int)Math.round(scale *50);

        int left = randomGenerator.nextInt(width - heartWidth);
        int up = randomGenerator.nextInt(height - heartHeight);

        ImageView heartView = new ImageView(this);
        heartView.setImageResource(R.drawable.ic_herz);
        heartView.setOnClickListener(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(heartWidth,heartHeight);
        params.leftMargin = left;
        params.topMargin = up;
        params.gravity = Gravity.TOP + Gravity.LEFT;
        gameView.addView(heartView,params);

        heartView.setTag(R.id.creationTime, new Date());
    }

    @Override
    public void onClick(View heartView) {
        collectedHearts++;
        points += 100;
        reloadScreen();
        gameView.removeView(heartView);

    }

    @Override
    public void run() {
        countDown();
    }
}
