package com.momotoff.sonichero.classes;

import android.graphics.Point;

import com.momotoff.my_framework.CollisionDetector;
import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.TimerDelay;
import com.momotoff.sonichero.generation.Background;
import com.momotoff.sonichero.objects.Asteroid;
import com.momotoff.sonichero.objects.BonusShield;
import com.momotoff.sonichero.objects.BonusSpeed;
import com.momotoff.sonichero.objects.Hud;
import com.momotoff.sonichero.objects.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Manager
{
    private final int HUD_HEIGHT = 100;
    public final Point maxScreen;
    public Background background;
    public Player player;
    private final Hud hud;
    public ArrayList<Asteroid> asteroids = new ArrayList<>();
    private BonusSpeed bonusSpeed;
    private BonusShield bonusShield;
    private List<IDrawable> zOrder = new ArrayList<>();

    TimerDelay gameOverDelay = new TimerDelay();

    public boolean gameOver = false;


    public Manager(CoreFW coreFW, Point displaySize)
    {
        int ASTEROIDS_COUNT = 3;
        this.maxScreen = displaySize;

        background = new Background(displaySize, HUD_HEIGHT);
        player = new Player(coreFW, maxScreen, HUD_HEIGHT);
        hud = new Hud(coreFW, player, HUD_HEIGHT);
        bonusSpeed = new BonusSpeed(displaySize, HUD_HEIGHT);
        bonusShield = new BonusShield(displaySize, HUD_HEIGHT);

        for (int i = 0; i < ASTEROIDS_COUNT; ++i)
            asteroids.add(new Asteroid(displaySize, HUD_HEIGHT));

        zOrder.add(background);
        zOrder.add(bonusSpeed);
        zOrder.add(bonusShield);
        zOrder.add(player);
        zOrder.add(hud);
    }

    public void update()
    {
        for(IDrawable entry : zOrder)
        {
            entry.update();
        }

        for(IDrawable ast: asteroids)
            ast.update();

        checkHit();

        if (gameOverDelay.isElapsed(1))
            gameOver = true;
    }

    private void checkHit()
    {
        Optional<Asteroid> optional = asteroids.stream()
                .filter(asteroid -> CollisionDetector.detect(player, asteroid))
                .findFirst();

        optional.ifPresent(asteroid -> {
            player.hitObject();
            asteroid.restartFromInitialPosition();

            if (player.isDead())
                gameOverDelay.start();
        });

        if (CollisionDetector.detect(player, bonusShield))
        {
            bonusShield.restartFromInitialPosition();
            player.shieldDelay.start();
            player.hitShield = true;
        }

        if (CollisionDetector.detect(player, bonusSpeed))
        {
            bonusSpeed.restartFromInitialPosition();

            player.speed += 2;
            --player.dexterity;
        }
    }

    public void drawing(GraphicsFW graphicsFW)
    {
        for(IDrawable entry : zOrder)
            entry.drawing(graphicsFW);

        for (IDrawable ast: asteroids)
            ast.drawing(graphicsFW);
    }

    public int getHUD_HEIGHT() {
        return HUD_HEIGHT;
    }

    public List<IDrawable> getzOrder() {
        return zOrder;
    }
}