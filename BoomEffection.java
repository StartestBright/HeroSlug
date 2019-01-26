package com.jknull.heroslug;


import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Paint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;

public class BoomEffection {
    private Bitmap bitmap;
    private Point boomPosition;
    private int totalFrame;
    private int currentFrame;
    private int frameW,frameH;
    private boolean isFished=false;
    private  int intXPosition;
    private  int intYPostion;

    public BoomEffection(Bitmap bitmap, Point boomPosition, int totalFrame) {
        this.bitmap = bitmap;
        this.boomPosition=boomPosition;
        int a = Math.round(3.14f);
        this.totalFrame = totalFrame;
        frameW = bitmap.getWidth()/totalFrame;
        frameH = bitmap.getHeight();
     //   intXPosition = Math.round(boomPosition.x);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(boomPosition.x,boomPosition.y,boomPosition.x+frameW,boomPosition.y+frameH);
        canvas.drawBitmap(bitmap,boomPosition.x-currentFrame*frameW,boomPosition.y,paint);
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
