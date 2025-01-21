package com.momotoff.sonichero.utilities;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Save implements Serializable
{
    private int[] distance = {0, 0, 0, 0, 0};

    public Save(int[] distance)
    {
        this.distance = distance;
    }

    public Save()
    {
    }

    public void addDistance(int value)
    {
        for (int i = 0; i < distance.length; ++i)
        {
            int number = value;

            if (number > distance[i])
            {
                value = distance[i];
                distance[i] = number;
            }
        }
    }

    public void save(SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        String result = "";

        try
        {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(this);
            objStream.close();
            result = Base64.getEncoder().encodeToString(byteStream.toByteArray());
        }
        catch (Exception e)
        {
            Log.w(this.getClass().getSimpleName(), "Error while storing save data. Exception says: " + e.getMessage());
        }

        editor.putString(this.getClass().getSimpleName(), result);
        editor.apply();
    }

    public void loadDistance(SharedPreferences preferences)
    {
        String serialized = preferences.getString(this.getClass().getSimpleName(), "");
        byte[] data = Base64.getDecoder().decode(serialized);

        try
        {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.distance = ((Save) ois.readObject()).distance;
            ois.close();
        }
        catch (Exception e)
        {
            Log.w(this.getClass().getSimpleName(), "Error while deserializing save data. Exception says: " + e.getMessage());
        }
    }

    public int[] getDistance() { return distance; }
}
