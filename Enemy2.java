package com.jknull.heroslug;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy2 extends Enemy{

    private static int enemy2MaxHp = 250;
    public int enemySize=100;
    public Canvas canvas;
    private boolean canDush = true;
    private  long dushDelay = 50000;
    private  long startedDushTime;
    private  long flyDelay = 50;
   // private  long flyTime;
    private boolean canFly = true;

    public Enemy2(Context context,Point p, int enemyIndex) {
        super(context,p,enemyIndex);
        curHp = enemy2MaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;
        enemyVelocityX = 2.0;
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
        GamePanel.hero.takeDamage(50);
        while(canFly = true) {
            startedDushTime= System.currentTimeMillis();
            GamePanel.hero.playerVelocityX = -10;
            GamePanel.hero.playerPos.x+=GamePanel.hero.playerVelocityX;
            GamePanel.hero.playerVelocityY = -15;
            GamePanel.hero.playerPos.y+=GamePanel.hero.playerVelocityY;
            if(System.currentTimeMillis()-startedDushTime<=flyDelay) {
                canFly = false;
            }





        }




    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.rgb(255,255,255));
        canvas.drawRect(enemyRect,p);
    }


    @Override
    public void update() {
        //  this.attack();

        if(!enemyLanded) {
            enemyVelocityY += gravity;
        }else if(enemyLanded){
            enemyVelocityY = 0;
            enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemySize;
        }
        //   enemyPos.y += enemyVelocityY;
        enmyWalk(this);
        enmyFollow(this);
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
   public void enmyFollow(Enemy enemy){
       if(Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)<=800
               &&Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)>=400) {
           enemy.enemyInWalkMode = false;
           if (enemy.enemyPos.x < GamePanel.hero.getHeroPos().x) {
               enemy.enemyPos.x += enemy.enemyVelocityX * 2;
               canDush = false;
           } else if (enemy.enemyPos.x > GamePanel.hero.getHeroPos().x) {
               enemy.enemyPos.x -= enemy.enemyVelocityX * 2;
               canDush = false;
           }
       }
       else if(Math.abs(GamePanel.hero.getHeroPos().x-enemy.enemyPos.x)<=400) {

           if (canDush) {
               if (enemy.enemyPos.x < GamePanel.hero.getHeroPos().x) {
                   enemy.enemyPos.x += enemy.enemyVelocityX * 5;
                   canDush = false;
               } else if (enemy.enemyPos.x > GamePanel.hero.getHeroPos().x) {
                   enemy.enemyPos.x -= enemy.enemyVelocityX * 5;
                   canDush = false;
               }
           }
           if ((enemy.enemyPos.x - enemy.getEnemySize()  == GamePanel.hero.tempPlayer.right) ||
                   (enemy.enemyPos.x + enemy.getEnemySize()  == GamePanel.hero.tempPlayer.left)) {
               attack();
               enemyVelocityX = 0;
           }
           else if(System.currentTimeMillis()-startedDushTime/100 >=dushDelay){

               canDush = true;
           }
       }




       else{
           enemy.enemyInWalkMode=true;
       }
   }

    @Override
    public int getEnemySize() {
        return enemySize;
    }
}
