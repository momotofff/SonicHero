package com.momotoff.my_framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoopFW extends SurfaceView implements Runnable
{
    private final int FPS = 30;
    private boolean running = false;

    private Thread gameThread = null;
    private final CoreFW coreFW;
    private final Bitmap frameBuffer;
    private final SurfaceHolder surfaceHolder;

    public LoopFW(CoreFW coreFW, Bitmap frameBuffer)
    {
        super(coreFW);
        this.frameBuffer = frameBuffer;
        this.coreFW = coreFW;
        this.surfaceHolder = getHolder();
    }

    @Override
    public void run()
    {
        long drawInterval = 1000 / FPS;
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null && running)
        {
            synchronized (surfaceHolder)
            {
                updateGame();
                drawingGame();
            }

            long remainingTime = nextDrawTime - System.currentTimeMillis();

            try
            {
                if (remainingTime > 0)
                    Thread.sleep(remainingTime);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            nextDrawTime += drawInterval;
        }
    }

    public void startGame()
    {
        if (running)
            return;

        running = true;

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame()
    {
        if (!running)
            return;

        running = false;
        gameThread = null;
    }

    public void pausedGame()
    {
        running = false;

        try
        {
            gameThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void updateGame()
    {
        coreFW.getCurrentScene().update();
    }

    private void drawingGame()
    {
        if (!surfaceHolder.getSurface().isValid() || !running)
            return;

        Rect rect = new Rect();
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.getClipBounds(rect);
        canvas.drawBitmap(frameBuffer, null, rect, null);
        coreFW.getCurrentScene().drawing();
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
