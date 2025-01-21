package com.momotoff.my_framework;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class BackgroundAudioFW
{
    private final MediaPlayer mediaPlayer;
    private final Context context;
    private boolean isPlaying = false;
    private int currentPos = 0;

    public BackgroundAudioFW(CoreFW coreFW)
    {
        context = coreFW.getBaseContext();
        mediaPlayer = new MediaPlayer();
    }

    public void setTrack(int track)
    {
        Uri mediaPath = Uri.parse("android.resource://" + context.getPackageName() + "/" + track);

        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context.getApplicationContext(), mediaPath);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            isPlaying = false;
        }
        catch (Exception e)
        {
            Log.w(this.getClass().getSimpleName(), "Error loading audio track " + track + ". Exception says: " + e.getMessage());
        }
    }

    public void start()
    {
        if (isPlaying)
            return;

        if (currentPos != 0)
            mediaPlayer.seekTo(currentPos);

        mediaPlayer.start();
        currentPos = 0;
        isPlaying = true;
    }

    public void stop()
    {
        mediaPlayer.stop();
    }

    public void pause()
    {
        if (!isPlaying)
            return;

        currentPos = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
        isPlaying = false;
    }
}
