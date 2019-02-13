package com.jknull.heroslug;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;

//import android.support.annotation.RequiresApi;

public class Enemy3 extends Enemy{
 //   private Bitmap enemyBullet3Boom;


    protected SoundPool enemyRealsedBoomSound;


    @Override
    public void init(){
        enemyBoom = new Bitmap[12];
        enemyBoom[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_01);
        enemyBoom[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_02);
        enemyBoom[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_03);
        enemyBoom[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_04);
        enemyBoom[4]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_05);
        enemyBoom[5]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_06);
        enemyBoom[6]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_07);
        enemyBoom[7]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_08);
        enemyBoom[8]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_09);
        enemyBoom[9]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_10);
        enemyBoom[10]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_11);
        enemyBoom[11]= BitmapFactory.decodeResource(context.getResources(),R.drawable.behit_jiguanpao_c_12);

    }






    public Enemy3(Context context,Point p) {
        super(context,p);
        enemyMaxHp = 100; // Again you didn't set max hp
        curHp = enemyMaxHp;
        enemyLanded = false;
        enemyWidth = 200;
        enemyHeight = 150;
        enemyVelocityX = 2.0;
        enemyVelocityY = 0;
        enemyBitMapRight = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy3right);
        enemyBitMapLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy3left);
        enemyPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-enemyHeight-600;
        enemyRealsedBoomSound= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);

        enemyRealsedBoomSound.load(context,R.raw.boomreleased,1);
   //     enemyBullet3Boom = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemyboom);
    }

    @Override
    public String getCharacterTag() {
        return "Enemy3";
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
            canFire = false;
            gunShotDelayStartTime = System.currentTimeMillis();
            Point tempPoint = new Point(enemyPos.x,enemyPos.y+105);
            enemyGunShots.add(new EnemyReleaseBoom( context,0, 1, tempPoint,6));
            bulletIndex++;
            enemyRealsedBoomSound.play(1,1,1,0,0,1);
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update() {
        enmyWalk(this);
        enmyFollow(this);
        super.update();
        enemyDie();
    }




      // @Override
     //  public int getEnemySize() {
     //   return enemySize;
    //}

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void enmyFollow(Enemy enemy){
        if(curHp>0){
            if((Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=800)
                    &&(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)>=10)) {
                enemy.enemyInWalkMode = false;
                if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                    enemyVelocityX =4;
                }else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                    enemyVelocityX =-4;
                }
            }
            else if(Math.abs(GamePanel.HERO.getHeroPos().x-enemy.enemyPos.x)<=10){
                enemyVelocityX=0;
                if (canFire == true) {
                    this.attack();
                } else if ((System.currentTimeMillis() - gunShotDelayStartTime) / 100 >= gunShotDelay) {
                    canFire = true;
                }
            }
            else{
                enemy.enemyInWalkMode=true;
            }
        }

    }


    @Override
    public void takeDamage(int damage) {
        if(curHp>=0){
        curHp -= damage;}
        if(curHp<=0) {
            curHp = 0;
           enemyVelocityY = gravity;
           enemyInWalkMode = false;
          //  EnemyManager.killEnemy();
            //  enemyAlive = false;
        }
    }

    public void enemyDie(){
        if(Math.abs(enemyRect.bottom -(MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT))<=5){
            enemyBoomSound.play(1,1,1,0,0,1);
            EnemyManager.killEnemy();
            enemyAlive = false;

        }
    }
}
