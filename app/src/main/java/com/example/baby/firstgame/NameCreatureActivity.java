package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.baby.firstgame.data.CreatureHandler;

/**
 * Created by Pawan on 5/12/2017.
 */

public class NameCreatureActivity extends Activity
{
    private ImageSwitcher eggSwitcher;
    Integer[] eggs = {R.drawable.denise_egg,
            R.drawable.pawan_egg, R.drawable.nicole_egg};
    private String[] speciesList = {"denise","pawan","nicole"};
    private EditText edtname;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.namecreature);
        Button btnenter = (Button) findViewById(R.id.Enter);
        edtname = (EditText) findViewById(R.id.nameEntered);

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
        final String species = speciesList[creatureEgg];


        btnenter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = edtname.getText().toString();
                //creates creature here with the give name and species

                CreatureHandler creatureCreated = new CreatureHandler(getApplicationContext());
                creatureCreated.createObject(name, species);
                Intent intent = new Intent(getApplicationContext(), MonsterHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
