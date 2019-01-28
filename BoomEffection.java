package com.jknull.heroslug;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class BoomEffection implements GameObject {
    private Bitmap bitmap;
    private Point boomPosition;
    private int totalFrame;
    private int currentFrame = 0;
    private int frameW,frameH;
    private boolean isFished;
    protected  Canvas canvas;
    private  Context context;
    private  Rect boomRect;



    public BoomEffection(Context context,Bitmap bitmap, Point boomPosition, int totalFrame, Rect rect) {
        this.context = context;
        this.bitmap = bitmap;
        this.boomPosition=boomPosition;
        this.totalFrame = totalFrame;
        boomRect = rect;

     //   intXPosition = Math.round(boomPosition.x);
    }

    public void update(){
        booming();
    }



    public void draw(Canvas canvas){
        Paint paint = new Paint();
        this.canvas = canvas;
        canvas.save();
        canvas.clipRect(boomRect);
        canvas.drawBitmap(bitmap,null,boomRect,paint);
        canvas.restore();
        booming();
    }

    public void booming(){
        if(currentFrame<totalFrame){
            currentFrame++;
        }else {
            isFished = true;
        }
    }

    public boolean isFished(){
        return  isFished;
    }
}