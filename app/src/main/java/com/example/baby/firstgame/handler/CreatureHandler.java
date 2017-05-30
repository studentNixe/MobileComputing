package com.example.baby.firstgame.handler;

import android.content.Context;
import android.util.Log;

import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.data.InternalStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CreatureHandler {

    /**
     * Creates a creature object and saves it to the internal storage
     * @param name creatures' name
     * @param species creatures’ species
     * @param context application context
     */
    public static void createObject(String name, String species, Context context) {
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


    /**
     *Loads the creature from internal storage
     * @param context application context
     */
    public static CreatureObject loadObject(Context context){
        try{
            // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");
            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() != null){
                    Log.d("DEBUG: ", creature.getName());
                    return creature;
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
        return null;
    }

    /**
     * Creature will be saved to internal storage
     * @param creature
     * @param context
     */
    public static void saveObject(CreatureObject creature, Context context) {
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
