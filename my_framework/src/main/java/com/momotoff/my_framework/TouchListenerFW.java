package com.momotoff.my_framework;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class TouchListenerFW implements View.OnTouchListener
{
    PointF touch = new PointF();
    boolean isTouchDown;
    boolean isTouchUp;
    final PointF scale;

    public TouchListenerFW(View view, PointF scale)
    {
        view.setOnTouchListener(this);
        this.scale = scale;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        synchronized (this)
        {
            isTouchDown = false;
            isTouchUp = false;

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    touch.x = event.getX() * scale.x;
                    touch.y = event.getY() * scale.y;
                    isTouchDown = true;
                    isTouchUp = false;
                    break;

                case MotionEvent.ACTION_UP:
                    touch.x = event.getX() * scale.x;
                    touch.y = event.getY() * scale.y;
                    isTouchDown = false;
                    isTouchUp = true;
                    break;
            }
        }

        return true;
    }

    public boolean getTouchUp(Rect touchArea)
    {
        if (!isTouchUp)
            return false;

        if (!touchArea.contains((int) touch.x, (int) touch.y))
            return false;

        isTouchUp = false;
        return true;
    }

    public boolean getTouchDown(Rect touchArea)
    {
        if (!isTouchDown)
            return false;

        if (!touchArea.contains((int) touch.x, (int) touch.y))
            return false;

        isTouchDown = false;
        return true;
    }
}
