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
    protected  PlayerHP playerHP;
    protected int playerCurHp;

    private boolean heroDead= false;


    public abstract int getHeroMaxHP();


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
        playerVelocityX=value;
        if(playerVelocityX>= PLAYERMAXHORIZONTALSPEED)
            playerVelocityX = PLAYERMAXHORIZONTALSPEED;
        else if(playerVelocityX<= -1* PLAYERMAXHORIZONTALSPEED)
            playerVelocityX = -1* PLAYERMAXHORIZONTALSPEED;
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

    @Override
    public void takeDamage(int damage) {
        playerCurHp -= damage;
        GamePanel.playerHP.getDamage(damage);
        if(playerCurHp<=0){
            heroDead = true;
        }
    }
}
