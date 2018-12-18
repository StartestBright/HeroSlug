package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PlayerGunShot extends View implements GameObject{

    private int bulletColor;
    private boolean active = true;
    private float bulletSpeed = 150f;
    private float xPos =500,yPos=500,velocityX=0,velocityY=0;
    private float radius=10;
    private Bitmap bulletImage;

    private int screenWidth = MainActivity.SCREEN_WIDTH;
    private int screenHeight = MainActivity.SCREEN_HEIGHT;

    BitmapFactory.Options opt = new BitmapFactory.Options();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PlayerGunShot(Context context,float velocityX,float velocityY,float xPos,float yPos) {
        super(context);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.xPos = xPos;
        this.yPos = yPos;

        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context){
        opt.inMutable = true;
        bulletImage = BitmapFactory.decodeResource(getResources(),R.drawable.gunshot);
        bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        bulletImage.setWidth(800);
        bulletImage.setHeight(800);
        bulletColor = Color.YELLOW;




    }

    @Override
    public void update() {
        if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += (velocityY * bulletSpeed);
        }
        if(xPos>screenWidth||xPos<0||yPos>screenHeight||yPos<0){
            active = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(active) {
            super.draw(canvas);
            //canvas.drawBitmap(bulletImage,0,0,null);
            Paint paint = new Paint();
            paint.setColor(bulletColor);
            canvas.drawCircle(xPos, yPos, radius, paint);

        }

    }
    public Point getBulletPoint(){
        Point p = new Point();
        p.set((int)xPos,(int)yPos);
        return p;
    }
    public boolean isActive(){
        return active;
    }

}
