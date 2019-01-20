package com.jknull.heroslug;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy2 extends Enemy{

    private static int enemy2MaxHp = 500;
    public int enemySize=100;

    public Enemy2(Context context,Point p, int enemyIndex) {
        super(context,p,enemyIndex);
        curHp = enemy2MaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;
    }

    @Override
    public String getCharacterTag() {
        return "Enemy2";
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
        p.setColor(Color.rgb(255,255,255));
        canvas.drawRect(enemyRect,p);
    }


    @Override
    public void update() {
        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
        }
        enemyPos.y += enemyVelocityY;
        enemyPos.x += enemyVelocityX;
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
