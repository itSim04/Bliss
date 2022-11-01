package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Link {



    public static void checkAvailability(Context context, RegisterActivity activity, User user) {

        Relay relay = new Relay(Constants.APIs.IS_USERNAME_AVAILABLE, response -> checkAvailabilityRESPONSE1(context, response, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, user.getUsername());

        relay.sendRequest();

    }

    private static void checkAvailabilityRESPONSE1(Context context, Response response, User user) {

        if (response.is_available) {

            Relay relay = new Relay(Constants.APIs.IS_EMAIL_AVAILABLE, response1 -> checkAvailabilityRESPONSE2(context, response1, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

            relay.setConnectionMode(Relay.MODE.POST);

            relay.addParam(Constants.Users.EMAIL, user.getEmail());

            relay.sendRequest();

        } else {

            Toast.makeText(context, "Username Taken", Toast.LENGTH_LONG).show();

        }

    }

    private static void checkAvailabilityRESPONSE2(Context context, Response response, User user) {

        if (response.is_available) {

            addUserToDatabase(context, user);

        } else {

            Toast.makeText(context, "Email Taken", Toast.LENGTH_LONG).show();

        }

    }

    private static void addUserToDatabase(Context context, User user) {

        Relay relay = new Relay(Constants.APIs.ADD_USER, response -> addUserToDatabaseRESPONSE(context, response, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, user.getUsername());
        relay.addParam(Constants.Users.PASSWORD, user.getPassword());
        relay.addParam(Constants.Users.EMAIL, user.getEmail());
        relay.addParam(Constants.Users.BANNER, user.getBanner());
        relay.addParam(Constants.Users.PICTURE, user.getProfile());
        relay.addParam(Constants.Users.GENDER, user.getGender());
        relay.addParam(Constants.Users.BIRTHDAY, user.getBirthday());

        relay.sendRequest();

    }

    public static void addUserToDatabaseRESPONSE(Context context, Response response, User user) {

        if (response.success) {

            Intent intent = new Intent(context, FeedActivity.class);
            user.setUser_id(response.last_id);
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

        JSONObject user_json = response.getQuery_result();
        User result = Helper.rebaseUserFromJSON(user_json);
        assert result != null;
        Temp.TEMP_USERS.put(result.getUser_id(), result);

    }

    public static void getAndStoreUser(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_USER, response -> getAndStoreUserRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getAndStoreUserRESPONSE(Context context, Response response) {

        JSONObject user_json = response.getQuery_result();
        User result = Helper.rebaseUserFromJSON(user_json);
        assert result != null;
        Helper.storeUser(context, result);
        Temp.TEMP_USERS.put(result.getUser_id(), result);

    }

    public static void getAllGemsStoreInTempAndUpdateFeed(Context context, SwipeRefreshLayout layout, ListView list) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsStoreInTempAndUpdateFeedRESPONSE(context, response, layout, list), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

        relay.sendRequest();

    }

    private static void getAllGemsStoreInTempAndUpdateFeedRESPONSE(Context context, Response response, SwipeRefreshLayout layout, ListView list) {

        try {
            JSONObject store_json = response.getQuery_result();

            JSONArray users_json = store_json.getJSONArray("users");
            ArrayList<User> user_result = Helper.rebaseUsersFromJSON(users_json);
            user_result.forEach(user -> {
                Temp.TEMP_USERS.put(user.getUser_id(), user);
            });


            JSONArray gems_json = store_json.getJSONArray("gems");
            ArrayList<Gem> gems_result = Helper.rebaseGemsFromJSON(gems_json);

            Collections.reverse(gems_result);

            ((GemsAdapter)list.getAdapter()).flush();
            gems_result.forEach(gem -> {
                Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
                ((GemsAdapter) list.getAdapter()).add(gem);
                list.setAdapter(list.getAdapter());
            });



        } catch (JSONException e) {


        } finally {

            layout.setRefreshing(false);

        }

    }

    public static void getAllGemsAndStoreInTemp(Context context) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

        relay.sendRequest();

    }

    private static void getAllGemsAndStoreInTempRESPONSE(Context context, Response response) {

        try {
            JSONObject store_json = response.getQuery_result();

            JSONArray users_json = store_json.getJSONArray("users");
            ArrayList<User> user_result = Helper.rebaseUsersFromJSON(users_json);
            user_result.forEach(user -> {
                Temp.TEMP_USERS.put(user.getUser_id(), user);
            });


            JSONArray gems_json = store_json.getJSONArray("gems");
            ArrayList<Gem> gems_result = Helper.rebaseGemsFromJSON(gems_json);
            gems_result.forEach(gem -> {
                Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
            });

        } catch (JSONException e) {

        }
    }

    public static void authenticateUser(Context context, String username, String password) {

        Relay relay = new Relay(Constants.APIs.AUTHENTICATE_LOGIN, response -> authenticateUserRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, username);
        relay.addParam(Constants.Users.PASSWORD, password);

        relay.sendRequest();

    }

    private static void authenticateUserRESPONSE(Context context, Response response) {

        if (response.is_authenticated) {

            User user = Helper.rebaseUserFromJSON(response.getQuery_result());
            assert user != null;
            Helper.storeUser(context, user);
            Intent i = new Intent(context, FeedActivity.class);
            context.startActivity(i);

        } else {

            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();

        }

    }

    public static void error(String api, Context context, Exception e, String error_message) {

        StringBuilder result = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(t -> result.append(t).append("\n"));
        Log.i(String.format("Error in API %s in %s", api, e.getLocalizedMessage()), String.valueOf(result));
        ContextCompat.getMainExecutor(context).execute(()  -> Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show());

    }

}

