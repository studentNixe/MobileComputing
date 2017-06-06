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
    private Context context;

    public CreatureHandler(Context context) {
        this.context = context;
    }

    /**
     * Saves a creature to the internal storage
     * @param creature being saved
     */
    public void createObject(CreatureObject creature) {
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

    public void removeItemfromInventory(CreatureObject creature, String itemName) {
        creature.getInventory().remove(0);
        Log.d("DEBUG: ","Item removed.");
        saveObject(creature);
    }

    public void addItemtoInventory(CreatureObject creature, String itemName, int index) {
        creature.getInventory().add(index, new SpongeObject("Sponge","Sponge.png",20));
        Log.d("DEBUG: ","Item added.");
        saveObject(creature);
    }

    /**
    *Loads the creature from internal storage and returns it
    */
    public CreatureObject loadObject(){
        CreatureObject obj = null;
        try{
            // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");

            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() != null){
                    obj = creature;
                    Log.d("DEBUG: ", creature.getName());
                }else {
                    Log.d("DEBUG: " , "Name is null-");
                    obj = null;
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
        return obj;
    }

    /**
     * Creature will be saved to internal storage
     * @param creature being saved
     */
    public void saveObject(CreatureObject creature) {
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
}
