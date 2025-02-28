package com.momotoff.my_framework;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class StaticButtonFW
{
    public Bitmap bitmap;
    public Point position;
    public int size;

    public StaticButtonFW(Bitmap bitmap, Point position)
    {
        this.bitmap = bitmap;
        this.position = position;
    }

    public Rect getTouchArea()
    {
        return new Rect(position.x,position.y - size,position.x + bitmap.getHeight(), position.y + bitmap.getHeight());
    }
}
