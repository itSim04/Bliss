package com.csc498g.bliss;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Locale;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    // Activity for the Profile

    private User owner; // The owner of the profile
    private Button follow; // The follow/edit button
    private TextView followers; // The box of the followers count

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Creates the activity
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Forces a clean Full Screen layout
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile);

        // Retrieves the follow boxes
        follow = findViewById(R.id.editProfileBtn);
        followers = ((TextView) findViewById(R.id.followersNum));

        // Retrieves the ID of the miner
        int profile_id = getIntent().getIntExtra(Constants.Users.USER_ID, -1);

        // Checks if the miner is the owner
        if(profile_id == -1 || profile_id == Helper.getOwnerId(ProfileActivity.this)) {

            // Handles the owner's profile
            follow.setText(R.string.edit_profile);
            follow.setOnClickListener(this::edit);
            owner = Helper.extractUser(ProfileActivity.this);

        } else {

            // Handles random profiles
            owner = Objects.requireNonNull(Temp.TEMP_USERS.get(profile_id));
            Link.checkFollowAndToggleButton(ProfileActivity.this, owner, follow);
            follow.setOnClickListener(this::follow);

        }

        // Populates the UI with the information of the miner
        ((TextView) findViewById(R.id.userNameText)).setText(owner.getUsername());
        ((TextView) findViewById(R.id.followingNum)).setText(String.valueOf(owner.getFollowings()));
        followers.setText(String.valueOf(owner.getFollowers()));
        ((TextView) findViewById(R.id.birthDate)).setText(String.format(Locale.US, "Born %d %s %d", owner.getBirthday().getDayOfMonth(), owner.getBirthday().getMonth().toString().toLowerCase(), owner.getBirthday().getYear()));
        ((TextView) findViewById(R.id.followersNum)).setText(String.valueOf(owner.getFollowers()));
        ((TextView) findViewById(R.id.joinedDate)).setText(String.format(Locale.US, "Joined %s %d", owner.getJoinDate().getMonth().toString().toLowerCase(), owner.getJoinDate().getYear()));
        ((TextView) findViewById(R.id.bioText)).setText(owner.getBio());

        // Retrieves the layout that will hold the gems
        SwipeRefreshLayout swipeLayout = findViewById(R.id.pullToRefreshProfile);

        // Forks the layout with the user's gems
        Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUserId(), findViewById(R.id.feed), swipeLayout);

        // Adds refreshing functionality
        swipeLayout.setOnRefreshListener(() -> Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUserId(), findViewById(R.id.feed), swipeLayout));

    }

    private void follow(View view) {

        // Follows the miner
        Link.checkFollowAndToggle(ProfileActivity.this, owner, followers, follow);

    }

    public void edit(View v) {

        // Handles the editing process
        startActivity(new Intent(this, EditProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
    }

    public void home(View v) {

        // Handles the home bar
        Helper.home(ProfileActivity.this);

    }

    public void mining(View v) {

        // Handles the mining bar
        Helper.mine(ProfileActivity.this);

    }


}