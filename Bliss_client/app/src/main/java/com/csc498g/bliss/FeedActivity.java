package com.csc498g.bliss;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

public class FeedActivity extends AppCompatActivity {

    // Activity for displaying the Gems

    ListView feed; // The feed holding the gems
    SwipeRefreshLayout pullToRefresh; // The layout holding the feed

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_feed);

        // Initializes the feed
        feed = ((ListView) findViewById(R.id.feed));

        // Prepares the adapted for the feed
        GemsAdapter adapter = new GemsAdapter(FeedActivity.this, new ArrayList<>(), false, feed);
        feed.setAdapter(adapter);

        // Initializes the layout holding the feed
        pullToRefresh = findViewById(R.id.pullToRefreshProfile);

        // Retrieves gems from the server an updates the feed once fetches
        Link.getAllGemsStoreInTempAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1));

        // Adds a refresh functionality to the layout
        pullToRefresh.setOnRefreshListener(this::onRefresh);

    }

    @Override
    protected void onResume() {

        // Updates the feed after mining
        super.onResume();
        if (Temp.TEMP_LATEST_GEM != -1) {

            ((GemsAdapter) feed.getAdapter()).remove(Temp.TEMP_GEMS.get(Temp.TEMP_LATEST_GEM));
            ((GemsAdapter) feed.getAdapter()).insert(Temp.TEMP_GEMS.get(Temp.TEMP_LATEST_GEM), 0);
            Temp.TEMP_LATEST_GEM = -1;
            feed.invalidateViews();

        }
    }

    public void onRefresh() {

        // Handles the refresh
        Link.getAllGemsStoreInTempAndUpdateFeed(getApplicationContext(), pullToRefresh, feed, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.Users.USER_ID, -1));

    }

    public void profileOptions(View v) {

        // Handles profile operations

        PopupMenu popup = new PopupMenu(getApplicationContext(), v);
        popup.setOnMenuItemClickListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.viewProfile) {

                // Opens the profile
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class).putExtra(Constants.Users.USER_ID, PreferenceManager.getDefaultSharedPreferences(this).getInt(Constants.Users.USER_ID, -1)));
                return true;

            } else if (item.getItemId() == R.id.viewLogout) {

                // Logs out
                PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }

            return false;
        });

        // Display a list to choose the operation
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_details, popup.getMenu());
        popup.show();


    }


    public void mining(View v) {

        // Handles the mining bar
        Helper.mine(FeedActivity.this);

    }
}
