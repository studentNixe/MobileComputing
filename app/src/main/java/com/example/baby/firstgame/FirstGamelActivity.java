package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstGamelActivity extends Activity implements View.OnClickListener{
    public Button btn;
    public Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btn.isPressed()){
            startActivity(new Intent(this, GameActivity.class));
        }else if(btn2.isPressed()){
            startActivity(new Intent(this, ObjectHandlerActivity.class));
        }

    }
}
