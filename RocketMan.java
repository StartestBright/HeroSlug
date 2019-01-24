package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class RocketMan extends Hero {
    public static int ROCKETMANMAXHP = 200;
    private int rocketSpeed = 30;
    private Context context;


    public RocketMan(Rect rectangle, int color, Point pos, Context context){
        this.context = context;
        this.tempPlayer = rectangle;
        this.heroColor = color;
        playerPos = pos;
        playerLanded = false;

        playerVelocityY =0;
        playerVelocityX =0;

        jumpPower = 100;
        heroTag = "RocketMan";

        gunShotDelay = 3;
        bulletSpeed = rocketSpeed;
        bulletDamge = rocketSpeed;
        canFire = true;

        playerBullets = new ArrayList<SoldierGunShot>();
    }
    @Override
    public int getHeroMaxHP() {
        return ROCKETMANMAXHP;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        if(canFire) {
            RocketManGunShot newBullet = new RocketManGunShot(context, (float) Math.cos(playerRotation), (float) Math.sin(playerRotation), playerPos.x, playerPos.y);
            newBullet.setBulletSpeed(bulletSpeed);
            newBullet.setBulletDamage(bulletDamge);
            playerBullets.add(newBullet);
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void update() {
        super.update();
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(heroColor);
        canvas.drawRect(tempPlayer,paint);
        for(int i=0;i<playerBullets.size();i++){
            RocketManGunShot gunShot = (RocketManGunShot) playerBullets.get(i);
            if(gunShot.isActive())
                gunShot.draw(canvas);
        }
    }
}
