package com.example.baby.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DataObjects.CreatureObject;

/**
 * Created by Denise on 07.05.2017.
 */

public class ObjectHandlerActivity extends Activity implements View.OnClickListener{
    public Button btnCreate;
    public Button btnSave;
    public Button btnLoad;
    public EditText txtName;
    public CreatureObject creature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.objects);

        Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        EditText txtName = (EditText) findViewById(R.id.txtName);
    }

    @Override
    public void onClick(View v) {
        if(btnCreate.isPressed()){
           createObject();
        }else if(btnSave.isPressed()){
            loadObject();
        }else if(btnLoad.isPressed()){
            saveObject();
        }

    }

    public void createObject(){
        String name = txtName.getText().toString();
        creature = new CreatureObject(name);
    }
    public void saveObject(){
        try {
            FileOutputStream fileOut = new FileOutputStream("/saveData/creatureObject.xml");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(creature);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved.");
        }catch(IOException i) {
            i.printStackTrace();
        }

    }
    public void loadObject(){
        try{
            FileInputStream fileIn = new FileInputStream("/saveData/creatureObject.xml");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            try {
                Object obj = in.readObject();
                creature = (CreatureObject) obj;
            } catch (ClassNotFoundException e) {
                System.out.println("No such File exists.");
            }

            System.out.printf("Serialized data was loaded.");
            txtName.setText(creature.getName());


    }catch(IOException i) {
        i.printStackTrace();
    }

    }
}
