package com.momotoff.my_framework;
import android.graphics.Point;
import android.graphics.Rect;

public class StaticTextFW
{
    public String text;
    public Point position;
    public int size;

    public StaticTextFW(String text, Point position, int size)
    {
        this.text = text;
        this.position = position;
        this.size = size;
    }

    public Rect getTouchArea(GraphicsFW graphics)
    {
        return new Rect(position.x,position.y - size,position.x + graphics.measureText(this), position.y);
    }
}
