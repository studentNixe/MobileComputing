package com.example.baby.firstgame.game;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.baby.firstgame.MonsterHomeActivity;
import com.example.baby.firstgame.data.CreatureHandler;
import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.data.InternalStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GameEngine implements Runnable {
    CreatureObject creature;
    CreatureHandler creatureHandler;
    MonsterHomeActivity activity;

    Handler gamehandler = new Handler();


    public GameEngine(MonsterHomeActivity activity) {
        this.activity = activity;
        this.creatureHandler = new CreatureHandler(activity);
        this.creature = creatureHandler.loadObject();

        countdown();
    }

    /**
     * Counts down the creatures' attributes
     */
    public void countdown(){
        if(creature.getGametime() == 0 && creature.getHunger() > 100){
//            String message = "Hunger: " + Integer.toString(creature.getHunger());
//            Toast.makeText(MonsterHomeActivity.this, message, Toast.LENGTH_SHORT).show();
            //handler.getCreature().setAge(creature.getAge()+1);
            creature.setAge(creature.getAge()+1);
            if(creature.getAge() < 5) {
                creature.setGametime(100);
                activity.setCreatureImg(creature);
            }
        }else {
            creature.setHunger(creature.getHunger() - 5);
            creature.setClean(creature.getClean() - 5);
            creature.setGametime(creature.getGametime() - 20);
        }
        if(!checkGameover()) {
            creatureHandler.saveObject(creature);
            String message = "Hunger: " + Integer.toString(creature.getHunger());
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            gamehandler.postDelayed(this, 30000);
        }
    }

    private boolean checkGameover() {
        if (creature.getHunger() == 0){
            activity.gameOver();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        countdown();
    }
}
