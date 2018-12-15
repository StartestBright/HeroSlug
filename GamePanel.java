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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    private Point playerPoint;
    private Floor floor;
    Bitmap joystick;


    static int floorColor = Color.GREEN;
    static int floorHeight = 20;


    public GamePanel(Context context) {
        super(context);
        init(context);
    }

    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
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

    PlayerGunShot playerGunShot;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context){
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        player = new Player(new Rect(100,100,200,200), Color.BLUE,new Point(100,100));
        playerPoint = new Point(150,150);
        floor = new Floor(new Rect(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT),Color.GREEN,this);
        //joystick = BitmapFactory.decodeResource(getResources(),R.drawable.joystick);
        //System.out.println(joystick);
        playerGunShot = new PlayerGunShot(context);

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

        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        floor.draw(canvas);

        playerGunShot.draw(canvas);


        //canvas.drawBitmap(joystick,0,0,null);


        //Paint temp = new Paint();
        //temp.setColor(Color.GREEN);
        //canvas.drawRect(0,canvas.getHeight()-floorHeight,getWidth(),canvas.getHeight(),temp);


    }
    public void update(){
        //player.update(playerPoint);
        player.update();
        floor.update();


    }

    public Player getPlayer(){
        return this.player;
    }
}
