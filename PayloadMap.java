package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class PayloadMap extends View {
    public static int PAYLOADMAPWIDTH= MainActivity.SCREEN_WIDTH/2 ,PAYLOADMAPHEIGHT=50;
    public static int PAYLOADMAPX=MainActivity.SCREEN_WIDTH/2,PAYLOADMAPY=100;


    private static int pointerX,pointerY;
    private static int pointerSize =25;


    public PayloadMap(Context context) {
        super(context);
        pointerX = PAYLOADMAPX;
        pointerY = PAYLOADMAPY-2;
        pointerX = PAYLOADMAPX;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);



        Paint paintForMapFrame = new Paint();
        paintForMapFrame.setStyle(Paint.Style.STROKE);
        paintForMapFrame.setColor(Color.rgb(132,144,132));
        paintForMapFrame.setStrokeWidth(5);
        Path path = new Path();
        path.moveTo(pointerX,pointerY);
        path.lineTo(pointerX-pointerSize,pointerY-pointerSize);
        path.lineTo(pointerX+pointerSize,pointerY-pointerSize);
        path.lineTo(pointerX,pointerY);
        path.close();
        Paint paintForPointer = new Paint();
        paintForPointer.setStyle(Paint.Style.FILL);
        paintForPointer.setColor(Color.BLUE);
        canvas.drawPath(path,paintForPointer);
        canvas.drawRect(PAYLOADMAPX,PAYLOADMAPY,PAYLOADMAPX+PAYLOADMAPWIDTH,PAYLOADMAPY+PAYLOADMAPHEIGHT,paintForMapFrame);

        Paint paintForMapFill = new Paint();
        paintForMapFill.setColor(Color.rgb(132,144,132));
        paintForMapFill.setStyle(Paint.Style.FILL);
        canvas.drawRect(PAYLOADMAPX,PAYLOADMAPY,pointerX,PAYLOADMAPY+PAYLOADMAPHEIGHT,paintForMapFill);

    }

    public static void movePayloadPointer(float ratio){
        pointerX = (int) (PAYLOADMAPX + (PAYLOADMAPWIDTH*ratio));

    }


}
