package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.Random;

//import android.support.annotation.RequiresApi;

public class Boss extends Enemy {
    private ArrayList<BossGunShot > bossBullets;
    private long flashStartedTime;
    private long flashDelay = 20;
    private boolean canFlash = true;
    private boolean bossActive = false;
    private boolean askForHelp = true;
    private Bitmap bossFlashingLeftImages[];
    private Bitmap bossFlashingRightImages[];
    private  int bulletSpeed=2;

 //   private boolean specialGun;
private int i = 0;
private  boolean flashing = false;
    private  boolean flashingFinished = false;

    public Boss(Context context, Point p) {
        super(context,p);
        enemyMaxHp = 2000;
        curHp = enemyMaxHp;
        enemyWidth = 50;
        enemyHeight = 100;
        enemyVelocityX=0;
        enemyVelocityY=0;
        enemyBitMapLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleft);
        enemyBitMapRight    = BitmapFactory.decodeResource(context.getResources(),R.drawable.bossright);
        enemyShotSound= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);

        enemyShotSound.load(context,R.raw.enemyshot,1);


        bossFlashingLeftImages = new Bitmap[8];
        bossFlashingRightImages = new Bitmap[8];

        bossFlashingLeftImages[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh1);
        bossFlashingLeftImages[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh2);
        bossFlashingLeftImages[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh3);
        bossFlashingLeftImages[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh4);
        bossFlashingLeftImages[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh5);
        bossFlashingLeftImages[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh6);
        bossFlashingLeftImages[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash7);
        bossFlashingLeftImages[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash8);
        bossFlashingRightImages[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash1);
        bossFlashingRightImages[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash2);
        bossFlashingRightImages[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash3);
        bossFlashingRightImages[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash4);
        bossFlashingRightImages[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash5);
        bossFlashingRightImages[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash6);
        bossFlashingRightImages[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash7);
        bossFlashingRightImages[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossrightflash8);
      //  EnemyManager enemyHelp;
    }

    public void askHelp(){
        if((curHp<=enemyMaxHp/2)&&askForHelp){
            askForHelp=false;

            EnemyManager.enemies.add(new Enemy2(context,new Point(GamePanel.HERO.playerPos.x+800,500)));
            EnemyManager.enemies.add(new Enemy2(context,new Point(GamePanel.HERO.playerPos.x+900,500)));
            EnemyManager.enemies.add(new Enemy2(context,new Point(GamePanel.HERO.playerPos.x-800,500)));
        }
    }
    public void setSpecialGun(){
        if (curHp<=enemyMaxHp/4){
            bulletSpeed =11;
        }
    }

    @Override
    public String getCharacterTag() {
        return "Boss";
    }

    @Override
    public void jump() {

    }



    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack(){

            int x = GamePanel.HERO.playerPos.x-enemyPos.x;
            int y = GamePanel.HERO.playerPos.y-enemyPos.y;

            float temp = (float) (Math.atan2(x, y) + Math.PI + Math.PI / 2);
            temp *= -1;
            Point tempPos;
            tempPos = new Point(enemyPos.x,enemyPos.y);
            enemyGunShots.add(new BossGunShot( context, (float) Math.cos(temp), -(float) Math.sin(temp),tempPos,bulletSpeed));
            bulletIndex++;
            enemyShotSound.play(1,1,1,0,0,1);
    }



    @Override
    public void draw(Canvas canvas) {

        this.canvas = canvas;
        Paint p = new Paint();
       if(GamePanel.HERO.playerPos.x<=enemyPos.x){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }
        else{
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);

        }
        drawEnemyHpBar(canvas);
       if((flashing)&&(canFlash)){
           drawFlash(canvas,p);
       }




        for(int i=0;i<enemyGunShots.size();i++){
            if(enemyGunShots.get(i).isActive()) {
                enemyGunShots.get(i).draw(canvas);
            }
        }

        for(int i=0;i<gunShotEffections.size();i++){
            if(!gunShotEffections.get(i).isFished()) {
                gunShotEffections.get(i).draw(canvas,p);
            }
        }

    }
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void falsh(Enemy enemy){
        if(canFlash){
            flashing = true;
            if(flashingFinished) {
                canFlash = false;
                final int randomX = new Random().nextInt(2); // [0, 60] + 20 => [20, 80]
                final int random = new Random().nextInt(450) + 100; // [0, 60] + 20 => [20, 80]
                if (randomX == 0) {
                    enemyPos.x = GamePanel.HERO.playerPos.x + random;
                } else {
                    enemyPos.x = GamePanel.HERO.playerPos.x - random;
                }
                enemyPos.y = GamePanel.HERO.playerPos.y - random;

                attack();
                flashStartedTime = System.currentTimeMillis();
                flashing = false;
                flashingFinished = false;
            }
        }else if((System.currentTimeMillis()-flashStartedTime)/100>=flashDelay){
            canFlash = true;
        }
    }

    public void drawFlash(Canvas canvas, Paint paint) {
        Paint p = new Paint();
        if(flashing) {
            if (enemyPos.x <= GamePanel.HERO.playerPos.x) {

                canvas.save();
                canvas.drawBitmap(bossFlashingRightImages[i], null, enemyRect, p);
                canvas.restore();
                i++;
                if (i == 8) {
                    i = 0;
                    flashingFinished = true;
                }

            } else {
                canvas.save();


                canvas.drawBitmap(bossFlashingLeftImages[i], null, enemyRect, p);
                canvas.restore();
                i++;
                if (i == 8) {
                    i = 0;
                    flashingFinished = true;
                }
            }
        }
    }


    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        if (bossActive) {
            falsh(this);
        }else if (Math.abs(enemyPos.x-GamePanel.HERO.playerPos.x)<=300){
            bossActive = true;
        }
        Paint paint = new Paint();
        if(!flashing) {
            enemyRect.set(enemyPos.x - enemyWidth, enemyPos.y - enemyHeight, enemyPos.x + enemyWidth, enemyPos.y + enemyHeight);
        }

        for(int i=0;i<enemyGunShots.size();i++) {
            if (enemyGunShots.get(i).isActive()) {
                enemyGunShots.get(i).update();
            } else if ((!enemyGunShots.get(i).boomming)&&(enemyGunShots.get(i).getTag()=="Enemy3")) {
                gunShotEffections.add(new BoomEffection(enemyGunShots.get(i).boomBitmap, enemyGunShots.get(i).bulletPos.x, enemyGunShots.get(i).bulletPos.y, 5,50));
                enemyGunShots.get(i).boomming = true;
            }


            for (int j = 0; j < gunShotEffections.size(); j++) {
                if (gunShotEffections.get(j).isFished()) {
                    gunShotEffections.remove(j);
                    System.out.println("effecremovee");
                    enemyGunShots.remove(i);
                    System.out.println("bulletremovee");
                }
            }
        }
        askHelp();
        setSpecialGun();
    }
}
