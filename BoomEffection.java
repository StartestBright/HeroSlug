package com.jknull.heroslug;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BoomEffection{
    private Bitmap bitmap;
    private int x,y;
    private int totalFrame;
    private int currentFrame;
    private  int imageDelay = 0;
    private  int delayTime =0;
    private int frameW,frameH;
    private boolean isFinished= false;


    public BoomEffection(Bitmap bitmap, int x, int y, int totalFrame, int imDelay) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.totalFrame = totalFrame;
        delayTime = imDelay;
        frameW = bitmap.getWidth()/totalFrame;
        frameH = bitmap.getHeight();
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameH);
        canvas.drawBitmap(bitmap,x-currentFrame*frameW,y,paint);
        canvas.restore();
        booming();
    }

    public void booming(){
        if((currentFrame<totalFrame)&&imageDelay>=delayTime){
            currentFrame++;
            imageDelay = 0;
        }else if(currentFrame<totalFrame) {
            imageDelay++;
        }
            else{
            isFinished = true;
        }
    }

    public boolean isFished(){
        return  isFinished;
    }
}
