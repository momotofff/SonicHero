package com.momotoff.sonichero.objects;

import android.graphics.Color;
import android.graphics.Point;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.my_framework.StaticTextFW;

public class Hud extends ObjectFW implements IDrawable
{
    private final StaticTextFW txtPassedDistance;
    private final StaticTextFW txtCurrentSpeedPlayer;
    private final StaticTextFW txtCurrentShieldsPlayer;
    private final StaticTextFW txtLevel;

    private final CoreFW coreFW;
    private final Player player;

    private final int height;

    public Hud(CoreFW coreFW, Player player, int height)
    {
        this.coreFW = coreFW;
        this.player = player;
        this.height = height;

        txtPassedDistance = new StaticTextFW(player.getTxtPassedDistance(), new Point(10, 70), 40);
        txtCurrentSpeedPlayer = new StaticTextFW(player.getSpeed(), new Point(400, 70), 40);
        txtCurrentShieldsPlayer = new StaticTextFW(player.getShields(), new Point(700, 70), 40);
        txtLevel = new StaticTextFW(player.getLevel(), new Point(1000,70), 40);
    }

    @Override
    public void update()
    {
        txtPassedDistance.text = player.getTxtPassedDistance();
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

    }
}
