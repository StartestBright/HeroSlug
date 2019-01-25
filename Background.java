package com.jknull.heroslug;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
//import android.support.annotation.RequiresApi;

public class Background {
    private Bitmap image;
    private int x, y,dx;

    public Background(Bitmap res){
        image = res;
        //image.setWidth(MainActivity.SCREEN_WIDTH);
        //image.setHeight(MainActivity.SCREEN_HEIGHT);
    }
    public void update(){
        x+=dx;
        if(x<-MainActivity.SCREEN_WIDTH){
            x=0;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if(x<0){
            canvas.drawBitmap(image,x+MainActivity.SCREEN_WIDTH,y,null);

        }
    }

    public void moveBg(float val){
        x-= val;
    }

}
