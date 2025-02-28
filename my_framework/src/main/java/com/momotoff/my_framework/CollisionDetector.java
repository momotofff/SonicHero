package com.momotoff.my_framework;

import android.graphics.Rect;

public class CollisionDetector
{
    public enum Side
    {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        NONE
    }

    public static void detectObject(ObjectFW player, ObjectFW earth)
    {
        if (detectEarth(player, earth))
        {
            if (Side.BOTTOM == player.getSide())
                return;

            // Получаем границы объектов
            Rect hitBox1 = player.getHitBox();
            Rect hitBox2 = earth.getHitBox();

            // Определяем сторону столкновения с использованием hitBox
            int deltaX = hitBox1.centerX() - hitBox2.centerX();
            int deltaY = hitBox1.centerY() - hitBox2.centerY();

            int overlapX = hitBox1.width() / 2 + hitBox2.width() / 2 - Math.abs(deltaX);
            int overlapY = hitBox1.height() / 2 + hitBox2.height() / 2 - Math.abs(deltaY);

            if (overlapX > 0 && overlapY > 0)
            {
                // Столкновение произошло по горизонтали
                if (overlapX < overlapY)
                {
                    if (deltaX > 0)
                        player.updateHorizontalSide(Side.RIGHT);
                    else
                        player.updateHorizontalSide(Side.LEFT);

                }

                else
                {    // Столкновение произошло по вертикали
                    if (deltaY > 0)
                        player.updateVerticalSide(Side.TOP);
                     else
                        player.updateVerticalSide(Side.BOTTOM);
                }
            }
        }
        else
        {
            player.updateVerticalSide(Side.NONE); // Если столкновение не произошло
        }
    }

    public static boolean detect(ObjectFW object1, ObjectFW object2)
    {
        int dx = object1.getHitBox().centerX() - object2.getHitBox().centerX();
        int dy = object1.getHitBox().centerY() - object2.getHitBox().centerY();
        int radius = object1.getRadius() + object2.getRadius();

        return dx * dx + dy * dy < radius * radius;
    }

    private static boolean detectEarth(ObjectFW object1, ObjectFW object2)
    {
        Rect hitBox1 = object1.getHitBox();
        Rect hitBox2 = object2.getHitBox();


        return hitBox1.intersect(hitBox2);
    }

}
