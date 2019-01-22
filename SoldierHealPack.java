package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.RelativeLayout;

public class SoldierHealPack implements GameObject{
    private Point healpackPos;
    private int healpackRange = 80;
    private float healpackGravity = 9.8f;
    private boolean healpackLanded = false;
    private float healpackVelocityY = 1;

    private boolean healpackOn = true;
    private long healpackStartTime;


    Rect healpack;
    Hero hero;


    long start,end;
    public SoldierHealPack(Point point,Hero hero){
        healpackPos = new Point(point.x,point.y);

        this.hero = hero;
        healpack = new Rect(healpackPos.x,healpackPos.y,healpackPos.x,healpackPos.y);
        //healpack = new Rect(-10,-10,10,10);
        healpack.set(healpackPos.x-healpackRange,healpackPos.y+20,healpackPos.x+healpackRange,healpackPos.y+40);

        healpackOn = true;
        healpackStartTime = System.currentTimeMillis();


    }
    @Override
    public void draw(Canvas canvas) {
            Paint p = new Paint();
            p.setColor(Color.YELLOW);
            canvas.drawRect(healpack, p);

    }

    @Override
    public void update() {
        Rect tempHero = hero.getHero();
        if(!healpackLanded)
            healpackVelocityY = healpackGravity*1;
        if(healpackPos.y>=MainActivity.SCREEN_HEIGHT-GamePanel.floorHeight-40){
            healpackVelocityY = 0f;
            healpackLanded = true;
        }
        healpackPos.y += healpackVelocityY;
        healpack.set(healpackPos.x-healpackRange,healpackPos.y+20,healpackPos.x+healpackRange,healpackPos.y+40);
        if((System.currentTimeMillis()-healpackStartTime)/1000<=5){
            if(tempHero.right>=healpack.left&&tempHero.left<=healpack.right &&tempHero.top<=healpack.bottom &&tempHero.bottom>=healpack.top) {
                GamePanel.playerHP.getHeal(1);
            }
        }else{
            Soldier soldier = (Soldier) hero;
            soldier.setHealPackNull();
        }

    }

}
