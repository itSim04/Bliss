package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

public class FeedActivity extends AppCompatActivity {

    ListView feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_feed);


        feed = ((ListView)findViewById(R.id.feed));
        GemsAdapter adapter = new GemsAdapter(FeedActivity.this, new ArrayList<>(Temp.TEMP_GEMS.values()), false, feed);
        feed.setAdapter(adapter);

        SwipeRefreshLayout pullToRefresh = ((SwipeRefreshLayout)findViewById(R.id.pullToRefreshProfile));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Link.getAllGemsStoreInTempAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1));
            }
        });

        Log.i("Debug", Temp.TEMP_GEMS.toString());
        //for(int i = 0; i < .getChildCount(); i++)
          //  Log.i("Debug", ((ConstraintLayout)findViewById(R.id.TextGemItem)).getChildAt(i).toString());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Temp.TEMP_LATEST_GEM != -1) {
            ((GemsAdapter) feed.getAdapter()).remove(Temp.TEMP_GEMS.get(Temp.TEMP_LATEST_GEM));
            ((GemsAdapter) feed.getAdapter()).insert(Temp.TEMP_GEMS.get(Temp.TEMP_LATEST_GEM), 0);
            Temp.TEMP_LATEST_GEM = -1;
            feed.invalidateViews();
        }


    }

    public void enterProfile(View v) {

        startActivity(new Intent(getApplicationContext(), ProfileActivity.class).putExtra(Constants.Users.USER_ID, PreferenceManager.getDefaultSharedPreferences(this).getInt(Constants.Users.USER_ID, -1)));

    }


    public void mining(View v) {

        Helper.mine(FeedActivity.this);

    }
}
