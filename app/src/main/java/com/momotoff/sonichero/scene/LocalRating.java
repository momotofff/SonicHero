package com.momotoff.sonichero.scene;

import android.graphics.Point;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Resource;
import com.momotoff.sonichero.utilities.Save;

import java.util.Locale;

public class LocalRating extends SceneFW
{
    private final StaticTextFW[] numbers = new StaticTextFW[5];
    private final StaticTextFW localRating = new StaticTextFW(coreFW.getString(R.string.txtLocalRating), new Point(50, 100), 100);
    private final StaticTextFW worldRating = new StaticTextFW(coreFW.getString(R.string.txtWorldRating), new Point(400, 580), 70);
    private final StaticTextFW back = new StaticTextFW(coreFW.getString(R.string.txtBack), new Point(50, 580), 70);

    private final Save save;

    public LocalRating(CoreFW coreFW, Save save)
    {
        super(coreFW);
        this.save = save;

        final int RESULT_START_Y = 200;
        final int RESULT_STEP_Y = 70;

        Point position = new Point(localRating.position.x, RESULT_START_Y);

        for (int i = 0; i < numbers.length; ++i)
        {
            String text = String.format(Locale.getDefault(), "%d. %d", i + 1, save.getDistance()[i]);
            this.numbers[i] = new StaticTextFW(text, new Point(position), 50);
            position.y += RESULT_STEP_Y;
        }
    }

    @Override
    public void update()
    {
        if (coreFW.getTouchListenerFW().getTouchUp(back.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(MainMenu.getInstance());
        }

        if (coreFW.getTouchListenerFW().getTouchUp(worldRating.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(new WorldRating(coreFW, save));
        }
    }

    @Override
    public void drawing()
    {
        graphicsFW.drawTexture(Resource.background, new Point(0, 0));
        graphicsFW.drawText(localRating);
        graphicsFW.drawText(back);
        graphicsFW.drawText(worldRating);

        for (StaticTextFW number: numbers)
            graphicsFW.drawText(number);
    }
}
