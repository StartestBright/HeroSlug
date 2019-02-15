package com.jknull.heroslug;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Floor {

    private Rect floor;
    public static int FLOORHEIGHT = 200;
    Bitmap floorBitmap;
    Hero hero;


    public Floor(Rect floor,GamePanel gamePanel){
        this.floor = floor;
        hero = gamePanel.HERO;
        floorBitmap = BitmapFactory.decodeResource(gamePanel.getContext().getResources(),R.drawable.floor_image);
    }



    public boolean playerCollide(Hero hero){
        Rect heroRect = hero.getHero();
        if(floor.top<=heroRect.bottom && floor.left<=heroRect.right && floor.right >=heroRect.right){
            return true;
        }
        return false;
        /*
        if(floor.contains(HERO.getHero().left,HERO.getHero().top)||
        floor.contains(HERO.getHero().right,HERO.getHero().top)||
                floor.contains(HERO.getHero().left,HERO.getHero().bottom)||
                floor.contains(HERO.getHero().right,HERO.getHero().bottom) ||
                HERO.getHeroPos().y>=MainActivity.SCREEN_HEIGHT-50){
            return true;

        return  false;*/
    }

    public boolean enemyCollide(int enemyIndex){
        Enemy enemy = EnemyManager.enemies.get(enemyIndex);
        if(enemy.getEnemyRect().bottom>=floor.top&&floor.left<=enemy.getEnemyRect().right&&floor.right>= enemy.getEnemyRect().right){
            return true;
        }
        return false;
    }


    public void draw(Canvas canvas) {
        //Paint paint = new Paint();
        //paint.setColor(Color.GREEN);
        floor.set(0,MainActivity.SCREEN_HEIGHT-FLOORHEIGHT,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT);
        //canvas.drawRect(floor,paint);
        canvas.drawBitmap(floorBitmap,null,floor,null);

    }

    public void update() {
        if(playerCollide(hero)) {
            hero.setPlayerLanded(true);
        }

        for(int i=0;i<EnemyManager.enemies.size();i++){
            Enemy enemy = EnemyManager.enemies.get(i);
            enemy.setEnemyLanded(true);

        }
    }

    public Rect getFloorRect(){
        return floor;
    }
}
