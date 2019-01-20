package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

public class EnemyManager {
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private int enemyIndex= 0;

    public void spawnEnemy1(Point spawnPoint){
        enemies.add(new Enemy1(spawnPoint,enemyIndex));
        enemyIndex++;
    }
    public void spawnEnemy2(Point spawnPoint){
        enemies.add(new Enemy2(spawnPoint,enemyIndex));
        enemyIndex++;
    }
    public void spawnEnemy3(Point spawnPoint){
        enemies.add(new Enemy3(spawnPoint,enemyIndex));
        enemyIndex++;
    }

    EnemyManager(){
        spawnEnemy1(new Point(500,500));
        spawnEnemy1(new Point(900,400));
        spawnEnemy1(new Point(1500,300));
        spawnEnemy1(new Point(1600,200));
        spawnEnemy1(new Point(2000,100));
        spawnEnemy2(new Point(2200,1200));
        spawnEnemy3(new Point(2500,-500));
        spawnEnemy3(new Point(3600,-500));
        spawnEnemy3(new Point(4000,-500));
        //test

    }
    public void update(){
        for(int i=0;i<enemies.size();i++){
            if(enemies.get(i).isAlive())
                enemies.get(i).update();
        }
    }
    public void draw(Canvas canvas){
        for(int i=0;i<enemies.size();i++){
            if(enemies.get(i).isAlive())
                enemies.get(i).draw(canvas);

        }
    }
    public static void killEnemy(int index){
        for(int i =0;i<enemies.size();i++){
            if(enemies.get(i).getEnemyIndex()==index){
                enemies.remove(i);
            }
        }
        //if(enemies.get(index)!=null)
            //enemies.get(index).setEnemyAlive(false);

    }




}
