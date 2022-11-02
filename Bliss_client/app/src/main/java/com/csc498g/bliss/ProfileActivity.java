package com.csc498g.bliss;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    User owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_profile);

        owner = Helper.extractUser(ProfileActivity.this);

        ((TextView) findViewById(R.id.userNameText)).setText(owner.getUsername());
        ((TextView) findViewById(R.id.followingNum)).setText(String.valueOf(owner.getFollowings()));
        ((TextView) findViewById(R.id.followersNum)).setText(String.valueOf(owner.getFollowers()));
        ((TextView) findViewById(R.id.bornDate)).setText(String.format("Born %d %s %d", owner.getBirthday().getDayOfMonth(), owner.getBirthday().getMonth().toString().toLowerCase(), owner.getBirthday().getYear()));
        ((TextView) findViewById(R.id.joinedDate)).setText(String.format("Joined %s %d", owner.getBirthday().getMonth().toString().toLowerCase(), owner.getBirthday().getYear()));


        SwipeRefreshLayout swipeLayout = findViewById(R.id.pullToRefreshProfile);
        Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout);
        swipeLayout.setOnRefreshListener(() -> Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout));

    }

    public void logout(View v) {

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

    }


}