package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
//import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static Context CONTEXT;
    public static int GAMESTAGE = 1;
    public static int MAPSIZE =10000;
    public static float bgmVolume = 0.4f;
    private static boolean STAGECLEAR = false;
    public static Hero HERO;
    public static Background BG;
    public static Payload PAYLOAD;
    public static PlayerHP HEROHP;
    public static EnemyManager enemyManager;
    private MainThread thread;
    private Point playerPoint;
    private PayloadMap payloadMap;


    Bitmap joystick;

    public static float floorHeight = Floor.FLOORHEIGHT;
    public static Floor FLOOR;
    public static Rect floorRect;

    private MediaPlayer backgroundMusic;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GamePanel(Context context) {
        super(context);
        init(context);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {


        payloadMap = MainActivity.payloadMap;

        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();



    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        /*while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}

        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(Context context){
        CONTEXT = context;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        HEROHP = MainActivity.playerHP;
        BG = new Background(getContext());
        HERO = HeroChooseScreen.ChosenHero;
        playerPoint = new Point(150,150);
        FLOOR = new Floor(new Rect(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT),this);
        floorRect = FLOOR.getFloorRect();
        enemyManager = new EnemyManager(context);
        PAYLOAD = new Payload(context);

        backgroundMusic = MediaPlayer.create(context,R.raw.bgm1);
        backgroundMusic.setVolume(bgmVolume,bgmVolume);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        BG.draw(canvas);
        HERO.draw(canvas);
        HEROHP.draw(canvas);
        if (!STAGECLEAR)
            enemyManager.draw(canvas);
        PAYLOAD.draw(canvas);
        payloadMap.draw(canvas);
        FLOOR.draw(canvas);
        MainActivity.joyStick.draw(canvas);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update(){

        if(!STAGECLEAR) {
            HERO.update();
            HEROHP.update();
            BG.update();
            FLOOR.update();
            enemyManager.update();
            PAYLOAD.update();
        }

    }

    public static void ClearStage() {
        STAGECLEAR = true;
    }

}
