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

public class CommentActivity extends AppCompatActivity {

    int gem_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_comments);

        gem_id = getIntent().getIntExtra(Constants.Gems.GEM_ID, -1);
        GemsAdapter adapter = new GemsAdapter(CommentActivity.this, new ArrayList<>());

        ListView feed = ((ListView)findViewById(R.id.feed));
        feed.setAdapter(adapter);

        SwipeRefreshLayout pullToRefresh = ((SwipeRefreshLayout)findViewById(R.id.pullToRefreshProfile));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Link.getAllCommentsAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1), gem_id);
            }
        });

        Log.i("Debug", Temp.TEMP_GEMS.toString());
        //for(int i = 0; i < .getChildCount(); i++)
          //  Log.i("Debug", ((ConstraintLayout)findViewById(R.id.TextGemItem)).getChildAt(i).toString());


    }

    public void enterProfile(View v) {

        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

    }

    public void home(View v) {

        Helper.home(CommentActivity.this);

    }


    public void mining(View v) {

        Helper.mine(CommentActivity.this);

    }
}
