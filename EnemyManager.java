package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

public class EnemyManager {
    private Context context;
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private int enemyIndex= 0;

    public void spawnEnemy1(Point spawnPoint){
        enemies.add(new Enemy1(context,spawnPoint,enemyIndex));
        enemyIndex++;
    }
    public void spawnEnemy2(Point spawnPoint){
        enemies.add(new Enemy2(context,spawnPoint,enemyIndex));
        enemyIndex++;
    }
    public void spawnEnemy3(Point spawnPoint){
        enemies.add(new Enemy3(context,spawnPoint,enemyIndex));
        enemyIndex++;
    }
    public void Boss1(Point spawnPoint){
        //enemies.add(new Boss(context,spawnPoint,enemyIndex));
        //enemyIndex++;
    }

    EnemyManager(Context context){
        this.context = context;
        Boss1(new Point(2000,500));
    //    spawnEnemy1(new Point(1800,400));
    //    spawnEnemy1(new Point(2200,300));
        spawnEnemy1(new Point(3300,200));
        spawnEnemy1(new Point(4000,100));
        spawnEnemy2(new Point(5000,1200));
        spawnEnemy3(new Point(6000,-500));
        spawnEnemy3(new Point(8000,-500));
    //    spawnEnemy3(new Point(1000,-500));
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
