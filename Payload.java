package com.jknull.heroslug;

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
    private int payloadWidth= 200, payloadHeight=100;


    public Payload(){
        init();
    }
    public void init(){
        payloadRect = new Rect();
        payloadPos = new Point();
        payloadPos.x = payloadWidth/2;
        payloadPos.y = MainActivity.SCREEN_HEIGHT-payloadHeight/2-Floor.FLOORHEIGHT;
        payloadVelocityX = 5;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        canvas.drawRect(payloadRect,p);

    }

    @Override
    public void update() {
        payloadRect.set(payloadPos.x-payloadWidth/2,payloadPos.y-payloadHeight/2,payloadPos.x+payloadWidth/2,payloadPos.y+payloadHeight/2);
        if(!contestCheck()){
            payloadPos.x += payloadVelocityX;
        }


        float ratio = (float)payloadPos.x/GamePanel.MAPSIZE;
        PayloadMap.movePayloadPointer(ratio);


    }

    public void payloadMoveByPlayer(float playerVelocity){
        payloadPos.x-= playerVelocity;
    }

    public boolean contestCheck(){
        Hero hero = GamePanel.hero;
        Rect heroRect = GamePanel.hero.getHero();

        for(int i=0;i<EnemyManager.enemies.size();i++){
            Rect enemyRect = EnemyManager.enemies.get(i).enemyRect;
            if(payloadRect.left -payloadContestRange <= enemyRect.right && payloadRect.right + payloadContestRange >= enemyRect.left
                    && payloadRect.top -payloadContestRange<= enemyRect.bottom && payloadRect.bottom +payloadContestRange>= enemyRect.top){
                return true;
            }

        }
        if(payloadRect.left -payloadContestRange <= heroRect.right && payloadRect.right + payloadContestRange >= heroRect.left
                && payloadRect.top -payloadContestRange<= heroRect.bottom && payloadRect.bottom +payloadContestRange>= heroRect.top){
            return false;


        }
        return true;
    }


}
