package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PayloadMap extends View {
    public static int PAYLOADMAPWIDTH= MainActivity.SCREEN_WIDTH/2 ,PAYLOADMAPHEIGHT=50;
    public static int PAYLOADMAPX=MainActivity.SCREEN_WIDTH/2,PAYLOADMAPY=0;
    public PayloadMap(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.CYAN);
        canvas.drawRect(PAYLOADMAPX,0,PAYLOADMAPX+PAYLOADMAPWIDTH,PAYLOADMAPY+PAYLOADMAPHEIGHT,p);
    }
}
