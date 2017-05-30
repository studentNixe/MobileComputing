package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.baby.firstgame.data.CreatureObject;

/**
 * Created by Pawan on 5/12/2017.
 */

public class NameCreatureActivity extends Activity
{
    private ImageSwitcher eggSwitcher;
    Integer[] eggs = {R.drawable.denis_egg,
            R.drawable.pawan_egg, R.drawable.nicole_eggs};
    private String[] speciesList = {"denise","pawan","nicole"};

    private Button btnenter = (Button) findViewById(R.id.Enter);
    EditText edtname = (EditText) findViewById(R.id.nameEntered);

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
        String species = speciesList[creatureEgg];

        //creates creature here with the give name and species
       CreatureObject creature = new CreatureObject(edtname.getText().toString(), species);

        btnenter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MonsterHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
