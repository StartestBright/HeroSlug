package com.jknull.heroslug;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class Skill1 extends View {
    public static int SKILL1_WIDTH=150, SKILL1_HEIGHT=150;
    public static int SKILL1_XPOS = MainActivity.SCREEN_WIDTH-SKILL1_WIDTH-Skill2.SKILL2_WIDTH-Skill3.SKILL3_WIDTH;
    public static int SKILL1_YPOS = MainActivity.SCREEN_HEIGHT-AttackButton.ATTACKBUTTON_HEIGHT-SKILL1_HEIGHT;
    GamePanel gamePanel;

    public Skill1(Context context , GamePanel gamePanel) {
        super(context);
        this.setBackgroundResource(R.drawable.attackicon);
        this.gamePanel = gamePanel;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){

            if(GamePanel.hero.getHeroTag() == "Soldier"){
                Soldier soldier = (Soldier)GamePanel.hero;
                soldier.setSnipingMode();
            }
        }
        return super.onTouchEvent(event);
    }
}
