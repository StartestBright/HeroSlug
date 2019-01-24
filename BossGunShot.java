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
        super.update();
        if(active) {
            xPos += (velocityX * bulletSpeed);
            yPos += -(velocityY * bulletSpeed);
        }
    }
}
