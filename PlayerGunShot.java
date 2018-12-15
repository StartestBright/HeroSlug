package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PlayerGunShot extends View implements GameObject{

    private double xPos,yPos,VelocityX,velocityY;
    private Bitmap bulletImage;

    BitmapFactory.Options opt = new BitmapFactory.Options();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PlayerGunShot(Context context) {
        super(context);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void init(Context context){
        opt.inMutable = true;
        bulletImage = BitmapFactory.decodeResource(getResources(),R.drawable.joystick);
        bulletImage = bulletImage.copy(Bitmap.Config.ARGB_8888,true);
        bulletImage.setWidth(800);
        bulletImage.setHeight(800);





    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(bulletImage,0,0,null);


    }
}
