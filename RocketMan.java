package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private boolean rockManFlying = false;
    private int flyingGaze;
    private static int FLYINGMAXGAZE = 100;
    private RocketManShockShot shockShot;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RocketMan(Point pos, Context context){
        super(pos);
        heroWeaponBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldiergunimage);
        heroWeaponSizeX = 120;
        heroWeaponSizeY = 70;
        heroSizeX =60;
        heroSizeY =100;
        this.context = context;
        super.heroRect = new Rect(-heroSizeX,-heroSizeY,heroSizeX,heroSizeY);

        playerPos = pos;

        flyingGaze = FLYINGMAXGAZE;
        gunShotDelay = 80;
        bulletSpeed = rocketSpeed;
        bulletDamage = rocketSpeed;
        playerBullets = new ArrayList<RocketManGunShot>();

        jumpPower = 100;
        heroTag = "RocketMan";


        skill1OnCoolTime=false;
        skill1CoolTime = 15;
        skill1LastingTime=5;
        skill2OnCoolTime=false;
        skill2CoolTime = 1;
        skill2LastingTime= 0;


        ultimateSkillCoolTime = 45;
        ultimateSkillOnCoolTime = false;
        ultimateSkillLastingTime = 0;

        heroMovingRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r0);
        heroMovingRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r1);
        heroMovingRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r2);
        heroMovingRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r3);
        heroMovingRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r4);
        heroMovingRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r5);
        heroMovingRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r6);
        heroMovingRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_r7);

        heroMovingLeftBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l0);
        heroMovingLeftBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l1);
        heroMovingLeftBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l2);
        heroMovingLeftBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l3);
        heroMovingLeftBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l4);
        heroMovingLeftBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l5);
        heroMovingLeftBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l6);
        heroMovingLeftBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_move_l7);

        heroIdleRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r0);
        heroIdleRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r1);
        heroIdleRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r2);
        heroIdleRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r3);
        heroIdleRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r4);
        heroIdleRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r5);
        heroIdleRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r6);
        heroIdleRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r7);
        heroIdleRightBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_r8);

        heroIdleLeftBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l0);
        heroIdleLeftBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l1);
        heroIdleLeftBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l2);
        heroIdleLeftBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l3);
        heroIdleLeftBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l4);
        heroIdleLeftBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l5);
        heroIdleLeftBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l6);
        heroIdleLeftBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l7);
        heroIdleLeftBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_idle_l8);

        heroDyingRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r0);
        heroDyingRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r1);
        heroDyingRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r2);
        heroDyingRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r3);
        heroDyingRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r4);
        heroDyingRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r5);
        heroDyingRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r6);
        heroDyingRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r7);
        heroDyingRightBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r8);
        heroDyingRightBitmaps[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r9);
        heroDyingRightBitmaps[10] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r10);
        heroDyingRightBitmaps[11] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r11);
        heroDyingRightBitmaps[12] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r12);
        heroDyingRightBitmaps[13] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r13);
        heroDyingRightBitmaps[14] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r14);
        heroDyingRightBitmaps[15] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r15);
        heroDyingRightBitmaps[16] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r16);
        heroDyingRightBitmaps[17] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_r17);

        heroDyingLeftBitmaps[0] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l0);
        heroDyingLeftBitmaps[1] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l1);
        heroDyingLeftBitmaps[2] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l2);
        heroDyingLeftBitmaps[3] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l3);
        heroDyingLeftBitmaps[4] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l4);
        heroDyingLeftBitmaps[5] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l5);
        heroDyingLeftBitmaps[6] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l6);
        heroDyingLeftBitmaps[7] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l7);
        heroDyingLeftBitmaps[8] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l8);
        heroDyingLeftBitmaps[9] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l9);
        heroDyingLeftBitmaps[10] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l10);
        heroDyingLeftBitmaps[11] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l11);
        heroDyingLeftBitmaps[12] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l12);
        heroDyingLeftBitmaps[13] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l13);
        heroDyingLeftBitmaps[14] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l14);
        heroDyingLeftBitmaps[15] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l15);
        heroDyingLeftBitmaps[16] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l16);
        heroDyingLeftBitmaps[17] =BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_dying_l17);

        rocketManFlyingBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_flying_r);
        rocketManFlyingBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocketman_flying_l);



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        if(canFire&&!PlayerHP.HERODEAD) {
            RocketManGunShot newBullet = new RocketManGunShot(context, (float) Math.cos(playerRotation), (float) Math.sin(playerRotation), getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y);
            newBullet.setBulletSpeed(bulletSpeed);
            //newBullet.setBulletDamage(bulletDamage);
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
        if(!skill1OnCoolTime &&!PlayerHP.HERODEAD &&!playerLanded) {
            super.setSkill1On();
            playerLanded = false;
            playerVelocityY = 0;
        }

    }

    @Override
    public void setSkill2On() {
        if (!skill2OnCoolTime&&!PlayerHP.HERODEAD) {
            super.setSkill2On();
            shockShot =new RocketManShockShot(context,(float) Math.cos(playerRotation), (float) Math.sin(playerRotation), getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setUltimateSkillOn() {
        if (!ultimateSkillOnCoolTime&&!PlayerHP.HERODEAD) {
            super.setUltimateSkillOn();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<50;i++){
                        playerBullets.add(new RocketManGunShot(context,(float) (Math.cos(playerRotation)+(Math.random()*2)), (float) (Math.sin(playerRotation)+(Math.random()*2)), getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();


        }
    }

    public void setRockManFlying(boolean flying){
        rockManFlying = false;
    }
}
