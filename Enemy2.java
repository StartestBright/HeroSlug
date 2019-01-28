package com.jknull.heroslug;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;

public class Enemy2 extends Enemy {

    private boolean canDush = true;
    private long dushDelay = 30;
    private long startedDushTime;
    private boolean dashing;
    private long dashingDelay = 10;
    private long startedDashingTime;

    private SoundPool enemyDashSound;


    public Enemy2(Context context, Point p) {
        super(context, p);
        enemyMaxHp = 300; // You didn't set each enemies' max hp..
        curHp = enemyMaxHp;
        enemyVelocityX = 2.0;
        enemyHeight= 100;
        enemyWidth = 50;
        dashing=false;
        enemyBitMapRight = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy2right);
        enemyBitMapLeft = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy2left);
        enemyDashSound= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
        enemyDashSound.load(context,R.raw.enemydashsound,1);


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
        enemyDashSound.play(1,1,1,0,0,1);

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
        super.draw(canvas);
    }


    @Override
    public void update() {
        super.update();
        landDetect();
        enmyWalk(this);
        enmyFollow(this);
        enemyRect.set(enemyPos.x - enemyWidth, enemyPos.y - enemyWidth, enemyPos.x + enemyWidth, enemyPos.y + enemyHeight);
    }





    @Override
    public void enmyFollow(Enemy enemy) {
        if (Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) <= 1000
                && Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) >= 600) {
            enemy.enemyInWalkMode = false;
            if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                enemy.enemyVelocityX = 4;
                //    canDush = false;
            } else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                enemy.enemyVelocityX =- 4;
                //   canDush = false;
            }
        } else if ((Math.abs(GamePanel.HERO.getHeroPos().x - enemy.enemyPos.x) <= 600)) {
            if (canDush) {
                if (!dashing) {
                    if (enemy.enemyPos.x < GamePanel.HERO.getHeroPos().x) {
                        enemy.enemyVelocityX = 10;

                    } else if (enemy.enemyPos.x > GamePanel.HERO.getHeroPos().x) {
                        enemy.enemyVelocityX = -10;
                    }
                    enemy.enemyInWalkMode = false;
                    dashing = true;
                    startedDashingTime = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - startedDashingTime) / 100 >= dashingDelay) {
                    dashing = false;
                    //     enemy.enemyVelocityX = 2.0;
                }


                if ((enemy.enemyPos.y-GamePanel.HERO.heroRect.bottom<=enemy.enemyHeight)&&
                        (((enemy.enemyPos.x >= GamePanel.HERO.playerPos.x) &&
                        (enemy.enemyPos.x - enemy.getEnemyWidth() - GamePanel.HERO.heroRect.right <= 10)) ||
                        ((enemy.enemyPos.x <= GamePanel.HERO.playerPos.x) &&
                                (GamePanel.HERO.heroRect.left - enemy.enemyPos.x - enemy.getEnemyWidth() <= 10)))){
                  //  System.out.println(GamePanel.HERO.heroRect.bottom);
                    enemy.enemyVelocityX = 0;
                    //  System.out.println(this.enemyVelocityX);
                    startedDushTime = System.currentTimeMillis();
                    attack();
                    //   System.out.println(this.enemyVelocityX);
                    enemy.enemyPos.x += enemy.enemyVelocityX;
                }
            }
            else if ((System.currentTimeMillis() - startedDushTime) / 100 >= dushDelay) {
                    canDush = true;
                }

          //  enemy.enemyInWalkMode = true;
        } else {
            enemy.enemyInWalkMode = true;
        }
    }
}

