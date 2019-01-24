package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class EnemyGunShot1 extends EnemyGunShot {


  //  BitmapFactory.Options opt = new BitmapFactory.Options();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot1(Context context, float velocityX, float velocityY, float xPos, float yPos,int speed) {
         super(context,velocityX,velocityY,xPos,yPos,speed);
    }

    @Override
    public void update(){
        detectLeft();
        super.update();
    }
}
