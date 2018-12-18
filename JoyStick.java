package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class JoyStick extends View implements GestureDetector.OnGestureListener {
    public static int JOYSTICK_WIDTH = 500;
    public static int JOYSTICK_HEIGHT = 500;
    public static int JOYSTICK_LEFT_MARGIN = 100;
    public static int JOYSTICK_YPOS = MainActivity.SCREEN_HEIGHT - 500;


    private GestureDetector gestureDetector;
    GamePanel gamePanel;
    Player player;
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
        player = gamePanel.getPlayer();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        float xPos = (event.getX()-JOYSTICK_WIDTH/2);
        float yPos = (event.getY()-JOYSTICK_HEIGHT/2)*-1;

        float hypotenuse = (float)(Math.sqrt(xPos*xPos+yPos+yPos));



        float temp = (float) Math.atan(yPos/xPos);
        //System.out.println("xPos = "+xPos+" yPos = "+yPos+" hypo : "+hypotenuse);
        //System.out.println(temp/Math.PI*180);



        if(xPos<0 && yPos>0){
            temp +=Math.PI;
        }else if(xPos<0 &&yPos <0){
            temp +=Math.PI;
        }
        /*if(Math.atan(yPos/xPos)/Math.PI*180 < 0 && Math.atan(yPos/xPos)/Math.PI*180>= -90){
            temp +=Math.PI;
        }//else if(temp>=180 && temp <270){
*/
        //}
        temp *=-1;
        player.setPlayerRotation(temp);

        //System.out.println(Math.acos(xPos/hypotenuse));
        //System.out.println("AFTER = "+temp/Math.PI*180*-1);

        player.moveHorizontal((xPos/150.0)*Player.PlayerMaxHorizontalSpeed);
        int action = event.getAction();

        if(action == MotionEvent.ACTION_UP)
            player.moveHorizontal(0);


        /*curX = event.getX();
        curY = event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                oldX = getX();
                oldY = getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if(oldX>0 || oldY>0){
                    player.moveHorizontal((int)(curX-oldX));
                }
                oldX= curX;
                oldY= curY;


                //playerPoint.set((int)event.getX(),(int)event.getY());
                break;


        }*/

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
        player.moveHorizontal((xPos/150.0)*5);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
