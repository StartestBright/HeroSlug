package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

public class RocketManShockShot extends HeroGunShot {
    private float shockPower = 100;
    private float shockRange = 100;
    public RocketManShockShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context, velocityX, velocityY, xPos, yPos);
        radius = 30;
        gunShotDamage =25;
        active = true;
        bulletColor = Color.RED;
        bulletSpeed = 150f;
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
            if(enemy.isAlive()){
                if (xPos - radius <=enemy.getEnemyPos().x+enemy.getEnemySize()&& //if  collide with enemy
                        xPos+radius>=enemy.getEnemyPos().x-enemy.getEnemySize()&&
                        yPos-radius<=enemy.getEnemyPos().y+enemy.getEnemySize()&&
                        yPos+radius>=enemy.getEnemyPos().y-enemy.getEnemySize()) {


                    enemy.takeShockShot(new Point((int)xPos,(int) yPos),shockPower,shockRange);
                    //EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                    //(enemy.getEnemyPos().x-xPos)/shockRange*shockPower
                    //(enemy.getEnemyPos().y-yPos)/shockRange*shockPower
                    //active = false;
                    return;

                }
            }
        }
    }
}
