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
    private ArrayList<EnemyGunShot1> bossBullets;
    private long flashStartedTime;
    private long flashDelay = 100;
    private boolean canFlash = true;

    public Boss(Context context, Point p, int enemyIndex) {
        super(context,p,enemyIndex);
        //this.context = context;
        bossBullets = new ArrayList<EnemyGunShot1>();
        curHp = enemy1MaxHp;
        enemyRect = new Rect(enemyPos.x-enemySize,enemyPos.y-enemySize,enemyPos.x+enemySize,enemyPos.y+enemySize);
        enemyAlive =true;

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

        if(canFire) {
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();

            int x = GamePanel.HERO.playerPos.x-enemyPos.x;
            int y = GamePanel.HERO.playerPos.y-enemyPos.y;
            float temp = (float) (Math.atan2(x, y) + Math.PI + Math.PI / 2);

            temp *= -1;



            EnemyGunShot1 newBullet = new EnemyGunShot1( context, (float) Math.cos(temp)/10, (float) Math.sin(temp)/10,  enemyPos.x, enemyPos.y);

            newBullet.setBulletSpeed(2);
            bossBullets.add(newBullet);


        } else if((System.currentTimeMillis()-gunShotDelayStartTime)/100 >=gunShotDelay){

            canFire =true;
        }
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
            final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80]
            enemyPos.x = GamePanel.HERO.playerPos.x + random;
            enemyPos.y = GamePanel.HERO.playerPos.y - random;
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
