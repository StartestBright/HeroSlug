package com.jknull.heroslug;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

import static com.jknull.heroslug.MainActivity.SCREEN_HEIGHT;

public class AttackButton extends View {
    GamePanel gamePanel;

    public static int ATTACKBUTTON_WIDTH=MainActivity.SCREEN_WIDTH/8,ATTACKBUTTON_HEIGHT=MainActivity.SCREEN_WIDTH/8;
    public static int ATTACKBUTTON_XPOS=MainActivity.SCREEN_WIDTH-ATTACKBUTTON_WIDTH,ATTACKBUTTON_YPOS=SCREEN_HEIGHT-ATTACKBUTTON_HEIGHT;
    public AttackButton(Context context ,GamePanel gamePanel) {
        super(context);
        init(context);
        this.gamePanel = gamePanel;
        System.out.println(ATTACKBUTTON_XPOS+" "+ATTACKBUTTON_YPOS);
    }

    public void init(Context context){
        this.setBackgroundResource(R.drawable.attackicon);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            gamePanel.HERO.attack();
        return true;
    }
}
