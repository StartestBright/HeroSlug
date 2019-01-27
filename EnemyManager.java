package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class EnemyManager {
    private Context context;
    public static ArrayList<BoomEffection> enemyBoomEffections= new ArrayList<BoomEffection>();
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public static int enemyIndex= 0;

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
        enemies.add(new Boss(context,spawnPoint,enemyIndex));
        enemyIndex++;
    }
    EnemyManager(Context context){
        this.context = context;

        //Boss1(new Point(5000,500));
        spawnEnemy1(new Point(800,200));
       spawnEnemy1(new Point(1600,100));
        spawnEnemy1(new Point(2800,100));
        spawnEnemy1(new Point(3200,100));
       spawnEnemy1(new Point(3800,100));
    spawnEnemy2(new Point(1200,1200));
      spawnEnemy2(new Point(1900,1200));
      spawnEnemy2(new Point(3000,1200));
       spawnEnemy3(new Point(3500,-500));
     spawnEnemy3(new Point(4000,-500));
     spawnEnemy3(new Point(1200,-500));
      spawnEnemy3(new Point(2300,-500));
     spawnEnemy3(new Point(4500,-500));

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


    }

}
