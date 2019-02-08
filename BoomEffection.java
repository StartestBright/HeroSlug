package com.jknull.heroslug;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BoomEffection{


    public BoomEffection(Bitmap bitmap, int x, int y, int totalFrame, int imDelay) {
    protected   long delayTime =30;
    protected  int frameW,frameH;
    protected  long startedTime;


    public BoomEffection(Bitmap bitmap[], int x, int y, int totalFrame, int imDelay) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.totalFrame = totalFrame;
        delayTime = imDelay;
        frameW = bitmap[0].getWidth()/totalFrame;
        frameH = bitmap[0].getHeight();
    }

    public void draw(Canvas canvas, Paint paint){
        if(canDraw) {
            canvas.save();
            canvas.clipRect(x, y, x + frameW, y + frameH);
            canvas.drawBitmap(bitmap[0], x - currentFrame * frameW, y, paint);
            canvas.restore();
            booming();
        }


    }

    public void booming(){
        if((System.currentTimeMillis()-startedTime)/10>=delayTime){
            currentFrame++;
            startedTime = System.currentTimeMillis();
        }

        if(currentFrame>=totalFrame){
            isFinished = true;
            canDraw = false;
        }
    }

    public boolean isFished(){
        return  isFinished;
    }
}
