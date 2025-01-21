package com.momotoff.sonichero.objects;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;

public class Star extends ObjectFW implements IDrawable
{
    public Star(Point sceneSize, int height)
    {
        this.screen = new Rect(0, height, sceneSize.x, sceneSize.y);
        this.speed = 5;
        this.position = new Point((int) (Math.random() * screen.right), (int) (Math.random() * screen.bottom) + height);
    }

    public Point getPosition()
    {
        return position;
    }

    @Override
    public void update()
    {
        position.x -= speed * Math.random();

        if (position.x < -100)
        {
            position.x = screen.right;
            position.y = (int) (Math.random() * screen.bottom) + screen.top;
        }
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        graphicsFW.drawPixel(getPosition(), Color.WHITE);
    }
}
