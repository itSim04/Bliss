package com.csc498g.bliss;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    User owner;
    Button button;
    private TextView followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_profile);

        button = findViewById(R.id.editProfileBtn);
        followers = ((TextView) findViewById(R.id.followersNum));

        int profile_id = getIntent().getIntExtra(Constants.Users.USER_ID, -1);
        int owner_id = PreferenceManager.getDefaultSharedPreferences(this).getInt(Constants.Users.USER_ID, -1);
        if(profile_id == -1 || profile_id == owner_id) {

            button.setText("Edit Profile");
            button.setOnClickListener(this::edit);
            owner = Helper.extractUser(ProfileActivity.this);

        } else {

            button.setText("Follow");
            button.setOnClickListener(this::follow);
            owner = Temp.TEMP_USERS.get(profile_id);

        }



        ((TextView) findViewById(R.id.userNameText)).setText(owner.getUsername());
        ((TextView) findViewById(R.id.followingNum)).setText(String.valueOf(owner.getFollowings()));
        followers.setText(String.valueOf(owner.getFollowers()));
        ((TextView) findViewById(R.id.birthDate)).setText(String.format("Born %d %s %d", owner.getBirthday().getDayOfMonth(), owner.getBirthday().getMonth().toString().toLowerCase(), owner.getBirthday().getYear()));
        ((TextView) findViewById(R.id.followersNum)).setText(String.valueOf(owner.getFollowers()));
        ((TextView) findViewById(R.id.joinedDate)).setText(String.format("Joined %s %d", owner.getJoinDate().getMonth().toString().toLowerCase(), owner.getBirthday().getYear()));
        ((TextView) findViewById(R.id.bioText)).setText(owner.getBio());

        SwipeRefreshLayout swipeLayout = findViewById(R.id.pullToRefreshProfile);
        Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout);
        swipeLayout.setOnRefreshListener(() -> Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout));

    }

    private void follow(View view) {

        Link.followUser(ProfileActivity.this, owner.getUser_id(), followers);

    }

    public void edit(View v) {

        startActivity(new Intent(this, EditProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
    }

    public void logout(View v) {

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

    }

    public void home(View v) {

        Helper.home(ProfileActivity.this);

    }

    public void mining(View v) {

        Helper.mine(ProfileActivity.this);

    }


}