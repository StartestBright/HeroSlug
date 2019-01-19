package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

public class Soldier extends Hero{
    public static int SOLDIERMAXHP = 250;

    private boolean snipingMode = false;
    private Context context;
    private ArrayList<SoldierGunShot> playerBullets;
    private GamePanel gamePanel;
    private Background bg;
    private Canvas canvas;
    private SoldierHealPack healPack;
    private float snipingBulletSpeed=120f,normalBulletSpeed= 80f;
    private int healPackCoolTime = 12;
    private long healPackCoolTimeStart =0;
    private boolean healPackCoolTimeOn = false;
    private boolean ultimateSkillOn = false ,ultimateSkillCoolTimeOn=false;
    private long ultimateStart,ultimateCoolTime=15;



    int rayLength = 3000;


    boolean onFloor;
    Handler handler;
    public Soldier(Rect rectangle,int color,Point pos,Context context,GamePanel gamePanel){


        this.context = context;
        this.tempPlayer = rectangle;
        this.heroColor = color;
        this.gamePanel = gamePanel;
        this.bg = gamePanel.getBg();
        playerPos = pos;
        playerLanded = false;

        playerVelocityY =0;
        playerVelocityX =0;

        playerBullets = new ArrayList<SoldierGunShot>();

        jumpPower = 100;
        heroTag = "Soldier";

        playerHP = MainActivity.playerHP;

        //healPack = new SoldierHealPack(playerPos,this);
    }

    @Override
    public void update() {
        if(!playerLanded) {
            playerVelocityY += gravity;
        }else if(playerLanded){
            playerVelocityY = 0;
            playerPos.y = MainActivity.SCREEN_HEIGHT-Floor.FLOORHEIGHT-tempPlayer.height()/2;
        }
        playerPos.y += playerVelocityY;
        playerPos.x += playerVelocityX;

        tempPlayer.set(playerPos.x - tempPlayer.width()/2
                ,playerPos.y-tempPlayer.height()/2
                ,playerPos.x+tempPlayer.width()/2,playerPos.y+tempPlayer.height()/2);

        for(int i=0;i<playerBullets.size();i++){
            playerBullets.get(i).update();
            if(!playerBullets.get(i).isActive())
                playerBullets.remove(i);
        }


        if(playerVelocityX>0) {
            if (playerPos.x >= MainActivity.SCREEN_WIDTH / 2) {
                if(bg!=null) {
                    bg.moveBg((float) playerVelocityX * -1);
                    playerPos.x = MainActivity.SCREEN_WIDTH/2;
                }
                for(int i=0;i<EnemyManager.enemies.size();i++){
                    Enemy enemy = EnemyManager.enemies.get(i);
                    //enemy.

                }
            }
        }



        if(healPackCoolTimeOn){
            if((System.currentTimeMillis()-healPackCoolTimeStart)/1000>= healPackCoolTime){
                healPackCoolTimeOn = false;
            }
        }
        if(healPack !=null){
            healPack.update();
        }

        if(ultimateSkillCoolTimeOn){
            if( (System.currentTimeMillis()-ultimateStart)/1000>=ultimateCoolTime){
                ultimateSkillCoolTimeOn = false;
            }
            if( (System.currentTimeMillis()-ultimateStart)/1000>=10){
                ultimateSkillOn = false;
            }
        }


    }
    @Override
    public void draw(Canvas canvas){
        this.canvas=canvas;
        Paint paint = new Paint();
        paint.setColor(heroColor);
        canvas.drawRect(tempPlayer,paint);

        for(int i=0;i<playerBullets.size();i++){
            if(playerBullets.get(i).isActive())
                playerBullets.get(i).draw(canvas);
        }

        if(snipingMode) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStrokeWidth(8);
            canvas.drawLine(playerPos.x,playerPos.y,(int)(Math.cos(playerRotation)*rayLength)+playerPos.x,(int)(Math.sin(playerRotation)*rayLength)+playerPos.y,p);


        }

        if(ultimateSkillOn){
            for(int i=0;i<EnemyManager.enemies.size();i++){
                Enemy enemy = EnemyManager.enemies.get(i);
                if(enemy != null&&enemy.isAlive()) {
                    Paint p = new Paint();
                    p.setColor(Color.RED);
                    p.setStrokeWidth(8);
                    canvas.drawLine(playerPos.x, playerPos.y, enemy.getEnemyPos().x, enemy.getEnemyPos().y, p);

                }
            }

        }

        if(healPack!=null){
            healPack.draw(canvas);
        }



    }


    @Override
    public int getHeroMaxHP() {
        return SOLDIERMAXHP;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void attack() {
        if(ultimateSkillOn){
            for(int i=0;i<EnemyManager.enemies.size();i++){
                Enemy enemy = EnemyManager.enemies.get(i);
                if(EnemyManager.enemies.get(i).isAlive()) {
                    int x =enemy.getEnemyPos().x- playerPos.x;
                    int y = enemy.getEnemyPos().y - playerPos.y;
                    float temp = (float) (Math.atan2(x,y)+Math.PI+Math.PI/2);
                    //temp += Math.PI/2;
                    System.out.println("x : "+x + " "+"y : "+y+" temp : " +temp );
                    System.out.println(Math.atan2(x,y)/Math.PI*180);


                    /*if(x<0 && y>0){
                        temp +=Math.PI;
                    }else if(x<0 &&y <0){
                        temp +=Math.PI;
                    }*/
                    temp *=-1;

                    SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(temp), (float) Math.sin(temp), playerPos.x, playerPos.y);
                    newBullet.setBulletSpeed(normalBulletSpeed);
                    playerBullets.add(newBullet);

                }
            }
        }else {
            SoldierGunShot newBullet = new SoldierGunShot(context, (float) Math.cos(playerRotation), (float) Math.sin(playerRotation), playerPos.x, playerPos.y);
            if (!snipingMode) {
                newBullet.setBulletSpeed(normalBulletSpeed);
            } else if (snipingMode) {
                newBullet.setBulletSpeed(snipingBulletSpeed);
            }
            playerBullets.add(newBullet);

        }
    }

    @Override
    public void takeDamage(int damage) {
        GamePanel.playerHP.getDamage(damage);
    }

    public void setSnipingMode(){
        this.snipingMode = !this.snipingMode;
    }

    public void healPack(){
        //healPack.setPos(playerPos);
        if(!healPackCoolTimeOn) {
            healPackCoolTimeStart = System.currentTimeMillis();
            healPack = new SoldierHealPack(playerPos, this);
            healPackCoolTimeOn= true;
        }

    }
    public void setHealPackNull(){
        healPack = null;
    }

    public void ultimateSkill(){
        if(!ultimateSkillCoolTimeOn) {
            ultimateStart = System.currentTimeMillis();
            ultimateSkillCoolTimeOn = true;
            ultimateSkillOn = true;
        }
    }


}
