package com.csc498g.bliss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_splash_screen);

        Link.getAllGemsAndStoreInTemp(SplashScreenActivity.this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().clear().apply();
        Log.i("All Records", sp.getAll().toString());
        if(sp.contains(Constants.Users.USER_ID) && sp.contains(Constants.Users.USERNAME) && sp.contains(Constants.Users.PASSWORD) && sp.contains(Constants.Users.EMAIL)) {

            if (!sp.contains(Constants.Users.BIRTHDAY) || !sp.contains(Constants.Users.FOLLOWERS) || !sp.contains(Constants.Users.FOLLOWINGS) || !sp.contains(Constants.Users.PASSWORD) || !sp.contains(Constants.Users.GENDER) || !sp.contains(Constants.Users.BANNER) || !sp.contains(Constants.Users.PICTURE)) {

                Link.getAndStoreUser(getApplicationContext(), sp.getInt(Constants.Users.USER_ID, -1));

            }
            new Handler().postDelayed(() -> {

                Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(intent);
                finish();
            }, 2000);


        } else {

            new Handler().postDelayed(() -> {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }
    }
}