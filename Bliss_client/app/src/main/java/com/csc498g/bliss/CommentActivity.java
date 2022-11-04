package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CommentActivity extends AppCompatActivity {

    int gem_id;
    ListView feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_comments);

        gem_id = getIntent().getIntExtra(Constants.Gems.GEM_ID, -1);

        ListView solo = ((ListView)findViewById(R.id.originalComment));
        GemsAdapter solo_adapter = new GemsAdapter(CommentActivity.this, new ArrayList<>(Collections.singletonList(Temp.TEMP_GEMS.get(gem_id))), true, solo);

        solo.setAdapter(solo_adapter);


        feed = ((ListView)findViewById(R.id.feed));
        GemsAdapter adapter = new GemsAdapter(CommentActivity.this, new ArrayList<>(0), false, feed);
        feed.setAdapter(adapter);

        Link.getAllCommentsAndUpdateFeed(CommentActivity.this, null, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1), gem_id);


        SwipeRefreshLayout pullToRefresh = ((SwipeRefreshLayout)findViewById(R.id.pullToRefreshProfile));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Link.getAllCommentsAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1), gem_id);
            }
        });

        //for(int i = 0; i < .getChildCount(); i++)
          //  Log.i("Debug", ((ConstraintLayout)findViewById(R.id.TextGemItem)).getChildAt(i).toString());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Temp.TEMP_LATEST_COMMENT != -1) {
            ((GemsAdapter) feed.getAdapter()).remove(Temp.TEMP_COMMENTS.get(Temp.TEMP_LATEST_COMMENT));
            ((GemsAdapter) feed.getAdapter()).insert(Temp.TEMP_COMMENTS.get(Temp.TEMP_LATEST_COMMENT), 0);
            Temp.TEMP_LATEST_COMMENT = -1;
            feed.invalidateViews();
        }


    }

    public void backFromComments(View view){
        this.finish();
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
