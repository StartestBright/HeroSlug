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

public class Enemy1 extends Enemy {


    private ArrayList<EnemyGunShot> enemy1Bullets;
    //private Context context;
    private Canvas canvas;


    private static int enemy1MaxHp = 250;
    public int enemySize=50;




    public Enemy1(Context context,Point p,int enemyIndex) {
        super(context,p,enemyIndex);
        //this.context = context;
        enemy1Bullets = new ArrayList<EnemyGunShot>();
        curHp = enemy1MaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;
    }

    @Override
    public String getCharacterTag() {
        return "Enemy1";
    }

    @Override
    public void jump() {

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){

        if(canFire) {
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();

            EnemyGunShot newBullet = new EnemyGunShot( context,10, 0, enemyPos.x, enemyPos.y);

            newBullet.setBulletSpeed(10);
            enemy1Bullets.add(newBullet);
         }
    }



    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;
        Paint p = new Paint();
        p.setColor(Color.rgb(255,30,30));
        canvas.drawRect(enemyRect,p);

       for(int i=0;i<enemy1Bullets.size();i++){
            if(enemy1Bullets.get(i).isActive())
               enemy1Bullets.get(i).draw(canvas);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
      //  this.attack();
        enemyVelocityX = 2.0;
        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
        }
        enemyPos.y += enemyVelocityY;
        enmyWalk(this);
        enmyDash(this);

        for(int i=0;i<enemy1Bullets.size();i++){
            enemy1Bullets.get(i).update();
           if(!enemy1Bullets.get(i).isActive())
                enemy1Bullets.remove(i);
        }


        enemyRect.set(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);


        //System.out.println(enemyVelocityY);

    }

    @Override
    public void takeDamage(int damage) {
        curHp -= damage;
        if(curHp<=0) {
            EnemyManager.killEnemy(enemyIndex);
            enemyAlive = false;
        }
    }

    @Override
    public int getEnemySize() {
        return enemySize;
    }
}

