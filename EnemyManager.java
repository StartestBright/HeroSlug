package com.jknull.heroslug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class EnemyManager {
    private Context context;
//    public static ArrayList<BoomEffection> enemyBoomEffections= new ArrayList<BoomEffection>();
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    //public static ArrayList<BoomEffection> boomEffections = new ArrayList<BoomEffection>();

 //   public static int enemyIndex= 0;

    public static ArrayList<BoomEffection> boomEffections = new ArrayList<BoomEffection>();

    public void spawnEnemy1(Point spawnPoint){
        enemies.add(new Enemy1(context,spawnPoint));
    //    enemyIndex++;
    }
    public void spawnEnemy2(Point spawnPoint){
        enemies.add(new Enemy2(context,spawnPoint));
     //   enemyIndex++;
    }
    public void spawnEnemy3(Point spawnPoint){
        enemies.add(new Enemy3(context,spawnPoint));
   //     enemyIndex++;
    }
    public void Boss1(Point spawnPoint){
        enemies.add(new Boss(context,spawnPoint));
      //  enemyIndex++;
    }
    EnemyManager(Context context){
        this.context = context;
      // Boss1(new Point(9000,500));
      spawnEnemy1(new Point(800,200));
        spawnEnemy1(new Point(1100,100));
        spawnEnemy1(new Point(1200,100));
         spawnEnemy1(new Point(2300,100));
        spawnEnemy1(new Point(3400,100));

         spawnEnemy1(new Point(3500,100));
         spawnEnemy2(new Point(2600,1200));
         spawnEnemy2(new Point(5700,1200));
         spawnEnemy2(new Point(6800,1200));
         spawnEnemy3(new Point(7900,-500));
         spawnEnemy3(new Point(8100,-500));
         spawnEnemy3(new Point(9200,-500));
         spawnEnemy3(new Point(8300,-500));
         spawnEnemy3(new Point(5400,-500));

        spawnEnemy1(new Point(5800,200));
        spawnEnemy1(new Point(4600,100));
        spawnEnemy1(new Point(5700,100));
        spawnEnemy1(new Point(8800,100));
        spawnEnemy1(new Point(3800,100));

        spawnEnemy1(new Point(3200,100));
        spawnEnemy2(new Point(9100,1200));
        spawnEnemy2(new Point(8200,1200));
        spawnEnemy2(new Point(7300,1200));
        spawnEnemy3(new Point(2000,-500));
        spawnEnemy3(new Point(5500,-500));
        spawnEnemy3(new Point(8600,-500));
        spawnEnemy3(new Point(7700,-500));
        spawnEnemy3(new Point(5800,-500));

        spawnEnemy1(new Point(9900,200));
        spawnEnemy1(new Point(1200,100));
        spawnEnemy1(new Point(4300,100));
        spawnEnemy1(new Point(8400,100));
        spawnEnemy1(new Point(7500,100));

        spawnEnemy1(new Point(5600,100));
        spawnEnemy2(new Point(1700,1200));
        spawnEnemy2(new Point(2800,1200));
        spawnEnemy2(new Point(3100,1200));
        spawnEnemy3(new Point(6200,-500));
        spawnEnemy3(new Point(4300,-500));
        spawnEnemy3(new Point(7400,-500));
        spawnEnemy3(new Point(8500,-500));
        spawnEnemy3(new Point(9600,-500));




    }

    public void update(){

        for(int i=0;i<enemies.size();i++){
            if(enemies.get(i).isAlive())
                enemies.get(i).update();
            else if((!enemies.get(i).boomStarted)&&(enemies.get(i).getCharacterTag()!="Enemy3")){
                boomEffections.add(new BoomEffection(enemies.get(i).enemyBoom, enemies.get(i).enemyRect.left, enemies.get(i).enemyPos.y, 7,10));
                enemies.get(i).boomStarted= true;
            }else if((!enemies.get(i).boomStarted)&&(enemies.get(i).getCharacterTag()=="Enemy3")){
                boomEffections.add(new BoomEffectionMultipleImages(enemies.get(i).enemyBoom, enemies.get(i).enemyRect.left, enemies.get(i).enemyPos.y, 11,7));
                enemies.get(i).boomStarted= true;
            }
        }
        for(int j=0;j<boomEffections.size();j++){
            if(boomEffections.get(j).isFished()) {
                boomEffections.remove(j);

                System.out.println("remove!");
            }
        }
      //  for (int k=0;k<enemies.get(k).)


    }
    public void draw(Canvas canvas){
        Paint p = new Paint();
        for(int i=0;i<enemies.size();i++){
            if(enemies.get(i).isAlive())
                enemies.get(i).draw(canvas);

        }

        for(int i=0;i<boomEffections.size();i++){
            if(!boomEffections.get(i).isFished()) {
                boomEffections.get(i).draw(canvas,p);
            }
        }
    }
    public static void killEnemy(){

        for(int i =0;i<enemies.size();i++){
            if(enemies.get(i).enemyIsDead()){
               enemies.remove(i);
            }
        }
    }

}
