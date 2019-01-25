package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class RocketManGunShot extends HeroGunShot {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public RocketManGunShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context,velocityX,velocityY,xPos,yPos);

        radius = 20;
        gunShotDamage =50;
        active = true;
        bulletColor = Color.GREEN;
        bulletSpeed = 100f;
    }

    @Override
    public void update() {
        if(active)
            super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(active)
            super.draw(canvas);
    }
}
