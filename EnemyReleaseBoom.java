package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
//import android.support.annotation.RequiresApi;

public class EnemyReleaseBoom extends EnemyGunShot {

    private Bitmap bulletImageLeft;



    //RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public EnemyReleaseBoom(Context context, float velocityX, float velocityY, Point pos, int speed) {
        super(context,velocityX,velocityY,pos,speed);
        bulletWidth = 42;
        bulletHeight = 34;
        damage = 50;


    }



    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  void   init(Context context){

        if(bulletPos.x<=GamePanel.HERO.playerPos.x) {
            bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3bullet);
            bulletPos.x+=43;
        }else{
            bulletImage =BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3bulletleft);
            bulletPos.x-=43;
        }
        bulletRect = new Rect(bulletPos.x-bulletWidth,bulletPos.y-bulletHeight,bulletPos.x+bulletWidth,bulletPos.y+bulletHeight);

        //bulletImage.setWidth(800);
        //bulletImage.setHeight(800);
     //   bulletColor = Color.RED;
    }

    @Override
    public void update(){
    //    if(xPos>= GamePanel.HERO.getHeroPos().x) {
    //        directLeft = true;
     //   }

        if(active) {

        //    bulletPos.y += (bulletVelocityY * bulletSpeed);

            bulletPos.y += (bulletVelocityY * bulletSpeed);


            }else if(!boomming){
            boomEffections.add(new BoomEffection(boomBitmap,bulletPos.x,bulletPos.y,6));
            boomming= true;
            boomFinished = false;
        }

            super.update();

     //   collisionDetect();

    }
    @Override
    public void draw(Canvas canvas){
        Paint p = new Paint();
        super.draw(canvas);
        for(int i=0;i<boomEffections.size();i++){
            if(!boomEffections.get(i).isEnd()) {
                boomEffections.get(i).draw(canvas,p);
            }else{
                boomming = false;
                boomFinished = true;
            }
        }

    }

}
