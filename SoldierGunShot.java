package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
//import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SoldierGunShot extends HeroGunShot implements GameObject{
    private int screenWidth = MainActivity.SCREEN_WIDTH;
    private int screenHeight = MainActivity.SCREEN_HEIGHT;

    BitmapFactory.Options opt = new BitmapFactory.Options();

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SoldierGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        //super(context,velocityX,velocityY,xPos,yPos);
        super(context,velocityX,velocityY,xPos,yPos,false);
        init (context);


    }


    public void init(Context context){
        //opt.inMutable = true;
        //bulletImage = BitmapFactory.decodeResource(getResources(),R.drawable.soldier_bullet_image);
        //bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
        //bulletColor = Color.YELLOW;
        heroGunShotRectSizeX=20;
        herogunShotRectSizeY=20;
        if(velocityX>=0)
            heroGunShotBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_bullet_image);
        else
            heroGunShotBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.soldier_bullet_imag_l);
        gunShotDamage =25;
        active = true;
        bulletSpeed = 150f;




    }

    @Override
    public void update() {
        if(active)
            super.update();
        /*if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += (velocityY * bulletSpeed);
        }
        if(xPos>screenWidth||xPos<0||yPos>screenHeight||yPos<0){
            active = false;
        }
        collisionDetect();
        */
    }

    @Override
    public void draw(Canvas canvas) {
        if(active) {
            super.draw(canvas);
            //canvas.drawBitmap(bulletImage,0,0,null);
            /*Paint paint = new Paint();
            paint.setColor(bulletColor);
            canvas.drawCircle(xPos, yPos, radius, paint);*/

        }

    }
    /*public Point getBulletPoint(){
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
    }*/

/*
    public void collisionDetect(){
        if(this.active){

            for(int i=0;i<EnemyManager.enemies.size();i++) {
                Enemy enemy = EnemyManager.enemies.get(i);
                if(enemy.isAlive()){
                if (xPos - radius <=enemy.getEnemyPos().x+enemy.getEnemySize()&& //if  collide with enemy
                        xPos+radius>=enemy.getEnemyPos().x-enemy.getEnemySize()&&
                        yPos-radius<=enemy.getEnemyPos().y+enemy.getEnemySize()&&
                        yPos+radius>=enemy.getEnemyPos().y-enemy.getEnemySize()) {

                        EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                        active = false;
                        return;

                        }
                }
            }
        }
    }
*/

}
