package com.momotoff.sonichero.scene;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Resource;
import com.momotoff.sonichero.utilities.Save;

public class MainMenu extends SceneFW
{
    private final StaticTextFW Title = new StaticTextFW(coreFW.getString(R.string.txtMainMenuNameGame), new Point(50, 150), 100);
    private final StaticTextFW MenuStart = new StaticTextFW(coreFW.getString(R.string.txtMainMenuStartGame), new Point(50, 270), 60);
    private final StaticTextFW MenuSettings = new StaticTextFW(coreFW.getString(R.string.txtMainMenuMenuSettings), new Point(50, 370), 60);
    private final StaticTextFW MenuResults = new StaticTextFW(coreFW.getString(R.string.txtMainMenuResult), new Point(50, 470), 60);
    private final StaticTextFW MenuExit = new StaticTextFW(coreFW.getString(R.string.txtMainMenuExitGame), new Point(50, 570), 60);
    private final Save save;

    private final RegistrationWindow registrationWindow;
    private final BannerAdvertising adBanner;

    private static MainMenu instance;

    public static MainMenu createInstance(CoreFW coreFW, Save save)
    {
        if (instance == null)
            instance = new MainMenu(coreFW, save);

        return instance;
    }

    public static MainMenu getInstance()
    {
        if (instance == null)
            throw new RuntimeException("You should call createInstance first!");
        return instance;
    }

    private MainMenu(CoreFW coreFW, Save save)
    {
        super(coreFW);
        this.save = save;
        coreFW.getBackgroundAudioFW().setTrack(com.momotoff.my_framework.R.raw.menu);
        coreFW.getBackgroundAudioFW().start();

        ConstraintLayout layout = new ConstraintLayout(this.coreFW.getApplicationContext());
        layout.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
        layout.removeAllViews();
        layout.addView(coreFW.getLoopFW());
        coreFW.setContentView(layout);

        //    demo-banner-yandex
        adBanner = new BannerAdvertising(coreFW, "R-M-13765196-1");
        layout.addView(adBanner.banner, coreFW.getDisplaySize().x, coreFW.getDisplaySize().y * 2 - 160);

        registrationWindow = new RegistrationWindow(coreFW, save);
        layout.addView(registrationWindow);

        coreFW.setContentView(layout);
    }

    @Override
    public void update()
    {
        adBanner.setBannerVisibility(View.VISIBLE);

        if (registrationWindow.getVisibility() == View.VISIBLE)
        {
            if (coreFW.getTouchListenerFW().getTouchUp(new Rect(0, 0, sceneSize.x, sceneSize.y)))
                setRegistrationWindowVisibility(View.GONE);

            return;
        }

        if (coreFW.getTouchListenerFW().getTouchUp(MenuStart.getTouchArea(graphicsFW)))
        {
            coreFW.getBackgroundAudioFW().stop();
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(new GameScene(coreFW, save));
        }

        if (coreFW.getTouchListenerFW().getTouchUp(MenuResults.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(new LocalRating(coreFW, save));
        }

        if (coreFW.getTouchListenerFW().getTouchUp(MenuExit.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.onBackPressed();
        }

        if (coreFW.getTouchListenerFW().getTouchUp(MenuSettings.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(new Settings(coreFW));
        }
    }

    @Override
    public void drawing()
    {
        graphicsFW.drawTexture(Resource.background, new Point(0, 0));
        graphicsFW.drawText(Title);
        graphicsFW.drawText(MenuStart);
        graphicsFW.drawText(MenuSettings);
        graphicsFW.drawText(MenuResults);
        graphicsFW.drawText(MenuExit);
    }

    public void setBannerVisibility(int visibility)
    {
        this.coreFW.runOnUiThread(() -> adBanner.setBannerVisibility(visibility));
    }

    public void setRegistrationWindowVisibility(int visibility)
    {
        this.coreFW.runOnUiThread(() -> registrationWindow.setVisibility(visibility));
    }
}
