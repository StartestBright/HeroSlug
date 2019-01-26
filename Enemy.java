package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public abstract class Enemy implements Character{
    protected Point enemyPos;
    protected int enemyIndex;
    protected int curHp;
    protected double enemyVelocityY;
    protected double enemyVelocityX;
    protected boolean enemyLanded;
    protected boolean enemyAlive;
    protected  int enemySize;
    protected Rect enemyRect;
    protected final int walkLength=200;
    protected int walkAlready = 0;
    protected int walkBack = 0;
    protected boolean enemyInWalkMode = true;
    protected Context context;
    protected Boolean canFire = true;
    protected long gunShotDelayStartTime;
    protected long gunShotDelay = 50;
    protected static int enemyMaxHp;
    protected  Bitmap enemyBitMapRight;
    protected  Bitmap enemyBitMapLeft;
    protected Canvas canvas;


    public static ArrayList<EnemyGunShot> enemyGunShots= new ArrayList<EnemyGunShot>();
   // public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();



    protected  int bulletIndex = 0;

    public Point getEnemyPos(){
        return this.enemyPos;

    }




    public void enmyWalk(Enemy enemy){
        if(enemyInWalkMode == true){
            if(walkAlready<=walkLength) {
                enemyVelocityX=-2;
                walkAlready++;
            }else if(walkBack<=walkLength){
                enemyVelocityX=2;
                walkBack++;
            }else {
                walkAlready=0;
                walkBack=0;
            }
        }
    }

    public void enmyFollow(Enemy enemy){

        if(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=1000
                &&Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)>=600){

            enemy.enemyInWalkMode =false;
            if(enemy.enemyPos.x == GamePanel.HERO.getHeroPos().x) {


            }

            else if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {

                enemyVelocityX=4;
            } else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                enemy.enemyVelocityX=-4;
            }


        }else if(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=600){

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
        curHp = enemyMaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;
    }
    public boolean isAlive(){
        return enemyAlive;
    };


    public abstract void attack();

    public void landDetect(){
        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
        }
    }


    public  void update(){
        enemyPos.x += enemyVelocityX;
        enemyPos.y += enemyVelocityY;
        enemyRect.set(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        for(int i=0;i<enemyGunShots.size();i++){
            enemyGunShots.get(i).update();
            if(!enemyGunShots.get(i).isActive())
                enemyGunShots.remove(i);
        }
    }

    public void draw(Canvas canvas){
        this.canvas = canvas;
        Paint p = new Paint();
        if(enemyVelocityX>0) {
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);
        }else if(enemyVelocityX<0){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }else if(GamePanel.HERO.playerPos.x<=this.enemyPos.x){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }
        else{
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);
        }




        for(int i=0;i<enemyGunShots.size();i++){
            if(enemyGunShots.get(i).isActive()) {
                enemyGunShots.get(i).draw(canvas);
            }
        }
    }






    public int getEnemySize() {
        return enemySize;
    }
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
    public void takeDamage(int damage) {
        curHp -= damage;
        if(curHp<=0) {
            EnemyManager.killEnemy(enemyIndex);
            enemyAlive = false;
        }
    }

    public void takeShockShot(Point shockPoint,float shockPower,float shockRange){

        enemyVelocityX+=(shockRange-Math.abs(enemyPos.x-shockPoint.x))/shockRange*shockPower;
        enemyVelocityY+=(shockRange-Math.abs(enemyPos.y-shockPoint.y))/shockRange*shockPower;
        System.out.println((shockRange-Math.abs(enemyPos.y-shockPoint.y))/shockRange*shockPower);
    }
}
