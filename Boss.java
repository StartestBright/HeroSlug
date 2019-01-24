package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class Boss extends Enemy {
    private ArrayList<BossGunShot > bossBullets;
    private long flashStartedTime;
    private long flashDelay = 20;
    private boolean canFlash = true;
    private boolean bossActive = false;


    public Boss(Context context, Point p, int enemyIndex) {
        super(context,p,enemyIndex);
        curHp = 1000;
        enemySize = 50;
    }

    @Override
    public String getCharacterTag() {
        return "Boss";
    }

    @Override
    public void jump() {

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){

            int x = GamePanel.HERO.playerPos.x-enemyPos.x;
            int y = GamePanel.HERO.playerPos.y-enemyPos.y;

            float temp = (float) (Math.atan2(x, y) + Math.PI + Math.PI / 2);
            temp *= -1;
            enemyGunShots.add(new BossGunShot( context, (float) Math.cos(temp), -(float) Math.sin(temp),enemyPos.x, enemyPos.y,2));
            bulletIndex++;
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        Paint p = new Paint();
        p.setColor(Color.rgb(255, 30, 30));
        canvas.drawRect(enemyRect, p);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void falsh(Enemy enemy){
        if(canFlash){
            canFlash = false;

            final int randomX = new Random().nextInt(2); // [0, 60] + 20 => [20, 80]
            final int random = new Random().nextInt(450) + 100; // [0, 60] + 20 => [20, 80]
            if(randomX==0) {
                enemyPos.x = GamePanel.HERO.playerPos.x + random;
            }else{
                enemyPos.x = GamePanel.HERO.playerPos.x - random;
            }
            enemyPos.y = GamePanel.HERO.playerPos.y - random;

            attack();
            flashStartedTime = System.currentTimeMillis();
        }else if((System.currentTimeMillis()-flashStartedTime)/100>=flashDelay){
            canFlash = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        if (bossActive) {
            falsh(this);
        }else if (Math.abs(enemyPos.x-GamePanel.HERO.playerPos.x)<=300){
            bossActive = true;
        }
        super.update();
    }
}
