package com.example.baby.firstgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baby.firstgame.data.CreatureHandler;
import com.example.baby.firstgame.game.GameEngine;

import java.util.ArrayList;
import java.util.List;

import static com.example.baby.firstgame.R.id.newGame;
import static com.example.baby.firstgame.R.id.profile;

/**
 * Created by nicole on 09.05.17.
 */

public class MonsterHomeActivity extends Activity {
    private Button btnItems;
    private Button btnMenu;
    private TextView nameLabel;
    private ImageView creatureImg;

    private ProgressBar evolutionBar;

    private ImageView itemEat;
    private ImageView itemPlay;
    private ImageView itemClean;
    private View dragView2;
    private GestureOverlayView gesture;

    private LinearLayout linearLayout;

    CreatureHandler creatureHandler = new CreatureHandler(this);
    GestureLibrary lib;
    ScaleGestureDetector scaleGestureDetector;
    private float pinchDetector = 1.f;

    private boolean visible = false;
    private boolean power = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);
        Log.d("DEBUG: ","Creating MonsterHome");
        btnItems = (Button) findViewById(R.id.inventory);
        btnMenu = (Button) findViewById(R.id.menu);
        linearLayout = (LinearLayout) findViewById(R.id.itemList);
        nameLabel = (TextView) findViewById(R.id.name);
        gesture = (GestureOverlayView) findViewById(R.id.gestureOverlay);

        evolutionBar = (ProgressBar) findViewById(R.id.EvolutionBar);
        scaleGestureDetector = new ScaleGestureDetector(this, new MyPinchListner());

        creatureHandler.loadObject();


        setBtnItems();
        setBtnMenu();
        setEvolutionBar();
        setCreatureImg();
        setItems();

        dragView2 = (View) findViewById(R.id.backgroundLayout);
        dragView2.setOnDragListener(createZigzackGestureListener());

        createDoubleTapListener(creatureImg);

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
                visible = !visible;
                if (visible) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.INVISIBLE);
                    gesture.setVisibility(View.INVISIBLE);
                    power = false;
                }

            }
        });
    }

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
                        switch (item.getItemId()) {
                            case profile:
                                startActivity(new Intent(MonsterHomeActivity.this, CreatureProfileActivity.class));
                                break;
                            case newGame:
                                alertNewGame();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    /**
     * Sets the items
     */
    protected void setItems() {
        itemPlay = (ImageView) findViewById(R.id.play);
        itemPlay.setOnClickListener(createItemClickListener());
        itemEat = (ImageView) findViewById(R.id.eat);
        itemEat.setTag("DraggableDonut");
        itemEat.setOnTouchListener(createItemTouchListener());
        itemClean = (ImageView)  findViewById(R.id.clean);
        itemClean.setTag("DraggableBrush");
        itemClean.setOnTouchListener(createItemTouchListener());
    }


    /**
     * This listener creates the inventory menu
     * @return listener
     */
    public View.OnTouchListener createItemTouchListener(){
        View.OnTouchListener listener = new View.OnTouchListener() {
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
        };
        return listener;
    }

    /**
     * This listener returns true if a zig-zag pattern is detected
     * @return listener
     */
    public View.OnDragListener createZigzackGestureListener(){
        final View.OnDragListener listener = new View.OnDragListener() {
            // List of X and Y position for gesture detection
            List<Float> dragMovementX = new ArrayList<Float>();
            List<Float> dragMovementY = new ArrayList<Float>();
            boolean leftTurn = false;
            boolean rightTurn = false;
            int counter = 0;
            View vew;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                String e = event.getLocalState().toString();
                if(e.contains("clean")){
                    vew = itemClean;
                }else if(e.contains("eat")){
                    vew = itemEat;
                }else{
                    //Log.e("DEBUG", "No item dragged");
                }
                int action = event.getAction();
                float posX = vew.getX();
                float posY = vew.getY();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_STARTED");
                        Log.d("DEBUG:", "item is at Position:" + posX + " / " + posY);

                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_EXITED");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        if(vew == itemClean) {
                            // Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_LOCATION: "
                            //         + event.getX() + ", " + event.getY());
                            dragMovementX.add(event.getX());
                            dragMovementY.add(event.getY());
                            detectZigzag();
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("Drag Info: ", "ACTION_DROP event");
                        if(vew == itemEat){
                            creatureHandler.setAttrInt("hunger", 5);
                            String messageEat = "Donut was delicious : " + Integer.toString(creatureHandler.getAttrInt("hunger"));
                            Toast.makeText(getApplicationContext(), messageEat, Toast.LENGTH_SHORT).show();
                        }
                        creatureHandler.saveObject();
                        vew.setX(posX);
                        vew.setY(posY);
                        vew.setVisibility(v.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_ENDED");
                        leftTurn = false;
                        rightTurn = false;
                        counter = 0;
                        v.setVisibility(View.VISIBLE);
                    default:
                        break;
                }
                return true;
            }
            private void detectZigzag(){
                if (dragMovementX.size() >= 10) {
                    //detect if the movement was right - left - right
                    if (rightTurn) {
                        //Log.d("TEST:", "MOVE WAS TO THE RIGHT, TO THE LEFT AND NOW TO THE RIGHT AGAIN.");
                        counter++;
                        if (counter == 2) {
                            Log.d("TEST:", "Zig-zag pattern found.");
                            creatureHandler.setAttrInt("clean", 5);
                            String messageClean = "Cleaned successfully : " + Integer.toString(creatureHandler.getAttrInt("clean"));
                            Toast.makeText(getApplicationContext(), messageClean, Toast.LENGTH_SHORT).show();
                        }
                        rightTurn = false;
                        leftTurn = false;
                    }
                    //detect if a right run follows a left turn
                    if (leftTurn) {
                        if (dragMovementX.get(dragMovementX.size() - 5) < dragMovementX.get(dragMovementX.size() - 10)) {
                            //if this position is smaller than the one from before
                            //Log.d("TEST:", "Move was to the left.");
                            if (dragMovementX.size() >= 10) {  // is the List long enough?
                                if (dragMovementX.get(dragMovementX.size() - 1) > dragMovementX.get(dragMovementX.size() - 5)) {
                                    //did it turn left before?
                                    //Log.d("TEST:", "MOVE IS RIGHT NOW.");
                                    rightTurn = true; //gesture left turn detected!
                                }
                            }
                        }
                    }
                    //detect a right turn
                    if (dragMovementX.get(dragMovementX.size() - 5) > dragMovementX.get(dragMovementX.size() - 10)) {
                        //if this position is larger than the one from before
                        //Log.d("TEST:", "Move was to the right.");
                        if (dragMovementX.size() >= 10) {  // is the List long enough?
                            if (dragMovementX.get(dragMovementX.size() - 1) < dragMovementX.get(dragMovementX.size() - 5)) {
                                //did it turn left before?
                                //Log.d("TEST:", "MOVE IS LEFT NOW.");
                                leftTurn = true; //gesture left turn detected!
                            }
                        }
                    }
                }
            }
        };
        return listener;
    }

    /**
     * This listener creates the functions for the clickitems
     * @return listener
     */
    private View.OnClickListener createItemClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power = !power;
                if(power){
                    play();
                    Toast.makeText(getApplicationContext(), "Crystal power turned on", Toast.LENGTH_SHORT).show();
                }else{
                    gesture.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Crystal power turned off", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return listener;
    }
    /**
     * This listener reacts to doubletaps on the image
     * @return listener
     */
    public void createDoubleTapListener(View view) {
        final GestureDetector gd = new GestureDetector(view.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("OnDoubleTapListener:", " onDoubleTap");
                return true;
            }

        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("OnDoubleTapListener:", " onDoubleTap");
                return gd.onTouchEvent(event);
            }
        });
    }


    private void play() {
        lib = GestureLibraries.fromRawResource(this, R.raw.happygestures);
        if(!lib.load()) {
            finish();
            Log.e("MonsterHomeActivity", "Could not load gesture library.");
        }
        gesture.setVisibility(View.VISIBLE);
        gesture.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                ArrayList<Prediction> predictionArrayList = lib.recognize(gesture);
                Log.d("MonsterHomeActivity", "Size array" + predictionArrayList.size());
                for(Prediction prediction : predictionArrayList) {
                    Log.d("GESTURE", prediction.name);
                    Log.d("GESTURE", prediction.score + "");
                    if (prediction.score > 1.0) {
                        if (prediction.name.matches("power")){
                            creatureHandler.setAttrInt("happiness", 5);
                            String messagePower = "Crystal enchanted your creature: " + Integer.toString(creatureHandler.getAttrInt("happiness"));
                            Toast.makeText(getApplicationContext(), messagePower, Toast.LENGTH_LONG).show();
                        }else if(prediction.name.matches("Xpower")){
                            creatureHandler.setAttrInt("happiness", 10);
                            String messagePower = "Crystal enchanted your creature: " + Integer.toString(creatureHandler.getAttrInt("happiness"));
                            Toast.makeText(getApplicationContext(), messagePower, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

//----------------------------------------------------------


    @Override
    public boolean onTouchEvent(MotionEvent event){
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    //this is a method from scaleGestureDetector which detects the pinch action
    //and decides if it is pinch in or pinch out, in both case it opens the profile activity
    public class MyPinchListner extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            pinchDetector *= detector.getScaleFactor();
            //pinch/zooming out movement
                startActivity(new Intent(MonsterHomeActivity.this, CreatureProfileActivity.class));
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector){
            return true;
        }
        @Override
        public void onScaleEnd(ScaleGestureDetector detector){}

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

    /**
     * AlertDialog
     */
    public void alertNewGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MonsterHomeActivity.this);
        builder.setTitle("New Game")
            .setMessage("If you start a new game, your current game will be deleted. Are you sure?")
            .setPositiveButton("Yes, delete it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameOver();
            }
        })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
        creatureHandler.deleteObject();
        startActivity(new Intent(this, FirstGamelActivity.class));
        dialog.dismiss();
        this.finish();
    }
    /**
     * Sets evolutionBar
     */
    public void setEvolutionBar(){
        evolutionBar.setMax(creatureHandler.getAttrInt("maxAge"));
        evolutionBar.setProgress(creatureHandler.getAttrInt("age"));
    }
}
