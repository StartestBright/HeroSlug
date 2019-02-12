package com.jknull.heroslug;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class RocketManShockShot extends HeroGunShot {
    private float shockPower = 500;
    private float shockRange = 400;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RocketManShockShot(Context context, float velocityX, float velocityY, float xPos, float yPos) {
        super(context, velocityX, velocityY, xPos, yPos,true);
        gunShotDamage =25;
        heroGunShotRectSizeX=50;
        herogunShotRectSizeY=50;
        heroGunShotBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket_shot);
        active = true;
        bulletColor = Color.RED;
        bulletSpeed = 30;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void collisionDetect() {
        //super.collisionDetect();


        for(int i=0;i<EnemyManager.enemies.size();i++) {
            Enemy enemy = EnemyManager.enemies.get(i);
            float x  = Math.abs(enemy.enemyPos.x-xPos);
            float y  = Math.abs(enemy.enemyPos.y-yPos);

            /*if (enemy.isAlive() && active) {
                double dist = Math.sqrt(((enemy.enemyPos.x - xPos) * (enemy.enemyPos.x - xPos)) + ((enemy.enemyPos.y - yPos) * (enemy.enemyPos.y - yPos)));
                if (dist <= rocketmanRocketRange) {
                    enemy.takeDamage((int) ((rocketmanRocketRange - dist) / rocketmanRocketRange * gunShotDamage));
                }

            }*/
            if(enemy.isAlive()&&x<=shockRange&&y<=shockRange){
                if ((heroGunShotRect.left <=enemy.getEnemyRect().right&& //if  collide with enemy
                        heroGunShotRect.right>=enemy.getEnemyRect().left&&
                        heroGunShotRect.top<=enemy.getEnemyRect().bottom&&
                        heroGunShotRect.bottom>=enemy.getEnemyRect().top)||(heroGunShotRect.bottom>=MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT)) {

                    if(!explosionSoundPlayed) {
                        Hero.rocketExplosionSoundPool.play(Hero.ROCKETEXPLOSIONSOUND, 1, 1, 1, 0, 1);
                        explosionSoundPlayed = true;
                    }
                    enemy.takeShockShot(new Point((int)xPos,(int) yPos),shockPower,shockRange);
                    //EnemyManager.enemies.get(i).takeDamage(gunShotDamage);
                    //(enemy.getEnemyPos().x-xPos)/shockRange*shockPower
                    //(enemy.getEnemyPos().y-yPos)/shockRange*shockPower
                    active = false;
                }
            }
        }


        /*for (int i = 0; i < EnemyManager.enemies.size(); i++) {
            Enemy enemy = EnemyManager.enemies.get(i);
            if ((heroGunShotRect.left <= enemy.getEnemyPos().x + enemy.getEnemyWidth() && //if  collide with enemy
                    heroGunShotRect.right >= enemy.getEnemyPos().x - enemy.getEnemyWidth() &&
                    heroGunShotRect.top <= enemy.getEnemyPos().y + enemy.getEnemyHeight() &&
                    heroGunShotRect.bottom >= enemy.getEnemyPos().y - enemy.getEnemyHeight())
                    || heroGunShotRect.bottom>=GamePanel.FLOOR.getFloorRect().top) {
                if (enemy.isAlive() && active && isRocket) {
                    for (int j = 0; j < EnemyManager.enemies.size(); j++) {
                        Enemy enemy1 = EnemyManager.enemies.get(j);
                        double dist = Math.sqrt(((enemy1.enemyPos.x - xPos) * (enemy1.enemyPos.x - xPos)) + ((enemy1.enemyPos.y - yPos) * (enemy1.enemyPos.y - yPos)));
                        //System.out.println("DIST is = "+dist);
                        if (dist <= rocketmanRocketRange) {
                            enemy1.takeDamage((int) ((rocketmanRocketRange - dist) / rocketmanRocketRange * gunShotDamage));
                            //System.out.println(gunShotDamage);
                            //System.out.println(rocketmanRocketRange+" "+dist+" "+gunShotDamage);
                            //System.out.println((int) ( (rocketmanRocketRange-dist)/rocketmanRocketRange *gunShotDamage));
                        }
                    }

                    active = false;
                    return;
                }
            }
        }*/

    }
}
