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
 * The main Activity and Home screen. It shows the creature and a progressBar which shows
 * the creatures age. The inventory and menu can be accessed from here and
 * actions on the creature can be made.
 * <p>
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
    private GestureOverlayView overlayView;
    private LinearLayout linearLayout;

    private CreatureHandler creatureHandler = new CreatureHandler(this);
    private GestureLibrary lib;
    private ScaleGestureDetector scaleGestureDetector;
    private float pinchDetector = 1.f;
    private GameEngine engine;

    private boolean visible = false;
    private boolean power = false;

    /**
     * to make sure, that no old object is shown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);
        Log.d("DEBUG: ", "MonsterHomeActivity - started.");
        btnItems = (Button) findViewById(R.id.inventory);
        btnMenu = (Button) findViewById(R.id.menu);
        linearLayout = (LinearLayout) findViewById(R.id.itemList);
        nameLabel = (TextView) findViewById(R.id.name);
        overlayView = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        creatureImg = (ImageView) findViewById(R.id.creatureImg);
        evolutionBar = (ProgressBar) findViewById(R.id.EvolutionBar);
        scaleGestureDetector = new ScaleGestureDetector(this, new MyPinchListner());
        creatureHandler.loadObject();
        setBtnItems();
        setBtnMenu();
        setEvolutionBar();
        evolutionBar.setMax(creatureHandler.getAttrInt("maxAge"));
        setCreatureImg();
        setItems();
        dragView2 = findViewById(R.id.backgroundLayout);
        dragView2.setOnDragListener(createZigzackGestureListener());
        nameLabel.setText(creatureHandler.getAttrString("name"));
        engine = new GameEngine(this, creatureHandler);
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
                    overlayView.setVisibility(View.INVISIBLE);
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
                overlayView.setVisibility(View.INVISIBLE);
                power = false;
                visible = false;
                PopupMenu popup = new PopupMenu(MonsterHomeActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case profile:
                                startActivity(new Intent(MonsterHomeActivity.this, ProfileActivity.class));
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
     * Sets the items of the inventory
     */
    protected void setItems() {
        itemPlay = (ImageView) findViewById(R.id.play);
        itemPlay.setOnClickListener(createItemClickListener());
        itemEat = (ImageView) findViewById(R.id.eat);
        itemEat.setTag("DraggableDonut");
        itemEat.setOnTouchListener(createItemTouchListener());
        itemClean = (ImageView) findViewById(R.id.clean);
        itemClean.setTag("DraggableBrush");
        itemClean.setOnTouchListener(createItemTouchListener());
    }


    /**
     * This listener creates the inventory menu
     *
     * @return listener
     */
    public View.OnTouchListener createItemTouchListener() {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d("Debug: ", "MonsterHomeActivity - Action was DOWN");
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
                        Log.d("Debug: ", "MonsterHomeActivity - Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d("Debug: ", "MonsterHomeActivity - Action was UP");
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d("Debug: ", "MonsterHomeActivity - Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d("Debug: ", "MonsterHomeActivity - Movement occurred outside bounds " +
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
     * This is the Drag and Drop listener. It listens to every Drag and Drop gesture made.
     * It detects a Zig-Zag gesture of the brush is dragged
     * and a normal drop gesture if the donut is dragged
     *
     * @return listener
     */
    public View.OnDragListener createZigzackGestureListener() {
        final View.OnDragListener listener = new View.OnDragListener() {
            // List of X and Y position for Zigzag path detection
            List<Float> dragMovementX = new ArrayList<Float>();
            List<Float> dragMovementY = new ArrayList<Float>();
            boolean leftTurn = false;
            boolean rightTurn = false;
            int counter = 0;
            View view;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                String e = event.getLocalState().toString();
                if (e.contains("clean")) {
                    view = itemClean;
                } else if (e.contains("eat")) {
                    view = itemEat;
                } else {
                    Log.e("DEBUG", "MonsterHomeActivity - No item dragged");
                }
                float posX = view.getX();
                float posY = view.getY();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Drag Info: ", "MonsterHomeActivity - Action is DragEvent.ACTION_DRAG_STARTED");
                        Log.d("DEBUG:", "MonsterHomeActivity - item is at Position:" + posX + " / " + posY);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("Drag Info: ", "MonsterHomeActivity - Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("Drag Info: ", "MonsterHomeActivity - Action is DragEvent.ACTION_DRAG_EXITED");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        if (view == itemClean) {
                            // Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_LOCATION: "
                            //         + event.getX() + ", " + event.getY());
                            dragMovementX.add(event.getX());
                            dragMovementY.add(event.getY());
                            detectZigzag();
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("Drag Info: ", "MonsterHomeActivity - ACTION_DROP event");
                        if (view == itemEat) {
                            creatureHandler.setAttrInt("hunger", 5);
                            String messageEat = "Donut was delicious : " + Integer.toString(creatureHandler.getAttrInt("hunger"));
                            Toast.makeText(getApplicationContext(), messageEat, Toast.LENGTH_SHORT).show();
                        }
                        creatureHandler.saveObject();
                        view.setX(posX);
                        view.setY(posY);
                        view.setVisibility(v.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("Drag Info: ", "MonsterHomeActivity - Action is DragEvent.ACTION_DRAG_ENDED");
                        leftTurn = false;
                        rightTurn = false;
                        counter = 0;
                        v.setVisibility(View.VISIBLE);
                    default:
                        break;
                }
                return true;
            }

            /**
             * This method detects if a zig zag gesture was made.
             * First it ches if enough entries are inside teh array
             * the booleans RightTurn/LeftTurn and the counter are reset on a ACTION_DROP event.
             */
            private void detectZigzag() {
                if (dragMovementX.size() >= 10) {
                    if (rightTurn) {
                        counter++;
                        if (counter == 2) {
                            Log.d("GESTURE:", "MonsterHomeActivity - Zig-zag pattern found.");
                            creatureHandler.setAttrInt("clean", 5);
                            String messageClean = "Cleaned successfully : " + Integer.toString(creatureHandler.getAttrInt("clean"));
                            Toast.makeText(getApplicationContext(), messageClean, Toast.LENGTH_SHORT).show();
                        }
                        rightTurn = false;
                        leftTurn = false;
                    }
                    //detect if a right run followed a left turn
                    if (leftTurn) {
                        //is this position is smaller than the one from before?
                        if (dragMovementX.get(dragMovementX.size() - 5) < dragMovementX.get(dragMovementX.size() - 10)) {
                            if (dragMovementX.size() >= 10) {
                                // is it bigger now?
                                if (dragMovementX.get(dragMovementX.size() - 1) > dragMovementX.get(dragMovementX.size() - 5)) {
                                    rightTurn = true;
                                }
                            }
                        }
                    }
                    //detect a right turn: is the position larger than the one from before?
                    if (dragMovementX.get(dragMovementX.size() - 5) > dragMovementX.get(dragMovementX.size() - 10)) {
                        if (dragMovementX.size() >= 10) {
                            // is it smaller now?
                            if (dragMovementX.get(dragMovementX.size() - 1) < dragMovementX.get(dragMovementX.size() - 5)) {
                                leftTurn = true;
                            }
                        }
                    }
                }
            }
        };
        return listener;
    }

    /**
     * This listener creates the functions for the crystal click item
     *
     * @return listener
     */
    private View.OnClickListener createItemClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power = !power;
                if (power) {
                    play();
                    Toast.makeText(getApplicationContext(), "Crystal power turned on", Toast.LENGTH_SHORT).show();
                } else {
                    overlayView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Crystal power turned off", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return listener;
    }

    /**
     * The gestures(from GestureBuilder)are loaded and when the
     * gestures are detected, the creatures happiness changes
     */
    private void play() {
        lib = GestureLibraries.fromRawResource(this, R.raw.happygestures);
        if (!lib.load()) {
            finish();
            Log.e("DEBUG", "MonsterHomeActivity - Could not load gesture library.");
        }
        overlayView.setVisibility(View.VISIBLE);
        overlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                ArrayList<Prediction> predictionArrayList = lib.recognize(gesture);
                for (Prediction prediction : predictionArrayList) {
                    if (prediction.score > 1.0) {
                        Log.d("GESTURE", "MonsterHomeActivity - " + prediction.name);
                        Log.d("GESTURE", "MonsterHomeActivity - " + prediction.score + "");
                        if (prediction.name.matches("power") || prediction.name.matches("power2")) {
                            creatureHandler.setAttrInt("happiness", 5);
                            String messagePower = "Crystal enchanted your creature: " + Integer.toString(creatureHandler.getAttrInt("happiness"));
                            Toast.makeText(getApplicationContext(), messagePower, Toast.LENGTH_LONG).show();
                        } else if (prediction.name.matches("Xpower") || prediction.name.matches("Xpower2")) {
                            creatureHandler.setAttrInt("happiness", 10);
                            String messagePower = "Crystal enchanted your creature: " + Integer.toString(creatureHandler.getAttrInt("happiness"));
                            Toast.makeText(getApplicationContext(), messagePower, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    /**
     * Sets or reloads the image of the creature
     */
    public void setCreatureImg() {
        try {
            String fileName = creatureHandler.getAttrString("species") + creatureHandler.getAttrInt("age");
            int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
            creatureImg.setImageDrawable(getResources().getDrawable(id));
        } catch (Exception e) {
            Log.e("DEBUG", "MonsterHomeActivity - Image not found.");
            creatureImg.setImageResource(R.drawable.ghost);
        }
    }

    /**
     * AlertDialog to warn the user when they clicked on "New Game"
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

    /**
     * Closes this activity and deletes the creature
     */
    public void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
        creatureHandler.deleteObject();
        startActivity(new Intent(this, FirstGameActivity.class));
        dialog.dismiss();
        this.finish();
    }

    /**
     * Sets evolutionBar
     */
    public void setEvolutionBar() {

        evolutionBar.setProgress(creatureHandler.getAttrInt("age"));
    }

    /*
 * GestureDetector Methods start here.
 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    //this is a method from scaleGestureDetector which detects the pinch action
    //and decides if it is pinch in or pinch out, in both case it opens the profile activity
    public class MyPinchListner extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            pinchDetector *= detector.getScaleFactor();
            startActivity(new Intent(MonsterHomeActivity.this, ProfileActivity.class));
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

}
