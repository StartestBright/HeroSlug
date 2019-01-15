package com.jknull.heroslug;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class Hero implements Character{
    public static int PlayerMaxHorizontalSpeed = 15;
    protected Rect tempPlayer;
    protected int heroColor;
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
        if(playerVelocityX>=PlayerMaxHorizontalSpeed)
            playerVelocityX = PlayerMaxHorizontalSpeed;
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

}