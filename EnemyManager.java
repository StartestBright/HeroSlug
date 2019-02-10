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
    public void boss1(Point spawnPoint){
        enemies.add(new Boss(context,spawnPoint));
      //  enemyIndex++;
    }
    EnemyManager(Context context){
        this.context = context;
        boss1(new Point(1500,300));

    }

    public void update(){



        for(int i=0;i<enemies.size();i++){
            if(enemies.get(i).isAlive())
                enemies.get(i).update();
            else if((!enemies.get(i).boomStarted)&&(enemies.get(i).getCharacterTag()!="Enemy3")){
                boomEffections.add(new BoomEffection(enemies.get(i).enemyBoom, enemies.get(i).enemyRect.left, enemies.get(i).enemyPos.y, 7,10));
                enemies.get(i).boomStarted= true;
                System.out.println("is dead");
            }else if((!enemies.get(i).boomStarted)&&(enemies.get(i).getCharacterTag()=="Enemy3")){
                boomEffections.add(new BoomEffectionMultipleImages(enemies.get(i).enemyBoom, enemies.get(i).enemyRect.left, enemies.get(i).enemyPos.y, 11,7));
                enemies.get(i).boomStarted= true;
                System.out.println("is dead");
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
               System.out.println("ENEMYREMOBVE");
            }
        }
    }

}
