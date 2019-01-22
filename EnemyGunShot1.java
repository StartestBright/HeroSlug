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

public class EnemyGunShot1 extends EnemyGunShot {


  //  BitmapFactory.Options opt = new BitmapFactory.Options();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot1(Context context, float velocityX, float velocityY, float xPos, float yPos) {
         super(context,velocityX,velocityY,xPos,yPos);
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
        if(xPos>= GamePanel.hero.getHeroPos().x) {
            directLeft = true;
        }

        if(active) {
            if(directLeft) {
                xPos -= (velocityX * bulletSpeed);
                yPos += (velocityY * bulletSpeed);
            }
            else{
                xPos += (velocityX * bulletSpeed);
           //     yPos += (velocityY * bulletSpeed);

            }
        }
        if(xPos+radius>=screenWidth||xPos<0||yPos+radius>=screenHeight-GamePanel.floorHeight||yPos<0){
            active = false;
        }
        collisionDetect();

    }






}