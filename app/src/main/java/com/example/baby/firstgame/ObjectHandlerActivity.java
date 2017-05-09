package com.example.baby.firstgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.data.InternalStorage;

/**
 * Created by Denise on 07.05.2017.
 */

public class ObjectHandlerActivity extends Activity implements View.OnClickListener{
    public Button btnCreateAndSave, btnLoad;
    public EditText txtName;
    public CreatureObject creature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.objects);

        btnCreateAndSave = (Button) findViewById(R.id.btnCreateAndSave);
        btnCreateAndSave.setOnClickListener(this);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        txtName = (EditText) findViewById(R.id.txtName);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCreateAndSave){
           createAndSaveObject();
        }else if(v.getId() == R.id.btnLoad){
            loadObject();
        }

    }

    public void createAndSaveObject() {
        String name = txtName.getText().toString();
        creature = new CreatureObject(name);

        Log.d("DEBUG: ","Creature name is "+creature.getName() +" . \n and Age is "+ creature.getAge() + " \n and Health is " +creature.getClean()+ "\n and Hunger is " +creature.getHunger()+" .");
        // The list that should be saved to internal storage.
        List<CreatureObject> entries = new ArrayList<CreatureObject>();
        entries.add(creature);
        // Save the list of entries to internal storage
        Log.d("DEBUG: ", "Created new Creature.");
        try {
            InternalStorage.writeObject(this, "CreatureObject.xml", entries);
        } catch (IOException e) {
            Log.e("ERROR: ", "No Data could be saved.");
        }
    }
    public void loadObject(){
        try{
           // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(this, "CreatureObject.xml");

            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() == null){
                    Log.d("DEBUG: " , "Name is null-");
                }else {
                    Log.d("DEBUG: ", creature.getName());
                }
            }
            txtName.setText(creature.getName());

        } catch (IOException e) {
            Log.e("ERROR: ", "Could not load data.");
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "Class was not found.");
        }

    }
}
