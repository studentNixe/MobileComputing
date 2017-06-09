package com.example.baby.firstgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baby.firstgame.data.CreatureHandler;
import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.game.GameEngine;

import java.util.ArrayList;
import java.util.List;

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
    private ProgressBar evolutionBar;

    private ImageView itemEat;
    private View dragView;
    private View dragView2;

    private LinearLayout linearLayout;

    CreatureHandler creatureHandler = new CreatureHandler(this);

    private boolean visible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);
        Log.d("DEBUG: ","Creating MonsterHome");
        btnItems = (Button) findViewById(R.id.inventory);
        btnMenu = (Button) findViewById(R.id.menu);
        linearLayout = (LinearLayout) findViewById(R.id.itemList);
        nameLabel = (TextView) findViewById(R.id.name);
        evolutionBar = (ProgressBar) findViewById(R.id.EvolutionBar);
        Log.d("DEBUG: ","Creating MonsterHome");

        creatureHandler.loadObject();


        setBtnItems();
        setBtnMenu();
        setEvolutionBar();
        setCreatureImg();
        setItems();

        nameLabel.setText(creatureHandler.getAttrString("name"));

        GameEngine engine = new GameEngine(MonsterHomeActivity.this,creatureHandler);
    }


    /**
     * Sets the visibility of the inventory
     */
    protected void setBtnItems() {
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible) {
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
    protected void setItems() {
        itemEat = (ImageView) findViewById(R.id.eat);
        itemEat.setTag("DraggableImage");
        itemEat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d("Debug: ", "Action was DOWN");
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                view);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            view.startDragAndDrop(data, shadowBuilder, view, 0);
                        } else {
                            view.startDrag(data, shadowBuilder, view, 0);
                        }
                        view.setVisibility(View.INVISIBLE);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d("Debug: ", "Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d("Debug: ", "Action was UP");
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d("Debug: ", "Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d("Debug: ", "Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
 /**       dragView = findViewById(R.id.creatureImg);
        dragView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                float posX = itemEat.getX();
                float posY = itemEat.getY();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_STARTED");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_EXITED");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_LOCATION: "
                                + event.getX() + ", " + event.getY());
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("Drag Info: ", "ACTION_DROP event");
                        //drag onto location
                        //imageDrag.setX(event.getX()-imageDrag.getHeight()/2);
                        //imageDrag.setY(event.getY()-imageDrag.getWidth()/2);
                        itemEat.setVisibility(v.VISIBLE);
                        // reset position after drag
                        itemEat.setX(posX);
                        itemEat.setX(posY);
                        //
                        creature.setHunger(creature.getHunger() + 20);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENDED");
                        v.setVisibility(View.VISIBLE);
                    default:
                        break;
                }
                return true;
            }
        });
  */
        //----------------------------------------------------------
        dragView2 = (View) findViewById(R.id.backgroundLayout);
        dragView2.setOnDragListener(new View.OnDragListener() {
            // List of X and Y position for gesture detection
            List<Float> dragMovementX = new ArrayList<Float>();
            List<Float> dragMovementY = new ArrayList<Float>();
            boolean leftTurn = false;
            boolean rightTurn = false;
            int counter=0;
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                float posX = itemEat.getX();
                float posY = itemEat.getY();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_STARTED");
                        Log.e("DEBUG:", "itemEat is at Position:"+posX+" / "+posY);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_EXITED");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                       // Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_LOCATION: "
                       //         + event.getX() + ", " + event.getY());
                        dragMovementX.add(event.getX());
                        dragMovementY.add(event.getY());
                        if(dragMovementX.size() >=10){
                            //detect if the movement was right - left - right
                            if(rightTurn){
                                //Log.d("TEST:", "MOVE WAS TO THE RIGHT, TO THE LEFT AND NOW TO THE RIGHT AGAIN.");
                                counter++;
                                if(counter == 2){
                                    Log.d("TEST:", "MOVE WAS TO THE RIGHT, TO THE LEFT AND NOW TO THE RIGHT AGAIN. !!!!!!!!!APPEARED TWICE!!!!!!!!");
                                }
                                rightTurn= false;
                                leftTurn = false;
                            }
                            //detect if a right run follows a left turn
                            if(leftTurn){
                                if(dragMovementX.get(dragMovementX.size()-5) < dragMovementX.get(dragMovementX.size()-10)){
                                    //if this position is smaller than the one from before
                                    //Log.d("TEST:", "Move was to the left.");
                                    if(dragMovementX.size() >=10){  // is the List long enough?
                                        if(dragMovementX.get(dragMovementX.size()-1) > dragMovementX.get(dragMovementX.size()-5)){
                                            //did it turn left before?
                                            //Log.d("TEST:", "MOVE IS RIGHT NOW.");
                                            rightTurn = true; //gesture left turn detected!
                                        }
                                    }

                                }
                            }
                            //detect a right turn
                            if(dragMovementX.get(dragMovementX.size()-5) > dragMovementX.get(dragMovementX.size()-10)){
                                //if this position is larger than the one from before
                                //Log.d("TEST:", "Move was to the right.");
                                if(dragMovementX.size() >=10){  // is the List long enough?
                                    if(dragMovementX.get(dragMovementX.size()-1) < dragMovementX.get(dragMovementX.size()-5)){
                                        //did it turn left before?
                                        //Log.d("TEST:", "MOVE IS LEFT NOW.");
                                        leftTurn = true; //gesture left turn detected!
                                    }
                                }
                            }
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("Drag Info: ", "ACTION_DROP event");
                        itemEat.setX(posX);
                        itemEat.setY(posY);
                        itemEat.setVisibility(v.VISIBLE);
                        creatureHandler.setAttrInt("hunger",20);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENDED");
                        //Log.d("Message:" , "X: "+itemEat.getX());
                        //Log.d("Message:" ,"Y: "+itemEat.getY());
                        leftTurn = false;
                        rightTurn = false;
                        counter=0;
                        v.setVisibility(View.VISIBLE);
                    default:
                        break;
                }
                return true;
            }
        });
    }
//----------------------------------------------------------
    /**
     * Creates the Menu and sets actions when clicking
     */
    protected void setBtnMenu() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.INVISIBLE);
                visible = false;
                PopupMenu popup = new PopupMenu(MonsterHomeActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Dialog menuDialog = new Dialog(MonsterHomeActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

                        switch (item.getItemId()) {
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
    public void setCreatureImg(){
        creatureImg = (ImageView) findViewById(R.id.creatureImg);

        String fileName = creatureHandler.getAttrString("species") + creatureHandler.getAttrInt("age");
        int id = getResources().getIdentifier(fileName,"drawable", getPackageName());
        creatureImg.setImageDrawable(getResources().getDrawable(id));
    }

    public void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
    }

    /**
     * Sets evolutionBar
     */
    public void setEvolutionBar(){
        //evolutionBar.setVisibility(View.VISIBLE);
        evolutionBar.setMax(creatureHandler.getAttrInt("maxAge"));
        evolutionBar.setProgress(creatureHandler.getAttrInt("age"));
    }
}
