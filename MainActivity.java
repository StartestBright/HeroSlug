package com.jknull.heroslug;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
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
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    public static int SCREEN_WIDTH,SCREEN_HEIGHT;
    public static int RIGHT_MARGIN_OFFSET = 650;

    public static GamePanel gamePanel;
    public static PlayerHP playerHP;
    public static PayloadMap payloadMap;


    public static Skill1 skill1;
    public static Skill2 skill2;
    public static Skill3 ultimateSkill;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        SCREEN_WIDTH =dm.widthPixels;
        SCREEN_HEIGHT=dm.heightPixels;


        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        playerHP =new PlayerHP(getApplicationContext(),Soldier.SOLDIERMAXHP);
        FrameLayout game = new FrameLayout(getApplicationContext());
        FrameLayout gameWidgets = new FrameLayout(this);
        gamePanel = new GamePanel((this));


        gameWidgets.setAlpha(0.5f);

        JoyStick joyStick = new JoyStick(this,gamePanel);
        LinearLayout.LayoutParams layoutParamsForJoystick = new LinearLayout.LayoutParams(JoyStick.JOYSTICK_WIDTH,JoyStick.JOYSTICK_HEIGHT);
        joyStick.setY(JoyStick.JOYSTICK_YPOS);
        layoutParamsForJoystick.leftMargin = JoyStick.JOYSTICK_LEFT_MARGIN;
        //layoutParams.gravity = Constraints.LayoutParams.BOTTOM;
        //layoutParams.bottomMargin = 100;
        //joyStick.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT));
        joyStick.setLayoutParams(layoutParamsForJoystick);

        JumpButton jumpButton = new JumpButton(this,gamePanel);
        FrameLayout.LayoutParams layoutParamsForJump = new FrameLayout.LayoutParams(JumpButton.JUMPBUTTON_WIDTH,JumpButton.JUMPBUTTON_HEIGHT);
        jumpButton.setLayoutParams(layoutParamsForJump);
        //layoutParamsForJump.rightMargin = 400;
        jumpButton.setX(JumpButton.JUMPBUTTON_XPOS);
        jumpButton.setY(JumpButton.JUMPBUTTON_YPOS);


        AttackButton attackButton = new AttackButton(this,gamePanel);
        LinearLayout.LayoutParams layoutParamsForAttack = new LinearLayout.LayoutParams(AttackButton.ATTACKBUTTON_WIDTH,AttackButton.ATTACKBUTTON_HEIGHT);
        attackButton.setLayoutParams(layoutParamsForAttack);
        attackButton.setX(AttackButton.ATTACKBUTTON_XPOS);
        attackButton.setY(AttackButton.ATTACKBUTTON_YPOS);

        skill1 = new Skill1(getApplicationContext(),gamePanel);
        LinearLayout.LayoutParams layoutParamsForSkill1 = new LinearLayout.LayoutParams(Skill1.SKILL1_WIDTH,Skill1.SKILL1_HEIGHT);
        skill1.setLayoutParams(layoutParamsForSkill1);
        skill1.setX(Skill1.SKILL1_XPOS);
        skill1.setY(Skill1.SKILL1_YPOS);

        skill2 = new Skill2(getApplicationContext(),gamePanel);
        LinearLayout.LayoutParams layoutParamsForSkill2 = new LinearLayout.LayoutParams(Skill2.SKILL2_WIDTH,Skill2.SKILL2_HEIGHT);
        skill2.setLayoutParams(layoutParamsForSkill2);
        skill2.setX(Skill2.SKILL2_XPOS);
        skill2.setY(Skill2.SKILL2_YPOS);

        ultimateSkill = new Skill3(getApplicationContext());
        LinearLayout.LayoutParams layoutParamsForSkill3 = new LinearLayout.LayoutParams(Skill3.SKILL3_WIDTH,Skill3.SKILL3_HEIGHT);
        ultimateSkill.setLayoutParams(layoutParamsForSkill3);
        ultimateSkill.setX(Skill3.SKILL3_XPOS);
        ultimateSkill.setY(Skill3.SKILL3_YPOS);


        RelativeLayout heroInfoUI = new RelativeLayout(getApplicationContext());
        LinearLayout.LayoutParams layoutParamsForHeroUI = new LinearLayout.LayoutParams(SCREEN_WIDTH/2,SCREEN_HEIGHT/2);


        heroInfoUI.setLayoutParams(layoutParamsForHeroUI);
        heroInfoUI.addView(playerHP);


        payloadMap = new PayloadMap(getApplicationContext());
        LinearLayout.LayoutParams layoutParamsForPayloadMap = new LinearLayout.LayoutParams(PayloadMap.PAYLOADMAPWIDTH,PayloadMap.PAYLOADMAPHEIGHT);
        payloadMap.setLayoutParams(layoutParamsForPayloadMap);
        payloadMap.setX(PayloadMap.PAYLOADMAPX);
        payloadMap.setY(PayloadMap.PAYLOADMAPY);

        gameWidgets.addView(joyStick);
        gameWidgets.addView(jumpButton);
        gameWidgets.addView(attackButton);
        gameWidgets.addView(skill1);
        gameWidgets.addView(skill2);
        gameWidgets.addView(ultimateSkill);
        gameWidgets.addView(heroInfoUI);
        gameWidgets.addView(payloadMap);
        game.addView(gamePanel);
        game.addView(gameWidgets);
        setContentView(game);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
