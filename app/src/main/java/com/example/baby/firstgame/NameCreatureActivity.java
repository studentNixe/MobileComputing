package com.example.baby.firstgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * Created by Pawan on 5/12/2017.
 */

public class NameCreatureActivity extends Activity
{
    private ImageSwitcher eggSwitcher;
    Integer[] eggs = {R.drawable.denis_egg,
            R.drawable.pawan_egg, R.drawable.nicole_eggs};

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.namecreature);

        int creatureEgg = getIntent().getIntExtra("creatureSelect", 0);
        eggSwitcher = (ImageSwitcher) findViewById(R.id.eggSwitch);
        eggSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
        eggSwitcher.setImageResource(eggs[creatureEgg]);
    }
}
