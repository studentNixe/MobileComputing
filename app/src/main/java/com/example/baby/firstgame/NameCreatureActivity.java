package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.baby.firstgame.data.CreatureHandler;

/**
 * The Action to name your creature. The name can not be longer than 20 characters or empty.
 * And egg of the chosen creature is shown. If the button is pressed, the MonsterHomeActivity
 * is started and the CreatureObject is created and saved to the InternalStorage.
 * <p>
 * Created by Pawan on 5/12/2017.
 */

public class NameCreatureActivity extends Activity {
    private ImageSwitcher eggSwitcher;
    private Integer[] eggs = {R.drawable.denise_egg,
            R.drawable.pawan_egg, R.drawable.nicole_egg};
    private String[] speciesList = {"denise", "pawan", "nicole"};
    private EditText edtName;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.namecreature);
        Log.d("DEBUG:", "NameCreatureActivity - started.");
        Button btnEnter = (Button) findViewById(R.id.Enter);
        edtName = (EditText) findViewById(R.id.nameEntered);

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


        //this method creates creature object with the given name and species
        // only of the name has the correct length. If not, just a Toast message is shown.
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                if (name.length() <= 0) {
                    Toast.makeText(NameCreatureActivity.this, "Name too short.", Toast.LENGTH_SHORT).show();
                } else if (name.length() > 20) {
                    Toast.makeText(NameCreatureActivity.this, "Name too long.", Toast.LENGTH_SHORT).show();
                } else {
                    CreatureHandler creatureCreated = new CreatureHandler(getApplicationContext());
                    creatureCreated.createObject(name, species);
                    Intent intent = new Intent(getApplicationContext(), MonsterHomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
