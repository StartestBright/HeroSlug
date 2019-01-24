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
import java.util.Random;

public class Boss extends Enemy {
    private Canvas canvas;
    private static int enemy1MaxHp = 50;
    public int enemySize = 50;
    private ArrayList<BossGunShot > bossBullets;
    private long flashStartedTime;
    private long flashDelay = 40;
    private boolean canFlash = true;

    public Boss(Context context, Point p, int enemyIndex) {
        super(context,p,enemyIndex);
        //this.context = context;
        bossBullets = new ArrayList<BossGunShot>();
        curHp = enemyMaxHp*10;
    }

    @Override
    public String getCharacterTag() {
        return "Boss";
    }

    @Override
    public void jump() {

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){

     //   if(1) {
           // canFire = false;
           // gunShotDelayStartTime = System.currentTimeMillis();

            int x = GamePanel.hero.playerPos.x-enemyPos.x;
            int y = GamePanel.hero.playerPos.y-enemyPos.y;
            float temp = (float) (Math.atan2(x, y) + Math.PI + Math.PI / 2);

            temp *= -1;



            BossGunShot newBullet = new BossGunShot ( context, (float) Math.cos(temp), -(float) Math.sin(temp),enemyPos.x, enemyPos.y);
          //  System.out.println((float) Math.cos(temp);
        //    System.out.println((float) Math.sin(temp);
            newBullet.setBulletSpeed(4);
            bossBullets.add(newBullet);


      //  } else if((System.currentTimeMillis()-gunShotDelayStartTime)/100 >=gunShotDelay){

        //    canFire =true;
     //   }
    }



    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;
        Paint p = new Paint();
        p.setColor(Color.rgb(255,30,30));
        canvas.drawRect(enemyRect,p);

        for(int i=0;i<bossBullets.size();i++){
            if(bossBullets.get(i).isActive())
                bossBullets.get(i).draw(canvas);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void falsh(Enemy enemy){
        if(canFlash){
            canFlash = false;
            final int randomX = new Random().nextInt(2); // [0, 60] + 20 => [20, 80]
            final int random = new Random().nextInt(450) + 100; // [0, 60] + 20 => [20, 80]
            if(randomX==0) {
                enemyPos.x = GamePanel.hero.playerPos.x + random;
            }else{
                enemyPos.x = GamePanel.hero.playerPos.x - random;
            }
            enemyPos.y = GamePanel.hero.playerPos.y - random;
            attack();
            flashStartedTime = System.currentTimeMillis();
        }else if((System.currentTimeMillis()-flashStartedTime)/100>=flashDelay){
            canFlash = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        falsh(this);


        for(int i=0;i<bossBullets.size();i++){
            bossBullets.get(i).update();
            if(!bossBullets.get(i).isActive())
                bossBullets.remove(i);
        }


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
