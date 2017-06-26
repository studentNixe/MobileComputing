package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.baby.firstgame.data.CreatureHandler;

/**
 * The first Activity after starting the game.
 * If the button is pressed, the Activity checks if a creature already exists or not and switches
 * to MonsterHomeActivity or ChooseCreatureActivity.
 */
public class FirstGameActivity extends Activity implements View.OnClickListener {
    private Button btn;
    private CreatureHandler creatureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "FirstGameActivity - started.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        creatureHandler = new CreatureHandler(this);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            if (!creatureHandler.loadObject()) {
                Log.d("DEBUG", "FirstGameActivity - Unsuccessful load");
                startActivity(new Intent(this, ChooseCreatureActivity.class));
            } else {
                Log.d("DEBUG", "FirstGameActivity - Successful load");
                startActivity(new Intent(this, MonsterHomeActivity.class));
            }
        }
    }

}
