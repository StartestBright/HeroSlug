package com.jknull.heroslug;

public interface Character extends GameObject {
     String getCharacterTag();
     void jump();
     void attack();
     void takeDamage(int damage);
     float gravity = 9.8f;
}
