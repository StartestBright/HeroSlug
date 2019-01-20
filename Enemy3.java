package com.jknull.heroslug;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy3 extends Enemy {
    private static int enemy3MaxHp = 50;
    public int enemySize = 50;

    public Enemy3(Context context,Point p, int enemyIndex) {
        super(context,p, enemyIndex);
        curHp = enemy3MaxHp;
        enemyRect = new Rect(enemyPos.x - enemySize, enemyPos.y - enemySize, enemyPos.x + enemySize, enemyPos.y + enemySize);
        enemyAlive = true;
    }

    @Override
    public String getCharacterTag() {
        return "Enemy3";
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
        p.setColor(Color.rgb(200, 200, 200));
        canvas.drawRect(enemyRect, p);
    }


    @Override
    public void update() {
    /*      if(!enemyLanded) {
         enemyVelocityY += gravity;
           }else if(enemyLanded) {
              enemyVelocityY = -500;
              enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
          }*/
        enemyVelocityY = 10;
        enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize-400;
       if(enemyPos.x == GamePanel.hero.getHeroPos().x) {


        }


        else if (enemyPos.x < GamePanel.hero.getHeroPos().x) {
            enemyPos.x += 5;
        } else if (enemyPos.x > GamePanel.hero.getHeroPos().x) {
            enemyPos.x -= 5;


             }
            enemyPos.y += enemyVelocityY;
            enemyPos.x += enemyVelocityX;
            enemyRect.set(enemyPos.x - enemySize, enemyPos.y - enemySize, enemyPos.x + enemySize, enemyPos.y + enemySize);



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
