package com.jknull.heroslug;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public abstract class Hero implements Character{
    public static int PLAYERMAXHORIZONTALSPEED = 15;
    protected Rect tempPlayer;
    protected int heroColor;
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
    protected boolean flying;

    protected long skill1CoolTime,skill1StartTime,skill1LastingTime;
    protected long skill2CoolTime,skill2StartTime,skill2LastingTime;
    protected long ultimateSkillCoolTime, ultimateSkillStartTime,ultimateSkillLastingTime;

    protected boolean skill1OnCoolTime=false,skill1On=false;
    protected boolean skill2OnCoolTime=false,skill2On=false;
    protected boolean ultimateSkillOnCoolTime= false,ultimateSkillOn=false;

    protected ArrayList playerBullets;

    public static boolean HERODEAD= false;


    public abstract int getHeroMaxHP();

    public void update(){
        if(!playerLanded) {
            playerVelocityY += gravity;
        }else if(playerLanded){
            playerVelocityY = 0;
            playerPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-tempPlayer.height()/2;
        }
        playerPos.y += playerVelocityY;
        playerPos.x += playerVelocityX;

        tempPlayer.set(playerPos.x - tempPlayer.width()/2
                ,playerPos.y-tempPlayer.height()/2
                ,playerPos.x+tempPlayer.width()/2,playerPos.y+tempPlayer.height()/2);


        heroMoveBeyondHalf();

        if((System.currentTimeMillis()-gunShotDelayStartTime)/100 >=gunShotDelay){

            canFire =true;
        }
        flyFinished();

        for(int i=0;i<playerBullets.size();i++){
            /*if(getHeroTag()== "Soldier") {
                SoldierGunShot gunShot = (SoldierGunShot) playerBullets.get(i);
                gunShot.update();
            }*/
            HeroGunShot gunShot = (HeroGunShot) playerBullets.get(i);
            gunShot.update();


        }


        if (skill1OnCoolTime) {
            if( (System.currentTimeMillis()-skill1StartTime)/1000>=skill1CoolTime){
                skill1OnCoolTime = false;
            }
            if( (System.currentTimeMillis()- skill1StartTime)/1000>=skill1LastingTime){
                skill1On = false;
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
        if(!skill1OnCoolTime){
            skill1StartTime = System.currentTimeMillis();
            skill1OnCoolTime = true;
        }
    };
    public void setSkill2On(){
        if(!skill2OnCoolTime) {
            skill2StartTime = System.currentTimeMillis();
            skill2OnCoolTime = true;
        }
    };
    public void setUltimateSkillOn(){
        if(!ultimateSkillOnCoolTime) {
            ultimateSkillStartTime = System.currentTimeMillis();
            ultimateSkillOnCoolTime = true;
            ultimateSkillOn = true;
        }
    };


    public void getDashed(int l) {
        flying = true;
        playerLanded = false;
        if(l==1) {
            playerVelocityX = -30;
        }else{
            playerVelocityX = 30;
        }
        playerVelocityY = -80;

    }


    public void flyFinished() {
        if ((flying) && (playerLanded)) {
            playerVelocityX = 0;
            flying = false;


        }
    }


    public void jump(){
        if(playerLanded) {
            playerLanded = false;
            playerVelocityY -= jumpPower;

        }

    }
    protected boolean isLaneded(){
        return playerLanded;
}

    protected Rect getHero(){
        return this.tempPlayer;
    }
    public void moveHorizontal(double value){
        if(!flying) {
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
        playerRotation = rotation;
    }
    protected String getHeroTag(){
        return this.heroTag;
    }
    public Point getHeroPos() {
        return playerPos;
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


                GamePanel.PAYLOAD.payloadMoveByPlayer((float) playerVelocityX);
            }
        }else if(playerVelocityX<0){
            if(tempPlayer.left <=0){
                if(GamePanel.BG!=null){
                    GamePanel.BG.moveBg((float) playerVelocityX * 1);
                    playerPos.x = tempPlayer.width()/2;
                }

                for(int i=0;i<EnemyManager.enemies.size();i++){
                    Enemy enemy = EnemyManager.enemies.get(i);
                    enemy.enemyMoveByPlayer((float) playerVelocityX);
                }

                GamePanel.PAYLOAD.payloadMoveByPlayer((float) playerVelocityX);


            }

        }
    }

}
