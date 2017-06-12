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
        if(creatureHandler.getAttrInt("gametime") < 0 && creatureHandler.getAttrInt("hunger") > 100){
            creatureHandler.setAttrInt("age", 1);
            if(creatureHandler.getAttrInt("age") < creatureHandler.getAttrInt("maxAge")) {
                activity.setCreatureImg();
                activity.setEvolutionBar();
//                String message = "Species : " + creatureHandler.getAttrString("species")
//                        + "Age : " + Integer.toString(creatureHandler.getAttrInt("age"));
//                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                creatureHandler.setAttrInt("gametime", 100);
            }
        }else {
            creatureHandler.setAttrInt("hunger", -5);
            creatureHandler.setAttrInt("clean", -5);
            creatureHandler.setAttrInt("gametime", -25);
        }

        if(!checkGameover()) {
            creatureHandler.saveObject();
//            String message = "Hunger : " + Integer.toString(creatureHandler.getAttrInt("hunger"))
//                    + ", Gametime : " + Integer.toString(creatureHandler.getAttrInt("gametime"));
//            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            gamehandler.postDelayed(this, 30000);
        }
    }

    private boolean checkGameover() {
        if (creatureHandler.getAttrInt("hunger") < 0){
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
