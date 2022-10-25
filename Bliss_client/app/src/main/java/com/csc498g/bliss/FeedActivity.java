package com.csc498g.bliss;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_feed);

        //Testing
        GemsAdapter adapter = new GemsAdapter(this, new ArrayList<>(Arrays.asList(new TextGem(1, "", "", "itSim04", "Breaking news", 62, 34), new PollGem(2, "", "", "itSim04", "Breaking news", 32, 43, "Lorem", 14, "Ipsum", 3, "Dolor", 66, "Sit", 17), new TextGem(1, "", "", "itSim04", "Breaking news", 62, 34), new PollGem(2, "", "", "itSim04", "Breaking news", 32, 43, "Lorem", 14, "Ipsum", 3, "Dolor", 66, "Sit", 17))));
        ((ListView)findViewById(R.id.feed)).setAdapter(adapter);

    }
}
