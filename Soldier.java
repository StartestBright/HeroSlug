package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Soldier extends Hero{
    private String heroTag = "Soldier";
    private boolean snipingMode = false;
    private Rect tempPlayer;
    private Context context;
    private int jumpPower;
    private int charPos;
    private float playerRotation=0;
    private boolean playerLanded;
    private double playerVelocityY;
    private double playerVelocityX;
    private ArrayList<PlayerGunShot> playerBullets;
    private GamePanel gamePanel;
    private Background bg;
    public static int SOLDIERMAXHP = 250;

    int rayLength = 3000;


    boolean onFloor;
    Point playerPos;

    public Soldier(Rect rectangle,int color,Point pos,Context context,GamePanel gamePanel){
        this.context = context;
        this.tempPlayer = rectangle;
        this.heroColor = color;
        this.gamePanel = gamePanel;
        this.bg = gamePanel.getBg();
        playerPos = pos;
        playerLanded = false;

        playerVelocityY =0;
        playerVelocityX =0;

        playerBullets = new ArrayList<PlayerGunShot>();

        jumpPower = 100;
    }

    @Override
    public void update() {
        if(!playerLanded) {
            playerVelocityY += heroGravity;
        }else if(playerLanded){
            playerVelocityY = 0;
            playerPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-tempPlayer.height()/2;
        }
        playerPos.y += playerVelocityY;
        playerPos.x += playerVelocityX;
        //System.out.println(playerLanded);
        tempPlayer.set(playerPos.x - tempPlayer.width()/2
                ,playerPos.y-tempPlayer.height()/2
                ,playerPos.x+tempPlayer.width()/2,playerPos.y+tempPlayer.height()/2);

        for(int i=0;i<playerBullets.size();i++){
            playerBullets.get(i).update();
            //if(playerBullets.get(i))
            if(!playerBullets.get(i).isActive())
                playerBullets.remove(i);
        }


        if(playerVelocityX>0) {
            if (playerPos.x >= MainActivity.SCREEN_WIDTH / 2) {
                if(bg!=null) {
                    bg.moveBg((float) playerVelocityX * -1);
                    playerPos.x = MainActivity.SCREEN_WIDTH/2;
                }
            }
        }

    }
    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(heroColor);
        canvas.drawRect(tempPlayer,paint);

        for(int i=0;i<playerBullets.size();i++){
            if(playerBullets.get(i).isActive())
                playerBullets.get(i).draw(canvas);
        }

        if(snipingMode) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStrokeWidth(8);
            canvas.drawLine(playerPos.x,playerPos.y,(int)(Math.cos(playerRotation)*rayLength)+playerPos.x,(int)(Math.sin(playerRotation)*rayLength)+playerPos.y,p);
        }
    }

    @Override
    public Rect getHero(){
        return this.tempPlayer;
    }

    @Override
    public int getHeroMaxHP() {
        return SOLDIERMAXHP;
    }

    @Override
    public void moveHorizontal(double value){
        playerVelocityX=value;
        if(playerVelocityX>=PlayerMaxHorizontalSpeed)
            playerVelocityX = PlayerMaxHorizontalSpeed;
    }
    @Override
    public boolean isLaneded(){
        return playerLanded;
    }

    @Override
    public String getCharacterTag() {
        return charTag;
    }

    @Override
    public void jump(){
        if(playerLanded) {
            playerLanded = false;
            playerVelocityY -= jumpPower;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        playerBullets.add(new PlayerGunShot(context,(float)Math.cos(playerRotation),(float)Math.sin(playerRotation),playerPos.x,playerPos.y));
    }

    @Override
    public void setPlayerLanded(boolean landed){
        this.playerLanded = landed;
    }
    @Override
    public void setPlayerRotation(float rotation) {
        playerRotation = rotation;
        //System.out.println(rotation);
    }
    @Override
    public String getHeroTag(){
        return this.heroTag;
    }

    @Override
    public Point getHeroPos() {
        return playerPos;
    }

    public void setSnipingMode(){
        this.snipingMode = !this.snipingMode;
    }
}
