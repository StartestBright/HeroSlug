package com.jknull.heroslug;

import android.content.Context;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BossGunShot extends EnemyGunShot {



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public BossGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context,velocityX,velocityY,xPos,yPos);
    }


    @Override
    public void update(){
        if(xPos>= GamePanel.hero.getHeroPos().x) {
            directLeft = true;
        }

        if(active) {
            //      if(directLeft) {
            //         xPos -= (velocityX * bulletSpeed);
            //   yPos += (velocityY * bulletSpeed);
            //       }
            //     else{
            //        xPos += (velocityX * bulletSpeed);
            //  yPos += -(velocityY * bulletSpeed);

            //   }
            xPos += (velocityX * bulletSpeed);
            yPos += -(velocityY * bulletSpeed);
        }
        if(xPos+radius>=screenWidth||xPos<0||yPos+radius>=screenHeight-GamePanel.floorHeight||yPos<0){
            active = false;
        }
        collisionDetect();

    }
}
