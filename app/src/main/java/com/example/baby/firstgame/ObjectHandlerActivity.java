package com.example.baby.firstgame;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import com.example.baby.firstgame.data.CreatureObject;
import com.example.baby.firstgame.data.InternalStorage;

/**
 * Created by Denise on 07.05.2017.
 */

public class ObjectHandlerActivity extends Activity implements View.OnClickListener{
    public Button btnCreateAndSave, btnLoad;
    public EditText txtName;
    public ImageView imageDrag;
    public View dragView;
    public CreatureObject creature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.objects);

        btnCreateAndSave = (Button) findViewById(R.id.btnCreateAndSave);
        btnCreateAndSave.setOnClickListener(this);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        txtName = (EditText) findViewById(R.id.txtName);
        imageDrag = (ImageView) findViewById(R.id.imageDrag);
        imageDrag.setTag("DraggableImage");
        imageDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        Log.d("Debug: ","Action was DOWN");
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
                    case (MotionEvent.ACTION_MOVE) :
                        Log.d("Debug: ","Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        Log.d("Debug: ","Action was UP");
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        Log.d("Debug: ","Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        Log.d("Debug: ","Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default :
                        return false;
                }
            }
        });
        dragView = findViewById(R.id.layoutMain);
        dragView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                float posX= imageDrag.getX();
                float posY= imageDrag.getY();
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
                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("Drag Info: ", "Action is DragEvent.ACTION_DRAG_LOCATION: "
                                +event.getX()+", "+event.getY());
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("Drag Info: ", "ACTION_DROP event");
                        //drag onto location
                        //imageDrag.setX(event.getX()-imageDrag.getHeight()/2);
                        //imageDrag.setY(event.getY()-imageDrag.getWidth()/2);
                        imageDrag.setVisibility(v.VISIBLE);
                        // reset position after drag
                        imageDrag.setX(posX);
                        imageDrag.setX(posY);
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
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCreateAndSave){
           createAndSaveObject();
        }else if(v.getId() == R.id.btnLoad){
            loadObject();
        }
    }

    public void createAndSaveObject() {
        String name = txtName.getText().toString();
        creature = new CreatureObject(name);

        Log.d("DEBUG: ","Creature name is "+creature.getName() +" . \n and Age is "+ creature.getAge() + " \n and Health is " +creature.getClean()+ "\n and Hunger is " +creature.getHunger()+" .");
        // The list that should be saved to internal storage.
        List<CreatureObject> entries = new ArrayList<CreatureObject>();
        entries.add(creature);
        // Save the list of entries to internal storage
        Log.d("DEBUG: ", "Created new Creature.");
        Toast.makeText(getBaseContext(), "File saved successfully!",
                Toast.LENGTH_SHORT).show();

        try {
            InternalStorage.writeObject(this, "CreatureObject.xml", entries);
        } catch (IOException e) {
            Log.e("ERROR: ", "No Data could be saved.");
        }
    }
    public void loadObject(){
        try{
           // Retrieve the list from internal storage
            List<CreatureObject> cachedEntries = (List<CreatureObject>) InternalStorage.readObject(this, "CreatureObject.xml");

            // Display the items from the list retrieved.
            for (CreatureObject creature : cachedEntries) {
                if(creature.getName() != null){
                    this.creature = creature;
                    Log.d("DEBUG: ", creature.getName());
                }else {
                    Log.d("DEBUG: " , "Name is null-");
                }
            }
            try {
                txtName.setText(creature.getName());
            }catch(NullPointerException ex){
                Log.e("ERROR: ", "Data was not found and returned empty.");
            }

        } catch (IOException e) {
            Log.e("ERROR: ", "Could not load data.");
        } catch (ClassNotFoundException e) {
            Log.e("ERROR: ", "Class was not found.");
        }

    }
}
