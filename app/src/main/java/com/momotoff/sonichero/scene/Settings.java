package com.momotoff.sonichero.scene;

import static android.content.ContentValues.TAG;

import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momotoff.my_framework.CoreFW;
import com.momotoff.my_framework.SceneFW;
import com.momotoff.my_framework.StaticTextFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Resource;

public class Settings extends SceneFW
{
    private final StaticTextFW settings = new StaticTextFW(coreFW.getString(R.string.txtMainMenuMenuSettings), new Point(50, 150), 100);
    private final StaticTextFW singOut = new StaticTextFW(coreFW.getString(R.string.txtSignOut), new Point(50, 250), 60);
    private final StaticTextFW deleteAccount = new StaticTextFW(coreFW.getString(R.string.txtDeleteAccount), new Point(50, 350), 60);
    private final StaticTextFW back = new StaticTextFW(coreFW.getString(R.string.txtBack), new Point(50, 580), 70);

    public Settings(CoreFW coreFW)
    {
        super(coreFW);
    }

    @Override
    public void update()
    {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null)
        {
            if (coreFW.getTouchListenerFW().getTouchUp(singOut.getTouchArea(graphicsFW)))
            {
                coreFW.getSoundFW().start(R.raw.tap);

                FirebaseAuth.getInstance().signOut();
                coreFW.runOnUiThread(() -> Toast.makeText(coreFW.getApplication(), coreFW.getString(R.string.txtSignOutSuccess), Toast.LENGTH_SHORT).show());
            }

            if (coreFW.getTouchListenerFW().getTouchUp(deleteAccount.getTouchArea(graphicsFW)))
            {
                coreFW.getSoundFW().start(R.raw.tap);

                currentUser.delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Log.d(TAG, "User account deleted");
                        coreFW.runOnUiThread(() -> Toast.makeText(coreFW.getApplication(), coreFW.getString(R.string.txtAccountDeleteSuccess), Toast.LENGTH_SHORT).show());
                    }
                    else
                    {
                        Log.e(TAG, "User account delete failed");
                        coreFW.runOnUiThread(() -> Toast.makeText(coreFW.getApplication(), coreFW.getString(R.string.txtAccountDeleteFailed), Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }

        if (coreFW.getTouchListenerFW().getTouchUp(back.getTouchArea(graphicsFW)))
        {
            coreFW.getSoundFW().start(R.raw.tap);
            coreFW.setScene(MainMenu.getInstance());
        }
    }

    @Override
    public void drawing()
    {
        graphicsFW.drawTexture(Resource.menuImage, new Point(0, 0));
        graphicsFW.drawText(settings);
        graphicsFW.drawText(singOut);
        graphicsFW.drawText(deleteAccount);
        graphicsFW.drawText(back);
    }
}
