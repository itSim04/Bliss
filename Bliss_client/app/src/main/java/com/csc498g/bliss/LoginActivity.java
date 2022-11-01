package com.csc498g.bliss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        Link.getAllGemsAndStoreInTemp(LoginActivity.this);

        //sp.edit().clear().apply();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        Log.i("All Records", sp.getAll().toString());
        if (sp.contains(Constants.Users.USER_ID) && sp.contains(Constants.Users.USERNAME) && sp.contains(Constants.Users.PASSWORD) && sp.contains(Constants.Users.EMAIL)) {

            if (!sp.contains(Constants.Users.BIRTHDAY) || !sp.contains(Constants.Users.FOLLOWERS) || !sp.contains(Constants.Users.FOLLOWINGS) || !sp.contains(Constants.Users.PASSWORD) || !sp.contains(Constants.Users.GENDER) || !sp.contains(Constants.Users.BANNER) || !sp.contains(Constants.Users.PICTURE)) {

                Link.getAndStoreUser(getApplicationContext(), sp.getInt(Constants.Users.USER_ID, -1));

            }
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_login);

    }

    public void signIn(View v) {
        EditText username = (EditText) findViewById(R.id.nameEdt);
        EditText password = (EditText) findViewById(R.id.passwordEdt);

        String username_input = username.getText().toString();
        String password_input = password.getText().toString();
        if (username_input.equals("") || password_input.equals("")) {    //Making sure the user inputs both
            Toast.makeText(getApplicationContext(), "Missing entries.", Toast.LENGTH_LONG).show();
        } else {
            Link.authenticateUser(LoginActivity.this, username_input, password_input);
        }
    }

    public void togglePassword(View v) {

        if (((TextView) v).getText().toString().equalsIgnoreCase("Show")) {
            ((TextView) v).setText("Hide");
            ((EditText) findViewById(R.id.passwordEdt)).setTransformationMethod(null);

        } else {

            ((TextView) v).setText("Show");
            ((EditText) findViewById(R.id.passwordEdt)).setTransformationMethod(new PasswordTransformationMethod());

        }

    }

    public void signUp(View v) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
