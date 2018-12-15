package com.jknull.heroslug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

public class Floor {

    public static int FLOORHEIGHT = 20;
    Player player;
    public Floor(Rect floor,int color,GamePanel gamePanel){
        this.floor = floor;
        this.floorColor = color;
        player = gamePanel.getPlayer();
    }


    int floorColor;
    private Rect floor;
    public boolean playerCollide(Player player){
        if(floor.contains(player.getPlayer().left,player.getPlayer().top)||
        floor.contains(player.getPlayer().right,player.getPlayer().top)||
                floor.contains(player.getPlayer().left,player.getPlayer().bottom)||
                floor.contains(player.getPlayer().right,player.getPlayer().bottom) ||
                player.playerPos.y>=MainActivity.SCREEN_HEIGHT-50){
            return true;
        }
        return  false;
    }


    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        floor.set(0,MainActivity.SCREEN_HEIGHT-20,MainActivity.SCREEN_WIDTH,MainActivity.SCREEN_HEIGHT);
        canvas.drawRect(floor,paint);

    }

    public void update() {
        if(playerCollide(player)) {
            player.setPlayerLanded(true);
        }

    }
}
