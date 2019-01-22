package com.jknull.heroslug;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class Hero implements Character{
    public static int PLAYERMAXHORIZONTALSPEED = 15;
    protected Rect tempPlayer;
    protected int heroColor;
    protected long gunShotDelay;
    protected long gunShotDelayStartTime;
    protected boolean canFire;
    //protected float gravity = 9.8f;
    protected String charTag = "Hero";
    protected float playerRotation=0;
    protected String heroTag;
    protected double playerVelocityY;
    protected double playerVelocityX;
    protected  int jumpPower;
    protected boolean playerLanded;
    protected Point playerPos;
    protected boolean flying;
    //protected int playerCurHp;

    public static boolean HERODEAD= false;


    public abstract int getHeroMaxHP();


    public void getDashed(int l) {


        // startedFlyTime = System.currentTimeMillis();

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
            System.out.println("finishd");


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
/*
    @Override
    public void takeDamage(int damage) {
        playerCurHp -= damage;
        GamePanel.playerHP.getDamage(damage);
        System.out.println("HP = "+playerCurHp+" "+ HERODEAD);
        if(playerCurHp<=0){
            HERODEAD = true;
        }
    }
    */
}
