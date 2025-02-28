package com.momotoff.sonichero.objects;

import android.graphics.Point;
import android.graphics.Rect;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.sonichero.utilities.Resource;

public class Earth extends ObjectFW implements IDrawable
{
    public Earth(Point sceneSize, int height)
    {
        this.screen = new Rect(0, height, sceneSize.x, sceneSize.y);
        this.position.y = this.screen.bottom - Resource.earth.getHeight();
        radius = Resource.bonusSpeedSprite.get(0).getHeight() / 2;
    }

    @Override
    public void update()
    {
        if (Player.getRunning())
        {
            position.x -= speed;
            updatePosition(position, Resource.earth);
        }

        if (position.x + Resource.earth.getWidth()  < 0)
            position.x = Resource.earth.getWidth();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        this.drawing(graphicsFW);
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}