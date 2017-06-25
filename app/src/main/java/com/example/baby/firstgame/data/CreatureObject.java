package com.example.baby.firstgame.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denise on 07.05.2017.
 */

public class CreatureObject implements java.io.Serializable{
    //class attributes
    private String[] speciesList = {"denise","pawan","nicole"};
    private int ageMax = 3;
    private int attrMin = 0;
    private int attrMax = 100;

    //object attributes
    private String name;
    private String species;
    private int age;
    private int hunger;
    private int clean;
    private int happiness;
    private int gametime;

    //constructor
    public CreatureObject(String name, String species){
        this.species = species;
        this.name = name;
        this.age = 1;
        this.hunger = 100;
        this.clean = 100;
        this.happiness = 100;
        this.gametime = 100;
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
        if(checkInt(hunger, attrMin, attrMax)) {
            this.hunger = hunger;
        }
    }

    public int getClean() {
        return clean;
    }

    public void setClean(int clean) {
        if(checkInt(clean, attrMin, attrMax)) {
            this.clean = clean;
        }
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        if(checkInt(happiness, attrMin, attrMax)) {
            this.happiness = happiness;
        }
    }

    public int getGametime() {
        return gametime;
    }

    public void setGametime(int gametime) {
        this.gametime = gametime;
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

    /**
     * Checks if the attributes value is in the correct range
     * @param value to be checked
     * @param minv minimum of range
     * @param maxv maximum of range
     * @return true, if it is in the range, else false
     */
    public boolean checkInt(int value, int minv, int maxv){
        if (value >= minv && value <= maxv) {
            return true;
        }
        return false;
    }
}


