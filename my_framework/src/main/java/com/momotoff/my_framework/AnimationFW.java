package com.momotoff.my_framework;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

public class AnimationFW
{
    private int frameIndex;
    private final  ArrayList<Bitmap> sprite;
    private int speedAnimation = 2;

    public AnimationFW(ArrayList<Bitmap> sprite)
    {
        this.sprite = sprite;
    }

    public void runAnimation()
    {
        if (--speedAnimation > 0)
        {
            if (++frameIndex >= sprite.size())
                frameIndex = 0;
        }
        else
            speedAnimation = 2;
    }

    public void drawingAnimation(GraphicsFW graphicsFW, Point position)
    {
            graphicsFW.drawTexture(sprite.get(frameIndex), position);
    }
}
