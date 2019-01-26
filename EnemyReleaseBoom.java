package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
//import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    //RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        bulltSize = 50;
        damage = 50;
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  void   init(Context context){
        opt.inMutable = true;
        bulletImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.gunshot);
        bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
        bulletColor = Color.RED;
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

        if(bulletPos.x+bulltSize>=screenWidth||bulletPos.x<0||bulletPos.y+bulltSize>=screenHeight-GamePanel.floorHeight||bulletPos.y<0){
            active = false;
        }
        collisionDetect();

    }

}
