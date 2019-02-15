package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
//import android.support.annotation.RequiresApi;

public class Background {
    private Bitmap image;
    private Rect backGroundRect[];
    private int x1,y1,x2,y2;

    public Background(Context context){
        x1=0;x2=x1+MainActivity.SCREEN_WIDTH;y1=0;y2=0;
        backGroundRect = new Rect[2];
        backGroundRect[0] = new Rect();
        backGroundRect[1] = new Rect();
        image = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg3);
        //image.setWidth(MainActivity.SCREEN_WIDTH);
        //image.setHeight(MainActivity.SCREEN_HEIGHT);
    }
    public void update(){
        backGroundRect[0].set(x1,y1,x1+MainActivity.SCREEN_WIDTH,y1+MainActivity.SCREEN_HEIGHT);
        backGroundRect[1].set(x2,y2,x2+MainActivity.SCREEN_WIDTH,y2+MainActivity.SCREEN_HEIGHT);

        if(backGroundRect[1].left<=0){
            x1=0;
            x2=x1+MainActivity.SCREEN_WIDTH;
        }else if(x1>0){
            x2 = 0;
            x1 = -MainActivity.SCREEN_WIDTH;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,null,backGroundRect[0],null);
        canvas.drawBitmap(image,null,backGroundRect[1],null);

    }

    public void moveBg(float val){
        x1-= val;x2-=val;
    }

}
