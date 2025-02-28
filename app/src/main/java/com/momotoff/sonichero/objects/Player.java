package com.momotoff.sonichero.objects;

import static com.momotoff.my_framework.CollisionDetector.Side.*;

import android.graphics.Point;

import com.momotoff.my_framework.AnimationFW;
import com.momotoff.my_framework.CollisionDetector;
import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.my_framework.TimerDelay;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Resource;

import java.util.Locale;

public class Player extends ObjectFW implements IDrawable
{
    private AnimationFW playerSprite;
    private AnimationFW playerSpriteUp;
    private AnimationFW playerSpriteDamage;
    private AnimationFW playerSpriteUpDamage;
    private AnimationFW playerSpriteDestruction;
    private AnimationFW playerSpriteShield;
    private CoreFW coreFW;

    private int passedDistance;
    public int level = 1;
    public int shields = 3;

    private boolean hitAsteroid = false;
    private boolean isGameOver = false;
    public boolean hitShield = false;

    private static boolean running = false;
    private static final int JUMP_HEIGHT = 200;
    private int jumpCounter = 0;
    private double jumpVelocity = 45;

    private double fallVelocity = 1;
    private double acceleration = 5;

    private static boolean jumping = false;
    private static boolean falling = true;

    public CollisionDetector.Side verticalSide = NONE;
    public CollisionDetector.Side horizontalSide = NONE;

    private TimerDelay damageDelay = new TimerDelay();
    public TimerDelay shieldDelay = new TimerDelay();

    public Player(CoreFW coreFW, Point maxScreen, int height)
    {
        radius = Resource.playerSprite.get(0).getHeight() / 2;


        updatePosition(new Point(100, 200), Resource.playerSprite.get(0));
        this.coreFW = coreFW;
        this.screen.right = maxScreen.x;
        this.screen.bottom = maxScreen.y - Resource.playerSprite.get(0).getHeight();
        this.screen.top = height;
        this.playerSprite = new AnimationFW(Resource.playerSprite);
        this.playerSpriteUp = new AnimationFW(Resource.playerSpriteUp);
        this.playerSpriteUpDamage = new AnimationFW(Resource.playerSpriteUpDamage);
        this.playerSpriteDamage = new AnimationFW(Resource.playerSpriteDamage);
        this.playerSpriteDestruction = new AnimationFW(Resource.playerSpriteDestruction);
        this.playerSpriteShield = new AnimationFW(Resource.playerSpriteShield);
    }

    @Override
    public void update()
    {
        if (verticalSide == NONE)
        {
            falling = true;
        }

        if (verticalSide == BOTTOM)
            falling = false;

        if (verticalSide == TOP)
        {
            jumping = false;
            falling = true;
            jumpVelocity = 45;
            jumpCounter = 0;
        }


        if (horizontalSide == RIGHT)
            running = false;

        if (running)
        {
            if (speed < 20)
            {
                speed += (int) acceleration;
                passedDistance += speed;
                acceleration *= 1.3;
            }
            else
                passedDistance += speed;
        }
        else
        {
            acceleration = 1;
            speed = 1;
        }

        if (jumping)
        {
            if (jumpCounter < JUMP_HEIGHT)
            {
                jumpCounter += (int) jumpVelocity;
                position.y -= (int) jumpVelocity;
                jumpVelocity *= 0.8;
            }
            else
            {
                jumping = false;
                falling = true;
                jumpVelocity = 45;
                jumpCounter = 0;
            }
        }
        if (falling || !jumping)
        {
            if (verticalSide == BOTTOM)
            {
                falling = false;
                jumpCounter = 0;
                fallVelocity = 1;
            }
            else
            {
                position.y += (int) fallVelocity;

                if (fallVelocity < 15)
                    fallVelocity *= 1.2;
            }
        }

        if (coreFW.getTouchListenerFW().getTouchDown(Hud.getButtonRightArea())) {
            startRunning();
        }

        if (coreFW.getTouchListenerFW().getTouchUp(Hud.getButtonRightArea())) {
            stopRunning();
        }

        if (coreFW.getTouchListenerFW().getTouchDown(Hud.getButtonUpArea()) && verticalSide == BOTTOM) {
            startJump();
        }

        if (coreFW.getTouchListenerFW().getTouchUp(Hud.getButtonUpArea())) {
            stopJump();
        }

        updatePosition(position, Resource.playerSprite.get(0));

        if (shieldDelay.isElapsed(2))
            hitShield = false;

        if (damageDelay.isElapsed(1))
            hitAsteroid = false;

       if (hitAsteroid)
       {
           if (running)
               playerSpriteUpDamage.runAnimation();
           else
               playerSpriteDamage.runAnimation();
       }

       else
       {
           if (running)
               playerSpriteUp.runAnimation();
           else
               playerSprite.runAnimation();
       }

        if (hitShield)
        {
            if (running)
                playerSpriteShield.runAnimation();
            else
                playerSpriteShield.runAnimation();
        }

        if (isGameOver)
            playerSpriteDestruction.runAnimation();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        if (hitShield)
        {
            if (running)
                playerSpriteShield.drawingAnimation(graphicsFW, position);

            else
                playerSpriteShield.drawingAnimation(graphicsFW, position);
            return;
        }

        if (hitAsteroid)
        {
            if (running)
                playerSpriteUpDamage.drawingAnimation(graphicsFW, position);
            else
                playerSpriteDamage.drawingAnimation(graphicsFW, position);
        }

        else
        {
            if (running)
                playerSpriteUp.drawingAnimation(graphicsFW, position);
            else
                playerSprite.drawingAnimation(graphicsFW, position);
        }

        if (isGameOver)
        {
            playerSpriteDestruction.drawingAnimation(graphicsFW, position);
        }
    }

    public void hitObject()
    {
        if (hitShield)
        {
            hitAsteroid = false;
            coreFW.getSoundFW().start(R.raw.tap);
            return;
        }

        else
        {
            --shields;
            hitAsteroid = true;
            damageDelay.start();
            coreFW.getSoundFW().start(R.raw.damage);
        }

        if (isDead())
        {
            isGameOver = true;
            coreFW.getSoundFW().start(R.raw.destroy);
        }
    }

    public String getShields() { return String.format(Locale.getDefault(), "%s: %d", coreFW.getString(R.string.txtHudCurrentShieldsPlayer), shields); }
    public String getTxtPassedDistance() { return String.format(Locale.getDefault(), "%s: %d", coreFW.getString(R.string.txtHudPassedDistance), passedDistance); }
    public String getSpeed() { return String.format(Locale.getDefault(), "%s: %d", coreFW.getString(R.string.txtHudCurrentSpeedPlayer), speed); }
    public String getLevel() { return String.format(Locale.getDefault(), "%s: %d", coreFW.getString(R.string.txtLevel), level); }
    public int getPassedDistance()
    {
        return passedDistance;
    }
    public boolean isDead() { return shields < 0; }

    private void startRunning() { running = true; }
    private void stopRunning() { running = false; }

    private void startJump()
    {
        if (verticalSide == BOTTOM)
        {
            jumping = true;
            falling = false;
            jumpCounter = 0;
        }
    }

    private void stopJump() {}

    public static boolean getRunning() { return running; }

    public void updateVerticalSide(CollisionDetector.Side side) { verticalSide = side; }
    public void updateHorizontalSide(CollisionDetector.Side side) { horizontalSide = side; }

    public CollisionDetector.Side getSide()  { return verticalSide; }
}

