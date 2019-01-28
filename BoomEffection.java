package com.jknull.heroslug;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BoomEffection{
    private Bitmap bitmap;
    private int x,y;
    private int totalFrame;
    private int currentFrame;
    private int frameW,frameH;
    private boolean isEnd = false;

    public BoomEffection(Bitmap bitmap, int x, int y, int totalFrame) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.totalFrame = totalFrame;
        frameW = bitmap.getWidth()/totalFrame;
        frameH = bitmap.getHeight();
        System.out.println("boom!!!!!!");
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bitmap,x-currentFrame*frameW,y,paint);
        canvas.restore();
        logic();
    }

    public void logic(){
        if(currentFrame<totalFrame){
            currentFrame++;
        }else {
            isEnd = true;
        }
    }

    public boolean isEnd(){
        return  isEnd;
    }
}
