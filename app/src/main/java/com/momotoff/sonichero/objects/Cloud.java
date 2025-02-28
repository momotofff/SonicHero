package com.momotoff.sonichero.objects;

import android.graphics.Point;
import android.graphics.Rect;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;

public class Cloud extends ObjectFW implements IDrawable
{
    public Cloud(Point sceneSize, int height)
    {
        this.screen = new Rect(0, height, sceneSize.x, sceneSize.y);
        this.position.y = height;
    }

    @Override
    public void update()
    {
        --position.x;

        if (position.x + this.screen.right  < 0)
            position.x = screen.right;
    }

    @Override
    public void drawing(GraphicsFW graphicsFW) {}
}
