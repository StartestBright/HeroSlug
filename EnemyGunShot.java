package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public  abstract class EnemyGunShot implements GameObject{
    protected int gunShotDamage;
    protected int bulletColor;
    protected boolean active = true;
    protected float bulletSpeed = 150f;
    protected float xPos =500,yPos=500,velocityX=0,velocityY=0;
    protected float radius=10;
    protected Bitmap bulletImage;


    private int screenWidth = MainActivity.SCREEN_WIDTH;
    private int screenHeight = MainActivity.SCREEN_HEIGHT;

    //BitmapFactory.Options opt = new BitmapFactory.Options();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        //super(context);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.xPos = xPos;
        this.yPos = yPos;

        //init(context);


    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  abstract void   init(Context context);


   // @Override
    public abstract void update();

    //@Override
    public abstract void  draw(Canvas canvas);

    public Point getBulletPoint(){
        Point p = new Point();
        p.set((int)xPos,(int)yPos);
        return p;
    }
    public void setBulletSpeed(float speed){
        bulletSpeed =speed;
    }
    public boolean isActive(){
        return active;
    }


    public void collisionDetect(){
        if(this.active){

            for(int i=0;i<EnemyManager.enemies.size();i++) {
                Enemy enemy = EnemyManager.enemies.get(i);
                if(enemy.isAlive()){
                    if (xPos - radius <=enemy.getEnemyPos().x+enemy.getEnemySize()&& //if  collide with enemy
                            xPos+radius>=enemy.getEnemyPos().x-enemy.getEnemySize()&&
                            yPos-radius<=enemy.getEnemyPos().y+enemy.getEnemySize()&&
                            yPos+radius>=enemy.getEnemyPos().y-enemy.getEnemySize()) {

                        EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                        active = false;
                        return;

                    }
                }
            }
        }
    }
}
