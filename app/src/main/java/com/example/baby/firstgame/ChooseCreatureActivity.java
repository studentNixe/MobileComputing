package com.example.baby.firstgame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.baby.firstgame.data.CreatureHandler;

/**
 * Created by Pawan on 5/8/2017.
 */

public class ChooseCreatureActivity extends Activity
        implements GestureDetector.OnGestureListener
{
    private Button btnNext, btnPre, btnSelect;
    private ImageSwitcher creSwitcher;
    private GestureDetectorCompat gestureDetector;

    private int swipeMinDistance = 130;
    private int swipeVelocity = 200;

    Integer[] creatures = {R.drawable.denise1,
            R.drawable.pawan1, R.drawable.nicole2};
    int iterate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecreature);
        btnNext = (Button) findViewById(R.id.next);
        btnPre = (Button) findViewById(R.id.previous);
        btnSelect = (Button) findViewById(R.id.select);
        this.gestureDetector = new GestureDetectorCompat(this, this);

        //------------- will be deleted ----------
        CreatureHandler handler = new CreatureHandler(this);
        handler.setCreature("dragon","nicole");
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

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeVelocity)
        {
            iterate = (iterate+1) % creatures.length;
            creSwitcher.setImageResource(creatures[iterate]);
            return true;
        }
        else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeVelocity)
        {
            iterate = (iterate+1) % creatures.length;
            creSwitcher.setImageResource(creatures[iterate]);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
