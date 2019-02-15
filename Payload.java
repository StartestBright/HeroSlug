package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Payload implements GameObject {
    private Rect payloadRect;
    private Point payloadPos;
    private float payloadSpeed = 20;
    private float payloadVelocityX ,getPayloadVelocityY;
    private float payloadContestRange = 150;
    private int payloadWidth= 250, payloadHeight=140;
    private Bitmap payloadBitmap;
    public static int PAYLOADPROGRESS = 0;


    public Payload(Context context){
        init(context);
    }
    public void init(Context context){
        payloadRect = new Rect();
        payloadPos = new Point();
        payloadPos.x = payloadWidth/2;
        payloadPos.y = MainActivity.SCREEN_HEIGHT-payloadHeight/2-Floor.FLOORHEIGHT;
        payloadVelocityX = 5;
        payloadBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.payload_image);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        //canvas.drawRect(payloadRect,p);
        canvas.drawBitmap(payloadBitmap,null,payloadRect,p);

    }

    @Override
    public void update() {
        payloadRect.set(payloadPos.x-payloadWidth/2,payloadPos.y-payloadHeight/2,payloadPos.x+payloadWidth/2,payloadPos.y+payloadHeight/2);
        if(!contestCheck()){
            payloadPos.x += payloadVelocityX;
            PAYLOADPROGRESS += payloadVelocityX;
        }



        float ratio = (float)Payload.PAYLOADPROGRESS/GamePanel.MAPSIZE;
        if(Payload.PAYLOADPROGRESS == GamePanel.MAPSIZE){
            GamePanel.ClearStage();
        }
        PayloadMap.movePayloadPointer(ratio);


    }

    public void payloadMoveByPlayer(float playerVelocity){
        payloadPos.x-= playerVelocity;
    }

    public boolean contestCheck(){
        Hero hero = GamePanel.HERO;
        Rect heroRect = GamePanel.HERO.getHero();

        for(int i=0;i<EnemyManager.enemies.size();i++){
            Rect enemyRect = EnemyManager.enemies.get(i).enemyRect;
            if(EnemyManager.enemies.get(i).isAlive()) {
                if (payloadRect.left - payloadContestRange <= enemyRect.right && payloadRect.right + payloadContestRange >= enemyRect.left
                        && payloadRect.top - payloadContestRange <= enemyRect.bottom && payloadRect.bottom + payloadContestRange >= enemyRect.top) {
                    return true;
                }
            }

        }
        if(payloadRect.left -payloadContestRange <= heroRect.right && payloadRect.right + payloadContestRange >= heroRect.left
                && payloadRect.top -payloadContestRange<= heroRect.bottom && payloadRect.bottom +payloadContestRange>= heroRect.top){
            return false;


        }
        return true;
    }


}
