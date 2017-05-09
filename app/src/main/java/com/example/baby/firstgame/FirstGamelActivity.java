package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstGamelActivity extends Activity implements View.OnClickListener{
    public Button btn, btn2;

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
            startActivity(new Intent(this, ChooseCreatureActivity.class));
        }else if(v.getId() == R.id.button2){
            startActivity(new Intent(this, ObjectHandlerActivity.class));
        }

    }
}
