package com.jknull.heroslug;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BoomEffectionMultipleImages extends BoomEffection{




private  int boomFrame = 0;
private Rect enemy3Rect;
    public BoomEffectionMultipleImages(Bitmap bitmap[], int x, int y, int totalFrame, int imDelay) {
        super(bitmap,x,y,totalFrame,imDelay);
        enemy3Rect = new Rect(x,y-100,x+400,y+200);

    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        if(canDraw) {
            canvas.save();
            canvas.drawBitmap(bitmap[currentFrame],null,enemy3Rect,paint);
            canvas.restore();
            booming();
        }
}
    @Override
    public void booming(){
        if((System.currentTimeMillis()-startedTime)/10>=delayTime){
            startedTime = System.currentTimeMillis();
            currentFrame++;
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
