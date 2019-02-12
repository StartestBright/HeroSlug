package com.jknull.heroslug;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class HeroChoiceScreen extends AppCompatActivity {

    public static Hero ChosenHero;
    Intent gameStartIntent;
    ImageView soldierSelected,RocketManSelected,RainerSelected,loadingPage;
    ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_choose_screen);

        gameStartIntent = new Intent(getApplicationContext(),MainActivity.class);
        gameStartIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        soldierSelected =findViewById(R.id.choose_soldier);
        RocketManSelected = findViewById(R.id.choose_Rocketman);
        RainerSelected = findViewById(R.id.choose_Someone1);


        loadingBar = findViewById(R.id.loadingBar);
        loadingPage = findViewById(R.id.loadingImage);


        setLoadingPage(false);

        soldierSelected.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    setLoadingPage(true);
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
                    setLoadingPage(true);
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

    private void setLoadingPage(boolean b){
        loadingBar.setEnabled(b);
        loadingPage.setEnabled(b);
        if(!b) {
            loadingPage.setVisibility(View.INVISIBLE);
            loadingBar.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout =findViewById(R.id.hero_selection_page);
            for(int i=0;i<linearLayout.getChildCount();i++){
                View child = linearLayout.getChildAt(i);
                child.setVisibility(View.VISIBLE);
            }
        }else{
            LinearLayout linearLayout =findViewById(R.id.hero_selection_page);
            for(int i=0;i<linearLayout.getChildCount();i++){
                View child = linearLayout.getChildAt(i);
                child.setVisibility(View.INVISIBLE);
            }
            loadingBar.setVisibility(View.VISIBLE);
            loadingPage.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setLoadingPage(false);
    }
}
