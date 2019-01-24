package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, float xPos, float yPos,int speed) {
        super(context,velocityX,velocityY,xPos,yPos,speed);
        radius = 50;
        damage = 50;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  void   init(Context context){
        opt.inMutable = true;
        bulletImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.gunshot);
        bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        bulletImage.setWidth(800);
        bulletImage.setHeight(800);
        bulletColor = Color.RED;
    }

    @Override
    public void update(){
    //    if(xPos>= GamePanel.HERO.getHeroPos().x) {
    //        directLeft = true;
     //   }

        if(active) {

              //  xPos -= (velocityX * bulletSpeed);
                     yPos += (velocityY * bulletSpeed);


            }

        if(xPos+radius>=screenWidth||xPos<0||yPos+radius>=screenHeight-GamePanel.floorHeight||yPos<0){
            active = false;
        }
        collisionDetect();

    }

}
