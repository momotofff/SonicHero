package com.momotoff.my_framework;

public class TimerDelay
{
    long startNs;
    boolean isStarted = false;
    final long NS_IN_SECOND = 1000000000;

    public void start()
    {
        startNs = System.nanoTime();
        isStarted = true;
    }

    public boolean isElapsed(int second)
    {
        long difference = System.nanoTime() - startNs;
        return isStarted && (difference > second * NS_IN_SECOND);
    }

    public void stop() {isStarted = false;
    }
}
