package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_splash_screen);

        Link.get_all_gems();
        Link.POST get_user = new Link.POST(new PROCESS() {
            @Override
            public void ACCESS(Response response) {
                Log.i("POST", response.toString());
            }
        });
        get_user.execute(Constants.URL.buildUrl(Constants.APIs.GET_USER), "{\"user_id\": 9}");

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

//        new Handler().postDelayed(() -> {
//
//
//            finish();
//        }, 5000);
    }
}