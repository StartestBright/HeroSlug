package com.jknull.heroslug;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Enemy2 extends Enemy {

    private boolean canDush = true;
    private long dushDelay = 30;
    private long startedDushTime;


    public Enemy2(Context context, Point p, int enemyIndex) {
        super(context, p, enemyIndex);
        curHp = enemyMaxHp + 200;
        enemyVelocityX = 2.0;
        enemySize = 100;
    }

    @Override
    public String getCharacterTag() {
        return "Enemy2";
    }

    @Override
    public void jump() {

    }


    @Override
    public void attack() {
        canDush = false;

        GamePanel.HERO.takeDamage(50);
        if (GamePanel.HERO.playerPos.x <= enemyPos.x) {
            GamePanel.HERO.getDashed(1);
        } else {
            GamePanel.HERO.getDashed(0);
        }
        enemyVelocityX = 2;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(enemyRect, p);
    }


    @Override
    public void update() {
        landDetect();
        enmyWalk(this);
        enmyFollow(this);
        enemyRect.set(enemyPos.x - enemySize, enemyPos.y - enemySize, enemyPos.x + enemySize, enemyPos.y + enemySize);
    }

    @Override
    public void takeDamage(int damage) {
        curHp -= damage;
        if (curHp <= 0) {
            EnemyManager.killEnemy(enemyIndex);
            enemyAlive = false;
        }
    }



    @Override
    public void enmyFollow(Enemy enemy) {
        if (Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) <= 800
                && Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) >= 400) {
            enemy.enemyInWalkMode = false;
            if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                enemy.enemyPos.x += enemy.enemyVelocityX * 2;
                //    canDush = false;
            } else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                enemy.enemyPos.x -= enemy.enemyVelocityX * 2;
                //   canDush = false;
            }
        } else if ((Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) <= 400)) {
            if (canDush) {
                if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                    enemy.enemyPos.x += enemy.enemyVelocityX * 10;

                } else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                    enemy.enemyPos.x -= enemy.enemyVelocityX * 10;
                }
                if (((enemy.enemyPos.x >= GamePanel.HERO.playerPos.x) &&
                        (enemy.enemyPos.x - enemy.getEnemySize() - GamePanel.HERO.heroRect.right <= 10)) ||
                        ((enemy.enemyPos.x <= GamePanel.HERO.playerPos.x) &&
                                (GamePanel.HERO.heroRect.left - enemy.enemyPos.x - enemy.getEnemySize() <= 10))) {
                    enemy.enemyVelocityX = 0;
                  //  System.out.println(this.enemyVelocityX);
                    startedDushTime = System.currentTimeMillis();
                    attack();
                 //   System.out.println(this.enemyVelocityX);
                    enemy.enemyPos.x += enemy.enemyVelocityX;
                } else if ((System.currentTimeMillis() - startedDushTime) / 100 >= dushDelay) {
                    canDush = true;
                }
            }
            enemy.enemyInWalkMode = false;
        } else {
            enemy.enemyInWalkMode = true;
        }
    }
}

