package com.example.baby.firstgame;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import android.widget.TextView;
import android.widget.Toast;

import com.example.baby.firstgame.data.CreatureHandler;
import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.game.GameEngine;

import static com.example.baby.firstgame.R.id.help;
import static com.example.baby.firstgame.R.id.profile;
import static com.example.baby.firstgame.R.id.settings;


/**
 * Created by baby on 09.05.17.
 */

public class MonsterHomeActivity extends Activity {
    private Button btnItems;
    private Button btnMenu;
    private TextView nameLabel;
    private ImageView creatureImg;

    private LinearLayout linearLayout;
    private CreatureObject creature;

    CreatureHandler creatureHandler = new CreatureHandler(this);
    //Handler gamehandler = new Handler();

    private boolean visible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);

        btnItems = (Button) findViewById(R.id.inventory);
        btnMenu = (Button) findViewById(R.id.menu);
        linearLayout = (LinearLayout) findViewById(R.id.itemList);
        nameLabel = (TextView) findViewById(R.id.name);

        setBtnItems();
        setBtnMenu();

        this.creature = creatureHandler.loadObject();

        setCreatureImg(creature);
        setItems();
        nameLabel.setText(creature.getName());

        //countdown();
        GameEngine engine = new GameEngine(MonsterHomeActivity.this);
    }


    /**
     * Sets the visibility of the inventory
     */
    protected void setBtnItems(){
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible){
                    linearLayout.setVisibility(View.INVISIBLE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                visible = !visible;
            }
        });
    }

    /**
     * Sets the items
     */
    protected void setItems(){
        ImageView itemEat = (ImageView) findViewById(R.id.eat);
        itemEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatureHandler.changeAttributesValues(creature, "hunger", (+20));
                String message = "Hunger: " + Integer.toString(creature.getHunger());
                Toast.makeText(MonsterHomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Creates the Menu and sets actions when clicking
     */
    protected void setBtnMenu(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.INVISIBLE);
                visible = false;
                PopupMenu popup = new PopupMenu(MonsterHomeActivity.this,v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Dialog menuDialog = new Dialog(MonsterHomeActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

                        switch(item.getItemId()){
                            case profile:
                                Toast.makeText(MonsterHomeActivity.this, "You clicked profile", Toast.LENGTH_SHORT).show();
                                menuDialog.setContentView(R.layout.gameover);
                                break;
                            case settings:
                                Toast.makeText(MonsterHomeActivity.this, "You clicked settings", Toast.LENGTH_SHORT).show();
                                menuDialog.setContentView(R.layout.gameover);
                                break;
                            case help:
                                Toast.makeText(MonsterHomeActivity.this, "You clicked help", Toast.LENGTH_SHORT).show();
                                menuDialog.setContentView(R.layout.gameover);
                                break;
                        }
                        menuDialog.show();
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    /**
     * Sets or reloads the image of the creature
     */
    public void setCreatureImg(CreatureObject creature){
        creatureImg = (ImageView) findViewById(R.id.creatureImg);

        String fileName = creature.getSpecies() + creature.getAge();
        int id = getResources().getIdentifier(fileName,"drawable", getPackageName());
        creatureImg.setImageDrawable(getResources().getDrawable(id));
    }


    //--------------Game Engine-----------------------
    /**
     * Counts down the creatures' attributes

    public void countdown(){
        if(creature.getGametime() == 0 && creature.getHunger() > 100){
//            String message = "Hunger: " + Integer.toString(creature.getHunger());
//            Toast.makeText(MonsterHomeActivity.this, message, Toast.LENGTH_SHORT).show();
            //handler.getCreature().setAge(creature.getAge()+1);
            creature.setAge(creature.getAge()+1);
            if(creature.getAge() < 5) {
                creature.setGametime(100);
                setCreatureImg(creature);
            }
        }else {
            creature.setHunger(creature.getHunger() - 5);
            creature.setClean(creature.getClean() - 5);
            creature.setGametime(creature.getGametime() - 20);
        }
        if(!checkGameover()) {
            String message = "Hunger: " + Integer.toString(creature.getHunger());
            Toast.makeText(MonsterHomeActivity.this, message, Toast.LENGTH_SHORT).show();
            creatureHandler.saveObject(creature);
            gamehandler.postDelayed(this, 30000);
        }
    }

    private boolean checkGameover() {
        if (creature.getHunger() == 0){
            gameOver();
            return true;
        }
        return false;
    }
     */
    public void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
    }


/*    @Override
    public void run() {
        countdown();
    }*/
}
