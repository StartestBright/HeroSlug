package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
//import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    private Bitmap bulletImageLeft;

    public SoundPool bulletBoom;




    //RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        bulletWidth = 42;
        bulletHeight = 34;
        damage = 50;
        enemyTag = "enemy3";

        bulletBoom= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);

        bulletBoom.load(context,R.raw.enemyboom,1);


    }



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


            }else if(!boomming){
            bulletBoom.play(1,1,1,0,0,1);
            boomEffections.add(new BoomEffection(boomBitmap,bulletPos.x,bulletPos.y,6,20));
            boomming= true;
            boomFinished = false;
        }

        if (bulletPos.x + bulletWidth >= screenWidth || bulletPos.x < 0 || bulletPos.y + bulletHeight>= screenHeight - GamePanel.floorHeight -70|| bulletPos.y < 0) {
            active = false;
        }
        bulletRect.set(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);
        collisionDetect();

     //   collisionDetect();

    }



    @Override
    public void draw(Canvas canvas){
        Paint p = new Paint();
        super.draw(canvas);
        for(int i=0;i<boomEffections.size();i++){
            if(!boomEffections.get(i).isFished()) {
                boomEffections.get(i).draw(canvas,p);
            }else{
                boomming = false;
                boomFinished = true;
            }
        }

    }

}
