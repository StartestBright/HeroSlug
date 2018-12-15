package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements Character{
    private Rect tempPlayer;
    private int color;
    private int charPos;
    private boolean playerLanded;
    private double playerVelocityY;
    private double playerVelocityX;
    public static int PlayerMaxHorizontalSpeed = 15;

    boolean onFloor;
    Point playerPos;

    public Player(Rect rectangle,int color,Point pos){
        this.tempPlayer = rectangle;
        this.color = color;
        playerPos = pos;
        playerLanded = false;

        playerVelocityY =0;
        playerVelocityX =0;
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
    }

    public boolean isLaneded(){
        return playerLanded;
    }
    public void jump(){
        if(playerLanded) {
            playerLanded = false;
            playerVelocityY -= 20;
        }

    }

    @Override
    public void attack() {

    }

    @Override
    public void update() {
        if(!playerLanded) {
            playerVelocityY += 1;
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

    }


    public void setPlayerLanded(boolean landed){
        this.playerLanded = landed;
    }
}
