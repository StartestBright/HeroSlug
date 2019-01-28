package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Point;
//import android.support.annotation.RequiresApi;

public class EnemyGunShot1 extends EnemyGunShot {


  //  BitmapFactory.Options opt = new BitmapFactory.Options();

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyGunShot1(Context context, float velocityX, float velocityY, Point pos, int speed) {
         super(context,velocityX,velocityY,pos,speed);
        detectLeft();


        bulletWidth = 20;
        bulletHeight = 20;
        damage = 50;
    }

    @Override
    public void update(){
        super.update();
        detectLeft();
    }
}
