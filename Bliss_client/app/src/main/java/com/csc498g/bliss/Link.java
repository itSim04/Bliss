package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Link {


    public static void checkAvailability(Context context, RegisterActivity activity, User user, TextView error_box) {

        Relay relay = new Relay(Constants.APIs.IS_USERNAME_EMAIL_AVAILABLE, response -> checkAvailabilityRESPONSE(context, response, user, error_box), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, user.getUsername());
        relay.addParam(Constants.Users.EMAIL, user.getEmail());

        relay.sendRequest();

    }

    private static void checkAvailabilityRESPONSE(Context context, Response response, User user, TextView error_box) {

        int availability = response.isAvailable();

        if (availability == Constants.Availability.NONE_AVAILABLE || availability == Constants.Availability.EMAIL_AVAILABLE) {

            error_box.setText("Username Taken");

        } else if (availability == Constants.Availability.USERNAME_AVAILABLE) {

            error_box.setText("Email Taken");

        } else {

            addUserToDatabase(context, user);

        }

    }

    public static void addUserToDatabase(Context context, User user) {

        Relay relay = new Relay(Constants.APIs.ADD_USER, response -> addUserToDatabaseRESPONSE(context, response, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, user.getUsername());
        relay.addParam(Constants.Users.PASSWORD, user.getPassword());
        relay.addParam(Constants.Users.EMAIL, user.getEmail());
        relay.addParam(Constants.Users.BANNER, user.getBanner());
        relay.addParam(Constants.Users.PICTURE, user.getProfile());
        relay.addParam(Constants.Users.GENDER, user.getGender());
        relay.addParam(Constants.Users.BIRTHDAY, user.getBirthday());
        relay.addParam(Constants.Users.BIO, user.getBio());

        relay.sendRequest();

    }

    private static void addUserToDatabaseRESPONSE(Context context, Response response, User user) {

        if (response.isSuccess()) {

            Intent intent = new Intent(context, FeedActivity.class);
            user.setUser_id(response.getLastId());
            Helper.storeUser(context, user);
            context.startActivity(intent);

        } else {

            Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();

        }

    }

    public static void getUserAndStoreInTemp(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_USER, response -> getUserAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getUserAndStoreInTempRESPONSE(Context context, Response response) {

        User result = (User) response.getQueryResult().get(Constants.Classes.USER).get(0);
        assert result != null;
        Temp.TEMP_USERS.put(result.getUser_id(), result);

    }

    public static void getAndStoreUser(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_USER, response -> getAndStoreUserRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching User from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getAndStoreUserRESPONSE(Context context, Response response) {

        User result = (User) response.getQueryResult().get(Constants.Classes.USER).get(0);
        assert result != null;
        Helper.storeUser(context, result);
        Temp.TEMP_USERS.put(result.getUser_id(), result);

    }

    public static void getAllGemsStoreInTempAndUpdateFeed(Context context, SwipeRefreshLayout layout, ListView list, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsStoreInTempAndUpdateFeedRESPONSE(context, response, layout, list), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);


        relay.sendRequest();

    }

    private static void getAllGemsStoreInTempAndUpdateFeedRESPONSE(Context context, Response response, SwipeRefreshLayout layout, ListView list) {

        ArrayList<User> user_result = (ArrayList<User>) response.getQueryResult().get(Constants.Classes.USER);
        user_result.forEach(user -> {
            Temp.TEMP_USERS.put((user).getUser_id(), user);
        });

        ArrayList<Gem> gems_result = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);

        assert gems_result != null;
        Collections.reverse(gems_result);

        ((GemsAdapter) list.getAdapter()).flush();
        gems_result.forEach(gem -> {
            Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
            ((GemsAdapter) list.getAdapter()).add(gem);
            list.setAdapter(list.getAdapter());
        });


        layout.setRefreshing(false);


    }

    public static void getAllCommentsAndUpdateFeed(Context context, SwipeRefreshLayout layout, ListView list, int user_id, int root_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllCommentsAndUpdateFeedRESPONSE(context, response, layout, list), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);
        relay.addParam(Constants.Gems.ROOT, root_id);


        relay.sendRequest();

    }

    private static void getAllCommentsAndUpdateFeedRESPONSE(Context context, Response response, SwipeRefreshLayout layout, ListView list) {

        ArrayList<User> user_result = (ArrayList<User>) response.getQueryResult().get(Constants.Classes.USER);
        user_result.forEach(user -> {
            Temp.TEMP_USERS.put((user).getUser_id(), user);
        });

        ArrayList<Gem> comments_result = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);

        assert comments_result != null;
        Collections.reverse(comments_result);

        ((GemsAdapter) list.getAdapter()).flush();
        comments_result.forEach(gem -> {
            Temp.TEMP_COMMENTS.put(gem.getGem_id(), gem);
            ((GemsAdapter) list.getAdapter()).add(gem);
            list.setAdapter(list.getAdapter());
        });


        if (layout != null)
            layout.setRefreshing(false);


    }

    public static void getAllGemsAndStoreInTemp(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching Gems from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getAllGemsAndStoreInTempRESPONSE(Context context, Response response) {

        ArrayList<User> user_result = (ArrayList<User>) response.getQueryResult().get(Constants.Classes.USER);
        user_result.forEach(user -> Temp.TEMP_USERS.put(user.getUser_id(), user));

        ArrayList<Gem> gems_result = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);
        gems_result.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));

    }

    public static void authenticateUser(Context context, String username, String password) {

        Relay relay = new Relay(Constants.APIs.AUTHENTICATE_LOGIN, response -> authenticateUserRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, username);
        relay.addParam(Constants.Users.PASSWORD, password);

        relay.sendRequest();

    }

    private static void authenticateUserRESPONSE(Context context, Response response) {

        if (response.isAuthenticated()) {

            User user = (User) response.getQueryResult().get(Constants.Classes.USER).get(0);
            assert user != null;
            Helper.storeUser(context, user);
            Intent i = new Intent(context, FeedActivity.class);
            context.startActivity(i);

        } else {

            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();

        }

    }

    public static void getAllGemsByUserAndStoreInTemp(Context context, int owner_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS_BY_USER, response -> getAllGemsByUserAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error fetching data from the server"));

        relay.setConnectionMode(Relay.MODE.POST);
        relay.addParam(Constants.Gems.OWNER_ID, owner_id);
        relay.addParam(Constants.Users.USER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.sendRequest();


    }

    private static void getAllGemsByUserAndStoreInTempRESPONSE(Context context, Response response) {

        ArrayList<Gem> gems = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);
        gems.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));


    }

    public static void getAllGemsByUserStoreInTempAndUpdateList(Context context, int owner_id, ListView list, SwipeRefreshLayout layout) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS_BY_USER, response -> getAllGemsByUserStoreInTempAndUpdateListRESPONSE(context, response, list, layout), (api, e) -> error(api, context, e, "Error fetching data from the server"));

        relay.setConnectionMode(Relay.MODE.POST);
        relay.addParam(Constants.Gems.OWNER_ID, owner_id);
        relay.addParam(Constants.Users.USER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.sendRequest();


    }

    private static void getAllGemsByUserStoreInTempAndUpdateListRESPONSE(Context context, Response response, ListView list, SwipeRefreshLayout layout) {

        ArrayList<Gem> gems = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);
        Collections.reverse(gems);
        gems.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));

        GemsAdapter adapter = new GemsAdapter(context, gems, false, list);
        list.setAdapter(adapter);
        layout.setRefreshing(false);


    }


    public static void diamondsGem(Context context, int gem_id, int user_id, TextView diamond_text) {
        Relay relay = new Relay(Constants.APIs.DIAMOND_GEM, response -> diamondsGemRESPONSE(context, response, gem_id, diamond_text), (api, e) -> error(api, context, e, "Error diamonding gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Diamonds.USER_ID, user_id);
        relay.addParam(Constants.Diamonds.GEM_ID, gem_id);
        relay.addParam(Constants.Diamonds.DIAMOND_DATE, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        relay.sendRequest();
    }

    private static void diamondsGemRESPONSE(Context context, Response response, int gem_id, TextView diamond_text) {

        Gem current;
        if(Temp.TEMP_GEMS.containsKey(gem_id)) {
            Temp.TEMP_GEMS.get(gem_id).setLiked(true);
            Temp.TEMP_GEMS.get(gem_id).incrementDiamond();;
        } else {
            Temp.TEMP_COMMENTS.get(gem_id).setLiked(true);
            Temp.TEMP_COMMENTS.get(gem_id).incrementDiamond();
        }
        diamond_text.setText(String.valueOf(Integer.parseInt(diamond_text.getText().toString()) + 1));

    }

    public static void answerPoll(Context context, int gem_id, int user_id, int option, ListView list, PollGem gem) {
        Relay relay = new Relay(Constants.APIs.ANSWER_POST, response -> answerPollRESPONSE(context, response, gem_id, option, gem, list), (api, e) -> error(api, context, e, "Error diamonding gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Answers.USER_ID, user_id);
        relay.addParam(Constants.Answers.GEM_ID, gem_id);
        relay.addParam(Constants.Answers.ANSWER_DATE, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        relay.addParam(Constants.Answers.OPTION_CHOSEN, option);

        relay.sendRequest();
    }

    private static void answerPollRESPONSE(Context context, Response response, int gem_id, int option, PollGem gem, ListView list) {

//        PollGem current;
//        if(Temp.TEMP_GEMS.containsKey(gem_id)) {
//
//            current = (PollGem) Temp.TEMP_GEMS.get(gem_id);
//
//        } else {
//
//            current = (PollGem) Temp.TEMP_COMMENTS.get(gem_id);
//
//        }
        gem.increment(option);
        gem.setIs_voted(option);
        //current.increment(option);
        list.invalidateViews();


    }

    public static void followUser(Context context, User user, TextView followers, Button follow) {

        Relay relay = new Relay(Constants.APIs.FOLLOW_USER, response -> followUserRESPONSE(context, response, user, followers, follow), (api, e) -> error(api, context, e, "Error following miner"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Follows.USER_ID1, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Follows.USER_ID2, user.getUser_id());
        relay.addParam(Constants.Follows.FOLLOW_DATE, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        relay.sendRequest();

    }

    private static void followUserRESPONSE(Context context, Response response, User user, TextView followers, Button follow) {

        user.incrementFollowers();
        follow.setText("Unfollow");
        follow.setTextColor(context.getColor(R.color.black));
        follow.setBackgroundResource(R.drawable.selected_btn_bg);
        User owner = Helper.extractUser(context);
        owner.incrementFollowings();
        Helper.storeUser(context, owner);
        followers.setText(String.valueOf(Integer.parseInt(followers.getText().toString()) + 1));

    }

    public static void unfollowUser(Context context, User user, TextView followers, Button follow) {

        Relay relay = new Relay(Constants.APIs.UNFOLLOW_USER, response -> unfollowUserRESPONSE(context, response, user, followers, follow), (api, e) -> error(api, context, e, "Error following miner"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Follows.USER_ID1, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Follows.USER_ID2, user.getUser_id());

        relay.sendRequest();

    }

    private static void unfollowUserRESPONSE(Context context, Response response, User user, TextView followers, Button follow) {

        user.decrementFollowers();
        follow.setText("Follow");
        follow.setTextColor(context.getColor(R.color.white));
        follow.setBackgroundResource(R.drawable.btn_bg);
        User owner = Helper.extractUser(context);
        owner.decrementFollowings();
        Helper.storeUser(context, owner);
        followers.setText(String.valueOf(Integer.parseInt(followers.getText().toString()) - 1));

    }

    public static void checkFollowAndToggle(Context context, User user, TextView followings, Button follow) {

        Relay relay = new Relay(Constants.APIs.IS_FOLLOWING, response -> checkFollowAndToggleRESPONSE(context, response, user, followings, follow), (api, e) -> error(api, context, e, "Error following miner"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Follows.USER_ID1, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Follows.USER_ID2, user.getUser_id());

        relay.sendRequest();


    }

    private static void checkFollowAndToggleRESPONSE(Context context, Response response, User user, TextView followings, Button follow) {

        if(response.isAuthenticated()) {

            unfollowUser(context, user, followings, follow);

        } else {

            followUser(context, user, followings, follow);

        }


    }

    public static void checkFollowAndToggleButton(Context context, User user, Button follow) {

        Relay relay = new Relay(Constants.APIs.IS_FOLLOWING, response -> checkFollowAndToggleButtonRESPONSE(context, response, user, follow), (api, e) -> error(api, context, e, "Error following miner"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Follows.USER_ID1, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Follows.USER_ID2, user.getUser_id());

        relay.sendRequest();


    }

    private static void checkFollowAndToggleButtonRESPONSE(Context context, Response response, User user, Button follow) {

        if(response.isAuthenticated()) {

            follow.setText("Unfollow");
            follow.setBackgroundResource(R.drawable.selected_btn_bg);
            follow.setTextColor(context.getColor(R.color.black));

        } else {

            follow.setText("Follow");
            follow.setBackgroundResource(R.drawable.btn_bg);
            follow.setTextColor(context.getColor(R.color.white));

        }


    }

    public static void undiamondsGem(Context context, int gem_id, int user_id, TextView diamond_text) {

        Relay relay = new Relay(Constants.APIs.UNDIAMOND_GEM, response -> undiamondsGemRESPONSE(context, response, gem_id, diamond_text), (api, e) -> error(api, context, e, "Error diamonding gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Diamonds.USER_ID, user_id);
        relay.addParam(Constants.Diamonds.GEM_ID, gem_id);

        relay.sendRequest();

    }

    private static void undiamondsGemRESPONSE(Context context, Response response, int gem_id, TextView diamond_text) {

        Gem current;
        if(Temp.TEMP_GEMS.containsKey(gem_id)) {
            Temp.TEMP_GEMS.get(gem_id).setLiked(false);
            Temp.TEMP_GEMS.get(gem_id).decrementDiamond();
        } else {
            Temp.TEMP_COMMENTS.get(gem_id).setLiked(false);
            Temp.TEMP_COMMENTS.get(gem_id).decrementDiamond();
        }

        diamond_text.setText(String.valueOf(Integer.parseInt(diamond_text.getText().toString()) - 1));

    }

    public static void addTextGem(Context context, String content, AppCompatActivity activity) {

        Relay relay = new Relay(Constants.APIs.ADD_GEM, response -> addTextGemRESPONSE(context, response, activity), (api, e) -> error(api, context, e, "Error mining gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Gems.OWNER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Gems.TYPE, 0);
        relay.addParam(Constants.Gems.MINE_DATE, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        relay.addParam(Constants.Gems.EDIT_DATE, "1970-01-01");
        relay.addParam(Constants.Gems.CONTENT, String.format("{\"text\":\"%s\"}", content));

        relay.sendRequest();
    }

    private static void addTextGemRESPONSE(Context context, Response response, AppCompatActivity activity) {

        Gem gem = (Gem) response.getQueryResult().get(Constants.Classes.GEM).get(0);
        Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
        Temp.TEMP_LATEST_GEM = gem.getGem_id();
        activity.finish();

    }

    public static void addTextComment(Context context, String content, int root, AppCompatActivity activity) {

        Relay relay = new Relay(Constants.APIs.ADD_GEM, response -> addTextCommentRESPONSE(context, response, activity), (api, e) -> error(api, context, e, "Error mining gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Gems.OWNER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Gems.TYPE, 0);
        relay.addParam(Constants.Gems.ROOT, root);
        relay.addParam(Constants.Gems.MINE_DATE, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        relay.addParam(Constants.Gems.EDIT_DATE, "1970-01-01");
        relay.addParam(Constants.Gems.CONTENT, String.format("{\"text\":\"%s\"}", content));

        relay.sendRequest();
    }

    private static void addTextCommentRESPONSE(Context context, Response response, AppCompatActivity activity) {

        Gem gem = (Gem) response.getQueryResult().get(Constants.Classes.GEM).get(0);
        Temp.TEMP_COMMENTS.put(gem.getGem_id(), gem);
        Temp.TEMP_LATEST_COMMENT = gem.getGem_id();
        activity.finish();

    }

    public static void deleteGem(Context context, int gem_id, ListView list) {

        Relay relay = new Relay(Constants.APIs.DELETE_GEM, response -> deleteGemRESPONSE(context, response, gem_id, list), (api, e) -> error(api, context, e, "Error deleting gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Gems.GEM_ID, gem_id);

        relay.sendRequest();

    }

    private static void deleteGemRESPONSE(Context context, Response response, int gem_id, ListView list) {

        ((GemsAdapter)list.getAdapter()).remove(Temp.TEMP_GEMS.containsKey(gem_id) ? Temp.TEMP_GEMS.get(gem_id) : Temp.TEMP_COMMENTS.get(gem_id));
        Temp.TEMP_GEMS.remove(gem_id);
        Temp.TEMP_COMMENTS.remove(gem_id);
        list.invalidateViews();

    }

    public static void updateUserInDatabase(Context context, EditProfileActivity activity, User user) {

        Relay relay = new Relay(Constants.APIs.UPDATE_USER, response -> updateUserInDatabaseRESPONSE(context, response, activity, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user.getUser_id());
        relay.addParam(Constants.Users.USERNAME, user.getUsername());
        relay.addParam(Constants.Users.PASSWORD, user.getPassword());
        relay.addParam(Constants.Users.EMAIL, user.getEmail());
        relay.addParam(Constants.Users.BANNER, user.getBanner());
        relay.addParam(Constants.Users.PICTURE, user.getProfile());
        relay.addParam(Constants.Users.GENDER, user.getGender());
        relay.addParam(Constants.Users.BIRTHDAY, user.getBirthday());
        relay.addParam(Constants.Users.BIO, user.getBio());

        relay.sendRequest();

    }

    private static void updateUserInDatabaseRESPONSE(Context context, Response response, EditProfileActivity activity, User user) {

        Temp.TEMP_USERS.put(user.getUser_id(), user);
        Helper.storeUser(context, user);
        context.startActivity(new Intent(context, ProfileActivity.class));

    }

    public static void addPollGem(Context context, String content, String choice1, String choice2, String choice3, String choice4, MiningActivity activity) {

        Relay relay = new Relay(Constants.APIs.ADD_GEM, response -> addPollGemRESPONSE(context, response, activity), (api, e) -> error(api, context, e, "Error mining gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Gems.OWNER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Gems.TYPE, 3);
        relay.addParam(Constants.Gems.MINE_DATE, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        relay.addParam(Constants.Gems.EDIT_DATE, "1970-01-01");

        try {
            JSONObject content_json = new JSONObject();
            content_json.put(Constants.Gems.Content.OPTION1, choice1);
            content_json.put(Constants.Gems.Content.OPTION2, choice2);
            content_json.put(Constants.Gems.Content.OPTION3, choice3);
            content_json.put(Constants.Gems.Content.OPTION4, choice4);
            content_json.put(Constants.Gems.Content.OPTION1PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION2PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION3PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION4PERC, 0);
            content_json.put(Constants.Gems.Content.PROMPT, content);

        relay.addParam(Constants.Gems.CONTENT, content_json.toString());

        relay.sendRequest();

        } catch (JSONException e) {


        }
    }

    private static void addPollGemRESPONSE(Context context, Response response, MiningActivity activity) {

        Gem gem = (Gem) response.getQueryResult().get(Constants.Classes.GEM).get(0);
        Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
        Temp.TEMP_LATEST_GEM = gem.getGem_id();
        activity.finish();

    }

    public static void addPollComment(Context context, String content, String choice1, String choice2, String choice3, String choice4, int root, CommentingActivity activity) {

        Relay relay = new Relay(Constants.APIs.ADD_GEM, response -> addPollCommentRESPONSE(context, response, activity), (api, e) -> error(api, context, e, "Error mining gem"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Gems.OWNER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.addParam(Constants.Gems.TYPE, 3);
        relay.addParam(Constants.Gems.ROOT, root);
        relay.addParam(Constants.Gems.MINE_DATE, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        relay.addParam(Constants.Gems.EDIT_DATE, "1970-01-01");

        try {
            JSONObject content_json = new JSONObject();
            content_json.put(Constants.Gems.Content.OPTION1, choice1);
            content_json.put(Constants.Gems.Content.OPTION2, choice2);
            content_json.put(Constants.Gems.Content.OPTION3, choice3);
            content_json.put(Constants.Gems.Content.OPTION4, choice4);
            content_json.put(Constants.Gems.Content.OPTION1PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION2PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION3PERC, 0);
            content_json.put(Constants.Gems.Content.OPTION4PERC, 0);
            content_json.put(Constants.Gems.Content.PROMPT, content);

            relay.addParam(Constants.Gems.CONTENT, content_json.toString());

            relay.sendRequest();

        } catch (JSONException e) {


        }
    }

    private static void addPollCommentRESPONSE(Context context, Response response, CommentingActivity activity) {

        Gem gem = (Gem) response.getQueryResult().get(Constants.Classes.GEM).get(0);
        Temp.TEMP_COMMENTS.put(gem.getGem_id(), gem);
        Temp.TEMP_LATEST_COMMENT = gem.getGem_id();
        activity.finish();

    }

    public static void error(String api, Context context, Exception e, String error_message) {

        StringBuilder result = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(t -> result.append(t).append("\n"));
        Log.i(String.format("Error in API %s in %s", api, e.getMessage()), String.valueOf(result));
        ContextCompat.getMainExecutor(context).execute(() -> Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show());

    }


}

