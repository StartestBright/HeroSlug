package com.jknull.heroslug;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;


;

public class BossGunShot extends EnemyGunShot {
    public boolean specialGunShot = false;

   //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public BossGunShot(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        if(speed >=10){
            bulletImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.bossspecial);
            specialGunShot = true;
            enemyTag = "BossSpe";
            bulletWidth = 30;
            bulletHeight = 30;
            bulletSpeed = 1;
        }
    }


    @Override
    public void update(){
        super.update();
        if(active) {
            bulletPos.x += (bulletVelocityX * bulletSpeed);
            bulletPos.y += -(bulletVelocityY * bulletSpeed);
        }else if (specialGunShot){
                EnemyManager.enemies.add(new Enemy2(context,bulletPos));
                specialGunShot =false;
        }
    }
}
