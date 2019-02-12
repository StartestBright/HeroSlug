package com.jknull.heroslug;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class HeroChooseScreen extends AppCompatActivity {

    public static Hero ChosenHero;
    Intent gameStartIntent;
    ImageView soldierSelected,RocketManSelected,RainerSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_choose_screen);

        gameStartIntent = new Intent(getApplicationContext(),MainActivity.class);
        gameStartIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        soldierSelected =findViewById(R.id.choose_soldier);
        RocketManSelected = findViewById(R.id.choose_Rocketman);
        RainerSelected = findViewById(R.id.choose_Someone1);


        soldierSelected.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    //gameStartIntent.putExtra("SelectedHero","Soldier");
                    ChosenHero = new Soldier(new Point(100,100),getApplicationContext());
                    startActivity(gameStartIntent);
                }
                return true;
            }
        });
        RocketManSelected.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    //gameStartIntent.putExtra("SelectedHero","RocketMan");
                    ChosenHero = new RocketMan(new Point(100,100),getApplicationContext());
                    startActivity(gameStartIntent);
                }
                return true;
            }
        });
        RainerSelected.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    //gameStartIntent.putExtra("SelectedHero","Soldier");
                    //startActivity(gameStartIntent);
                }
                return true;
            }
        });

    }
}
