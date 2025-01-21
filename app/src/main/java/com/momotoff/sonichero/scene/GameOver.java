package com.momotoff.sonichero.scene;

import android.graphics.Color;
import android.graphics.Point;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.classes.Manager;
import com.momotoff.sonichero.utilities.Save;

public class GameOver extends SceneFW
{
    private final StaticTextFW GameOver = new StaticTextFW(coreFW.getString(R.string.txtGameOver), new Point(300,200), 100);
    private final StaticTextFW Restart = new StaticTextFW(coreFW.getString(R.string.txtRestart), new Point(300,350), 50);
    private final StaticTextFW ExitMenu = new StaticTextFW(coreFW.getString(R.string.txtExitMenu), new Point(300,450), 50);

    private final Manager manager;
    private final Save save;

    public GameOver(CoreFW coreFW, Manager manager, Save save)
    {
        super(coreFW);
        this.manager = manager;
        this.save = save;

        coreFW.getBackgroundAudioFW().setTrack(com.momotoff.my_framework.R.raw.game_over);
        coreFW.getBackgroundAudioFW().start();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            databaseReference.child("Users").child(currentUser.getUid()).child("Email").setValue(currentUser.getEmail());
            databaseReference.child("Users").child(currentUser.getUid()).child("Result").setValue(save.getDistance()[0]);
        }
    }

    @Override
    public void update()
    {
        if (coreFW.getTouchListenerFW().getTouchUp(Restart.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.getBackgroundAudioFW().stop();
            coreFW.setScene(new GameScene(coreFW, save));
        }

        if (coreFW.getTouchListenerFW().getTouchUp(ExitMenu.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.getBackgroundAudioFW().setTrack(com.momotoff.my_framework.R.raw.menu);
            coreFW.getBackgroundAudioFW().start();
            coreFW.setScene(MainMenu.getInstance());
        }
    }

    @Override
    public void drawing()
    {
        graphicsFW.clearScene(Color.BLACK);
        graphicsFW.drawText(GameOver);
        graphicsFW.drawText(ExitMenu);
        graphicsFW.drawText(Restart);
        StaticTextFW result = new StaticTextFW(manager.player.getTxtPassedDistance(), new Point(300,550), 50);
        graphicsFW.drawText(result);
    }
}
