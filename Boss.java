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
    private long flashDelay = 50;
    private long informationDelay = 10;
    private long informatioinStarted = 0;
    private boolean canFlash = true;
    private boolean bossActive = false;
    private boolean askForHelp = true;
    private Bitmap bossFlashingLeftImages[];
    private Bitmap bossFlashingRightImages[];
    private Bitmap bossInformationImages[];
    private  int bulletSpeed=6;
    private boolean informationFinished = true;
    private boolean specialGunMode = false;

 //   private boolean specialGun;
   private int flashIndex = 0;
   private int informationIndex = 0;
   private  boolean flashing = false;
    private  boolean flashingFinished = false;
    private  boolean canDrawInformation = false;

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
        bossInformationImages = new Bitmap[18];
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
        bossInformationImages[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha01);
        bossInformationImages[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha02);
        bossInformationImages[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha03);
        bossInformationImages[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha04);
        bossInformationImages[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha05);
        bossInformationImages[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha06);
        bossInformationImages[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha07);
        bossInformationImages[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha08);
        bossInformationImages[8]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha09);
        bossInformationImages[9]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha01);
        bossInformationImages[10]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha02);
        bossInformationImages[11]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha03);
        bossInformationImages[12]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha04);
        bossInformationImages[13]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha05);
        bossInformationImages[14]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha06);
        bossInformationImages[15]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha07);
        bossInformationImages[16]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha08);
        bossInformationImages[17]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha09);bossInformationImages[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.guaiwubaozha01);
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
    public void takeShockShot(Point shockPoint,float shockPower,float shockRange){
        if(!flashing&&informationFinished){
            super.takeShockShot(shockPoint,shockPower,shockRange);
        }
    }
    @Override
    public void  takeDamage(int damage){
        if(!flashing&&informationFinished){
            super.takeDamage(damage);
            bossActive = true;
        }
    }

    @Override
    public String getCharacterTag() {
        return "Boss";
    }

    @Override
    public void jump() {

    }

    public void setSpecialGunMode(){
        if(curHp<=(enemyMaxHp/4)*3&&curHp>enemyMaxHp/4){
            specialGunMode =true;
        }else{
            specialGunMode =false;
        }
        if(curHp<enemyMaxHp/4){
            bulletSpeed = 2;
        }
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
            enemyGunShots.add(new BossGunShot( context,(float) Math.cos(temp), -(float) Math.sin(temp),tempPos,bulletSpeed));
            bulletIndex++;
            if(specialGunMode){
                float temp2 = (float) (Math.atan2(x+200, y) + Math.PI + Math.PI / 2);
                float temp3 = (float) (Math.atan2(x-200, y) + Math.PI + Math.PI / 2);
                temp2 *= -1;
                temp3 *= -1;
                Point tempPos2;
                tempPos2 = new Point(enemyPos.x,enemyPos.y);
                enemyGunShots.add(new BossGunShot( context,(float) Math.cos(temp2), -(float) Math.sin(temp2),tempPos2,bulletSpeed));
                bulletIndex++;
                Point tempPos3;
                tempPos3 = new Point(enemyPos.x,enemyPos.y);
                enemyGunShots.add(new BossGunShot( context,(float) Math.cos(temp3), -(float) Math.sin(temp3),tempPos3,bulletSpeed));
                bulletIndex++;
            }
            enemyShotSound.play(1,1,1,0,0,1);
    }

    public void drawBoss(Canvas canvas, Paint p){
        if(GamePanel.HERO.playerPos.x<=enemyPos.x){
            canvas.drawBitmap(enemyBitMapLeft, null, enemyRect, p);
        }
        else{
            canvas.drawBitmap(enemyBitMapRight, null, enemyRect, p);
        }

    }


    @Override
    public void draw(Canvas canvas) {

        this.canvas = canvas;
        Paint p = new Paint();
        if(!flashing&&informationFinished) {
            drawBoss(canvas, p);
        }
        if(!informationFinished){
            drawInformation(canvas,p);
        }
        if(!flashing&&informationFinished) {
            drawEnemyHpBar(canvas);
        }
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
                if(!informationFinished){
                    canDrawInformation = true;
                }


                if (randomX == 0) {
                    enemyPos.x = GamePanel.HERO.playerPos.x + random;
                } else {
                    enemyPos.x = GamePanel.HERO.playerPos.x - random;
                }
                enemyPos.y = GamePanel.HERO.playerPos.y - random;


                flashStartedTime = System.currentTimeMillis();
                flashing = false;
                flashingFinished = false;
            }
        }else if((System.currentTimeMillis()-flashStartedTime)/100>=flashDelay){
            canFlash = true;
        }
    }

    public void drawInformation(Canvas canvas, Paint p){
        if(!informationFinished) {
                canvas.save();
                canvas.drawBitmap(bossInformationImages[informationIndex], null, enemyRect, p);
                canvas.restore();
                if((System.currentTimeMillis()-informatioinStarted)/10>=informationDelay) {
                    informatioinStarted = System.currentTimeMillis();
                    informationIndex++;
                    if (informationIndex == 17) {
                        informationIndex = 0;
                        informationFinished = true;
                        attack();
                    }
                }
            }
    }

    public void drawFlash(Canvas canvas, Paint p) {
    //    Paint p = new Paint();
        if(flashing) {
            if (enemyPos.x <= GamePanel.HERO.playerPos.x) {
                canvas.save();
                canvas.drawBitmap(bossFlashingRightImages[flashIndex], null, enemyRect, p);
                canvas.restore();
                flashIndex++;
                if (flashIndex == 8) {
                    flashIndex = 0;
                    flashingFinished = true;
                    informationFinished = false;
                }
            } else {
                canvas.save();
                canvas.drawBitmap(bossFlashingLeftImages[flashIndex], null, enemyRect, p);
                canvas.restore();
                flashIndex++;
                if (flashIndex == 8) {
                    flashIndex = 0;
                    flashingFinished = true;
                    informationFinished = false;
                }
            }
        }
    }


    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        if (bossActive) {
            falsh(this);
        }else if (Math.abs(enemyPos.x-GamePanel.HERO.playerPos.x)<=500){
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
                    enemyGunShots.remove(i);
                }
            }
        }
        askHelp();
        setSpecialGunMode();
    }
}
