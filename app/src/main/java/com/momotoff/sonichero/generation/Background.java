package com.momotoff.sonichero.generation;

import android.graphics.Point;

import com.momotoff.my_framework.GraphicsFW;
import com.momotoff.my_framework.IDrawable;
import com.momotoff.my_framework.ObjectFW;
import com.momotoff.sonichero.objects.Cloud;
import com.momotoff.sonichero.objects.Earth;
import com.momotoff.sonichero.utilities.Resource;

import java.util.ArrayList;
import java.util.List;

public class Background extends ObjectFW implements IDrawable
{
    final public Cloud cloud;
    public List<Earth> earths = new ArrayList<>();
    //final public ArrayList<Star> bigStars1 = new ArrayList<>();
    //final public ArrayList<Star> bigStars2 = new ArrayList<>();
    //final public ArrayList<Star> garbageList1 = new ArrayList<>();
    //final public ArrayList<Star> garbageList2 = new ArrayList<>();

    public Background(Point displaySize, int hudHeight)
    {
        final int STARS_COUNT = 80;
        final int GARBAGE_COUNT = 5;

        cloud = new Cloud(displaySize, Resource.earth.getHeight());

        Earth earth1 = new Earth(displaySize, Resource.earth.getWidth());
        earth1.updatePosition(earth1.position, Resource.earth);

        Earth earth2 = new Earth(displaySize, Resource.earth.getWidth());
        earth2.position.x = earth1.position.x + Resource.earth.getWidth();
        //earth2.position.y -= 50;
        earth2.updatePosition(earth2.position, Resource.earth);

        Earth earth3 = new Earth(displaySize, Resource.earth.getHeight());
        earth3.position.y = 500;
        earth3.updatePosition(earth3.position, Resource.earth);

        earths.add(earth1);
        earths.add(earth2);
        earths.add(earth3);


        //this.screen = new Rect(0, hudHeight, displaySize.x, displaySize.y);


        Point positionCloud = new Point(screen.right, screen.bottom + Resource.cloud.getHeight() + hudHeight);
        updatePosition(positionCloud, Resource.cloud);

        /*
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
                 */
    }

    @Override
    public void update()
    {
        cloud.update();

        for(Earth earth: earths)
            earth.update();

        //for (Star star: bigStars1)
        //    star.update();

        //for (Star star: bigStars2)
        //    star.update();

        //for (Star star: garbageList1)
        //    star.update();

        //for (Star star: garbageList2)
        //    star.update();
    }

    @Override
    public void drawing(GraphicsFW graphicsFW)
    {
        graphicsFW.drawTexture(Resource.background, new Point(0, 0));
        graphicsFW.drawTexture(Resource.cloud, cloud.getPosition());

        for(Earth earth: earths)
        {
            graphicsFW.drawTexture(Resource.earth, earth.getPosition());
            graphicsFW.drawHitBox(earth.getHitBox());
        }

        //for (Star star: bigStars1)
        //    graphicsFW.drawTexture(Resource.bigStarSprite.get(0), star.position);

       // for (Star star: bigStars2)
        //    graphicsFW.drawTexture(Resource.bigStarSprite.get(1), star.position);

       // for (Star star: garbageList1)
       //    graphicsFW.drawTexture(Resource.bigStarSprite.get(2), star.position);

        //for (Star star: garbageList2)
        //    graphicsFW.drawTexture(Resource.bigStarSprite.get(3), star.position);
    }
}
