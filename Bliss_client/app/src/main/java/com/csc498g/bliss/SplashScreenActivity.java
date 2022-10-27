package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        String url = "http://192.168.0.103/Bliss/Bliss_server/get_all_gems.php";
        Link.get_all_gems();

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}