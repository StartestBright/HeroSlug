package com.jknull.heroslug;

import android.content.Context;
;

public class BossGunShot extends EnemyGunShot {

   //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public BossGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos, int speed) {
        super(context,velocityX,velocityY,xPos,yPos,speed);
    }

    @Override
    public void update(){
        super.update();
        if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += -(velocityY * bulletSpeed);
        }
    }
}
