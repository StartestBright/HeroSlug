package com.jknull.heroslug;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Rect;
import android.provider.SyncStateContract;
import android.support.constraint.Constraints;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    public static int SCREEN_WIDTH,SCREEN_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        SCREEN_WIDTH =dm.widthPixels;
        SCREEN_HEIGHT=dm.heightPixels;

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        FrameLayout game = new FrameLayout(getApplicationContext());
        LinearLayout gameWidgets = new LinearLayout(this);
        GamePanel gamePanel = new GamePanel((this));

        gameWidgets.setAlpha(0.3f);

        JoyStick joyStick = new JoyStick(this,gamePanel);
        LinearLayout.LayoutParams layoutParamsForJoystick = new LinearLayout.LayoutParams(JoyStick.JOYSTICK_WIDTH,JoyStick.JOYSTICK_HEIGHT);
        joyStick.setY(JoyStick.JOYSTICK_YPOS);
        layoutParamsForJoystick.leftMargin = JoyStick.JOYSTICK_LEFT_MARGIN;
        //layoutParams.gravity = Constraints.LayoutParams.BOTTOM;
        //layoutParams.bottomMargin = 100;
        //joyStick.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT));
        joyStick.setLayoutParams(layoutParamsForJoystick);

        JumpButton jumpButton = new JumpButton(this,gamePanel);
        LinearLayout.LayoutParams layoutParamsForJump = new LinearLayout.LayoutParams(JumpButton.JUMPBUTTON_WIDTH,JumpButton.JUMPBUTTON_HEIGHT);
        jumpButton.setLayoutParams(layoutParamsForJump);
        jumpButton.setX(JumpButton.JUMPBUTTON_XPOS);
        jumpButton.setY(JumpButton.JUMPBUTTON_YPOS);


        AttackButton attackButton = new AttackButton(this,gamePanel);
        LinearLayout.LayoutParams layoutParamsForAttack = new LinearLayout.LayoutParams(AttackButton.ATTACKBUTTON_WIDTH,AttackButton.ATTACKBUTTON_HEIGHT);
        attackButton.setLayoutParams(layoutParamsForAttack);
        attackButton.setX(AttackButton.ATTACKBUTTON_XPOS);
        attackButton.setY(AttackButton.ATTACKBUTTON_YPOS);







        gameWidgets.addView(joyStick);
        gameWidgets.addView(jumpButton);
        gameWidgets.addView(attackButton);
        game.addView(gamePanel);
        game.addView(gameWidgets);
        setContentView(game);







    }

}
