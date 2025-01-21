package com.momotoff.sonichero.utilities;

import android.content.SharedPreferences;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.sonichero.R;

import java.util.Locale;

public class Settings
{
    public static int[] distance = {0, 0, 0, 0, 0};

    public static void save(CoreFW coreFW)
    {
        SharedPreferences.Editor editor = coreFW.getSharedPreferences().edit();
        editor.clear();

        for (int i = 0; i  < distance.length; ++i)
            editor.putInt((String.format(Locale.getDefault(), "%s %s%d", coreFW.getString(R.string.txtMainMenuResult), "№", i)), distance[i]);

        editor.apply();
    }

    public static void load(CoreFW coreFW)
    {
        for (int i = 0; i < distance.length; ++i)
        {
            distance[i] = coreFW.getSharedPreferences().getInt((String.format(Locale.getDefault(), "%s %s%d", coreFW.getString(R.string.txtMainMenuResult), "№", i)), 0);
        }
    }

    public static void refresh()
    {

    }

    public static void addDistance(int value)
    {
        for (int i = 0; i < distance.length; ++i)
        {
            int number = value;

            if (value > distance[i])
            {

                number = distance[i];
                distance[i] = value;
                value = number;
            }
        }
    }

    public static void pushServer()
    {

    }

    public static void pullServer()
    {

    }
}
