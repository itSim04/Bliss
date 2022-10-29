package com.csc498g.bliss;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class FeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_feed);



        GemsAdapter adapter = new GemsAdapter(getApplicationContext(), new ArrayList<>(Temp.TEMP_GEMS.values()));
        ((ListView)findViewById(R.id.feed)).setAdapter(adapter);

        //Log.i("Debug", ((ListView)findViewById(R.id.feed))(0).toString());
        //for(int i = 0; i < .getChildCount(); i++)
          //  Log.i("Debug", ((ConstraintLayout)findViewById(R.id.TextGemItem)).getChildAt(i).toString());


    }
}
