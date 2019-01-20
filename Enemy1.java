package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy1 extends Enemy {
    private static int enemy1MaxHp = 250;
    public int enemySize=50;




    public Enemy1(Point p,int enemyIndex) {
        super(p,enemyIndex);
        curHp = enemy1MaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;
    }

    @Override
    public String getCharacterTag() {
        return "Enemy1";
    }

    @Override
    public void jump() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.rgb(255,30,30));
        canvas.drawRect(enemyRect,p);
    }


    @Override
    public void update() {
        enemyVelocityX = 1.0;
        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
        }
        enemyPos.y += enemyVelocityY;
        enmyWalk(this);
        enmyDash(this);


        enemyRect.set(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);


        //System.out.println(enemyVelocityY);

    }

    @Override
    public void takeDamage(int damage) {
        curHp -= damage;
        if(curHp<=0) {
            EnemyManager.killEnemy(enemyIndex);
            enemyAlive = false;
        }
    }

    @Override
    public int getEnemySize() {
        return enemySize;
    }
}
