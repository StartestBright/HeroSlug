package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

//mport tyrantgit.explosionfield.ExplosionField;

//import android.support.annotation.RequiresApi;

public abstract class EnemyGunShot implements GameObject {
    protected int bulletColor;
    protected boolean active = true;
    protected float bulletSpeed = 10;
    protected float bulletVelocityX = 0, bulletVelocityY = 0;
    protected  Point bulletPos;
    protected int bulletWidth = 25;
    protected int bulletHeight = 25;
    protected Bitmap bulletImage;
    protected boolean directLeft;
    protected int damage = 25;
    protected int screenWidth = MainActivity.SCREEN_WIDTH;
    protected int screenHeight = MainActivity.SCREEN_HEIGHT;
    protected Rect bulletRect;
    protected  Canvas canvas;
    protected Context context;
    protected  Bitmap boomBitmap[];
    protected Boolean boomming = false;
    protected   Boolean boomFinished = false;
    protected  String enemyTag;

    protected String getTag(){
        return enemyTag;
    }




    //////////////////////////////////
    public static ArrayList<BoomEffection> boomEffections = new ArrayList<BoomEffection>();



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
        boomBitmap = new Bitmap[1];
        boomBitmap[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemyboom);

        init(context);
     //   bulletRect = new Rect(bulletPos.x-bulltSize,bulletPos.y-bulltSize,bulletPos.x+bulltSize,bulletPos.y+bulltSize);
     //   bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet);

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

        if (bulletPos.x + bulletWidth >= screenWidth || bulletPos.x < 0 || bulletPos.y + bulletHeight>= screenHeight - GamePanel.floorHeight || bulletPos.y < 0) {
            active = false;
        }
        bulletRect.set(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);
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
        boomBitmap = new Bitmap[1];

        bulletRect = new Rect(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);
        bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyshot);
        boomBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.gunshot);

     //   bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888, true);
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
     //   bulletColor = Color.RED;
      //  bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.gunshot);


    }

    public void collisionDetect() {
        if (this.active) {
            if (bulletRect.right>= GamePanel.HERO.getHero().left && //if  collide with enemy
                    bulletRect.left <= GamePanel.HERO.getHero().right &&
                    bulletRect.bottom>= GamePanel.HERO.getHero().top &&
                    bulletRect.top <= GamePanel.HERO.getHero().bottom) {
                     GamePanel.HERO.takeDamage(damage);
                     active = false;
            }
        }
    }
    public void moveByHero(float velocityX){
        bulletPos.x -= velocityX;
    }
}


