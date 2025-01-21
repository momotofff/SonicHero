package com.momotoff.my_framework;

import android.content.Context;

import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundFW
{
    private final SoundPool soundPool;
    private final Context context;
    private Map<Integer, Integer> sounds = new HashMap<>();

    public SoundFW(CoreFW coreFW)
    {
        context = coreFW.getBaseContext();
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    }

    public void load(int resourceId)
    {
        sounds.put(resourceId, soundPool.load(context, resourceId,1));
    }

    public void start(int resourceId)
    {
        Integer index = sounds.get(resourceId);

        if (index != null)
            soundPool.play(index, 1, 1, 0, 0, 1);
    }
}

