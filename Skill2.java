package com.jknull.heroslug;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class Skill2 extends View {
    public static int SKILL2_WIDTH=150, SKILL2_HEIGHT=150;
    public static int SKILL2_XPOS = MainActivity.SCREEN_WIDTH-SKILL2_WIDTH-Skill3.SKILL3_WIDTH;
    public static int SKILL2_YPOS = MainActivity.SCREEN_HEIGHT-SKILL2_HEIGHT-AttackButton.ATTACKBUTTON_HEIGHT;

    public Skill2(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.attackicon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
