package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static int GAMESTAGE = 1;
    public static int MAPSIZE =10000;

    private static boolean STAGECLEAR = false;
    private MainThread thread;
    public static Hero hero;
    private Point playerPoint;
    private Floor floor;
    public static Background BG;
    public static Payload PAYLOAD;
    private PayloadMap payloadMap;

    public static final int WIDTH = 1024,HEIGHT = 512;

    public static PlayerHP playerHP;
    public static EnemyManager enemyManager;
    Bitmap joystick;


    static int floorColor = Color.GREEN;
    static int floorHeight = 20;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public GamePanel(Context context) {
        super(context);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        Boolean retry = true;
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}
            retry = false;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context){
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        playerHP = MainActivity.playerHP;
        BG = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.backgroundimage));
        hero = new Soldier(new Rect(100,100,200,200), Color.BLUE,new Point(100,100),context,this);
        playerPoint = new Point(150,150);
        floor = new Floor(new Rect(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT),Color.GREEN,this);
        enemyManager = new EnemyManager(context);
        PAYLOAD = new Payload();
        //payloadMap = MainActivity.payloadMap;
        //joystick = BitmapFactory.decodeResource(getResources(),R.drawable.joystick);
        //System.out.println(joystick);
        //playerHP = new PlayerHP(context,Player.PLAYERMAXHP);
        //playerHP.setAlpha(0.1f);

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
        hero.draw(canvas);
        floor.draw(canvas);
        playerHP.draw(canvas);
        if(!STAGECLEAR)
            enemyManager.draw(canvas);
        PAYLOAD.draw(canvas);
        payloadMap.draw(canvas);
        //Paint temp = new Paint();
        //temp.setColor(Color.GREEN);
        //canvas.drawRect(0,canvas.getHeight()-floorHeight,getWidth(),canvas.getHeight(),temp);



    }
    public void update(){
            BG.update();
            if(!PlayerHP.HERODEAD&&!STAGECLEAR) {
                hero.update();
                playerHP.update();
            }

            floor.update();
            if(!STAGECLEAR) {
                enemyManager.update();
                PAYLOAD.update();
            }


    }

    public Hero getPlayer(){
        return hero;
    }

    public static void ClearStage(){
        STAGECLEAR = true;
    }
    public static void GameOver(){

    }

}
