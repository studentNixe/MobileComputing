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

/**
 * The Activity starts of no creature was found. Shows different creatures
 * in an ImageSwitcher component. If the Button is pressed, the species is chosen
 * and the NameCreatureActivity is called.
 * Images/Creatures can be switched via Button or the Fling gesture,
 * implemented by a GestureBuilder.
 *
 * Credit: https://developer.android.com/training/gestures/detector.html
 *
 * Created by Pawan on 5/8/2017.
 */
public class ChooseCreatureActivity extends Activity implements GestureDetector.OnGestureListener {
    private ImageSwitcher creSwitcher;
    private static final String DEBUG_TAG = "DEBUG";
    private GestureDetectorCompat mDetector;

    //an array of the drawable containing the chosen creature
    Integer[] creatures = {R.drawable.denise1,
            R.drawable.pawan1, R.drawable.nicole2};
    int iterate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecreature);
        Log.d("DEBUG:", "ChooseCreatureActivity - started.");

        Button btnNext = (Button) findViewById(R.id.next);
        Button btnPre = (Button) findViewById(R.id.previous);
        Button btnSelect = (Button) findViewById(R.id.select);
        mDetector = new GestureDetectorCompat(this, this);

        creSwitcher = (ImageSwitcher) findViewById(R.id.creatureSwitcher);
        creSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        //we start the creature image by start from 0 and pressing next iterate to next image in loop
        //pressing previous iterate to previous image in loop
        creSwitcher.setImageResource(creatures[0]);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterate = (iterate + 2) % creatures.length;
                creSwitcher.setImageResource(creatures[iterate]);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterate = (iterate + 1) % creatures.length;
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
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * the method saves the starting and ending point of a Fling motion event.
     * Aa fling occurs when the finger is dragged fast over the screen.
     * by calculating the distance of the starting and end point, the direction is determined.
     *
     * @param event1
     * @param event2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        //Log.d(DEBUG_TAG, "ChooseCreatureActivity - onFling: " + event1.toString() + event2.toString());
        float distance = event1.getX() - event2.getX();
        if (distance < -200) {
            Log.d(DEBUG_TAG, "ChooseCreatureActivity - Fling detected. Fling was to the right.");
            iterate = (iterate + 1) % creatures.length;
            creSwitcher.setImageResource(creatures[iterate]);
        } else if (distance > 200) {
            Log.d(DEBUG_TAG, "ChooseCreatureActivity - Fling detected. Fling was to the left.");
            iterate = (iterate + 2) % creatures.length;
            creSwitcher.setImageResource(creatures[iterate]);
        } else {
            Log.d(DEBUG_TAG, "ChooseCreatureActivity - No left or right fling occurred.");
        }
        return true;
    }

    // not important and without function, but needs to be implemented.
    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
}

