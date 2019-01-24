package com.jknull.heroslug;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class Skill3 extends View {
    public static int SKILL3_WIDTH=150, SKILL3_HEIGHT=150;
    public static int SKILL3_XPOS = MainActivity.SCREEN_WIDTH-SKILL3_WIDTH;
    public static int SKILL3_YPOS = MainActivity.SCREEN_HEIGHT-AttackButton.ATTACKBUTTON_HEIGHT-SKILL3_HEIGHT;

    public Skill3(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.attackicon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            GamePanel.HERO.setUltimateSkillOn();
        }
        return true;

    }
}
