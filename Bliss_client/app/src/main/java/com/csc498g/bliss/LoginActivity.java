package com.csc498g.bliss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // Activity for the login

    TextView error_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);

        // Initializes the Splash
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initializes the error box
        error_box = findViewById(R.id.errorBox);

        // Fetches the Shared Preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        // Checks if a user exists
        if (sp.contains(Constants.Users.USER_ID) && sp.contains(Constants.Users.USERNAME) && sp.contains(Constants.Users.PASSWORD) && sp.contains(Constants.Users.EMAIL)) {

            // Updates the user from the database
            Link.getAndStoreUser(LoginActivity.this, sp.getInt(Constants.Users.USER_ID, -1));

            // Opens the feed
            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
            startActivity(intent);

        } else {

            // Displays the login interface
            setContentView (R.layout.activity_login);

        }

    }

    public void signIn(View v) {

        // Signs the user in

        // Initializes the elements
        EditText username = findViewById(R.id.nameEdt);
        EditText password = findViewById(R.id.birthDateEdt);

        // Populates the elements
        String username_input = username.getText().toString();
        String password_input = password.getText().toString();

        // Checks if the inputs are valid
        if (username_input.equals("") || password_input.equals("")) {

            error_box.setText(R.string.missing);

        } else {

            // Authenticates the user
            Link.authenticateUser(LoginActivity.this, username_input, password_input, error_box);
        }

    }

    public void togglePassword(View v) {

        // Shows or hides the password

        if (((TextView) v).getText().toString().equalsIgnoreCase("Show")) {
            ((TextView) v).setText(R.string.hide);
            ((EditText) findViewById(R.id.birthDateEdt)).setTransformationMethod(null);

        } else {

            ((TextView) v).setText(R.string.show);
            ((EditText) findViewById(R.id.birthDateEdt)).setTransformationMethod(new PasswordTransformationMethod());

        }

    }

    public void signUp(View v) {

        // Switches to the Sign Up page
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
