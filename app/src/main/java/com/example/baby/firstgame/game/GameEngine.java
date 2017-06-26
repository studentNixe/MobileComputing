package com.example.baby.firstgame.game;

import android.os.Handler;
import android.util.Log;

import com.example.baby.firstgame.MonsterHomeActivity;
import com.example.baby.firstgame.data.CreatureHandler;

/**
 * The game engine is started when MonsterHomeActivity is started.
 * run() decreases the stats over time and checks if the creature has dies (stats at zero)
 * increases the age over time.
 *
 *  * Created by Nicole.
 */
public class GameEngine implements Runnable {
    private CreatureHandler creatureHandler;
    private MonsterHomeActivity activity;
    private Handler gameHandler = new Handler();

    /**
     * Constructor
     * @param activity MonsterHomeActivity
     * @param creatureHandler CreatureHandler from MonsterHomeActivity
     */
    public GameEngine(MonsterHomeActivity activity, CreatureHandler creatureHandler) {
        this.activity = activity;
        this.creatureHandler = creatureHandler;
        if(!checkGameOver()) {
            updateAttributes();
        }
    }
    /**
     * Updates the creatures attributes and checks if the game should continue
     * @see #checkGameOver()
     */
    public void updateAttributes(){
        boolean evolve = creatureHandler.getAttrInt("gameTime") == 0
                && creatureHandler.getAttrInt("hunger") >= 80
                && creatureHandler.getAttrInt("clean") >= 80
                && creatureHandler.getAttrInt("happiness") >= 80
                && (creatureHandler.getAttrInt("age") < creatureHandler.getAttrInt("maxAge"));
        if(evolve) {
            creatureHandler.setAttrInt("age", 1);
            creatureHandler.setAttrInt("gameTime", 100);
            activity.setCreatureImg();
            activity.setEvolutionBar();
        } else {
            creatureHandler.setAttrInt("hunger", -5);
            creatureHandler.setAttrInt("clean", -5);
            creatureHandler.setAttrInt("happiness", -5);
            creatureHandler.setAttrInt("gameTime", -10);
        }
       if(!checkGameOver()) {
            creatureHandler.saveObject();
            gameHandler.postDelayed(this, 30000);
        }
    }

    /**
     * Checks if the creature already died
     * @return true the game is over, otherwise false
     */
    private boolean checkGameOver() {
        boolean dead = false;
        try {
            dead = creatureHandler.getAttrInt("hunger") == 0
                    && creatureHandler.getAttrInt("clean") == 0
                    && creatureHandler.getAttrInt("happiness") == 0;
        }catch(Exception e){
            return true;
        }
        if (dead){
            Log.e("DEBUG", "GameOver detected. App will restart.");
            activity.gameOver();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        if(!checkGameOver()) {
            updateAttributes();
        }
    }
}
