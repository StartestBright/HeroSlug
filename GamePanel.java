package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public static int GAMESTAGE = 1;
    public static int MAPSIZE =10000;
    public static float bgmVolume = 0.4f;

    private static boolean STAGECLEAR = false;
    private MainThread thread;
    public static Hero HERO;
    private Point playerPoint;
    private Floor floor;
    public static Background BG;
    public static Payload PAYLOAD;
    private PayloadMap payloadMap;

    public static final int WIDTH = 1024,HEIGHT = 512;

    public static PlayerHP HEROHP;
    public static EnemyManager enemyManager;
    Bitmap joystick;

    public static float floorHeight = Floor.FLOORHEIGHT;
    public static Rect floorRect;

    private MediaPlayer backgroundMusic;




    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GamePanel(Context context) {
        super(context);
        init(context);

    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}

        }
    }


   // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(Context context){
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        HEROHP = MainActivity.playerHP;
        BG = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.backgroundimage));

        HERO = new Soldier(Color.BLUE,new Point(100,100),context,this);
        //HERO = new RocketMan(Color.YELLOW,new Point(100,100),context);

        playerPoint = new Point(150,150);
        floor = new Floor(new Rect(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT),this);
        floorRect = floor.getFloorRect();
        enemyManager = new EnemyManager(context);
        PAYLOAD = new Payload(context);

        backgroundMusic = MediaPlayer.create(context,R.raw.bgm1);
        backgroundMusic.setVolume(bgmVolume,bgmVolume);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
        //payloadMap = MainActivity.payloadMap;
        //joystick = BitmapFactory.decodeResource(getResources(),R.drawable.joystick);
        //System.out.println(joystick);
        //HEROHP = new PlayerHP(context,Player.PLAYERMAXHP);
        //HEROHP.setAlpha(0.1f);

    }

    float oldX,oldY,curX,curY;
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        curX = event.getX();
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


        }
        return true;
    }*/
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //final float scaleFactorX = MainActivity.SCREEN_WIDTH/WIDTH;
        //final float scaleFactorY = MainActivity.SCREEN_HEIGHT/HEIGHT;
        //canvas.scale(scaleFactorX,scaleFactorY);

        canvas.drawColor(Color.WHITE);
        BG.draw(canvas);
        HERO.draw(canvas);
        HEROHP.draw(canvas);
        if(!STAGECLEAR)
            enemyManager.draw(canvas);
        PAYLOAD.draw(canvas);
        payloadMap.draw(canvas);
        floor.draw(canvas);
        //Paint temp = new Paint();
        //temp.setColor(Color.GREEN);
        //canvas.drawRect(0,canvas.getHeight()-floorHeight,getWidth(),canvas.getHeight(),temp);



    }
    public void update(){

        if(!STAGECLEAR) {
            HERO.update();
            HEROHP.update();
            BG.update();
            floor.update();
            enemyManager.update();
            PAYLOAD.update();
        }

    }

    public static void ClearStage(){
        STAGECLEAR = true;
    }
    public static void GameOver(){

    }

}
