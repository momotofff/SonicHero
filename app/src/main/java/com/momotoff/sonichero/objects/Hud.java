package com.momotoff.sonichero.objects;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.my_framework.StaticButtonFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.sonichero.utilities.Resource;

public class Hud extends ObjectFW implements IDrawable
{
    private final StaticTextFW txtPassedDistance;
    private final StaticTextFW txtCurrentSpeedPlayer;
    private final StaticTextFW txtCurrentShieldsPlayer;
    private final StaticTextFW txtLevel;

    private final CoreFW coreFW;
    private final Player player;

    private final int height;

    private static StaticButtonFW buttonUp;
    private static StaticButtonFW buttonRight;

    public Hud(CoreFW coreFW, Player player, int height)
    {
        this.coreFW = coreFW;
        this.player = player;
        this.height = height;

        txtPassedDistance = new StaticTextFW(player.getTxtPassedDistance(), new Point(10, 70), 40);
        txtCurrentSpeedPlayer = new StaticTextFW(player.getSpeed(), new Point(400, 70), 40);
        txtCurrentShieldsPlayer = new StaticTextFW(player.getShields(), new Point(700, 70), 40);
        txtLevel = new StaticTextFW(player.getLevel(), new Point(1000,70), 40);

        buttonUp = new StaticButtonFW(Resource.buttonUp, new Point(0 + 50, coreFW.getFRAME_BUFFER().y - 100));
        buttonRight = new StaticButtonFW(Resource.buttonRight, new Point(coreFW.getFRAME_BUFFER().x - 100, coreFW.getFRAME_BUFFER().y - 100));
    }

    public static Rect getButtonUpArea()
    {
        return buttonUp.getTouchArea();
    }

    public static Rect getButtonRightArea()
    {
        return buttonRight.getTouchArea();
    }

    @Override
    public void update()
    {
        //txtPassedDistance.text = player.getTxtPassedDistance();
        txtPassedDistance.text = player.verticalSide.toString();
        txtCurrentSpeedPlayer.text = player.getSpeed();
        txtCurrentShieldsPlayer.text = player.getShields();
        txtLevel.text = player.getLevel();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        graphicsFW.drawLine(0, height, graphicsFW.getFrameBufferWidth(), height, Color.WHITE);
        graphicsFW.drawText(txtPassedDistance);
        graphicsFW.drawText(txtCurrentSpeedPlayer);
        graphicsFW.drawText(txtCurrentShieldsPlayer);
        graphicsFW.drawText(txtLevel);

        graphicsFW.drawTexture(buttonUp.bitmap, buttonUp.position);
        graphicsFW.drawTexture(buttonRight.bitmap, buttonRight.position);

    }
}
