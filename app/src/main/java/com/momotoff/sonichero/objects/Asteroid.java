package com.momotoff.sonichero.objects;

import android.graphics.Point;
import android.graphics.Rect;

import com.momotoff.my_framework.AnimationFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.sonichero.utilities.Resource;

public class Asteroid extends ObjectFW implements IDrawable
{
    AnimationFW asteroidAnim;

    public Asteroid(Point sceneSize, int height)
    {
        this.screen = new Rect(0, height, sceneSize.x, sceneSize.y);
        this.asteroidAnim = new AnimationFW(Resource.asteroidSprite);

        Point position = new Point((int) (screen.right * Math.random()), (int) (Math.random() * screen.bottom) + Resource.asteroidSprite.get(0).getHeight() + height);
        updatePosition(position, Resource.asteroidSprite.get(0));

        radius = Resource.asteroidSprite.get(0).getHeight() / 2;
    }

    public void restartFromInitialPosition()
    {
        position.x = screen.right;
        position.y = (int) (screen.bottom * Math.random()) + Resource.asteroidSprite.get(0).getHeight() * 2;
    }

    @Override
    public void update()
    {
        position.x -= speed;

        if (position.x + Resource.asteroidSprite.get(0).getWidth() < 0)
        {
            position.x = screen.right;
            position.y = (int) (Math.random() * screen.bottom) + screen.top;
        }

        updatePosition(position, Resource.asteroidSprite.get(0));
        asteroidAnim.runAnimation();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        asteroidAnim.drawingAnimation(graphicsFW, getPosition());
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}
