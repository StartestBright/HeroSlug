package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Vector;

public class SoldierHealPack implements GameObject{
    Context context;
    private Point healpackPos;
    private int healpackRange = 80;
    private int healpackHeight = 80;
    private float healpackGravity = 9.8f;
    private boolean healpackLanded = false;
    private float healpackVelocityY = 1;
    private Bitmap healPackBitmap;

    private boolean healpackOn = true;
    private long healpackStartTime;


    Rect healpack;
    Hero hero;


    long start,end;
    public SoldierHealPack(Point point,Hero hero,Context context){
        this.context = context;
        healPackBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_healpack_img);
        healpackPos = new Point(point.x,point.y);

        this.hero = hero;
        healpack = new Rect(healpackPos.x,healpackPos.y,healpackPos.x,healpackPos.y);
        //healpack = new Rect(-10,-10,10,10);
        healpack.set(healpackPos.x-healpackRange,healpackPos.y-healpackHeight,healpackPos.x+healpackRange,healpackPos.y+healpackHeight);

        healpackOn = true;
        healpackStartTime = System.currentTimeMillis();


    }
    @Override
    public void draw(Canvas canvas) {
            Paint p = new Paint();
            p.setColor(Color.YELLOW);
            //canvas.drawRect(healpack, p);
            canvas.drawBitmap(healPackBitmap,null,healpack,null);


    }

    @Override
    public void update() {
        Rect tempHero = hero.getHero();
        if(!healpackLanded)
            healpackVelocityY = healpackGravity*1;
        if(healpack.bottom>=MainActivity.SCREEN_HEIGHT-GamePanel.floorHeight){
            healpackVelocityY = 0f;
            healpackLanded = true;
        }
        healpackPos.y += healpackVelocityY;
        healpack.set(healpackPos.x-healpackRange,healpackPos.y-healpackHeight,healpackPos.x+healpackRange,healpackPos.y+healpackHeight);
        if((System.currentTimeMillis()-healpackStartTime)/1000<=5){
            if(tempHero.right>=healpack.left&&tempHero.left<=healpack.right &&tempHero.top<=healpack.bottom &&tempHero.bottom>=healpack.top) {
                GamePanel.HEROHP.getHeal(1);
            }
        }else{
            Soldier soldier = (Soldier) hero;
            soldier.setHealPackNull();
        }

    }

    public void moveByHero(float heroVelocityX){
        healpackPos.x -= heroVelocityX;
    }

}
