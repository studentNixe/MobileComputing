package com.example.baby.firstgame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Pawan on 5/8/2017.
 */

public class ChooseCreatureActivity extends Activity implements View.OnClickListener
{
    //public Button btnNext, btnPre,btnSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecreature);

        /*btnNext = (Button) findViewById(R.id.next);
        btnNext.setOnClickListener(this);

        btnPre = (Button) findViewById(R.id.previous);
        btnPre.setOnClickListener(this);

        btnSelect = (Button) findViewById(R.id.select);
        btnSelect.setOnClickListener(this);*/
    }

    @Override
    public void ClickNext(View view)
    {
        Intent intent = new Intent(this, ChooseCreature2Act.class);
        startActivity(intent);
    }
    public void ClickPrevious(View view)
    {
        Intent intent = new Intent(this, ChooseCreature3Act.class);
        startActivity(intent);
    }

}
