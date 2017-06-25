package com.example.baby.firstgame.game;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.baby.firstgame.MonsterHomeActivity;
import com.example.baby.firstgame.data.CreatureHandler;




public class GameEngine implements Runnable {
    CreatureHandler creatureHandler;
    MonsterHomeActivity activity;

    Handler gamehandler = new Handler();


    public GameEngine(MonsterHomeActivity activity, CreatureHandler creatureHandler) {
        this.activity = activity;
        this.creatureHandler = creatureHandler;
        countdown();

    }

    /**
     * Counts down the creatures' attributes
     */
    public void countdown(){
        if(creatureHandler.getAttrInt("gametime") <= 0 && creatureHandler.getAttrInt("hunger") >= 80
                && creatureHandler.getAttrInt("clean") >= 80
                && creatureHandler.getAttrInt("happiness") >= 80) {
            creatureHandler.setAttrInt("age", 1);
            if(creatureHandler.getAttrInt("age") < creatureHandler.getAttrInt("maxAge")) {
                activity.setCreatureImg();
                activity.setEvolutionBar();
                creatureHandler.setAttrInt("gametime", 100);
            }
        }else {
            creatureHandler.setAttrInt("hunger", -5);
            creatureHandler.setAttrInt("clean", -5);
            creatureHandler.setAttrInt("happiness", -5);
            creatureHandler.setAttrInt("gametime", -10);
        }
       if(!checkGameover()) {
            creatureHandler.saveObject();
            gamehandler.postDelayed(this, 30000);
        }
    }

    private boolean checkGameover() {
        if (creatureHandler.getAttrInt("hunger") == 0 && creatureHandler.getAttrInt("clean") == 0){
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
