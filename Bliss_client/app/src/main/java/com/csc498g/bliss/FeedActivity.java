package com.csc498g.bliss;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

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

        SwipeRefreshLayout pullToRefresh = ((SwipeRefreshLayout)findViewById(R.id.pullToRefresh));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(false);
            }
        });

        //Log.i("Debug", ((ListView)findViewById(R.id.feed))(0).toString());
        //for(int i = 0; i < .getChildCount(); i++)
          //  Log.i("Debug", ((ConstraintLayout)findViewById(R.id.TextGemItem)).getChildAt(i).toString());


    }
}
