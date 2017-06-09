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
        if(creatureHandler.loadObject()){
            countdown();
        }else{
            Log.e("ERROR: ", "Load Error.");
        }

    }

    /**
     * Counts down the creatures' attributes
     */
    public void countdown(){
        if(creatureHandler.getAttrInt("gametime") == 0 && creatureHandler.getAttrInt("hunger") > 100){
            creatureHandler.setAttrInt("age", 1);
            if(creatureHandler.getAttrInt("age") < creatureHandler.getAttrInt("maxAge")) {
                creatureHandler.setAttrInt("gametime", 100);
                activity.setCreatureImg();
            }
        }else {
            creatureHandler.setAttrInt("hunger", -5);
            creatureHandler.setAttrInt("clean", -5);
            creatureHandler.setAttrInt("gametime", -20);
        }

        if(!checkGameover()) {
            creatureHandler.saveObject();
            String message = "Hunger: " + Integer.toString(creatureHandler.getAttrInt("hunger"));
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            gamehandler.postDelayed(this, 30000);
        }
    }

    private boolean checkGameover() {
        if (creatureHandler.getAttrInt("hunger") == 0){
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
