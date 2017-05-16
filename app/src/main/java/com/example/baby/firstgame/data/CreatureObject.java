package com.example.baby.firstgame.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denise on 07.05.2017.
 */

public class CreatureObject implements java.io.Serializable{
    private String name;
    private int age;
    private int hunger;
    private int clean;
    private List<ItemObject> inventory;

    //constructor
    public CreatureObject(String name){

        this.name = name;
        this.age = 0;
        this.hunger = 100;
        this.clean = 100;
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

    public List<ItemObject> getInventory() {
        return inventory;
    }

    public void setInventory(List<ItemObject> inventory) {
        this.inventory = inventory;
    }


}
