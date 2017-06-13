package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.baby.firstgame.data.CreatureHandler;

/**
 * Created by Pawan on 5/30/2017.
 */

public class CreatureProfileActivity extends Activity {

    private TextView txtname, txtSpecies, txthunger, txtage, txtclean;
    private ProgressBar proHunger,proAge,proClean;
    private ImageView creatureImg;

    CreatureHandler creature = new CreatureHandler(this);
    ScaleGestureDetector scaleGestureDetector;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.creature_profile);

        creature.loadObject();

        txtname = (TextView) findViewById(R.id.name);
        txtSpecies = (TextView) findViewById(R.id.textView9);
        txthunger = (TextView) findViewById(R.id.hunger);
        txtage = (TextView) findViewById(R.id.age);
        txtclean = (TextView) findViewById(R.id.clean);
        proHunger = (ProgressBar) findViewById(R.id.hungerBar);
        proAge = (ProgressBar) findViewById(R.id.ageBar);
        proClean = (ProgressBar) findViewById(R.id.cleanBar);

        creatureImg = (ImageView) findViewById(R.id.creatureImg);
        scaleGestureDetector = new ScaleGestureDetector(this, new MyOnScaleGestureListener());

        txtname.setText(creature.getAttrString("name"));
        txtSpecies.setText(creature.getAttrString("species"));
        txthunger.setText(String.valueOf(creature.getAttrInt("hunger")));
        txtage.setText(String.valueOf(creature.getAttrInt("age")));
        txtclean.setText(String.valueOf(creature.getAttrInt("clean")));
        proHunger.setProgress(creature.getAttrInt("hunger"));
        proAge.setProgress(creature.getAttrInt("age"));
        proClean.setProgress(creature.getAttrInt("clean"));

        setCreatureImg();
    }

    public void setCreatureImg(){
        String fileName = creature.getAttrString("species") + creature.getAttrInt("age");
        int id = getResources().getIdentifier(fileName,"drawable", getPackageName());
        creatureImg.setImageDrawable(getResources().getDrawable(id));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public class MyOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            float pinchDetector = detector.getScaleFactor();
            //pinch/zooming in movement
            if(pinchDetector < 1){
                finish();
            }
            return true;
        }
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector){
            return true;
        }
        @Override
        public void onScaleEnd(ScaleGestureDetector detector){}

    }
}