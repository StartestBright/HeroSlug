package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

public class Floor {

    public static int FLOORHEIGHT = 20;
    Hero hero;
    public Floor(Rect floor,int color,GamePanel gamePanel){
        this.floor = floor;
        this.floorColor = color;
        hero = gamePanel.getPlayer();
    }


    int floorColor;
    private Rect floor;
    public boolean playerCollide(Hero hero){
        if(floor.contains(hero.getHero().left,hero.getHero().top)||
        floor.contains(hero.getHero().right,hero.getHero().top)||
                floor.contains(hero.getHero().left,hero.getHero().bottom)||
                floor.contains(hero.getHero().right,hero.getHero().bottom) ||
                hero.getHeroPos().y>=MainActivity.SCREEN_HEIGHT-50){
            return true;
        }
        return  false;
    }


    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        floor.set(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT);
        canvas.drawRect(floor,paint);

    }

    public void update() {
        if(playerCollide(hero)) {
            hero.setPlayerLanded(true);
        }

    }
}
