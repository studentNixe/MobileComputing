package com.example.baby.firstgame.game;

import android.os.Handler;

import com.example.baby.firstgame.MonsterHomeActivity;
import com.example.baby.firstgame.data.CreatureHandler;




public class GameEngine implements Runnable {
    CreatureHandler creatureHandler;
    MonsterHomeActivity activity;

    Handler gamehandler = new Handler();

    /**
     * Constructor
     * @param activity MonsterHomeActivity
     * @param creatureHandler CreatureHandler from MonsterHomeActivity
     */
    public GameEngine(MonsterHomeActivity activity, CreatureHandler creatureHandler) {
        this.activity = activity;
        this.creatureHandler = creatureHandler;
        updateAttributes();

    }

    /**
     * Updates the creatures attributes and
     * checks if the game should continue
     * @see #checkGameover()
     */
    public void updateAttributes(){
        boolean evolve = creatureHandler.getAttrInt("gametime") == 0
                && creatureHandler.getAttrInt("hunger") >= 80
                && creatureHandler.getAttrInt("clean") >= 80
                && creatureHandler.getAttrInt("happiness") >= 80
                && (creatureHandler.getAttrInt("age") < creatureHandler.getAttrInt("maxAge"));
        if(evolve) {
            creatureHandler.setAttrInt("age", 1);
            creatureHandler.setAttrInt("gametime", 100);
            activity.setCreatureImg();
            activity.setEvolutionBar();
        } else {
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

    /**
     * Checks if the creature already died
     * @return true the game is over, otherwise false
     */
    private boolean checkGameover() {
        boolean dead = creatureHandler.getAttrInt("hunger") == 0
                && creatureHandler.getAttrInt("clean") == 0
                && creatureHandler.getAttrInt("happiness") == 0;
        if (dead){
            activity.gameOver();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        updateAttributes();
    }
}
