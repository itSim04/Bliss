package com.csc498g.bliss;

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
    ListView originalComment;
    SwipeRefreshLayout pullToRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_comments);

        // Retrieves the ID of the Gem this comment will belong to
        gem_id = getIntent().getIntExtra(Constants.Gems.GEM_ID, -1);

        // Populates the Original comment in the design
        originalComment = findViewById(R.id.originalComment);
        GemsAdapter solo_adapter = new GemsAdapter(CommentActivity.this, new ArrayList<>(Collections.singletonList(Temp.TEMP_GEMS.containsKey(gem_id) ? Temp.TEMP_GEMS.get(gem_id) : Temp.TEMP_COMMENTS.get(gem_id))), true, originalComment);
        originalComment.setAdapter(solo_adapter);

        // Prepares the nested comments
        feed = ((ListView)findViewById(R.id.feed));
        GemsAdapter adapter = new GemsAdapter(CommentActivity.this, new ArrayList<>(0), false, feed);
        feed.setAdapter(adapter);

        // Prepares the nested comments
        Link.getAllCommentsAndUpdateFeed(CommentActivity.this, null, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1), gem_id);

        // Adds refresh capabilities to the display
        pullToRefresh = ((SwipeRefreshLayout)findViewById(R.id.pullToRefreshProfile));
        pullToRefresh.setOnRefreshListener(this::onRefresh);

    }

    @Override
    protected void onResume() {

        // Updates the comment feed after commenting
        super.onResume();
        if(Temp.TEMP_LATEST_COMMENT != -1) {
            ((GemsAdapter) feed.getAdapter()).remove(Temp.TEMP_COMMENTS.get(Temp.TEMP_LATEST_COMMENT));
            ((GemsAdapter) feed.getAdapter()).insert(Temp.TEMP_COMMENTS.get(Temp.TEMP_LATEST_COMMENT), 0);
            Temp.TEMP_LATEST_COMMENT = -1;
            feed.invalidateViews();
        }


    }

    public void onRefresh() {

        // Handles the refreshing process
        Link.getAllCommentsAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1), gem_id);

    }

    public void goBack(View view) {

        // Handles the Back action
        this.finish();
    }

    public void home(View v) {

        // Handles the home bar
        Helper.home(CommentActivity.this);

    }


    public void mining(View v) {

        // Handles the mining bar
        Helper.mine(CommentActivity.this);

    }
}
