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
    CreatureObject creature;

    public CreatureHandler(Context context) {
        this.context = context;
    }

    /**
     * Saves a creature to the internal storage
     * @param name being saved
     * @param species being saved
     */
    public void createObject(String name, String species) {
        this.creature = new CreatureObject(name, species);
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
/*
    public void removeItemfromInventory(CreatureObject creature, String itemName) {
        creature.getInventory().remove(0);
        Log.d("DEBUG: ","Item removed.");
        saveObject();
    }

    public void addItemtoInventory(CreatureObject creature, String itemName, int index) {
        creature.getInventory().add(index, new SpongeObject("Sponge","Sponge.png",20));
        Log.d("DEBUG: ","Item added.");
        saveObject();
    }
*/
    /**
    *Loads the creature from internal storage and returns it
    */
    public boolean loadObject(){
        this.creature = null;
        try{
            // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");

            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() != null){
                    this.creature = creature;
                    Log.d("DEBUG: ", creature.getName());
                    return true;
                }else {
                    Log.d("DEBUG: " , "Name is null-");
                    this.creature = null;
                }
            }
            try {
                // DATA THAT SHOULD BE TAKEN OUT OF THE OBJECT
            }catch(NullPointerException ex){
                Log.e("ERROR: ", "Data was not found and returned empty.");
            }
        } catch (IOException e) {
            Log.e("ERROR: ", "Could not load data.");
            return false;
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "Class was not found.");
        }
        return false;
    }

    /**
     * Creatures attribute values will be increased
     * @param attribute of creature, which should be changed
     * @param value for increase or decrease
     */
    public void setAttrInt(String attribute, int value){
        switch(attribute){
            case "hunger":
                creature.setHunger(creature.getHunger() + (value));
                break;
            case "clean":
                creature.setClean(creature.getClean() + (value));
                break;
            case "happiness":
                creature.setHappiness(creature.getHappiness() + (value));
                break;
            case "gametime":
                creature.setGametime(creature.getGametime() + (value));
                break;
            case "age":
                creature.setAge(creature.getAge() + (value));
                break;
            default:
                Log.e("ERROR: ", "Creature value could not be set.");

        }
        saveObject();
    }

    /**
     * Returns int value of a chosen attribute
     * @param attribute of creature
     */
    public int getAttrInt(String attribute){
        switch(attribute){
            case "hunger":
                return creature.getHunger();
            case "clean":
                return creature.getClean();
            case "happiness":
                return creature.getHappiness();
            case "gametime":
                return creature.getGametime();
            case "age":
                return creature.getAge();
            case "maxAge":
                return creature.getAgeMax();
            default:
                Log.e("ERROR: ", "Creature value could not be found.");

        }
        return -1;
    }

    /**
     *  Returns String value of a chosen attribute
     * @param attribute of creature
     */
    public String getAttrString(String attribute){
        switch(attribute){
            case "name":
                return creature.getName();
            case "species":
                return creature.getSpecies();
            default:
                Log.e("ERROR: ", "Creature value could not be found.");
        }
        return null;
    }

    /**
     * Creature will be saved to internal storage
     */
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

    /**
        Delete the Object from the local storage
     */
    public void deleteObject() {
        context.deleteFile("CreatureObject.xml");
        Log.d("DEBUG: ", "Deleted Object.");
        //check if the creature was deleted
        try {
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "Data was successfully deleted.");
        } catch (IOException e) {
            Log.e("ERROR: ", "IOexception Happened.");
        }
    }
}
