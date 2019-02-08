package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

public abstract class Enemy implements Character{
    protected Point enemyPos;
    protected int enemyIndex;
    protected int curHp;
    protected double enemyVelocityY;
    protected double enemyVelocityX;
    protected boolean enemyLanded;
    protected boolean enemyAlive;
    protected  int enemyWidth;
    protected int enemyHeight;
    protected Rect enemyRect;
    protected final int walkLength=200;
    protected int walkAlready = 0;
    protected int walkBack = 0;
    protected boolean enemyInWalkMode = true;
    protected Context context;
    protected Boolean canFire = true;
    protected long gunShotDelayStartTime;
    protected long gunShotDelay = 50;
    protected int enemyMaxHp; //wow... omg u made it as static... IT shouldn't be study what static is! I was looking for this problem
    protected  Bitmap enemyBitMapRight;
    protected  Bitmap enemyBitMapLeft;
    protected Canvas canvas;
    protected  Bitmap enemyBoom[];
    protected  boolean boomStarted= false;

    protected SoundPool enemyBoomSound;
    protected SoundPool enemyShotSound;

   // enemyBoomSound= newSoundPool(10,AudioManager.STREAM_SYSTEM,5);







    public static ArrayList<EnemyGunShot> enemyGunShots= new ArrayList<EnemyGunShot>();
    public static ArrayList<BoomEffection> gunShotEffections = new ArrayList<BoomEffection>();

    protected  int bulletIndex = 0;


    protected boolean enemyIsDead(){
        if(enemyAlive){
            return false;
        }else{
    //        Explosion explosion = new Explosion(15000,enemyPos.x,enemyPos.y);
        //    explosions.add(new Explosion(1500,enemyPos.x,enemyPos.y));
            return true;
        }
    }

    public Point getEnemyPos(){
        return this.enemyPos;

    }




    public void enmyWalk(Enemy enemy){
        if(enemyInWalkMode){
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

            if(canFire){
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
    public void init(){
        enemyBoom = new Bitmap[1];
        enemyBoom[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemyboom,null);
    }

    public Enemy(Context context,Point p){
        this.context = context;
        enemyPos = p;
        this.enemyIndex = enemyIndex;
        curHp = enemyMaxHp;
        enemyRect = new Rect(enemyPos.x-enemyWidth,enemyPos.y-enemyHeight,enemyPos.x+enemyWidth,enemyPos.y+enemyHeight);
        enemyAlive =true;
        enemyBoomSound= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);


        enemyBoomSound.load(context,R.raw.enemyboom,1);
        init();
    }
    public boolean isAlive(){
        return enemyAlive;
    }

    public static void killBullet(){

        for(int i =0;i<enemyGunShots.size();i++){
            if(!enemyGunShots.get(i).isActive()){
                enemyGunShots.remove(i);
            }
        }
    }

    public abstract void attack();

    public void landDetect(){
        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemyHeight;
        }

    }


    public  void update(){
        Paint paint = new Paint();
        enemyPos.x += enemyVelocityX;
        enemyPos.y += enemyVelocityY;
        enemyRect.set(enemyPos.x-enemyWidth,enemyPos.y-enemyHeight,enemyPos.x+enemyWidth,enemyPos.y+enemyHeight);
        for(int i=0;i<enemyGunShots.size();i++) {
            if (enemyGunShots.get(i).isActive()) {
                enemyGunShots.get(i).update();
            } else if ((!enemyGunShots.get(i).boomming)&&((enemyGunShots.get(i).getTag()=="Enemy3")||(enemyGunShots.get(i).getTag()=="BoosSpe"))) {
                gunShotEffections.add(new BoomEffection(enemyGunShots.get(i).boomBitmap, enemyGunShots.get(i).bulletPos.x, enemyGunShots.get(i).bulletPos.y, 5,50));
                enemyGunShots.get(i).boomming = true;
            }


            for (int j = 0; j < gunShotEffections.size(); j++) {
                if (gunShotEffections.get(j).isFished()) {
                    gunShotEffections.remove(j);
                    System.out.println("effecremovee");
                    enemyGunShots.remove(i);
                    System.out.println("bulletremovee");
                }
            }
        }
               //     enemyGunShots.get(i).boomming = false;
                //    enemyGunShots.get(i).boomFinished = true;
              //      System.out.println("88888888888888888888888888");

       //         boomFinished = true;
  //          System.out.println("removed!!!78787878787878!!!!!!!!!!!!!!!!");
        }


    public void draw(Canvas canvas){

        this.canvas = canvas;
       Paint p = new Paint();
        if(enemyVelocityX>0) {
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);
        }else if(enemyVelocityX<0){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }else if(GamePanel.HERO.playerPos.x<=enemyPos.x){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }
        else{
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);

        }
        drawEnemyHpBar(canvas);




        for(int i=0;i<enemyGunShots.size();i++){
            if(enemyGunShots.get(i).isActive()) {
                enemyGunShots.get(i).draw(canvas);
            }
        }

        for(int i=0;i<gunShotEffections.size();i++){
            if(!gunShotEffections.get(i).isFished()) {
                gunShotEffections.get(i).draw(canvas,p);
            }
        }
    }


    protected void drawEnemyHpBar(Canvas canvas){
        Paint barFramePaint = new Paint();
        barFramePaint.setColor(Color.RED);
        barFramePaint.setStyle(Paint.Style.STROKE);
        barFramePaint.setStrokeWidth(2);
        int enemyBarHeight =40;
        int enemyBarPosTop =enemyRect.top-enemyBarHeight-10;
        int enemyBarPosBot =enemyBarPosTop+enemyBarHeight;
        canvas.drawRect(enemyRect.left,enemyBarPosTop,enemyRect.right,enemyBarPosBot,barFramePaint);

        Paint hpPaint = new Paint();
        hpPaint.setColor(Color.RED);
        hpPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(enemyRect.left,enemyBarPosTop,enemyRect.left+ ((float)curHp/enemyMaxHp)*enemyRect.width(),enemyBarPosBot,hpPaint);
    }



    public int getEnemyWidth() {
        return enemyWidth;
    }
    public int getEnemyHeight() {
        return enemyHeight;
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
            enemyBoomSound.play(1,1,1,0,0,1);

            EnemyManager.killEnemy();
            enemyAlive = false;
        }
    }

    public void takeShockShot(Point shockPoint,float shockPower,float shockRange){

        if(enemyPos.x-shockPoint.x>0)
            enemyVelocityX += (shockRange - (enemyPos.x - shockPoint.x)) / shockRange * shockPower;
        else
            enemyVelocityX -= (shockRange - (enemyPos.x - shockPoint.x)) / shockRange * shockPower;
        if(enemyPos.y-shockPoint.y>0)
            enemyVelocityY += (shockRange - (enemyPos.y - shockPoint.y)) / shockRange * shockPower;
        else
            enemyVelocityY -= (shockRange - (enemyPos.y - shockPoint.y)) / shockRange * shockPower;
    }

    public abstract String getCharacterTag();





}
