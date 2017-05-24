package com.example.baby.firstgame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
{
    private ImageSwitcher creSwitcher;

    Integer[] creatures = {R.drawable.denise1,
            R.drawable.pawan1, R.drawable.nicole1};
    int iterate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecreature);

        Button btnNext = (Button) findViewById(R.id.next);
        Button btnPre = (Button) findViewById(R.id.previous);
        Button btnSelect = (Button) findViewById(R.id.select);

        //------------- will be deleted ----------
        CreatureHandler handler = new CreatureHandler(this);
        handler.setCreature("dragon","denise");
        //----------------------------------------

        creSwitcher = (ImageSwitcher) findViewById(R.id.creatureSwitcher);
        creSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //     imageView.setLayoutParams(
//                        new ImageSwitcher.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT));
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
                startActivity(new Intent(getApplicationContext(), MonsterHomeActivity.class));
            }
        });
    }
}
