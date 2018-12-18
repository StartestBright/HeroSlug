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

public class Player implements Character{
    private Rect tempPlayer;
    private Context context;
    private int jumpPower;
    private int color;
    private int charPos;
    private float playerRotation=0;
    private boolean playerLanded;
    private double playerVelocityY;
    private double playerVelocityX;
    private ArrayList<PlayerGunShot> playerBullets;
    private float playerGravity = 9.8f;
    private GamePanel gamePanel;
    private Background bg;
    public static int PlayerMaxHorizontalSpeed = 15;
    public static int PLAYERMAXHP = 250;


    boolean onFloor;
    Point playerPos;

    public Player(Rect rectangle,int color,Point pos,Context context,GamePanel gamePanel){
        this.context = context;
        this.tempPlayer = rectangle;
        this.color = color;
        this.gamePanel = gamePanel;
        this.bg = gamePanel.getBg();
        playerPos = pos;
        playerLanded = false;

        playerVelocityY =0;
        playerVelocityX =0;

        playerBullets = new ArrayList<PlayerGunShot>();

        jumpPower = 100;
    }


    public void update(Point point){
        playerPos = point;
        update();
    }
    public Rect getPlayer(){
        return this.tempPlayer;
    }

    public void moveHorizontal(double value){
        playerVelocityX=value;
        if(playerVelocityX>=PlayerMaxHorizontalSpeed)
            playerVelocityX = PlayerMaxHorizontalSpeed;


    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(tempPlayer,paint);

        for(int i=0;i<playerBullets.size();i++){
            playerBullets.get(i).draw(canvas);
        }
    }

    public boolean isLaneded(){
        return playerLanded;
    }
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
    public void update() {
        if(!playerLanded) {
            playerVelocityY += playerGravity;
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

    public void setPlayerLanded(boolean landed){
        this.playerLanded = landed;
    }
    public void setPlayerRotation(float rotation) {
        playerRotation = rotation;
        //System.out.println(rotation);
    }
}
