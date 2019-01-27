package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

public class RocketManShockShot extends HeroGunShot {
    private float shockPower = 500;
    private float shockRange = 400;
    public RocketManShockShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context, velocityX, velocityY, xPos, yPos);
        radius = 30;
        gunShotDamage =25;
        active = true;
        bulletColor = Color.RED;
        bulletSpeed = 30;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void collisionDetect() {
        super.collisionDetect();


        for(int i=0;i<EnemyManager.enemies.size();i++) {
            Enemy enemy = EnemyManager.enemies.get(i);
            float x  = Math.abs(enemy.enemyPos.x-xPos);
            float y  = Math.abs(enemy.enemyPos.y-yPos);
            if(enemy.isAlive()&&x<=shockRange&&y<=shockRange){
                if ((xPos - radius <=enemy.getEnemyPos().x+enemy.getEnemyWidth()&& //if  collide with enemy
                        xPos+radius>=enemy.getEnemyPos().x-enemy.getEnemyWidth()&&
                        yPos-radius<=enemy.getEnemyPos().y+enemy.getEnemyHeight()&&
                        yPos+radius>=enemy.getEnemyPos().y-enemy.getEnemyHeight())||(yPos>=MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT)) {

                    enemy.takeShockShot(new Point((int)xPos,(int) yPos),shockPower,shockRange);
                    //EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                    //(enemy.getEnemyPos().x-xPos)/shockRange*shockPower
                    //(enemy.getEnemyPos().y-yPos)/shockRange*shockPower
                    active = false;

                }
            }
        }

    }
}
