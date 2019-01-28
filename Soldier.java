package com.jknull.heroslug;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
//import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Soldier extends Hero{
    public static int SOLDIERMAXHP = 500;
    private Context context;
    private float bulletSpeed;
    private int bulletDamge;
    private float snipingBulletSpeed=120f,normalBulletSpeed= 80f;
    private int snipingBulletDamage = 180,normalBulletDamage = 25;


    int rayLength = 3000;


    Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Soldier(Point pos, Context context){
        super(pos);
        heroMovingRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move0r);
        heroMovingRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move1r);
        heroMovingRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move2r);
        heroMovingRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move3r);
        heroMovingRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move4r);
        heroMovingRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move5r);
        heroMovingRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move6r);
        heroMovingRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move7r);

        heroMovingLeftBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move0l);
        heroMovingLeftBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move1l);
        heroMovingLeftBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move2l);
        heroMovingLeftBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move3l);
        heroMovingLeftBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move4l);
        heroMovingLeftBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move5l);
        heroMovingLeftBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move6l);
        heroMovingLeftBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_move7l);

        heroIdleRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle0r);
        heroIdleRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle1r);
        heroIdleRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle2r);
        heroIdleRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle3r);
        heroIdleRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle4r);
        heroIdleRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle5r);
        heroIdleRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle6r);
        heroIdleRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle7r);
        heroIdleRightBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle8r);

        heroIdleLeftBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle0l);
        heroIdleLeftBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle1l);
        heroIdleLeftBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle2l);
        heroIdleLeftBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle3l);
        heroIdleLeftBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle4l);
        heroIdleLeftBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle5l);
        heroIdleLeftBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle6l);
        heroIdleLeftBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle7l);
        heroIdleLeftBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_idle8l);

        heroDyingRightBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r0);
        heroDyingRightBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r1);
        heroDyingRightBitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r2);
        heroDyingRightBitmaps[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r3);
        heroDyingRightBitmaps[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r4);
        heroDyingRightBitmaps[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r5);
        heroDyingRightBitmaps[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r6);
        heroDyingRightBitmaps[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r7);
        heroDyingRightBitmaps[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r8);
        heroDyingRightBitmaps[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r9);
        heroDyingRightBitmaps[10] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r10);
        heroDyingRightBitmaps[11] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_r11);

        heroDyingLeftBitmaps[0] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l0);
        heroDyingLeftBitmaps[1] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l1);
        heroDyingLeftBitmaps[2] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l2);
        heroDyingLeftBitmaps[3] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l3);
        heroDyingLeftBitmaps[4] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l4);
        heroDyingLeftBitmaps[5] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l5);
        heroDyingLeftBitmaps[6] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l6);
        heroDyingLeftBitmaps[7] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l7);
        heroDyingLeftBitmaps[8] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l8);
        heroDyingLeftBitmaps[9] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l9);
        heroDyingLeftBitmaps[10] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l10);
        heroDyingLeftBitmaps[11] =BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_dying_l11);

        soldierSnipingBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_sniping_bitmap_r);
        soldierSnipingBitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_sniping_bitmap_l);

        heroSounds[HeroSounds.GUNSHOT.getValue()] = heroSoundEffects.load(context,R.raw.soldier_gunshot_sound,1);
        heroSounds[HeroSounds.SNIPINGSOUND.getValue()] = heroSoundEffects.load(context,R.raw.soldier_sniping_sound,1);
        heroSounds[HeroSounds.SKILL1.getValue()] = heroSoundEffects.load(context,R.raw.soldier_skill1on_sound,1);
        heroSounds[HeroSounds.ULTIMATE.getValue()]= heroSoundEffects.load(context,R.raw.soldier_ultimate_sound,1);
        heroSounds[HeroSounds.SKILL2.getValue()] = heroSoundEffects.load(context,R.raw.soldier_skill2on_sound,1);
        heroSounds[HeroSounds.JUMP.getValue()] = heroSoundEffects.load(context,R.raw.soldier_jump_sound,1);
        heroSounds[HeroSounds.ATTACKED.getValue()] = heroSoundEffects.load(context,R.raw.soldier_attacked_sound,1);
        heroSounds[HeroSounds.SOLDIERULTI2.getValue()] = heroSoundEffects.load(context,R.raw.soldier_ultimate_sound2,1);
        heroSounds[HeroSounds.MOVEPAYLOAD.getValue()] = heroSoundEffects.load(context,R.raw.soldier_movepayload_sound,1);
        heroSoundEffects.play(heroSounds[HeroSounds.MOVEPAYLOAD.getValue()],1,1,1,0,1);


        heroWeaponBitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_weapon_right);
        heroWeaponSizeX = 120;
        heroWeaponSizeY = 70;
        heroSizeX =80;
        heroSizeY =100;
        this.context = context;
        super.heroRect = new Rect(-heroSizeX,-heroSizeY,heroSizeX,heroSizeY);

        playerPos = pos;

        //gunShotDelay = 90;
        bulletSpeed = normalBulletSpeed;
        bulletDamge = normalBulletDamage;
        playerBullets = new ArrayList<SoldierGunShot>();

        jumpPower = 100;
        heroTag = "Soldier";

        skill1CoolTime = 15;
        skill1LastingTime=5;
        skill2CoolTime = 12;
        skill2LastingTime= 0;


        ultimateSkillCoolTime = 30;
        ultimateSkillOnCoolTime = false;
        ultimateSkillLastingTime = 10;

    }

    @Override
    public void update() {
        super.update();
        if(healPack !=null){
            healPack.update();
        }

        if(skill1On){
            canMove = false;
            gunShotDelay = 100;
            bulletSpeed = snipingBulletSpeed;
            bulletDamge = snipingBulletDamage;
        }else{
            gunShotDelay = 10;
            bulletSpeed =normalBulletSpeed;
            bulletDamge = normalBulletDamage;
        }



    }



    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        for(int i=0;i<playerBullets.size();i++){
            SoldierGunShot gunShot = (SoldierGunShot)playerBullets.get(i);
            if(gunShot.isActive())
                gunShot.draw(canvas);
        }


        if(skill1On){
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStrokeWidth(8);
            canvas.drawLine(getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y,(int)(Math.cos(playerRotation)*rayLength)+playerPos.x,(int)(Math.sin(playerRotation)*rayLength)+playerPos.y,p);
        }

        if(ultimateSkillOn){
            for(int i=0;i<EnemyManager.enemies.size();i++){
                Enemy enemy = EnemyManager.enemies.get(i);
                if(enemy != null&&enemy.isAlive()) {
                    if(enemy.getEnemyPos().x<=MainActivity.SCREEN_WIDTH && enemy.getEnemyPos().x>=0
                            && enemy.getEnemyPos().y<=MainActivity.SCREEN_HEIGHT && enemy.getEnemyPos().y>=0) {
                        Paint p = new Paint();
                        p.setColor(Color.RED);
                        p.setStrokeWidth(8);
                        canvas.drawLine(getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y, enemy.getEnemyPos().x, enemy.getEnemyPos().y, p);

                    }
                }
            }

        }

        if(healPack!=null){
            healPack.draw(canvas);
        }


    }



    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        if(canFire && !PlayerHP.HERODEAD) {
            if (ultimateSkillOn) {
                for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                    Enemy enemy = EnemyManager.enemies.get(i);
                    if (EnemyManager.enemies.get(i).isAlive()) {
                        if(enemy.getEnemyPos().x<=MainActivity.SCREEN_WIDTH && enemy.getEnemyPos().x>=0
                                && enemy.getEnemyPos().y<=MainActivity.SCREEN_HEIGHT && enemy.getEnemyPos().y>=0) {
                            int x = enemy.getEnemyPos().x - playerPos.x;
                            int y = enemy.getEnemyPos().y - playerPos.y;
                            float temp = (float) (Math.atan2(x, y) + Math.PI + Math.PI / 2);
                            temp *= -1;
                            SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(temp), (float) Math.sin(temp), getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y);
                            newBullet.setBulletSpeed(bulletSpeed);
                            newBullet.setBulletDamage(bulletDamge);
                            playerBullets.add(newBullet);

                            heroSoundEffects.play(heroSounds[HeroSounds.GUNSHOT.getValue()],1,1,1,0,1);
                        }
                    }
                }
            } else {
                SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(playerRotation), (float) Math.sin(playerRotation), getHeroShotSpawnPoint().x, getHeroShotSpawnPoint().y);
                newBullet.setBulletSpeed(bulletSpeed);
                newBullet.setBulletDamage(bulletDamge);
                playerBullets.add(newBullet);
                if(!skill1On)
                    heroSoundEffects.play(heroSounds[HeroSounds.GUNSHOT.getValue()],1,1,1,0,1);
                else
                    heroSoundEffects.play(heroSounds[HeroSounds.SNIPINGSOUND.getValue()],1,1,1,0,1);
            }
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
        }
    }
    /*
    @Override
    public void takeDamage(int damage) {
        GamePanel.HEROHP.getDamage(damage);
    }
    */

    @Override
    public void setSkill1On(){
        if(!ultimateSkillOn && !skill1OnCoolTime) {
            super.setSkill1On();
            playerVelocityX = 0;
            canMove= false;
        }
    }

    @Override
    public void setSkill2On(){
        if(!skill2OnCoolTime) {
            super.setSkill2On();
            healPack = new SoldierHealPack(playerPos, this,context);
        }

    }

    @Override
    public void setUltimateSkillOn() {
        if(!ultimateSkillOnCoolTime) {
            super.setUltimateSkillOn();
        }
    }

    public void setHealPackNull(){
        healPack = null;
    }

}
