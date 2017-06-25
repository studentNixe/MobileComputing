package com.example.baby.firstgame.data;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CreatureHandler class handles all changes of the CreatureObject.
 * createObject, loadObject, saveObject, deleteObject, set/get Attributes
 * Created by Denise on 16.05.2017.
 */
public class CreatureHandler {
    private Context context;
    private CreatureObject creature;

    public CreatureHandler(Context context) {
        this.context = context;
    }

    /**
     * Saves a creature to the internal storage
     *
     * @param name    being saved
     * @param species being saved
     */
    public void createObject(String name, String species) {
        this.creature = new CreatureObject(name, species);
        List<CreatureObject> entries = new ArrayList<CreatureObject>();  // The list that should be saved to internal storage.
        entries.add(creature);                              // Save the list of entries to internal storage
        try {
            InternalStorage.writeObject(context, "CreatureObject.xml", entries);
            Log.d("DEBUG: ", "CreatureHandler - Creature was created successfully:" + creature.toString());
        } catch (IOException e) {
            Log.e("ERROR: ", "CreatureHandler - No Data could be saved.");
        }
    }

    /**
     * Load the CreatureObject from the internal Storage by calling InternalStorage.class
     * If no creature was found, a null object is returned.
     *
     * @return true if creature was loaded successfully.
     */
    public boolean loadObject() {
        this.creature = null;
        try {
            // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");
            for (CreatureObject creature : cachedEntries) {
                this.creature = creature;
                Log.d("DEBUG: ", "CreatureHandler - Creature successfully loaded. The name is: " + creature.getName());
                return true; // returns the first creatureObject that is found.
            }
        } catch (IOException e) {
            Log.d("DEBUG: ", "CreatureHandler - No Object found.");
            return false;
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "CreatureHandler - Class was not found, returned Exception.");
            return false;
        }
        return false;
    }

    /**
     * Creatures attribute values will be increased
     *
     * @param attribute of creature, which should be changed
     * @param value     for increase or decrease
     */
    public void setAttrInt(String attribute, int value) {
        switch (attribute) {
            case "hunger":
                creature.setHunger(creature.getHunger() + (value));
                break;
            case "clean":
                creature.setClean(creature.getClean() + (value));
                break;
            case "happiness":
                creature.setHappiness(creature.getHappiness() + (value));
                break;
            case "gameTime":
                creature.setGameTime(creature.getGameTime() + (value));
                break;
            case "age":
                creature.setAge(creature.getAge() + (value));
                break;
            default:
                Log.e("ERROR: ", "CreatureHandler - Creature value could not be set.");
                break;
        }
    }

    /**
     * Returns int value of a chosen attribute. If the given attribute does not exists, -1 will be
     * returned, forcing the game to go GameOver.
     *
     * @param attribute of creature
     */
    public int getAttrInt(String attribute) {
        switch (attribute) {
            case "hunger":
                return creature.getHunger();
            case "clean":
                return creature.getClean();
            case "happiness":
                return creature.getHappiness();
            case "gameTime":
                return creature.getGameTime();
            case "age":
                return creature.getAge();
            case "maxAge":
                return creature.getAgeMax();
            default:
                Log.e("ERROR: ", "CreatureHandler - Creature value could not be found.");
                break;
        }
        return -1;
    }

    /**
     * Returns String value of a chosen attribute
     *
     * @param attribute of creature
     */
    public String getAttrString(String attribute) {
        switch (attribute) {
            case "name":
                return creature.getName();
            case "species":
                return creature.getSpecies();
            default:
                Log.e("ERROR: ", "CreatureHandler - Creature value could not be found.");
                break;
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
        Log.d("DEBUG: ", "CreatureHandler - Updated creature data.");
        try {
            InternalStorage.writeObject(context, "CreatureObject.xml", entries);
        } catch (IOException e) {
            Log.e("ERROR: ", "CreatureHandler - No Data could be saved.");
        }
    }

    /**
     * Delete the CreatureObject from the local storage and checks if it was deleted.
     * If it was successfully deleted an exception will be fired and caught.
     */
    public void deleteObject() {
        context.deleteFile("CreatureObject.xml");
        this.creature = null;
        Log.e("debug", "CreatureList: "+context.fileList());
        try {
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(context, "CreatureObject.xml");
            Log.e("DEBUG:", "CreatureHandler - Creature was NOT deleted!.");
        } catch (IOException e) {
            Log.d("ERROR: ", "CreatureHandler - No file found. Data was successfully deleted");
        } catch (ClassNotFoundException e) {
            Log.d("Debug: ", "CreatureHandler - No Class Found. Data was successfully deleted.");
        }
    }
}
