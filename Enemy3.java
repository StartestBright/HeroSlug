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

public class Enemy3 extends Enemy {
    private static int enemy3MaxHp = 50;
    public int enemySize = 50;
    private ArrayList<EnemyReleaseBoom> enemyBoom;

    public Enemy3(Context context,Point p, int enemyIndex) {
        super(context,p, enemyIndex);
        enemyBoom = new ArrayList<EnemyReleaseBoom>();
        curHp = enemy3MaxHp;
        enemyRect = new Rect(enemyPos.x - enemySize, enemyPos.y - enemySize, enemyPos.x + enemySize, enemyPos.y + enemySize);
        enemyAlive = true;
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
        Paint p = new Paint();
        p.setColor(Color.rgb(200, 200, 200));
        canvas.drawRect(enemyRect, p);

        for(int i=0;i<enemyBoom.size();i++){
            if(enemyBoom.get(i).isActive())
                enemyBoom.get(i).draw(canvas);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){

        if(canFire) {
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();

            EnemyReleaseBoom newBoom = new EnemyReleaseBoom( context,0, 1, enemyPos.x, enemyPos.y);

            newBoom.setBulletSpeed(1);
            enemyBoom.add(newBoom);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
    /*      if(!enemyLanded) {
         enemyVelocityY += gravity;
           }else if(enemyLanded) {
              enemyVelocityY = -500;
              enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
          }*/
        enemyVelocityY = 10;
        enemyVelocityX = 2.0;
        enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize-400;

        enmyWalk(this);
        enmyFollow(this);

        for(int i=0;i<enemyBoom.size();i++){

            enemyBoom.get(i).update();
            if(!enemyBoom.get(i).isActive())
                enemyBoom.remove(i);
        }
       //     enemyPos.y += enemyVelocityY;
       //     enemyPos.x += enemyVelocityX;
            enemyRect.set(enemyPos.x - enemySize, enemyPos.y - enemySize, enemyPos.x + enemySize, enemyPos.y + enemySize);



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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void enmyFollow(Enemy enemy){
        if((Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=800)
                &&(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)>=10)) {
            enemy.enemyInWalkMode = false;
            if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                enemy.enemyPos.x += enemy.enemyVelocityX * 2;
            }else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                enemy.enemyPos.x -= enemy.enemyVelocityX * 2;
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
