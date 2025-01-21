package com.momotoff.sonichero.scene;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.my_framework.TimerDelay;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.classes.Manager;
import com.momotoff.sonichero.objects.Asteroid;
import com.momotoff.sonichero.objects.Star;
import com.momotoff.sonichero.utilities.Save;

public class GameScene extends SceneFW
{
    private GameState gameState;
    private final Manager manager;
    private final Save save;
    private final TimerDelay powerUpDelay = new TimerDelay();

    private final StaticTextFW Ready = new StaticTextFW(coreFW.getString(R.string.txtGameSceneReady), new Point(300,200), 100);

    enum GameState
    {
        READY, RUNNING, PAUSE, GAME_OVER
    }

    public GameScene(CoreFW coreFW, Save save)
    {
        super(coreFW);

        this.save = save;
        gameState = GameState.READY;
        manager = new Manager(coreFW, sceneSize);

        coreFW.getBackgroundAudioFW().setTrack(com.momotoff.my_framework.R.raw.game1);
        coreFW.getBackgroundAudioFW().start();

        powerUpDelay.start();
    }

    @Override
    public void update()
    {
        MainMenu.getInstance().setBannerVisibility(View.GONE);
        MainMenu.getInstance().setRegistrationWindowVisibility(View.GONE);

        switch (gameState)
        {
            case READY:       updateStateReady(); break;
            case PAUSE:       updateStatePause(); break;
            case RUNNING:     updateStateRunning(); break;
            case GAME_OVER:   coreFW.setScene(new GameOver(coreFW, manager, save));
        }

        if (powerUpDelay.isElapsed(20))
        {
            coreFW.getSoundFW().start(R.raw.level_up);
            ++manager.player.level;
            ++manager.player.shields;
            ++manager.player.speed;
            ++manager.player.dexterity;


            powerUpDelay.start();

            Asteroid asteroid = new Asteroid(manager.maxScreen, manager.getHUD_HEIGHT());
            asteroid.restartFromInitialPosition();
            manager.getzOrder().add(asteroid);
            manager.asteroids.add(asteroid);

            for (Star star : manager.background.stars)
                ++star.speed;
            for (Star star : manager.background.bigStars1)
                ++star.speed;
            for (Star star : manager.background.bigStars2)
                ++star.speed;
        }
    }

    private void updateStateRunning()
    {
        gameState = GameState.RUNNING;
        manager.update();

        if (manager.gameOver)
        {
            save.addDistance(manager.player.getPassedDistance());
            save.save(coreFW.getSharedPreferences());
            gameState = GameState.GAME_OVER;
            coreFW.getBackgroundAudioFW().stop();
        }
    }

    private void updateStatePause() {}

    private void updateStateReady()
    {
        if (coreFW.getTouchListenerFW().getTouchUp(new Rect(0, 0, manager.maxScreen.x, manager.maxScreen.y)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            gameState = GameState.RUNNING;
        }
    }

    @Override
    public void drawing()
    {
        switch (gameState)
        {
            case READY:       drawingStateReady(); break;
            case PAUSE:       drawingStatePause(); break;
            case RUNNING:     drawingStateRunning(); break;
        }
    }

    private void drawingStateRunning()
    {
        graphicsFW.clearScene(Color.BLACK);
        manager.drawing(graphicsFW);
    }

    private void drawingStateReady()
    {
        graphicsFW.clearScene(Color.BLACK);
        graphicsFW.drawText(Ready);
    }

    private void drawingStatePause() {}
}
