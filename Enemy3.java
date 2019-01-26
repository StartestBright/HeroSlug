package com.jknull.heroslug;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
//import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Enemy3 extends Enemy {




    public Enemy3(Context context,Point p, int enemyIndex) {
        super(context,p, enemyIndex);
        curHp = enemyMaxHp;
        enemySize = 100;
        enemyVelocityX = 2.0;
        enemyVelocityY = 0;
        enemyBitMapRight = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy3right);
        enemyBitMapLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy3left);
    }

    @Override
    public String getCharacterTag() {
        return "Enemy3";
    }

    @Override
    public void jump() {

    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){
        if(canFire) {
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
            enemyGunShots.add(new EnemyReleaseBoom( context,0, 1, enemyPos,2));
            bulletIndex++;
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize-400;
        enmyWalk(this);
        enmyFollow(this);
        super.update();
    }


    @Override
    public int getEnemySize() {
        return enemySize;
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void enmyFollow(Enemy enemy){
        if((Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=800)
                &&(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)>=10)) {
            enemy.enemyInWalkMode = false;
            if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                enemyVelocityX =4;
            }else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                enemyVelocityX =-4;
            }
        }
       else if(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=10){
                if (canFire == true) {
                    this.attack();
                } else if ((System.currentTimeMillis() - gunShotDelayStartTime) / 100 >= gunShotDelay) {
                    canFire = true;
                }
        }
        else{
            enemy.enemyInWalkMode=true;
        }
    }
}
