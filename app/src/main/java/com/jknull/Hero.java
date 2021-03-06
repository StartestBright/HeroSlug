package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public abstract class Hero implements Character{
    public static int PLAYERMAXHORIZONTALSPEED = 15;
    public static SoundPool rocketExplosionSoundPool;
    protected Rect heroRect;
    protected long gunShotDelay;
    protected long gunShotDelayStartTime;
    protected boolean canFire;
    protected String charTag = "Hero";
    protected float playerRotation=0;
    protected String heroTag;
    protected double playerVelocityY;
    protected double playerVelocityX;
    protected  int jumpPower;
    protected boolean playerLanded;
    protected Point playerPos;
    protected int heroSizeX,heroSizeY;
    protected boolean flying;
    protected float bulletSpeed;
    protected int bulletDamage;
    protected boolean canMove = true;
    protected SoldierHealPack healPack;


    protected long skill1CoolTime,skill1StartTime,skill1LastingTime;
    protected long skill2CoolTime,skill2StartTime,skill2LastingTime;
    protected long ultimateSkillCoolTime, ultimateSkillStartTime,ultimateSkillLastingTime;

    protected boolean skill1OnCoolTime=false,skill1On=false;
    protected boolean skill2OnCoolTime=false,skill2On=false;
    protected boolean ultimateSkillOnCoolTime= false,ultimateSkillOn=false;

    protected ArrayList playerBullets;

    protected Bitmap rocketManFlyingBitmaps[];
    protected Bitmap soldierSnipingBitmaps[];
    protected Bitmap heroMovingRightBitmaps[];
    protected Bitmap heroMovingLeftBitmaps[];
    protected Bitmap heroIdleRightBitmaps[];
    protected Bitmap heroIdleLeftBitmaps[];
    protected Bitmap heroDyingRightBitmaps[];
    protected Bitmap heroDyingLeftBitmaps[];


    protected int heroMovingBitmapIndex =0;
    protected int heroIdleBitmapIndex = 0;
    protected int heroDyingBitmapIndex = 0;

    protected Bitmap[] heroWeaponBitmaps;
    protected int heroWeaponBitmapIndex=0;
    protected int heroWeaponSizeX,heroWeaponSizeY;
    protected Point heroWeaponPos;
    protected Rect heroWeaponRect;
    //Matrix heroWeaponMatrix;


    protected SoundPool heroSoundEffects;
    protected int maxStreamForHero=9;
    protected int heroSounds[];
    protected AudioAttributes heroSoundEffectsAttributes;

    protected Context context;


    enum HeroSounds{
        GUNSHOT(0),JUMP(1),SKILL1(2),
        SKILL2(3),ULTIMATE(4),SNIPINGSOUND(5),ATTACKED(6),SOLDIERULTI2(7),MOVEPAYLOAD(8);

        private final int x;
        HeroSounds(int i) {
            this.x = i;
        }
        public int getValue(){return this.x;}
    }

    HeroAnimManager animManager;
    class HeroAnimManager extends Thread {

        @Override
        public void run() {
            if(!PlayerHP.HERODEAD) {
                while (true) {
                    try {

                        if (playerVelocityX == 0 && !PlayerHP.HERODEAD) {
                            heroMovingBitmapIndex = 0;
                            heroIdleBitmapIndex = (heroIdleBitmapIndex + 1) % 9;
                            Thread.sleep(100);
                            continue;
                        } else if (playerVelocityX != 0) {
                            heroMovingBitmapIndex = (heroMovingBitmapIndex + 1) % 8;
                        }


                        long sleepTime = 100;

                        long realSleepTime = (long) Math.abs(((PLAYERMAXHORIZONTALSPEED / playerVelocityX) * sleepTime));
                        if (realSleepTime > 150) {
                            realSleepTime = 150;
                        }
                        Thread.sleep(realSleepTime);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(PlayerHP.HERODEAD){
                        if(heroTag=="Soldier"){
                            while(heroDyingBitmapIndex<11){
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                heroDyingBitmapIndex++;
                            }
                            return;
                        }else if(heroTag=="RocketMan") {
                            while (heroDyingBitmapIndex < 17) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                heroDyingBitmapIndex++;
                            }
                            return;
                        }
                    }


                }
            }
        }
    }

    public static int ROCKETEXPLOSIONSOUND;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Hero(Point heroSpawnPos,Context context){
        animManager = new HeroAnimManager();
        playerPos = heroSpawnPos;
        rocketManFlyingBitmaps= new Bitmap[2];
        soldierSnipingBitmaps = new Bitmap[2];
        heroMovingRightBitmaps = new Bitmap[8];
        heroMovingLeftBitmaps = new Bitmap[8];
        heroIdleRightBitmaps = new Bitmap[9];
        heroIdleLeftBitmaps = new Bitmap[9];
        heroDyingRightBitmaps = new Bitmap[18];
        heroDyingLeftBitmaps = new Bitmap[18];

        heroMovingBitmapIndex =0;
        heroWeaponBitmapIndex =0;
        heroWeaponBitmaps = new Bitmap[2];
        heroWeaponRect = new Rect();
        playerLanded = false;
        playerVelocityY =0;
        playerVelocityX =0;
        canFire = true;
        ultimateSkillStartTime = System.currentTimeMillis();
        heroWeaponPos = new Point();
        ultimateSkillOnCoolTime =true;
        skill1OnCoolTime=false;
        skill2OnCoolTime=false;

        heroWeaponPos.x = playerPos.x+20;
        heroWeaponPos.y = playerPos.y+40;

        animManager.start();


        heroSoundEffectsAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        heroSoundEffects = new SoundPool.Builder().setAudioAttributes(heroSoundEffectsAttributes).setMaxStreams(maxStreamForHero).build();
        heroSounds = new int[maxStreamForHero];
        rocketExplosionSoundPool =new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()).setMaxStreams(100).build();
        ROCKETEXPLOSIONSOUND = rocketExplosionSoundPool.load(context,R.raw.enemyboom,0);



        //heroWeaponMatrix = new Matrix();
        //Matrix mat = new Matrix();
        //mat.setRotate(56,heroWeaponRect.centerX(),heroWeaponRect.centerY());
        //mat.mapRect(heroWeaponRect);

    }

    @Override
    public void draw(Canvas canvas) {
        if (!PlayerHP.HERODEAD) {
                if(skill1On&& getHeroTag()=="Soldier") {
                    if (heroFacingRight()) {
                        canvas.drawBitmap(soldierSnipingBitmaps[0], null, heroRect, null);
                    } else {
                        canvas.drawBitmap(soldierSnipingBitmaps[1], null, heroRect, null);
                    }
                }else if(skill1On && getHeroTag()=="RocketMan"){
                    if (heroFacingRight()) {
                        canvas.drawBitmap(rocketManFlyingBitmaps[0], null, heroRect, null);
                    } else {
                        canvas.drawBitmap(rocketManFlyingBitmaps[1], null, heroRect, null);
                    }
                }else if (playerVelocityX > 0) {
                    canvas.drawBitmap(heroMovingRightBitmaps[heroMovingBitmapIndex], null, heroRect, null);
                } else if (playerVelocityX < 0) {
                    canvas.drawBitmap(heroMovingLeftBitmaps[heroMovingBitmapIndex], null, heroRect, null);
                } else if (playerVelocityX == 0) {
                    if (heroFacingRight()) {
                        canvas.drawBitmap(heroIdleRightBitmaps[heroIdleBitmapIndex], null, heroRect, null);
                    }
                    else
                        canvas.drawBitmap(heroIdleLeftBitmaps[heroIdleBitmapIndex], null, heroRect, null);

                }
            //canvas.save();
            //canvas.translate(heroWeaponPos.x,heroWeaponPos.y);
            //canvas.rotate(45);
            //canvas.drawBitmap(heroWeaponBitmaps[0],null,heroWeaponRect,paint);
            //canvas.restore();
        }
        else{
            if(heroFacingRight()){
                canvas.drawBitmap(heroDyingRightBitmaps[heroDyingBitmapIndex],null,heroRect,null);
            }else
                canvas.drawBitmap(heroDyingLeftBitmaps[heroDyingBitmapIndex],null,heroRect,null);
        }
    }


    public void playerBulletGarbageCollector(){
        for(int i=0;i<playerBullets.size();i++){
            HeroGunShot gunShot =(HeroGunShot )playerBullets.get(i);
            if(gunShot!=null)
                if(!gunShot.isActive())
                    playerBullets.remove(i);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update(){
        //System.out.println(heroMovingBitmapIndex);
        playerBulletGarbageCollector();

        if(PlayerHP.HERODEAD) {
            canMove = false;
            playerVelocityX = 0;
        }
        heroWeaponRect.set(heroWeaponPos.x-heroWeaponSizeX/2,heroWeaponPos.y-heroWeaponSizeY/2,heroWeaponPos.x+heroWeaponSizeX/2,heroWeaponPos.y+heroWeaponSizeY/2);
        if(!playerLanded) {
            if(GamePanel.HERO.getHeroTag()!="RocketMan")
                playerVelocityY += gravity;
            else if(GamePanel.HERO.getHeroTag()=="RocketMan"){
                if(!skill1On)
                    playerVelocityY += gravity;
            }
        }else if(playerLanded){
            playerVelocityY = 0;
            playerPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-heroRect.height()/2;
        }
        playerPos.y += playerVelocityY;
        //if(!PlayerHP.HERODEAD)
            playerPos.x += playerVelocityX;

        heroWeaponPos.x = playerPos.x+20;
        heroWeaponPos.y = playerPos.y;
        heroRect.set(playerPos.x - heroRect.width()/2
                ,playerPos.y-heroRect.height()/2
                ,playerPos.x+heroRect.width()/2,playerPos.y+heroRect.height()/2);


        heroMoveBeyondHalf();

        if((System.currentTimeMillis()-gunShotDelayStartTime)/10 >=gunShotDelay){
            canFire =true;
        }

        flyFinished();

        for(int i=0;i<playerBullets.size();i++){
            HeroGunShot gunShot = (HeroGunShot) playerBullets.get(i);
            gunShot.update();
        }



        if (skill1OnCoolTime) {
            if( (System.currentTimeMillis()-skill1StartTime)/1000>=skill1CoolTime){
                skill1OnCoolTime = false;
            }
            if( (System.currentTimeMillis()- skill1StartTime)/1000>=skill1LastingTime){
                skill1On = false;
                canMove = true;
            }

            MainActivity.skill1.setAlpha(( (float)(System.currentTimeMillis()-skill1StartTime)/1000 )/skill1CoolTime);

        }
        if (skill2OnCoolTime) {
            if( (System.currentTimeMillis()-skill2StartTime)/1000>=skill2CoolTime){
                skill2OnCoolTime = false;
            }
            if( (System.currentTimeMillis()- skill2StartTime)/1000>=skill2LastingTime){
                skill2On = false;
            }

            MainActivity.skill2.setAlpha(( (float)(System.currentTimeMillis()-skill2StartTime)/1000 )/skill2CoolTime);
        }

        if(ultimateSkillOnCoolTime) {
            if ((System.currentTimeMillis() - ultimateSkillStartTime) / 1000 >= ultimateSkillCoolTime) {
                ultimateSkillOnCoolTime = false;
            }
            if ((System.currentTimeMillis() - ultimateSkillStartTime) / 1000 >= ultimateSkillLastingTime) {
                ultimateSkillOn = false;
            }
            MainActivity.ultimateSkill.setAlpha(((float) (System.currentTimeMillis() - ultimateSkillStartTime) / 1000) / ultimateSkillCoolTime);
        }
    }





    public void setSkill1On(){
        if(!skill1OnCoolTime&&!PlayerHP.HERODEAD){
            skill1StartTime = System.currentTimeMillis();
            skill1OnCoolTime = true;
            skill1On = true;
            heroSoundEffects.play(heroSounds[HeroSounds.SKILL1.getValue()],1,1,1,0,1);
        }
    };
    public void setSkill2On(){
        if(!skill2OnCoolTime&&!PlayerHP.HERODEAD) {
            skill2StartTime = System.currentTimeMillis();
            skill2OnCoolTime = true;
            skill2On = true;
            heroSoundEffects.play(heroSounds[HeroSounds.SKILL2.getValue()],1,1,1,0,1);
        }
    };
    public void setUltimateSkillOn(){
        if(!ultimateSkillOnCoolTime&&!PlayerHP.HERODEAD) {
            ultimateSkillStartTime = System.currentTimeMillis();
            ultimateSkillOnCoolTime = true;
            ultimateSkillOn = true;
            heroSoundEffects.play(heroSounds[HeroSounds.ULTIMATE.getValue()],1,1,1,0,1);
            heroSoundEffects.play(heroSounds[HeroSounds.SOLDIERULTI2.getValue()],1,1,1,0,1);
        }
    };


    public void getDashed(int l) {
        if(!PlayerHP.HERODEAD) {
            flying = true;
            if (GamePanel.HERO.getHeroTag() == "RocketMan") {
                RocketMan rocketMan = (RocketMan) GamePanel.HERO;
                rocketMan.setRockManFlying(false);
            }
            playerLanded = false;
            if (l == 1) {
                playerVelocityX = -30;
            } else {
                playerVelocityX = 30;
            }
            playerVelocityY = -80;
        }

    }


    public void flyFinished() {
        if ((flying) && (playerLanded)) {
            playerVelocityX = 0;
            flying = false;


        }
    }


    public void jump(){
        if(playerLanded&&!PlayerHP.HERODEAD) {
            playerLanded = false;
            playerVelocityY -= jumpPower;
            heroSoundEffects.play(heroSounds[HeroSounds.JUMP.getValue()],0.8f,0.8f,1,0,1);

        }

    }
    protected boolean isLaneded(){
        return playerLanded;
}

    protected Rect getHero(){
        return this.heroRect;
    }


    public void moveHorizontal(double value){
        if(!flying && canMove&&!PlayerHP.HERODEAD) {
            playerVelocityX = value;
            if (playerVelocityX >= PLAYERMAXHORIZONTALSPEED)
                playerVelocityX = PLAYERMAXHORIZONTALSPEED;
            else if (playerVelocityX <= -1 * PLAYERMAXHORIZONTALSPEED)
                playerVelocityX = -1 * PLAYERMAXHORIZONTALSPEED;


        }
    }
    public String getCharacterTag() {
        return charTag;
    }
    protected void setPlayerLanded(boolean landed){
        this.playerLanded = landed;
    }
    protected void setPlayerRotation(float rotation) {
        if(!PlayerHP.HERODEAD)
            playerRotation = rotation;
        //heroWeaponMatrix.postRotate((float) (playerRotation/Math.PI*180));

        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(heroWeaponBitmaps,heroWeaponSizeX,heroWeaponSizeY,true);
        //heroWeaponBitmaps = Bitmap.createBitmap(scaledBitmap,0,0,heroWeaponRect.width(),heroWeaponRect.height(),heroWeaponMatrix,true);
    }
    protected String getHeroTag(){
        return this.heroTag;
    }
    public Point getHeroPos() {
        return playerPos;
    }
    @Override
    public void takeDamage(int damage) {
        GamePanel.HEROHP.getDamage(damage);
        heroSoundEffects.play(heroSounds[HeroSounds.ATTACKED.getValue()],1,1,1,0,1);
    }
    public void heroMoveBeyondHalf(){
        if(playerVelocityX>0) {
            if (playerPos.x >= MainActivity.SCREEN_WIDTH / 2) {

                if(GamePanel.BG!=null) {
                    GamePanel.BG.moveBg((float) playerVelocityX * 1);
                    playerPos.x = MainActivity.SCREEN_WIDTH/2;
                }

                for(int i=0;i<EnemyManager.enemies.size();i++){
                    Enemy enemy = EnemyManager.enemies.get(i);
                    enemy.enemyMoveByPlayer((float) playerVelocityX);
                }
                for(int i=0;i<EnemyManager.boomEffections.size();i++){
                    BoomEffection boomEffection = EnemyManager.boomEffections.get(i);
                    boomEffection.effectionMoveByPlayer((float) playerVelocityX);
                }

                for(int i=0;i<Enemy.enemyGunShots.size();i++){
                    EnemyGunShot gunShot = Enemy.enemyGunShots.get(i);
                    gunShot.moveByHero((float) playerVelocityX);
                }

                for(int i=0;i<playerBullets.size();i++){
                    HeroGunShot gunShot = (HeroGunShot) playerBullets.get(i);
                    if(gunShot.active&& gunShot!=null){
                        gunShot.gunshotMoveByPlayer((int) playerVelocityX);

                    }
                }

                if(getHeroTag()=="Soldier"){
                    if(healPack!=null)
                        healPack.moveByHero((float) playerVelocityX);
                }



                GamePanel.PAYLOAD.payloadMoveByPlayer((float) playerVelocityX);
            }
        }else if(playerVelocityX<0){
            if(heroRect.left <=0){
                if(GamePanel.BG!=null){
                    GamePanel.BG.moveBg((float) playerVelocityX * 1);
                    playerPos.x = heroRect.width()/2;
                }

                for(int i=0;i<EnemyManager.enemies.size();i++){
                    Enemy enemy = EnemyManager.enemies.get(i);
                    enemy.enemyMoveByPlayer((float) playerVelocityX);
                }

                for(int i=0;i<Enemy.enemyGunShots.size();i++){
                    EnemyGunShot gunShot = Enemy.enemyGunShots.get(i);
                    gunShot.moveByHero((float) playerVelocityX);
                }
                for(int i=0;i<playerBullets.size();i++){
                    HeroGunShot gunShot = (HeroGunShot) playerBullets.get(i);
                    if(gunShot.active&& gunShot!=null){
                        gunShot.gunshotMoveByPlayer((int) playerVelocityX);
                    }
                }

                if(getHeroTag()=="Soldier"){
                    if(healPack!=null)
                        healPack.moveByHero((float) playerVelocityX);
                }

                GamePanel.PAYLOAD.payloadMoveByPlayer((float) playerVelocityX);



            }

        }


    }

    public Point getHeroShotSpawnPoint(){
        if(getHeroTag()=="Soldier") {
            if (heroFacingRight())
                return new Point(heroWeaponRect.right - 10, heroWeaponRect.top + heroWeaponRect.height() / 2);
            else
                return new Point(heroWeaponRect.left - 35, heroWeaponRect.top + heroWeaponRect.height() / 2);
        }else if(getHeroTag()=="RocketMan"){
            if(skill1On) {
                if (heroFacingRight())
                    return new Point(heroWeaponRect.right - 10, heroWeaponRect.top + heroWeaponRect.height() / 2);
                else if (!heroFacingRight())
                    return new Point(heroWeaponRect.left - 35, heroWeaponRect.top + heroWeaponRect.height() / 2);
            }else
            if (heroFacingRight())
                return new Point(heroWeaponRect.right - 10, heroWeaponRect.top + heroWeaponRect.height() / 2+42);
            else if (!heroFacingRight())
                return new Point(heroWeaponRect.left - 35, heroWeaponRect.top + heroWeaponRect.height() / 2+42);
        }
        if (heroFacingRight())
            return new Point(heroWeaponRect.right - 10, heroWeaponRect.top + heroWeaponRect.height() / 2);
        else
            return new Point(heroWeaponRect.left - 35, heroWeaponRect.top + heroWeaponRect.height() / 2);
    }


    public boolean heroFacingRight(){
        if((playerRotation<Math.PI/2 &&playerRotation>=0)|| (playerRotation>-Math.PI/2 && playerRotation<=0))
            return true;
        return false;
    }



}
