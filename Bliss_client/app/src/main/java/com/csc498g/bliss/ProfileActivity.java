package com.csc498g.bliss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.TextView;

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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        int user_id = sp.getInt(Constants.Users.USER_ID, -1);
        int followings = sp.getInt(Constants.Users.FOLLOWINGS, 0);
        int followers = sp.getInt(Constants.Users.FOLLOWERS, 0);
        String username = sp.getString(Constants.Users.USERNAME, "lorem ipsum");
        String password = sp.getString(Constants.Users.PASSWORD, "lorem ipsum");
        String email = sp.getString(Constants.Users.EMAIL, "lorem_ipsum@co.com");
        String banner = sp.getString(Constants.Users.BANNER, "lorem ipsum");
        String profile = sp.getString(Constants.Users.PICTURE, "lorem ipsum");
        byte gender = (byte) sp.getInt(Constants.Users.GENDER, -1);
        String birthday = sp.getString(Constants.Users.BIRTHDAY, "1970-01-01");

        owner = new User(user_id, username, password, email, birthday, gender, profile, banner, followings, followers);

        ((TextView) findViewById(R.id.userNameText)).setText(owner.getUsername());

        SwipeRefreshLayout swipeLayout = ((SwipeRefreshLayout) findViewById(R.id.pullToRefreshProfile));
        Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Link.getAllGemsByUserStoreInTempAndUpdateList(ProfileActivity.this, owner.getUser_id(), findViewById(R.id.feed), swipeLayout);

            }
        });

    }
}