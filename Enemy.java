package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

public abstract class Enemy implements Character{
    protected Point enemyPos;
    protected int enemyIndex;
    protected int curHp;
    protected double enemyVelocityY;
    protected double enemyVelocityX;
    protected boolean enemyLanded;
    protected boolean enemyAlive;
    protected Rect enemyRect;

    public Point getEnemyPos(){
        return this.enemyPos;

    }

    public Enemy(Point p,int enemyIndex){
        enemyPos = p;
        this.enemyIndex = enemyIndex;
    }
    public boolean isAlive(){
        return enemyAlive;
    };

    public abstract void update();
    public abstract void draw(Canvas canvas);


    public abstract int getEnemySize();
    public Rect getEnemyRect(){
        return enemyRect;
    }
    public void setEnemyLanded(Boolean landed){
        enemyLanded = landed;
    }

    public int getEnemyIndex(){
        return enemyIndex;
    }
    public void setEnemyAlive(boolean alive){
        enemyAlive = alive;
    }
    public void enemyMoveByPlayer(float playerVelocityX){
        enemyPos.x-=playerVelocityX;
    }
}
