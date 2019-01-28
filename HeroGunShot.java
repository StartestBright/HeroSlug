package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
//import android.support.annotation.RequiresApi;
import android.view.View;

public class HeroGunShot extends View implements GameObject{
    protected int gunShotDamage;
    protected int bulletColor;
    protected boolean active = true;
    protected float bulletSpeed = 150f;
    protected float xPos ,yPos,velocityX=0,velocityY=0;
    protected float radius;
    protected Bitmap bulletImage;
    protected boolean isRocket = false;

    private int screenWidth = MainActivity.SCREEN_WIDTH;
    private int screenHeight = MainActivity.SCREEN_HEIGHT;


    protected Rect heroGunShotRect;
    protected int heroGunShotRectSizeX;
    protected Bitmap heroGunShotBitmap;
    protected float rocketmanRocketRange;


    public HeroGunShot(Context context,float velocityX,float velocityY,float xPos,float yPos,boolean isRocket){
        super(context);
        heroGunShotRect = new Rect();
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isRocket = isRocket;
        init(context);
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context){

        bulletImage = BitmapFactory.decodeResource(getResources(),R.drawable.gunshot);
        bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);




    }

    @Override
    public void update() {
        if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += (velocityY * bulletSpeed);
            collisionDetect();
        }
        heroGunShotRect.set((int)xPos- heroGunShotRectSizeX,(int)yPos- heroGunShotRectSizeX,(int)xPos+ heroGunShotRectSizeX,(int)yPos+ heroGunShotRectSizeX);
        if(xPos>screenWidth||xPos<0||yPos>screenHeight||yPos<0){
            active = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(active) {
            super.draw(canvas);
            //canvas.drawBitmap(bulletImage,0,0,null);
            canvas.drawBitmap(heroGunShotBitmap,null,heroGunShotRect,null);


        }

    }
    public Point getBulletPoint(){
        Point p = new Point();
        p.set((int)xPos,(int)yPos);
        return p;
    }
    public void setBulletSpeed(float speed){
        bulletSpeed =speed;
    }
    public void setBulletDamage(int damage){
        gunShotDamage = damage;
    }
    public boolean isActive(){
        return active;
    }


    public void collisionDetect(){
        if(this.active){

            if(!isRocket) {
                for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                    Enemy enemy = EnemyManager.enemies.get(i);
                    if (xPos - radius <= enemy.getEnemyPos().x + enemy.getEnemyWidth() && //if  collide with enemy
                            xPos + radius >= enemy.getEnemyPos().x - enemy.getEnemyWidth() &&
                            yPos - radius <= enemy.getEnemyPos().y + enemy.getEnemyHeight() &&
                            yPos + radius >= enemy.getEnemyPos().y - enemy.getEnemyHeight()
                            ) {

                        if (enemy.isAlive() && active && !isRocket) {
                            EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                            active = false;
                            return;
                        }

                    }

                }
            }else if(isRocket){
                for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                    Enemy enemy = EnemyManager.enemies.get(i);
                    if ((xPos - radius <= enemy.getEnemyPos().x + enemy.getEnemyWidth() && //if  collide with enemy
                            xPos + radius >= enemy.getEnemyPos().x - enemy.getEnemyWidth() &&
                            yPos - radius <= enemy.getEnemyPos().y + enemy.getEnemyHeight() &&
                            yPos + radius >= enemy.getEnemyPos().y - enemy.getEnemyHeight())
                            || heroGunShotRect.bottom>=GamePanel.FLOOR.getFloorRect().top) {
                        if (enemy.isAlive() && active && isRocket) {
                            for (int j = 0; j < EnemyManager.enemies.size(); j++) {
                                Enemy enemy1 = EnemyManager.enemies.get(j);
                                double dist = Math.sqrt(((enemy1.enemyPos.x - xPos) * (enemy1.enemyPos.x - xPos)) + ((enemy1.enemyPos.y - yPos) * (enemy1.enemyPos.y - yPos)));
                                //System.out.println("DIST is = "+dist);
                                if (dist <= rocketmanRocketRange) {
                                    enemy1.takeDamage((int) ((rocketmanRocketRange - dist) / rocketmanRocketRange * gunShotDamage));
                                    //System.out.println(gunShotDamage);
                                    //System.out.println(rocketmanRocketRange+" "+dist+" "+gunShotDamage);
                                    //System.out.println((int) ( (rocketmanRocketRange-dist)/rocketmanRocketRange *gunShotDamage));
                                }
                            }

                            active = false;
                            return;
                        }
                    }
                }
            }
        }
    }


}
