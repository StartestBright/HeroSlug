package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
//import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;

public abstract class EnemyGunShot implements GameObject {
    protected int bulletColor;
    protected boolean active = true;
    protected float bulletSpeed = 10;
    protected float bulletVelocityX = 0, bulletVelocityY = 0;
    protected  Point bulletPos;
    protected int bulltSize = 25;
    protected Bitmap bulletImage;
    protected boolean directLeft;
    protected int damage = 25;
    protected int screenWidth = MainActivity.SCREEN_WIDTH;
    protected int screenHeight = MainActivity.SCREEN_HEIGHT;
    protected Rect bulletRect;
    protected  Canvas canvas;
    protected Context context;
    BitmapFactory.Options opt = new BitmapFactory.Options();



    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot(Context context, float velocityX, float velocityY, Point pos, int speed) {
       // super(context);
      //  init(context);
        this.context = context;
        bulletVelocityX = velocityX;
        bulletVelocityY  = velocityY;
        bulletPos = pos;
        bulletSpeed = speed;
        bulletRect = new Rect(bulletPos.x-bulltSize,bulletPos.y-bulltSize,bulletPos.x+bulltSize,bulletPos.y+bulltSize);
        bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet);
    }

    public void detectLeft() {
        if (bulletPos.x >= GamePanel.HERO.getHeroPos().x) {
            directLeft = true;
        }
        if (active) {
            if (directLeft) {
                bulletPos.x -= (bulletVelocityX * bulletSpeed);
            } else {
                bulletPos.x += (bulletVelocityX* bulletSpeed);
            }
        }
    }


    @Override
    public void update() {
        if (bulletPos.x + bulltSize >= screenWidth || bulletPos.x < 0 || bulletPos.y + bulltSize >= screenHeight - GamePanel.floorHeight || bulletPos.y < 0) {
            active = false;
        }
        bulletRect.set(bulletPos.x-bulltSize,bulletPos.y-bulltSize,bulletPos.x+bulltSize,bulletPos.y+bulltSize);
        collisionDetect();
    }

    //@Override
    public void draw(Canvas canvas) {
        if (active) {
            this.canvas = canvas;
            // canvas.drawBitmap(bulletImage,0,0,null);
            Paint paint = new Paint();
        //    paint.setColor(bulletColor);
            canvas.drawBitmap(bulletImage,null,bulletRect,paint);

        }
    }

    public Point getBulletPoint() {
        Point p = new Point();
        p=bulletPos;
        return p;
    }

    public void setBulletSpeed(float speed) {
        bulletSpeed = speed;
    }

    public boolean isActive() {
        return active;
    }


   //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context) {


  //      bulletPos.x= Math.round(bulletPos.x);
     //   bulletPos.y=Math.round(bulletPos.y);
      //  opt.inMutable = true;
        bulletRect = new Rect(bulletPos.x-bulltSize,bulletPos.y-bulltSize,bulletPos.x+bulltSize,bulletPos.y+bulltSize);
        bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet);
     //   bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888, true);
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
     //   bulletColor = Color.RED;


    }

    public void collisionDetect() {
        if (this.active) {
            if (bulletPos.x + bulltSize +10>= GamePanel.HERO.getHero().left && //if  collide with enemy
                    bulletPos.x - bulltSize -10<= GamePanel.HERO.getHero().right &&
                    bulletPos.y + bulltSize +10>= GamePanel.HERO.getHero().top &&
                    bulletPos.y - bulltSize -10<= GamePanel.HERO.getHero().bottom) {
                GamePanel.HERO.takeDamage(damage);
                active = false;


            }
        }
    }
    public void moveByHero(float velocityX){
        bulletPos.x -= velocityX;
    }
}


