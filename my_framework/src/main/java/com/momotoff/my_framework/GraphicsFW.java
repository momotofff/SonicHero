package com.momotoff.my_framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsFW
{
    private final AssetManager assetManager;
    private final Bitmap frameBuffer;
    public final Canvas canvas;
    private final Paint paint;


    public GraphicsFW(AssetManager assetManager, Bitmap frameBuffer, Typeface tf)
    {
        this.assetManager = assetManager;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        paint.setTypeface(tf);

    }

    public void clearScene(int colorRGB)
    {
        canvas.drawRGB(colorRGB, colorRGB, colorRGB);
    }

    public void drawPixel(Point position, int color)
    {
        paint.setColor(color);
        canvas.drawPoint(position.x, position.y, paint);

    }

    public void drawLine(int startX, int startY, int stopX, int stopY, int color)
    {
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public int measureText(StaticTextFW text)
    {
        paint.setTextSize(text.size);
        return (int) paint.measureText(text.text);
    }

    public void drawText(StaticTextFW text)
    {
        paint.setColor(Color.WHITE);
        paint.setTextSize(text.size);
        canvas.drawText(text.text, text.position.x, text.position.y, paint);
    }

    public void drawTexture(Bitmap bitmap, Point position)
    {
        canvas.drawBitmap(bitmap, position.x, position.y, null);
    }

    public int getFrameBufferWidth()
    {
        return frameBuffer.getWidth();
    }
    public int getFrameBufferHeight() { return frameBuffer.getHeight(); }

    public Bitmap newTexture(String fileName)
    {
        InputStream inputStream;
        Bitmap texture;

        try
        {
            inputStream = assetManager.open(fileName);
            texture = BitmapFactory.decodeStream(inputStream);

            if (texture == null)
                throw new RuntimeException("File not found exception " + fileName);
        }

        catch (IOException e) {
            throw new RuntimeException("не находит он этот ебучий файл " + fileName);
        }

        if (inputStream != null)
        {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return texture;
    }

    public Bitmap newSprite(Bitmap textureAtlas, Rect sprite)
    {
        return Bitmap.createBitmap(textureAtlas, sprite.left, sprite.top, sprite.right, sprite.bottom);
    }
}
