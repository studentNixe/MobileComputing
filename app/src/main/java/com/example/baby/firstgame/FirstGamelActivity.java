package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.baby.firstgame.data.CreatureHandler;

public class FirstGamelActivity extends Activity implements View.OnClickListener{
    public Button btn;
    CreatureHandler creatureHandler = new CreatureHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            if(!creatureHandler.loadObject()){
                Log.d("DEBUG: ","Unsuccessful load");
                startActivity(new Intent(this, ChooseCreatureActivity.class));
            }else{
                Log.d("DEBUG: ","Successful load");
                startActivity(new Intent(this, MonsterHomeActivity.class));
            }
        }
    }

}
