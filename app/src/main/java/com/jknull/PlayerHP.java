package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.w3c.dom.Text;

public class PlayerHP extends View implements UIObject{
    private int maxHp;
    private int curHp;
    private int hegith,width = MainActivity.SCREEN_WIDTH/2;
    public static boolean HERODEAD =false;


    public PlayerHP(Context context,int maxHp) {
        super(context);
        this.maxHp = maxHp;
        init(context);
    }

    public void init(Context context){
        curHp = maxHp;
    }

    @Override
    public void update() {
        if(curHp<=0)
            curHp =0;
        else if(curHp>=maxHp)
            curHp = maxHp;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint solidPaint = new Paint();
        solidPaint.setStyle(Paint.Style.FILL);
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);

        solidPaint.setColor(Color.RED);
        strokePaint.setColor(Color.RED);
        strokePaint.setStrokeWidth(5);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(50);
        canvas.drawText("HP",0,50,textPaint);

        float curHpRatio = (float)curHp/maxHp;

        if(curHpRatio>=1)
            curHpRatio=1;
        else if(curHpRatio<=0)
            curHpRatio=0;

        canvas.drawRect( 0,50,width,150,strokePaint);
        canvas.drawRect(0,50,curHpRatio*width,150,solidPaint);


    }

    public void getDamage(int damage){
        curHp -=damage;
        if(curHp<=0){
            HERODEAD  = true;
        }
    }
    public void getHeal(int heal){
        if(curHp<=maxHp)
            curHp +=heal;
    }

}
