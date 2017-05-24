package com.example.baby.firstgame;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.util.Log;
import android.widget.Toast;

import com.example.baby.firstgame.data.CreatureHandler;
import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.data.InternalStorage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static com.example.baby.firstgame.R.id.help;
import static com.example.baby.firstgame.R.id.profile;
import static com.example.baby.firstgame.R.id.settings;


/**
 * Created by baby on 09.05.17.
 */

public class MonsterHomeActivity extends Activity{
    private Button btnItems;
    private Button btnMenu;
    private LinearLayout linearLayout;
    private CreatureObject creature;

    private boolean visible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);

        btnItems = (Button) findViewById(R.id.inventory);
        btnMenu = (Button) findViewById(R.id.menu);
        linearLayout = (LinearLayout) findViewById(R.id.itemList);

        setBtnItems();
        setBtnMenu();

        CreatureHandler handler = new CreatureHandler(this);
        handler.loadObject();
        this.creature = handler.getCreature();
        setCreatureImg(creature);
    }

    public void setBtnItems(){
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

    public void setBtnMenu(){
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

    public void setCreatureImg(CreatureObject creature){
        ImageView creatureImg = (ImageView) findViewById(R.id.creatureImg);

        String fileName = creature.getSpecies() + creature.getAge();
        int id = getResources().getIdentifier(fileName,"drawable", getPackageName());
        creatureImg.setImageDrawable(getResources().getDrawable(id));
    }


}
