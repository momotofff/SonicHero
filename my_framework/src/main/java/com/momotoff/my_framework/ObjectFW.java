package com.momotoff.my_framework;

import static com.momotoff.my_framework.CollisionDetector.Side.NONE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class ObjectFW
{
    public Rect screen = new Rect();
    public Point position = new Point();
    public static int speed = 1;
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

        this.hitBox.left = position.x;
        this.hitBox.top = position.y;
        this.hitBox.right = sprite.getWidth() + this.hitBox.left;
        this.hitBox.bottom = sprite.getHeight() + this.hitBox.top;
    }

    public void updateVerticalSide(CollisionDetector.Side side){}
    public void updateHorizontalSide(CollisionDetector.Side side){}
    public CollisionDetector.Side getSide(){return NONE;}
}
