package com.jknull.heroslug;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class JoyStick extends View implements GestureDetector.OnGestureListener {
    public static int JOYSTICK_WIDTH = 500;
    public static int JOYSTICK_HEIGHT = 500;
    public static int JOYSTICK_LEFT_MARGIN = 100;
    public static int JOYSTICK_YPOS = MainActivity.SCREEN_HEIGHT - 500;


    private GestureDetector gestureDetector;
    GamePanel gamePanel;
    Hero hero;
    public JoyStick(Context context , GamePanel gamePanel) {
        super(context);
        this.gamePanel = gamePanel;
        init(context);
    }

    public JoyStick(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        
    }
    public void init(Context context){
        this.setBackgroundResource(R.drawable.joystick);
        gestureDetector = new GestureDetector(this);
        hero = gamePanel.getPlayer();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        float xPos = (event.getX()-JOYSTICK_WIDTH/2);
        float yPos = (event.getY()-JOYSTICK_HEIGHT/2)*-1;

        float hypotenuse = (float)(Math.sqrt(xPos*xPos+yPos+yPos));

        float temp = (float) Math.atan(yPos/xPos);

        if(xPos<0 && yPos>0){
            temp +=Math.PI;
        }else if(xPos<0 &&yPos <0){
            temp +=Math.PI;
        }
        temp *=-1;
        hero.setPlayerRotation(temp);
        hero.moveHorizontal((xPos/150.0)*hero.PlayerMaxHorizontalSpeed);
        int action = event.getAction();

        if(action == MotionEvent.ACTION_UP)
            hero.moveHorizontal(0);

        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        double xPos = (motionEvent.getX()-150);
        hero.moveHorizontal((xPos/150.0)*5);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
