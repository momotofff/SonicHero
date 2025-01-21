package com.momotoff.my_framework;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class ObjectFW
{
    public Rect screen = new Rect();
    public Point position = new Point();
    public int speed = 10;
    protected Rect hitBox = new Rect();
    protected int radius;

    public Point getPosition() {
        return position;
    }
    public Rect getHitBox() { return hitBox; }
    public int getRadius() { return radius; }

    public void updatePosition(Point position, Bitmap sprite)
    {
        this.position = position;

        hitBox.left = position.x;
        hitBox.top = position.y;
        hitBox.right = sprite.getWidth() + hitBox.left;
        hitBox.bottom = sprite.getHeight() + hitBox.top;
    }
}
