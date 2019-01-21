package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

public abstract class Enemy implements Character{
    protected Point enemyPos;
    protected int enemyIndex;
    protected int curHp;
    protected double enemyVelocityY;
    protected double enemyVelocityX;
    protected boolean enemyLanded;
    protected boolean enemyAlive;
    protected Rect enemyRect;
    protected final int walkLength=200;
    protected int walkAlready = 0;
    protected int walkBack = 0;
    protected boolean enemyInWalkMode = true;
    protected Context context;
    protected Boolean canFire = true;
    protected long gunShotDelayStartTime;
    protected long gunShotDelay = 50000;

    public Point getEnemyPos(){
        return this.enemyPos;

    }
    public void enmyWalk(Enemy enemy){
        if(enemyInWalkMode == true){
            if(walkAlready<=walkLength) {
                enemyPos.x -= enemyVelocityX;
                walkAlready++;
            }else if(walkBack<=walkLength){
                enemyPos.x += enemyVelocityX;
                walkBack++;
            }else {
                walkAlready=0;
                walkBack=0;
            }
        }
    }

    public void enmyDash(Enemy enemy){
        if(Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)<=800
                &&Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)>=400){
            enemy.enemyInWalkMode =false;
            if(enemy.enemyPos.x == GamePanel.hero.getHeroPos().x) {


            }


            else if (enemy.enemyPos.x < GamePanel.hero.getHeroPos().x) {
                enemy.enemyPos.x += enemy.enemyVelocityX*2;
            } else if (enemy.enemyPos.x > GamePanel.hero.getHeroPos().x) {
                enemy.enemyPos.x -=  enemy.enemyVelocityX*2;


            }

        }else if(Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)<=400){
            if(canFire == true){
                this.attack();
            //    canFire = false;
           //     gunShotDelayStartTime = System.currentTimeMillis();

            }
            else if((System.currentTimeMillis()-gunShotDelayStartTime)/100 >=gunShotDelay){

                canFire =true;
            }

        }


        else{
            enemy.enemyInWalkMode=true;
        }
    }

    public Enemy(Context context,Point p, int enemyIndex){
        this.context = context;
        enemyPos = p;
        this.enemyIndex = enemyIndex;
    }
    public boolean isAlive(){
        return enemyAlive;
    };
    public abstract void attack();


    public abstract void update();
    public abstract void draw(Canvas canvas);


    public abstract int getEnemySize();
    public Rect getEnemyRect(){
        return enemyRect;
    }
    public void setEnemyLanded(Boolean landed){
        enemyLanded = landed;
    }

    public int getEnemyIndex(){
        return enemyIndex;
    }
    public void setEnemyAlive(boolean alive){
        enemyAlive = alive;
    }
    public void enemyMoveByPlayer(float playerVelocityX){
        enemyPos.x-=playerVelocityX;
    }

    //

}
