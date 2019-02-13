package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
//import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    private Bitmap bulletImageLeft;

    public SoundPool bulletBoom;

   // public static ArrayList<BoomEffection> boomEffections = new ArrayList<BoomEffection>();




    //RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        bulletWidth = 42;
        bulletHeight = 34;
        damage = 50;
        enemyTag = "enemy3";
        boomBitmap = new Bitmap[1];
        boomBitmap[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemyboom);

        bulletBoom= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);

        bulletBoom.load(context,R.raw.enemyboom,1);


    }
    @Override
    public void collisionDetect() {
        if (this.active) {
            if (bulletRect.right>= GamePanel.HERO.getHero().left && //if  collide with enemy
                    bulletRect.left <= GamePanel.HERO.getHero().right &&
                    bulletRect.bottom>= GamePanel.HERO.getHero().top &&
                    bulletRect.top <= GamePanel.HERO.getHero().bottom) {
                GamePanel.HERO.takeDamage(damage);
                bulletBoom.play(1,1,1,0,0,1);
                EnemyManager.boomEffections.add(new BoomEffection(boomBitmap,bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,7,10));
                boomFinished = false;
                active = false;
            }
        }
    }



    private boolean boomEffection = true;
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  void   init(Context context){

        if(bulletPos.x<=GamePanel.HERO.playerPos.x) {
            bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3bullet);
            bulletPos.x+=43;
        }else{
            bulletImage =BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3bulletleft);
            bulletPos.x-=43;
        }
        bulletRect = new Rect(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);

        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
     //   bulletColor = Color.RED;
    }

    @Override
    public void update(){
    //    if(xPos>= GamePanel.HERO.getHeroPos().x) {
    //        directLeft = true;
     //   }

        if(active) {

        //    bulletPos.y += (bulletVelocityY * bulletSpeed);

            bulletPos.y += (bulletVelocityY * bulletSpeed);


            }

        if (bulletPos.x + bulletWidth >= screenWidth || bulletPos.x < 0 || bulletPos.y + bulletHeight>= screenHeight - GamePanel.floorHeight -70|| bulletPos.y < 0) {
          if(boomEffection){

                bulletBoom.play(1,1,1,0,0,1);
                EnemyManager.boomEffections.add(new BoomEffection(boomBitmap,bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,7,10));
                boomFinished = false;
            }
            active = false;
        }
        bulletRect.set(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);
        collisionDetect();

     //   collisionDetect();

    }



    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);

    }

}
