package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
//import android.support.annotation.RequiresApi;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Debug;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

public class HeroGunShot extends View implements GameObject{
    /*class RocketExplosionAnim extends Thread{
        @Override
        public void run() {
            super.run();
            for(int i=0;i<12;i++){
                rocketExplosionBitmapIndex = i;

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/


    protected Matrix heroGunShotMatrix;
    protected int gunShotDamage;
    protected int bulletColor;
    protected boolean active = true;
    protected float bulletSpeed = 150f;
    protected float xPos ,yPos,velocityX=0,velocityY=0;
    protected Bitmap bulletImage;
    protected boolean isRocket = false;

    private int screenWidth = MainActivity.SCREEN_WIDTH;
    private int screenHeight = MainActivity.SCREEN_HEIGHT;
    //private long destroyStartTime,destroyDelayTime=1;
    private boolean destroy = false;
    private boolean loaded = false;
    //private Bitmap rocketExplosionBitmaps[];
    //private int rocketExplosionBitmapIndex = 0;
    //private RocketExplosionAnim explosionAnim;


    protected RectF heroGunShotRect;
    protected int herogunShotRectSizeY;
    protected int heroGunShotRectSizeX;
    protected Bitmap heroGunShotBitmap;
    protected float rocketmanRocketRange;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HeroGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos, boolean isRocket){
        super(context);
        heroGunShotRect = new RectF();
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isRocket = isRocket;
        init(context);
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(Context context){
        /*rocketExplosionBitmaps = new Bitmap[12];
        rocketExplosionBitmaps[0] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_01);
        rocketExplosionBitmaps[1] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_02);
        rocketExplosionBitmaps[2] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_03);
        rocketExplosionBitmaps[3] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_04);
        rocketExplosionBitmaps[4] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_05);
        rocketExplosionBitmaps[5] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_06);
        rocketExplosionBitmaps[6] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_07);
        rocketExplosionBitmaps[7] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_08);
        rocketExplosionBitmaps[8] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_09);
        rocketExplosionBitmaps[9] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_10);
        rocketExplosionBitmaps[10] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_11);
        rocketExplosionBitmaps[11] = BitmapFactory.decodeResource(getResources(),R.drawable.behit_jiguanpao_c_12);
        explosionAnim = new RocketExplosionAnim();
*/
        Hero.rocketExplosionSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                loaded = true;
            }
        });
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update() {
        if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += (velocityY * bulletSpeed);
            collisionDetect();
        }
        heroGunShotRect.set((int)xPos- heroGunShotRectSizeX,(int)yPos- herogunShotRectSizeY,(int)xPos+ heroGunShotRectSizeX,(int)yPos+ herogunShotRectSizeY);
        if(xPos>screenWidth||xPos<0||yPos>screenHeight||yPos<0){
            //active = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(active) {
            super.draw(canvas);
            if(!destroy&&heroGunShotBitmap!=null) {
                //canvas.save();
                //canvas.rotate((float) (GamePanel.HERO.playerRotation/Math.PI*180));
                canvas.drawBitmap(heroGunShotBitmap, null, heroGunShotRect, null);
                //canvas.drawBitmap(heroGunShotBitmap,);
                //canvas.restore();
            }//else
                //canvas.drawBitmap(rocketExplosionBitmaps[rocketExplosionBitmapIndex],null,heroGunShotRect,null);
        }

    }
    public Point getBulletPoint(){
        Point p = new Point();
        p.set((int)xPos,(int)yPos);
        return p;
    }
    public void setBulletSpeed(float speed){
        bulletSpeed =speed;
    }
    public void setBulletDamage(int damage){
        gunShotDamage = damage;
    }
    public boolean isActive(){
        return active;
    }


    protected boolean explosionSoundPlayed = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void collisionDetect(){
        if(this.active){
            if(!isRocket) {
                for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                    Enemy enemy = EnemyManager.enemies.get(i);
                    if ((heroGunShotRect.left <= enemy.getEnemyPos().x + enemy.getEnemyWidth() && //if  collide with enemy
                            heroGunShotRect.right>= enemy.getEnemyPos().x - enemy.getEnemyWidth() &&
                            heroGunShotRect.top <= enemy.getEnemyPos().y + enemy.getEnemyHeight() &&
                            heroGunShotRect.bottom >= enemy.getEnemyPos().y - enemy.getEnemyHeight()
                    )&&enemy.curHp>0) {

                        if (enemy.isAlive() && active && !isRocket) {
                            EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                            active = false;
                            return;
                        }

                    }

                }
            }else if(isRocket){
                if(heroGunShotRect.bottom>=GamePanel.FLOOR.getFloorRect().top){
                    //destroy = true;
                    //explosionAnim.start();
                    //soundPool.play(sound,1,1,1,0,1);
                    //if(loaded) {
                    if(!explosionSoundPlayed) {
                        Hero.rocketExplosionSoundPool.play(Hero.ROCKETEXPLOSIONSOUND, 1, 1, 1, 0, 1);
                        explosionSoundPlayed = true;
                    }
                    for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                        Enemy enemy = EnemyManager.enemies.get(i);

                            if (enemy.isAlive() && active) {
                                    double dist = Math.sqrt(((enemy.enemyPos.x - xPos) * (enemy.enemyPos.x - xPos)) + ((enemy.enemyPos.y - yPos) * (enemy.enemyPos.y - yPos)));
                                    if (dist <= rocketmanRocketRange) {
                                        enemy.takeDamage((int) ((rocketmanRocketRange - dist) / rocketmanRocketRange * gunShotDamage));
                                    }

                            }

                    }
                        active= false;
                        //explosionAnim.start();
                    //}
                }
                for (int i = 0; i < EnemyManager.enemies.size(); i++) {
                    Enemy enemy = EnemyManager.enemies.get(i);
                    if ((heroGunShotRect.left <= enemy.getEnemyPos().x + enemy.getEnemyWidth() && //if  collide with enemy
                            heroGunShotRect.right >= enemy.getEnemyPos().x - enemy.getEnemyWidth() &&
                            heroGunShotRect.top <= enemy.getEnemyPos().y + enemy.getEnemyHeight() &&
                            heroGunShotRect.bottom >= enemy.getEnemyPos().y - enemy.getEnemyHeight())&&enemy.curHp>0) {
                        if (enemy.isAlive() && active) {
                            for (int j = 0; j < EnemyManager.enemies.size(); j++) {
                                Enemy enemy1 = EnemyManager.enemies.get(j);
                                double dist = Math.sqrt(((enemy1.enemyPos.x - xPos) * (enemy1.enemyPos.x - xPos)) + ((enemy1.enemyPos.y - yPos) * (enemy1.enemyPos.y - yPos)));
                                if (dist <= rocketmanRocketRange) {
                                    enemy1.takeDamage((int) ((rocketmanRocketRange - dist) / rocketmanRocketRange * gunShotDamage));
                                }
                                //destroy = true;
                                //explosionAnim.start();
                                //if(loaded) {
                                if(!explosionSoundPlayed) {
                                    Hero.rocketExplosionSoundPool.play(Hero.ROCKETEXPLOSIONSOUND, 1, 1, 1, 0, 1);
                                    explosionSoundPlayed = true;
                                }
                                    active=false;
                                    //explosionAnim.start();
                                //}
                            }
                        }
                    }
                }
            }
        }
    }
    public void gunshotMoveByPlayer(int value){
        xPos-= value;
    }



}
