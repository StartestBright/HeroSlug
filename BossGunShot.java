package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Point;
;

public class BossGunShot extends EnemyGunShot {

   //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public BossGunShot(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
    }

    @Override
    public void update(){
        super.update();
        if(active) {
            bulletPos.x += (bulletVelocityX * bulletSpeed);
            bulletPos.y += -(bulletVelocityY * bulletSpeed);
        }
    }
}
