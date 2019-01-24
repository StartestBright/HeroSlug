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
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Soldier extends Hero{
    public static int SOLDIERMAXHP = 250;
    private Context context;
    private SoldierHealPack healPack;
    private float bulletSpeed;
    private int bulletDamge;
    private float snipingBulletSpeed=120f,normalBulletSpeed= 80f;
    private int snipingBulletDamage = 150,normalBulletDamage = 25;

    int rayLength = 3000;

    Handler handler;
    public Soldier(int color,Point pos,Context context,GamePanel gamePanel){
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
        ultimateSkillOnCoolTime = true;
        ultimateSkillLastingTime = 10;





        //healPack = new SoldierHealPack(playerPos,this);
    }

    @Override
    public void update() {
        super.update();
        if(healPack !=null){
            healPack.update();
        }

        if(skill1On){
            gunShotDelay = 10;
            bulletSpeed = snipingBulletSpeed;
            bulletDamge = snipingBulletDamage;
        }else{
            gunShotDelay = 3;
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
            canvas.drawLine(playerPos.x,playerPos.y,(int)(Math.cos(playerRotation)*rayLength)+playerPos.x,(int)(Math.sin(playerRotation)*rayLength)+playerPos.y,p);
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
                        canvas.drawLine(playerPos.x, playerPos.y, enemy.getEnemyPos().x, enemy.getEnemyPos().y, p);

                    }
                }
            }

        }

        if(healPack!=null){
            healPack.draw(canvas);
        }



    }


    @Override
    public int getHeroMaxHP() {
        return SOLDIERMAXHP;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        if(canFire) {
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
                            SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(temp), (float) Math.sin(temp), playerPos.x, playerPos.y);
                            newBullet.setBulletSpeed(bulletSpeed);
                            newBullet.setBulletDamage(bulletDamge);
                            playerBullets.add(newBullet);

                        }
                    }
                }
            } else {
                SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(playerRotation), (float) Math.sin(playerRotation), playerPos.x, playerPos.y);
                newBullet.setBulletSpeed(bulletSpeed);
                newBullet.setBulletDamage(bulletDamge);
                playerBullets.add(newBullet);

            }
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void takeDamage(int damage) {
        GamePanel.HEROHP.getDamage(damage);
    }

    @Override
    public void setSkill1On(){
        if(!ultimateSkillOn) {
            super.setSkill1On();
            //this.skill1On = !this.skill1On;
            //if (skill1On) {
            skill1On = true;
            //gunShotDelay = 10;
            //bulletSpeed = snipingBulletSpeed;
            //bulletDamge = snipingBulletDamage;

            //} else {
                //gunShotDelay = 3;
                //bulletSpeed =normalBulletSpeed;
                //bulletDamge = normalBulletDamage;
            //}
        }

    }

    @Override
    public void setSkill2On(){
        if(!skill2OnCoolTime) {
            super.setSkill2On();
            healPack = new SoldierHealPack(playerPos, this);
        }

    }

    @Override
    public void setUltimateSkillOn() {
        if(!ultimateSkillOnCoolTime) {
            super.setUltimateSkillOn();
            if(skill1On){
                setSkill1On();
            }
        }
    }

    public void setHealPackNull(){
        healPack = null;
    }
}
