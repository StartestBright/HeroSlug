package com.jknull.heroslug;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JumpButton extends View {

    public static int JUMPBUTTON_WIDTH = MainActivity.SCREEN_WIDTH/8;
    public static int JUMPBUTTON_HEIGHT = MainActivity.SCREEN_HEIGHT/8;

    public static int JUMPBUTTON_XPOS = MainActivity.SCREEN_WIDTH-JUMPBUTTON_WIDTH-AttackButton.ATTACKBUTTON_WIDTH;
    public static int JUMPBUTTON_YPOS = MainActivity.SCREEN_HEIGHT-JUMPBUTTON_HEIGHT-50;
    GamePanel gamePanel;

    public JumpButton(Context context,GamePanel gamePanel) {
        super(context);
        this.gamePanel  = gamePanel;
        init(context);
        System.out.println(JUMPBUTTON_XPOS+" "+JUMPBUTTON_YPOS);

    }

    public JumpButton(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {

        this.setBackgroundResource(R.drawable.jumpicon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //event.getPointerId(0);

        if(action == MotionEvent.ACTION_UP){

            if(gamePanel.hero.isLaneded()){
                gamePanel.hero.jump();

            }
        }
        return true;

    }
}
