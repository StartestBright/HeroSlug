package com.jknull.heroslug;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;

//import android.support.annotation.RequiresApi;

public class Enemy1 extends Enemy {


//    private ArrayList<EnemyGunShot1> enemy1Bullets;

    public Enemy1(Context context,Point p) {
        super(context,p);
      //  enemyVelocityX=4.0;
        enemyMaxHp = 200;

        curHp = enemyMaxHp;

        enemyHeight= 100;
        enemyWidth = 50;
        enemyBitMapRight = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy1);
        enemyBitMapLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy1left);


        enemyShotSound= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);

        enemyShotSound.load(context,R.raw.enemyshot,1);

   //     enemy1Bullets = new ArrayList<EnemyGunShot1>();
    }

    @Override
    public String getCharacterTag() {
        return "Enemy1";
    }

    @Override
    public void jump() {

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){
        if(canFire) {
            enemyVelocityX=0;
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
           // EnemyGunShot1 newBullet = new EnemyGunShot1( context,1, 0, enemyPos.x, enemyPos.y);
            Point tempPoint = new Point(enemyPos.x,enemyPos.y);
            enemyGunShots.add(new EnemyGunShot1( context,1, 0, tempPoint,2));

            enemyShotSound.play(1,1,1,0,0,1);

         }
    }





   //@RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void update() {
        landDetect();
        enmyWalk(this);
        enmyFollow(this);


        super.update();
    }
}

