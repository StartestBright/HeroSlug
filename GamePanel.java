package com.jknull.heroslug;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import android.support.annotation.RequiresApi;

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
    public  EnemyManager enemyManager;
    private MainThread thread;
    private Point playerPoint;
    private PayloadMap payloadMap;
    private  Context context;




    Bitmap joystick;

    public static float floorHeight = Floor.FLOORHEIGHT;
    public static Floor FLOOR;
    public static Rect floorRect;

    private MediaPlayer backgroundMusic;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GamePanel(Context context) {

        super(context);
        init(context);
         this.context = context;

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("i will open thread!!!!");
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
        enemyManager.clearEnemy();
        enemyManager.startClear=true;

        notFinished = false;
        backgroundMusic.stop();


     //   }
    }

    public static void sendFinishActivityBroadcast(Context context) {
        Intent intent = new Intent(MainActivity.stopActivity);
        context.sendBroadcast(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(Context context){
        CONTEXT = context;
        getHolder().addCallback(this);
        //thread = new MainThread(getHolder(),this);
        setFocusable(true);
        HEROHP = MainActivity.playerHP;
        BG = new Background(getContext());
        HERO = HeroChoiceScreen.ChosenHero;
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
    private boolean notFinished = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update(){
        if((!STAGECLEAR&&notFinished)||!enemyManager.isEnemyCleared()) {
            HERO.update();
            HEROHP.update();
            BG.update();
            FLOOR.update();
            enemyManager.update();
            PAYLOAD.update();
        }
        else{
            System.out.println("enemy clear");
            enemyManager = null;
            Intent intent = new Intent(MainActivity.stopActivity);
            context.sendBroadcast(intent);
            try {
                // thread.sleep(3000);
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void ClearStage() {
        STAGECLEAR = true;
    }

}
