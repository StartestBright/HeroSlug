package com.jknull.heroslug;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class Hero implements Character{
    public static int PlayerMaxHorizontalSpeed = 15;
    protected int heroColor;
    protected float heroGravity = 9.8f;
    protected String charTag = "Hero";


    public abstract int getHeroMaxHP();
    public abstract String getHeroTag();
    public abstract Point getHeroPos();
    public abstract Rect getHero();
    public abstract void moveHorizontal(double value);
    public abstract void setPlayerLanded(boolean b);
    public abstract boolean isLaneded();
    public abstract void setPlayerRotation(float rotation);

}
