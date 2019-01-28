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


    public Boss(Context context, Point p) {
        super(context,p);
        enemyMaxHp = 500;
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

        bossFlashingLeftImages[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh1);
        bossFlashingLeftImages[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh2);
        bossFlashingLeftImages[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh3);
        bossFlashingLeftImages[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh4);
        bossFlashingLeftImages[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh5);
        bossFlashingLeftImages[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh6);
        bossFlashingLeftImages[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash7);
        bossFlashingLeftImages[8]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash8);
        bossFlashingRightImages[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh1);
        bossFlashingRightImages[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh2);
        bossFlashingRightImages[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh3);
        bossFlashingRightImages[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh4);
        bossFlashingRightImages[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh5);
        bossFlashingRightImages[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftfalsh6);
        bossFlashingRightImages[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash7);
        bossFlashingRightImages[8]= BitmapFactory.decodeResource(context.getResources(),R.drawable.bossleftflash8);
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
            enemyGunShots.add(new BossGunShot( context, (float) Math.cos(temp), -(float) Math.sin(temp),tempPos,2));
            bulletIndex++;
            enemyShotSound.play(1,1,1,0,0,1);
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void falsh(Enemy enemy){
        if(canFlash){
            drawFlash();
            canFlash = false;
            final int randomX = new Random().nextInt(2); // [0, 60] + 20 => [20, 80]
            final int random = new Random().nextInt(450) + 100; // [0, 60] + 20 => [20, 80]
            if(randomX==0) {
                enemyPos.x = GamePanel.HERO.playerPos.x + random;
            }else{
                enemyPos.x = GamePanel.HERO.playerPos.x - random;
            }
            enemyPos.y = GamePanel.HERO.playerPos.y - random;

            attack();
            flashStartedTime = System.currentTimeMillis();
        }else if((System.currentTimeMillis()-flashStartedTime)/100>=flashDelay){
            canFlash = true;
        }
    }

    public void drawFlash() {
        Paint p = new Paint();
        if (enemyPos.x <= GamePanel.HERO.playerPos.x) {
            for (int i = 1; i <= 8; i++) {
                canvas.drawBitmap(bossFlashingRightImages[i], null, enemyRect, p);
            }
        } else {
            for (int i = 1; i <= 8; i++) {
                canvas.drawBitmap(bossFlashingLeftImages[i], null, enemyRect, p);
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
        super.update();
        askHelp();
    }
}
