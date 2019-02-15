package com.jknull.heroslug;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoyStick extends View{
    public static int JOYSTICK_WIDTH = 500;
    public static int JOYSTICK_HEIGHT = 500;
    public static int JOYSTICK_LEFT_MARGIN = 100;
    public static int JOYSTICK_YPOS = MainActivity.SCREEN_HEIGHT - 500;
    public static int JOYSTICK_XPOS = 100;


    private Rect joystickRect;
    private Bitmap joystickBitmap;


    GamePanel gamePanel;
    Hero hero;
    public JoyStick(Context context , GamePanel gamePanel) {
        super(context);
        this.gamePanel = gamePanel;
        init(context);
    }

    public JoyStick(Context context,AttributeSet attrs) {
        super(context, attrs);

    }
    public void init(Context context){
        joystickBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.joystick);
        joystickRect = new Rect(100,JOYSTICK_YPOS,JOYSTICK_XPOS+JOYSTICK_WIDTH,JOYSTICK_YPOS+JOYSTICK_HEIGHT);
        //this.setBackgroundResource(R.drawable.joystick);
        hero = gamePanel.HERO;

    }

    float xPos,yPos,temp;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();




        if(action == MotionEvent.ACTION_DOWN) {
            xPos = (event.getX()-JOYSTICK_WIDTH/2);
            yPos = (event.getY()-JOYSTICK_HEIGHT/2)*-1;
            temp = (float) Math.atan(yPos/xPos);

            if(xPos<0 && yPos>0){
                temp +=Math.PI;
            }else if(xPos<0 &&yPos <0){
                temp +=Math.PI;
            }
            temp *=-1;
            hero.setPlayerRotation(temp);
            hero.moveHorizontal((xPos/150.0)*hero.PLAYERMAXHORIZONTALSPEED);
        }
        else if(action == MotionEvent.ACTION_UP) {
            hero.moveHorizontal(0);
        }else if(action == MotionEvent.ACTION_MOVE){
            xPos = (event.getX()-JOYSTICK_WIDTH/2);
            yPos = (event.getY()-JOYSTICK_HEIGHT/2)*-1;
            temp = (float) Math.atan(yPos/xPos);

            if(xPos<0 && yPos>0){
                temp +=Math.PI;
            }else if(xPos<0 &&yPos <0){
                temp +=Math.PI;
            }
            temp *=-1;
            hero.setPlayerRotation(temp);
            hero.moveHorizontal((xPos/150.0)*hero.PLAYERMAXHORIZONTALSPEED);
        }

        //gestureDetector.onTouchEvent(event);


        return true;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.rotate((float) (GamePanel.HERO.playerRotation/Math.PI*180),JOYSTICK_XPOS+JOYSTICK_WIDTH/2,JOYSTICK_YPOS+JOYSTICK_HEIGHT/2);
        //System.out.println((float) (GamePanel.HERO.playerRotation/Math.PI*180));
        canvas.drawBitmap(joystickBitmap,null,joystickRect,null);
        canvas.restore();
    }
/*
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate((float) (GamePanel.HERO.playerRotation/Math.PI*180));
        System.out.println((float) (GamePanel.HERO.playerRotation/Math.PI*180));
        canvas.drawBitmap(joystickBitmap,null,joystickRect,null);
        canvas.restore();
    }
*/


}
