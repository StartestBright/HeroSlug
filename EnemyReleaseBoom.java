package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
//import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    private Bitmap bulletImageLeft;

    //RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        bulletWidth = 80;
        bulletHeight = 80;
        damage = 50;
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

              //  xPos -= (velocityX * bulletSpeed);
                     bulletPos.y += (bulletVelocityY * bulletSpeed);


            }

        collisionDetect();

    }

}
