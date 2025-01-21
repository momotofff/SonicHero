package com.momotoff.sonichero;

import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.sonichero.classes.Loading;
import com.momotoff.sonichero.scene.MainMenu;
import com.momotoff.sonichero.utilities.Save;

public class Main extends CoreFW
{
    private final Save save = new Save();

    @Override
    public SceneFW getStartScene()
    {
        Loading loading = new Loading(this, this.getGraphicsFW());
        save.loadDistance(getSharedPreferences());
        return MainMenu.createInstance(this, save);
    }
}