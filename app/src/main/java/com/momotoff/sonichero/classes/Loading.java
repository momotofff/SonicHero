package com.momotoff.sonichero.classes;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Resource;

import java.util.ArrayList;

public class Loading
{
    private final Point SPRITE_SIZE = new Point(128, 64);
    private final Point SPRITE_SIZE_PLAYER = new Point(62, 74);

    public Loading(CoreFW coreFW, GraphicsFW graphicsFW)
    {
        loadTexture(graphicsFW);
        loadSprite(graphicsFW);
        loadSounds(coreFW);
    }

    private void loadSprite(GraphicsFW graphicsFW)
    {
        readerSpritePlayer(graphicsFW, Resource.playerSprite);
        readerSprite(graphicsFW, 1, Resource.playerSpriteUp);
        //readerSprite(graphicsFW, 2, Resource.bigStarSprite);
        //readerSprite(graphicsFW, 3, Resource.asteroidSprite);
        readerSprite(graphicsFW, 4, Resource.playerSpriteDestruction);
        readerSprite(graphicsFW, 5, Resource.playerSpriteDamage);
        readerSprite(graphicsFW, 6, Resource.playerSpriteUpDamage);
        readerSprite(graphicsFW, 7, Resource.playerSpriteShield);
        readerSprite(graphicsFW, 8, Resource.bonusSpeedSprite);
        readerSprite(graphicsFW, 9, Resource.bonusShieldSprite);

    }

    private void loadSounds(CoreFW coreFW)
    {
        coreFW.getSoundFW().load(R.raw.tap);
        coreFW.getSoundFW().load(R.raw.damage);
        coreFW.getSoundFW().load(R.raw.destroy);
        coreFW.getSoundFW().load(R.raw.level_up);
    }

    private void readerSprite(GraphicsFW graphicsFW, int top, ArrayList<Bitmap> sprite)
    {
        Rect rect = new Rect(0,SPRITE_SIZE.y * top, SPRITE_SIZE.x, SPRITE_SIZE.y);
        final int FRAMES_COUNT = 4;

        for (int i = 0; i < FRAMES_COUNT; ++i)
        {
            sprite.add(graphicsFW.newSprite(Resource.textureAtlas, rect));
            rect.left += SPRITE_SIZE.x;
        }
    }

    private void readerSpritePlayer(GraphicsFW graphicsFW, ArrayList<Bitmap> sprite)
    {
        Rect rect = new Rect(0,0, SPRITE_SIZE_PLAYER.x, SPRITE_SIZE_PLAYER.y);
        final int FRAMES_COUNT = 8;

        for (int i = 0; i < FRAMES_COUNT; ++i)
        {
            sprite.add(graphicsFW.newSprite(Resource.sonicTexture, rect));
            rect.left += SPRITE_SIZE_PLAYER.x;
        }
    }

    private void loadTexture(GraphicsFW graphicsFW)
    {
        Resource.textureAtlas = graphicsFW.newTexture("textureAtlas.png");
        Resource.sonicTexture = graphicsFW.newTexture("sonicTexture.png");
        Resource.background = graphicsFW.newTexture("Background.png");
        Resource.earth = graphicsFW.newTexture("earth.jpg");
        Resource.cloud = graphicsFW.newTexture("cloud.png");
        Resource.buttonUp = graphicsFW.newTexture("up.png");
        Resource.buttonRight = graphicsFW.newTexture("right.png");
    }
}