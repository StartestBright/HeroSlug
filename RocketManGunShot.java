package com.jknull.heroslug;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class RocketManGunShot extends HeroGunShot {
    Context context;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public RocketManGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context,velocityX,velocityY,xPos,yPos,true);
        this.context = context;
        herogunShotRectSizeY = 30;
        heroGunShotRectSizeX = 50;
        gunShotDamage =300;
        active = true;
        bulletColor = Color.GREEN;
        bulletSpeed = 100f;
        rocketmanRocketRange = 300;
        bulletSpeed = 50;
        if(velocityX<0){
            heroGunShotBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket_shot_l);
        }else
            heroGunShotBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket_shot);
        //heroGunShotBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket_shot);
        //heroGunShotMatrix = new Matrix();
        //heroGunShotMatrix.setRotate((float) (GamePanel.HERO.playerRotation/Math.PI*180),xPos,yPos);
        //heroGunShotMatrix.mapRect(heroGunShotRect,heroGunShotRect);
        //heroGunShotBitmap = Bitmap.createBitmap(heroGunShotBitmap,0,0,heroGunShotBitmap.getWidth(),heroGunShotBitmap.getHeight(),heroGunShotMatrix,true);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update() {
        if(active)
            super.update();

    }

    @Override
    public void draw(Canvas canvas) {
        if(active)
            super.draw(canvas);
        //canvas.drawBitmap(heroGunShotBitmap);
    }
}
