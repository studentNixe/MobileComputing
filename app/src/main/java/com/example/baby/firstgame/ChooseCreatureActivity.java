package com.example.baby.firstgame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.baby.firstgame.handler.CreatureHandler;

/**
 * Created by Pawan on 5/8/2017.
 */

public class ChooseCreatureActivity extends Activity implements GestureDetector.OnGestureListener
{
    private ImageSwitcher creSwitcher;

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    Integer[] creatures = {R.drawable.denise1,
            R.drawable.pawan1, R.drawable.nicole2};
    int iterate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecreature);

        Button btnNext = (Button) findViewById(R.id.next);
        Button btnPre = (Button) findViewById(R.id.previous);
        Button btnSelect = (Button) findViewById(R.id.select);
        mDetector = new GestureDetectorCompat(this,this);


        //------------- will be deleted ----------
        CreatureHandler.createObject("dragon","nicole",this);
        //----------------------------------------

        creSwitcher = (ImageSwitcher) findViewById(R.id.creatureSwitcher);
        creSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        creSwitcher.setImageResource(creatures[0]);

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterate = (iterate + 1) % creatures.length;
                creSwitcher.setImageResource(creatures[iterate]);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterate = (iterate+1) % creatures.length;
                creSwitcher.setImageResource(creatures[iterate]);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NameCreatureActivity.class);
                intent.putExtra("creatureSelect", iterate);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
     // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(DEBUG_TAG,"onDown: " + e.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onShowPress: " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + e.toString());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onLongPress: " + e.toString());
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            float distance = event1.getX() - event2.getX();
            if(distance < -200){
                    Log.d(DEBUG_TAG,"Fling was to the right.");
                }else if(distance > 200){
                    Log.d(DEBUG_TAG,"Fling was to the left.");
                }else{
                    Log.d(DEBUG_TAG,"No left or right fling occured.");
                }
            return true;
        }
    }
}
