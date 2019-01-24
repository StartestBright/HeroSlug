package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


    public RocketMan(int color, Point pos, Context context){
        super(pos);
        heroBitmaps = new Bitmap[2];
        heroBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.testimage);
        heroWeaponBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldiergunimage);
        heroWeaponSizeX = 120;
        heroWeaponSizeY = 70;
        heroSizeX =60;
        heroSizeY =100;
        this.context = context;
        super.heroRect = new Rect(-heroSizeX,-heroSizeY,heroSizeX,heroSizeY);
        super.heroColor = color;

        playerPos = pos;

        gunShotDelay = 3;
        bulletSpeed = rocketSpeed;
        bulletDamge = rocketSpeed;
        playerBullets = new ArrayList<RocketManGunShot>();

        jumpPower = 100;
        heroTag = "RocketMan";

        skill1CoolTime = 15;
        skill1LastingTime=5;
        skill2CoolTime = 12;
        skill2LastingTime= 0;


        ultimateSkillCoolTime = 30;
        ultimateSkillOnCoolTime = true;
        ultimateSkillLastingTime = 10;
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
        super.draw(canvas);
        for(int i=0;i<playerBullets.size();i++){
            RocketManGunShot gunShot = (RocketManGunShot)playerBullets.get(i);
            if(gunShot.isActive())
                gunShot.draw(canvas);
        }
    }
}
