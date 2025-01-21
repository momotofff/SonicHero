package com.momotoff.sonichero.generation;

import android.graphics.Point;

import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.sonichero.objects.Star;
import com.momotoff.sonichero.utilities.Resource;

import java.util.ArrayList;

public class Background implements IDrawable
{
    final public ArrayList<Star> stars = new ArrayList<>();
    final public ArrayList<Star> bigStars1 = new ArrayList<>();
    final public ArrayList<Star> bigStars2 = new ArrayList<>();
    final public ArrayList<Star> garbageList1 = new ArrayList<>();
    final public ArrayList<Star> garbageList2 = new ArrayList<>();

    public Background(Point displaySize, int height)
    {

        final int STARS_COUNT = 80;
        final int GARBAGE_COUNT = 5;

        for (int i = 0; i < STARS_COUNT; ++i)
            stars.add(new Star(displaySize, height));

        for (int i = 0; i < GARBAGE_COUNT; ++i)
        {
            Star star = new Star(displaySize, height);
            Star star2 = new Star(displaySize, height);
            bigStars1.add(star);
            bigStars2.add(star2);

            Star garbage1 = new Star(displaySize, height);
            Star garbage2 = new Star(displaySize, height);
            garbageList1.add(garbage1);
            garbageList2.add(garbage2);
        }
    }

    @Override
    public void update()
    {
        for (Star star: stars)
             star.update();

        for (Star star: bigStars1)
            star.update();

        for (Star star: bigStars2)
            star.update();

        for (Star star: garbageList1)
            star.update();

        for (Star star: garbageList2)
            star.update();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        graphicsFW.drawTexture(Resource.starSky, new Point(0, 0));

        for (Star star: stars)
            star.drawing(graphicsFW);

        for (Star star: bigStars1)
            graphicsFW.drawTexture(Resource.bigStarSprite.get(0), star.position);

        for (Star star: bigStars2)
            graphicsFW.drawTexture(Resource.bigStarSprite.get(1), star.position);

        for (Star star: garbageList1)
            graphicsFW.drawTexture(Resource.bigStarSprite.get(2), star.position);

        for (Star star: garbageList2)
            graphicsFW.drawTexture(Resource.bigStarSprite.get(3), star.position);
    }
}
