package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.baby.firstgame.data.CreatureHandler;

public class FirstGamelActivity extends Activity implements View.OnClickListener{
    public Button btn, btn2;
    CreatureHandler creatureHandler = new CreatureHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            if(creatureHandler.loadObject()==null){
                startActivity(new Intent(this, ChooseCreatureActivity.class));
            }else{
                startActivity(new Intent(this, MonsterHomeActivity.class));
            }
        }else if(v.getId() == R.id.button2){
            startActivity(new Intent(this, ObjectHandlerActivity.class));
        }
    }

}
