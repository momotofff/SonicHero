package com.momotoff.sonichero.objects;

import android.graphics.Point;
import android.graphics.Rect;

import com.momotoff.my_framework.AnimationFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.sonichero.utilities.Resource;

public class BonusSpeed extends ObjectFW implements IDrawable
{
    AnimationFW bonusSpeedAnim;

    public BonusSpeed(Point sceneSize, int height)
    {
        this.screen = new Rect(0, height, sceneSize.x, sceneSize.y);
        this.bonusSpeedAnim = new AnimationFW(Resource.bonusSpeedSprite);

        Point position = new Point((int) (screen.right * Math.random()), (int) (Math.random() * screen.bottom) + Resource.bonusSpeedSprite.get(0).getHeight() + height);
        updatePosition(position, Resource.bonusSpeedSprite.get(0));

        radius = Resource.bonusSpeedSprite.get(0).getHeight() / 2;
    }

    public void restartFromInitialPosition()
    {
        position.x = screen.right;
        position.y = (int) (screen.bottom * Math.random()) + Resource.bonusSpeedSprite.get(0).getHeight() * 2;
    }

    @Override
    public void update()
    {
        position.x -= speed;

        if (position.x + Resource.bonusSpeedSprite.get(0).getWidth() < 0)
        {
            position.x = screen.right;
            position.y = (int) (Math.random() * screen.bottom) + screen.top;
        }

        updatePosition(position, Resource.bonusSpeedSprite.get(0));
        bonusSpeedAnim.runAnimation();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        bonusSpeedAnim.drawingAnimation(graphicsFW, getPosition());
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}