package com.example.baby.firstgame;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

/**
 * Created by baby on 24.04.17.
 */

public class GameActivity extends Activity implements View.OnClickListener, Runnable{
    private boolean spielLaeuft;
    private int runde;
    private int punkte;
    private int herzchen;
    private int gesammelteHerzchen;
    private int zeit;

    private Random zufallsGenerator = new Random();
    private Handler handler = new Handler();
    private float massstab;
    private ViewGroup spielbereich;

    private static final long HOECHSTALTER_MS = 2000;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        spielbereich = (ViewGroup)findViewById(R.id.spielebereich);
        spielStarten();

    }


    private void spielStarten(){
        spielLaeuft = true;
        runde = 0;
        punkte = 0;
        starteRunde();
    }

    private void starteRunde() {
        runde++;
        herzchen = runde * 10;
        gesammelteHerzchen = 0;
        zeit = 60;
        bildschirmAktualisieren();
        //verzoegert in Warteschlange stellen
        handler.postDelayed(this,1000);
    }

    private void bildschirmAktualisieren() {
        TextView tvPoints = (TextView) findViewById(R.id.points);
        tvPoints.setText(Integer.toString(punkte));

        TextView tvRound = (TextView) findViewById(R.id.round);
        tvRound.setText(Integer.toString(runde));

        TextView tvHits = (TextView) findViewById(R.id.hits);
        tvHits.setText(Integer.toString(gesammelteHerzchen));

        TextView tvTime = (TextView) findViewById(R.id.time);
        tvTime.setText(Integer.toString(zeit));

        massstab = getResources().getDisplayMetrics().density;
    }

    public void herunterzaehlen() {
        zeit --;
        float zufallsZahl = zufallsGenerator.nextFloat();

        if (zufallsZahl < herzchen * 1.5f / 60) {
            einHerzAnzeigen();
        }

        double wahrscheinlichkeit = herzchen * 1.5;
        if (wahrscheinlichkeit > 1){
            einHerzAnzeigen();
            if (zufallsZahl < wahrscheinlichkeit - 1){
                einHerzAnzeigen();
            }
        }else{
            if (zufallsZahl < wahrscheinlichkeit){
                einHerzAnzeigen();
            }
        }
        herzVerschwinden();
        bildschirmAktualisieren();
        if(!pruefeSpielEnde()){
           if(!pruefeRundenEnde())
               handler.postDelayed(this,1000);
        }

    }

    private boolean pruefeRundenEnde() {
        if( gesammelteHerzchen >= herzchen){
            starteRunde();
            return true;
        }
        return false;
    }


    private boolean pruefeSpielEnde() {
        if (zeit == 0 && gesammelteHerzchen < herzchen){
            gameOver();
            return true;
        }
        return false;
    }

    private void gameOver() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.gameover);
        dialog.show();
        spielLaeuft = false;
    }


    private void herzVerschwinden() {
        int nummer = 0;
        while (nummer < spielbereich.getChildCount() ){
            ImageView herzView = (ImageView) spielbereich.getChildAt(nummer);
            Date geburtsdatum = (Date) herzView.getTag(R.id.geburtsdatum);
            long alter = (new Date()).getTime() - geburtsdatum.getTime();

            if (alter > HOECHSTALTER_MS){
                spielbereich.removeView(herzView);
            }else{
                nummer++;
            }
        }


    }

    private void einHerzAnzeigen() {
        int breite = spielbereich.getWidth();
        int hoehe = spielbereich.getHeight();

        int herz_breite = (int)Math.round(massstab*50);
        int herz_hoehe = (int)Math.round(massstab*50);

        int links = zufallsGenerator.nextInt(breite - herz_breite);
        int oben = zufallsGenerator.nextInt(hoehe - herz_hoehe);

        ImageView herzView = new ImageView(this);
        herzView.setImageResource(R.drawable.ic_herz);
        herzView.setOnClickListener(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(herz_breite,herz_hoehe);
        params.leftMargin = links;
        params.topMargin = oben;
        params.gravity = Gravity.TOP + Gravity.LEFT;
        spielbereich.addView(herzView,params);

        herzView.setTag(R.id.geburtsdatum, new Date());
    }

    @Override
    public void onClick(View herzView) {
        gesammelteHerzchen++;
        punkte += 100;
        bildschirmAktualisieren();
        spielbereich.removeView(herzView);

    }

    @Override
    public void run() {
        herunterzaehlen();
    }
}
