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


    protected int screenWidth = MainActivity.SCREEN_WIDTH;
    protected int screenHeight = MainActivity.SCREEN_HEIGHT;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot( float velocityX, float velocityY, float xPos, float yPos) {
       // super(context);
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
    public  void  draw(Canvas canvas){
        if(active) {
            //super.draw(canvas);
            //canvas.drawBitmap(bulletImage,0,0,null);
            Paint paint = new Paint();
            paint.setColor(bulletColor);
            canvas.drawCircle(xPos, yPos, radius, paint);

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
    public boolean isActive(){
        return active;
    }


    public void collisionDetect(){
        if(this.active){
                    if (xPos - radius <=GamePanel.hero.playerPos.x+GamePanel.hero.tempPlayer.width()&& //if  collide with enemy
                            xPos+radius>=GamePanel.hero.playerPos.x-GamePanel.hero.tempPlayer.width()&&
                            yPos-radius<=GamePanel.hero.playerPos.y+GamePanel.hero.tempPlayer.height()&&
                            yPos+radius>=GamePanel.hero.playerPos.y-GamePanel.hero.tempPlayer.height()) {

                          //  GamePanel.
                                active = false;
                        return;

                    }
                }
            }
        }


