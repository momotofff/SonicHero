package com.momotoff.my_framework;

public class CollisionDetector
{
    public static boolean detect(ObjectFW object1, ObjectFW object2)
    {
        int dx = object1.getHitBox().centerX() - object2.getHitBox().centerX();
        int dy = object1.getHitBox().centerY() - object2.getHitBox().centerY();
        int radius = object1.getRadius() + object2.getRadius();

        return dx * dx + dy * dy < radius * radius;
    }
}
