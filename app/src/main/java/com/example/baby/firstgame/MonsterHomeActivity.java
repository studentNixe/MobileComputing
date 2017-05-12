package com.example.baby.firstgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;


/**
 * Created by baby on 09.05.17.
 */

public class MonsterHomeActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_home);
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        switch(v){
            case R.id.inventory:
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.itemList);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.menu:
                System.out.println("Menu opened!");
                PopupMenu popup = new PopupMenu(MonsterHomeActivity.this,view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                /*popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });*/
                popup.show();

                break;
            default: System.out.println("Nothing happened!");
        }
    }
}
