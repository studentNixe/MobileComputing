package com.example.baby.firstgame.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denise on 07.05.2017.
 */

public class CreatureObject implements java.io.Serializable{
    //class attributes
    private String[] speciesList = {"denise","pawan","nicole"};
    private int ageMax = 4;
    private int attributesMin = 0;
    private int attributesMax = 100;

    //object attributes
    private String name;
    private String species;
    private int age;
    private int hunger;
    private int clean;
    private int happiness;
    private int gametime;
    private List<ItemObject> inventory;

    //constructor
    public CreatureObject(String name, String species){
        this.species = species;
        this.name = name;
        this.age = 1;
        this.hunger = 100;
        this.clean = 100;
        this.happiness = 100;
        this.gametime = 100;
        this.inventory = new ArrayList<ItemObject>();
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if( age <= ageMax)
            this.age = age;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getClean() {
        return clean;
    }

    public void setClean(int clean) {
        this.clean = clean;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getGametime() {
        return gametime;
    }

    public void setGametime(int gametime) {
        this.gametime = gametime;
    }

    public List<ItemObject> getInventory() {
        return inventory;
    }

    public void setInventory(List<ItemObject> inventory) {
        this.inventory = inventory;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public boolean checkValue(int value, int minv, int maxv){
        if (value >= minv && value <= maxv) {
            return true;
            //if is smaller = 0
            //if is bigger = 100
        }
        return false;
    }
}


