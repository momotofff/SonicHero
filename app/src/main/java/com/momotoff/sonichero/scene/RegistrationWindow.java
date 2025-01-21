package com.momotoff.sonichero.scene;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momotoff.my_framework.CoreFW;
import com.momotoff.sonichero.R;
import com.momotoff.sonichero.utilities.Save;


public class RegistrationWindow extends LinearLayout
{
    private CoreFW coreFW;
    private EditText fieldName;
    private EditText fieldPassword;
    private Save save;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    public RegistrationWindow(CoreFW coreFW, Save save)
    {
        super(coreFW.getApplication());

        this.coreFW = coreFW;
        this.save = save;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        initialize();
    }

    public RegistrationWindow(Context context)
    {
        super(context);
    }

    private void initialize()
    {
        if (isLoggedIn())
            return;

        LayoutParams windowParam = new LayoutParams((coreFW.getDisplaySize().x / 2), WindowManager.LayoutParams.WRAP_CONTENT);
        windowParam.setMargins(10,10,10,10);

        LinearLayout window = new LinearLayout(coreFW.getApplication());
        window.setBackgroundColor(Color.WHITE);
        window.setOrientation(LinearLayout.VERTICAL);

        TextView name = new TextView(coreFW.getApplication());
        name.setText(coreFW.getString(R.string.txtUserName));
        name.setLayoutParams(windowParam);
        window.addView(name);

        fieldName = new EditText(coreFW.getApplication());
        fieldName.setLayoutParams(windowParam);
        window.addView(fieldName);

        TextView password = new TextView(coreFW.getApplication());
        password.setText(coreFW.getString(R.string.txtPassword));
        password.setLayoutParams(windowParam);
        window.addView(password);

        fieldPassword = new EditText(coreFW.getApplication());
        fieldPassword.setLayoutParams(windowParam);
        window.addView(fieldPassword);

        Button buttonSingIn = new Button(coreFW.getApplication());
        buttonSingIn.setText(coreFW.getString(R.string.txtSingIn));
        buttonSingIn.setLayoutParams(windowParam);
        buttonSingIn.setOnClickListener((View view) -> singInAccount(fieldName.getText().toString(), fieldPassword.getText().toString()));
        window.addView(buttonSingIn);

        Button buttonSingUp = new Button(coreFW.getApplication());
        buttonSingUp.setText(coreFW.getString(R.string.txtSingUp));
        buttonSingUp.setLayoutParams(windowParam);
        buttonSingUp.setOnClickListener((View view) -> singUpAccount(fieldName.getText().toString(), fieldPassword.getText().toString()));
        window.addView(buttonSingUp);

        this.setLayoutParams(new LayoutParams(coreFW.getDisplaySize().x, coreFW.getDisplaySize().y));
        this.setGravity(Gravity.CENTER);
        this.addView(window);
    }

    private boolean isLoggedIn()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
            return false;

        Application app = coreFW.getApplication();
        Toast.makeText(app, coreFW.getString(R.string.txtYouAreLoggedToast) + " " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();

        databaseReference.child("Users").child(currentUser.getUid()).child("Email").setValue(currentUser.getEmail());
        databaseReference.child("Users").child(currentUser.getUid()).child("Result").setValue(save.getDistance()[0]);
        return true;
    }

    private void singUpAccount(String email, String password)
    {
        Application app = coreFW.getApplication();

        if (fieldName.getText().toString().isEmpty() || fieldPassword.getText().toString().isEmpty())
        {
            Toast.makeText(app, coreFW.getString(R.string.txtFillToast), Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful())
            {
                if (task.getException() instanceof com.google.firebase.auth.FirebaseAuthUserCollisionException)
                    Toast.makeText(app, coreFW.getString(R.string.txtUserCollision), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(app, coreFW.getString(R.string.txtInvalidInputToast), Toast.LENGTH_SHORT).show();

                return;
            }

            Toast.makeText(app, coreFW.getString(R.string.txtYouAreLoggedToast) + " " + email, Toast.LENGTH_SHORT).show();
            databaseReference.child("Users").child(task.getResult().getUser().getUid()).child("Email").setValue(email);
            databaseReference.child("Users").child(task.getResult().getUser().getUid()).child("Result").setValue(save.getDistance()[0]);
            this.setVisibility(View.GONE);
        });
    }

    private void singInAccount(String email, String password)
    {
        Application app = coreFW.getApplication();

        if (fieldName.getText().toString().isEmpty() || fieldPassword.getText().toString().isEmpty())
        {
            Toast.makeText(app, coreFW.getString(R.string.txtFillToast), Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful())
            {
                Toast.makeText(app, coreFW.getString(R.string.txtInvalidInputToast), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(app, coreFW.getString(R.string.txtYouAreLoggedToast) + " " + email, Toast.LENGTH_SHORT).show();
            databaseReference.child("Users").child(task.getResult().getUser().getUid()).child("Email").setValue(email);
            databaseReference.child("Users").child(task.getResult().getUser().getUid()).child("Result").setValue(save.getDistance()[0]);
            this.setVisibility(View.GONE);
        });
    }
}
