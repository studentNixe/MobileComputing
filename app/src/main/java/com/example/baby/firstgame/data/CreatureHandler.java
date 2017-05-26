package com.example.baby.firstgame.data;


import android.content.Context;
import android.util.Log;

import com.example.baby.firstgame.data.items.SpongeObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denise on 16.05.2017.
 */

public class CreatureHandler {

    private CreatureObject creature;
    private Context context;

    public CreatureHandler(Context context) {
        this.context = context;
    }

    public void createObject(String name, String species) {
        CreatureObject creature = new CreatureObject(name, species);
        //creature.getInventory().add(0,new SpongeObject("Schwamm","star_icon.png",1));
        Log.d("DEBUG: ","Creature name is "+creature.getName() +" . \n and Age is "+ creature.getAge() + " \n and Health is " +creature.getClean()+ "\n and Hunger is " +creature.getHunger()+" .");
        // The list that should be saved to internal storage.
        List<CreatureObject> entries = new ArrayList<CreatureObject>();
        entries.add(creature);
        // Save the list of entries to internal storage
        Log.d("DEBUG: ", "Created new Creature.");
        try {
            InternalStorage.writeObject(context, "CreatureObject.xml", entries);
        } catch (IOException e) {
            Log.e("ERROR: ", "No Data could be saved.");
        }
    }
    public void removeItemfromInventory(String itemName) {
        creature.getInventory().remove(0);
        Log.d("DEBUG: ","Item removed.");
        saveObject();
    }
    public void addItemtoInventory(String itemName, int index) {
        creature.getInventory().add(index, new SpongeObject("Sponge","Sponge.png",20));
        Log.d("DEBUG: ","Item added.");
        saveObject();
    }

    public void loadObject(){
        try{
            // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");
            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() != null){
                    this.creature = creature;
                    Log.d("DEBUG: ", creature.getName());
                }else {
                    Log.d("DEBUG: " , "Name is null-");
                }
            }
            try {
                // DATA THAT SHOULD BE TAKEN OUT OF THE OBJECT
            }catch(NullPointerException ex){
                Log.e("ERROR: ", "Data was not found and returned empty.");
            }

        } catch (IOException e) {
            Log.e("ERROR: ", "Could not load data.");
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "Class was not found.");
        }

    }

    public void saveObject() {
        List<CreatureObject> entries = new ArrayList<CreatureObject>();
        entries.add(creature);
        // Save the list of entries to internal storage
        Log.d("DEBUG: ", "Saved new creature Object.");
        try {
            InternalStorage.writeObject(context, "CreatureObject.xml", entries);
        } catch (IOException e) {
            Log.e("ERROR: ", "No Data could be saved.");
        }
    }

    public CreatureObject getCreature() {
        return creature;
    }

    public void setCreature(String name, String species) {
        createObject(name,species);
    }
}
