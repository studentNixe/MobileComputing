package com.example.baby.firstgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.baby.firstgame.data.CreatureHandler;


/**
 * The ProfileActivity shows the profile of the creature and all its current attributes.
 * The profile can be closed with the Phone's [back] button or with a pinch (zoom) gesture.
 * <p>
 * Created by Pawan on 5/30/2017.
 */

public class ProfileActivity extends Activity {

    private TextView txtName, txtSpecies, txtHunger, txtAge, txtClean, txtHappy;
    private ProgressBar proHunger, proAge, proClean, proHappy;
    private ImageView creatureImg;

    private CreatureHandler creature = new CreatureHandler(this);
    private ScaleGestureDetector scaleGestureDetector;
    private float pinchDetector = 1.f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creature_profile);
        creature.loadObject();

        txtHappy = (TextView) findViewById(R.id.happy);
        txtName = (TextView) findViewById(R.id.name);
        txtSpecies = (TextView) findViewById(R.id.textView9);
        txtHunger = (TextView) findViewById(R.id.hunger);
        txtAge = (TextView) findViewById(R.id.age);
        txtClean = (TextView) findViewById(R.id.clean);
        proHunger = (ProgressBar) findViewById(R.id.hungerBar);
        proAge = (ProgressBar) findViewById(R.id.ageBar);
        proClean = (ProgressBar) findViewById(R.id.cleanBar);
        proHappy = (ProgressBar) findViewById(R.id.progressBar2);

        creatureImg = (ImageView) findViewById(R.id.creatureImg);
        scaleGestureDetector = new ScaleGestureDetector(this, new MyOnScaleGestureListener());

        //this part of the code gets the attribute of the creature
        txtHappy.setText(String.valueOf(creature.getAttrInt("happiness")));
        txtName.setText(creature.getAttrString("name"));
        txtSpecies.setText(creature.getAttrString("species"));
        txtHunger.setText(String.valueOf(creature.getAttrInt("hunger")));
        txtAge.setText(String.valueOf(creature.getAttrInt("age")));
        txtClean.setText(String.valueOf(creature.getAttrInt("clean")));
        proHappy.setProgress(creature.getAttrInt("happiness"));
        proHunger.setProgress(creature.getAttrInt("hunger"));
        proAge.setProgress(creature.getAttrInt("age"));
        proAge.setMax(creature.getAttrInt("maxAge"));
        proClean.setProgress(creature.getAttrInt("clean"));

        setCreatureImg();
    }

    /**
     * sets the creature image depending on the creature selected
     */
    public void setCreatureImg() {
        String fileName = creature.getAttrString("species") + creature.getAttrInt("age");
        int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
        creatureImg.setImageDrawable(getResources().getDrawable(id));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * this is a method from scaleGestureDetector which detects the pinch action
     * and decides if it is pinch in or pinch out
     * in both case, it closes the activity and returns to monsterHomeActivity
     */
    public class MyOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            pinchDetector *= detector.getScaleFactor();
            finish();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }

    }

    /**
     * to make sure, that no old object is shown
     */
    @Override
    public void onResume() {
        super.onResume();
        creature.loadObject();
    }

}