package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class RocketMan extends Hero {
    public static int ROCKETMANMAXHP = 200;
    private int rocketSpeed = 30;
    private Context context;
    private boolean rockManFlying = false;
    private int flyingGaze;
    private static int FLYINGMAXGAZE = 100;
    private RocketManShockShot shockShot;




    public RocketMan(int color, Point pos, Context context){
        super(pos);
        heroMovingBitmaps = new Bitmap[2];
        heroMovingBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.testimage);
        heroWeaponBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldiergunimage);
        heroWeaponSizeX = 120;
        heroWeaponSizeY = 70;
        heroSizeX =60;
        heroSizeY =100;
        this.context = context;
        super.heroRect = new Rect(-heroSizeX,-heroSizeY,heroSizeX,heroSizeY);
        super.heroColor = color;

        playerPos = pos;

        flyingGaze = FLYINGMAXGAZE;
        gunShotDelay = 3;
        bulletSpeed = rocketSpeed;
        bulletDamge = rocketSpeed;
        playerBullets = new ArrayList<RocketManGunShot>();

        jumpPower = 100;
        heroTag = "RocketMan";


        skill1OnCoolTime=false;
        skill1CoolTime = 15;
        skill1LastingTime=5;
        skill2OnCoolTime=false;
        skill2CoolTime = 1;
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

    private int rocketManJumpPosY;
    private boolean rockmanCanFly = false;
    public void fly(){
        rockManFlying = true;
        if(playerLanded) {
            rocketManJumpPosY = playerPos.y;
        }

    }

    @Override
    protected void setPlayerLanded(boolean landed) {
        super.setPlayerLanded(landed);
        rockManFlying= false;
        rockmanCanFly = false;
    }


    public void falling(){
        rockManFlying = false;
    }
    @Override
    public void update() {
        super.update();
        if(rockManFlying&&(flyingGaze>0)&&rockmanCanFly){
            playerVelocityY =0;
            playerPos.y -=25;
            flyingGaze-=2;
        }
        if(!rockManFlying && (flyingGaze<=FLYINGMAXGAZE) ){
            flyingGaze+=1;
        }

        if(rocketManJumpPosY-playerPos.y>=450) {
            rockmanCanFly = true;
        }
        if(shockShot!=null)
            shockShot.update();

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for(int i=0;i<playerBullets.size();i++){
            RocketManGunShot gunShot = (RocketManGunShot)playerBullets.get(i);
            if(gunShot.isActive())
                gunShot.draw(canvas);
        }
        if(shockShot!=null)
            shockShot.draw(canvas);


    }

    @Override
    protected String getHeroTag() {
        return "RocketMan";
    }

    @Override
    public void setSkill1On() {
        if(!skill1OnCoolTime) {
            super.setSkill1On();
            playerLanded = false;
            playerVelocityY = 0;
        }

    }

    @Override
    public void setSkill2On() {
        if (!skill2OnCoolTime) {
            super.setSkill2On();
            shockShot =new RocketManShockShot(context,(float) Math.cos(playerRotation), (float) Math.sin(playerRotation), playerPos.x, playerPos.y);
        }
    }
    public void setRockManFlying(boolean flying){
        rockManFlying = false;
    }
}
