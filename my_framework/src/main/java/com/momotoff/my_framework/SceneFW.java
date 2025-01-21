package com.momotoff.my_framework;

import android.graphics.Point;

public abstract class SceneFW
{
    public CoreFW coreFW;
    public Point sceneSize;
    public GraphicsFW graphicsFW;

    public SceneFW(CoreFW coreFW)
    {
        this.coreFW = coreFW;
        sceneSize = new Point(coreFW.getGraphicsFW().getFrameBufferWidth(), coreFW.getGraphicsFW().getFrameBufferHeight());
        graphicsFW = coreFW.getGraphicsFW();
    }

    public abstract void update();
    public abstract void drawing();
}
