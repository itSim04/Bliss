package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Link {


    public static void checkAvailability(Context context, RegisterActivity activity, User user) {

        Relay relay = new Relay(Constants.APIs.IS_USERNAME_EMAIL_AVAILABLE, response -> checkAvailabilityRESPONSE(context, response, user), (api, e) -> error(api, context, e, "Error Connecting to Server"));

        relay.setConnectionMode(Relay.MODE.POST);

        relay.addParam(Constants.Users.USERNAME, user.getUsername());
        relay.addParam(Constants.Users.EMAIL, user.getEmail());

        relay.sendRequest();

    }

    private static void checkAvailabilityRESPONSE(Context context, Response response, User user) {

        int availability = response.isAvailable();

        if (availability == Constants.Availability.NONE_AVAILABLE || availability == Constants.Availability.EMAIL_AVAILABLE) {

            Toast.makeText(context, "Username Taken", Toast.LENGTH_LONG).show();

        } else if (availability == Constants.Availability.USERNAME_AVAILABLE) {

            Toast.makeText(context, "Email Taken", Toast.LENGTH_LONG).show();

        } else {

            addUserToDatabase(context, user);

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

        Relay relay = new Relay(Constants.APIs.GET_USER, response -> getAndStoreUserRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

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

    public static void getAllGemsStoreInTempAndUpdateFeed(Context context, SwipeRefreshLayout layout, ListView list) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsStoreInTempAndUpdateFeedRESPONSE(context, response, layout, list), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

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

    public static void getAllGemsAndStoreInTemp(Context context) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

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
        relay.sendRequest();


    }

    private static void getAllGemsByUserStoreInTempAndUpdateListRESPONSE(Context context, Response response, ListView list, SwipeRefreshLayout layout) {

        ArrayList<Gem> gems = (ArrayList<Gem>) response.getQueryResult().get(Constants.Classes.GEM);
        gems.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));

        GemsAdapter adapter = new GemsAdapter(context, gems);
        list.setAdapter(adapter);
        layout.setRefreshing(false);


    }


    public static void diamondsGem(Context context, int gem_id, int user_id){
        Relay relay = new Relay(Constants.APIs.DIAMOND_GEM, null, (api, e) -> error(api, context, e, "Error diamonding gem"));
        relay.setConnectionMode(Relay.MODE.POST);
        relay.addParam(Constants.Diamonds.USER_ID, user_id);
        relay.addParam(Constants.Diamonds.GEM_ID, gem_id);
        relay.addParam(Constants.Diamonds.DIAMOND_DATE, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public static void diamondsGemRESPONSE(Context context, Response response, int gem_id, TextView diamonds){

        Temp.TEMP_GEMS.get(gem_id).incrementDiamond();
        diamonds.setText(String.valueOf(Integer.parseInt(diamonds.getText().toString()) + 1));
        
    }


    public static void error(String api, Context context, Exception e, String error_message) {

        StringBuilder result = new StringBuilder();
        Arrays.stream(e.getStackTrace()).forEach(t -> result.append(t).append("\n"));
        Log.i(String.format("Error in API %s in %s", api, e.getMessage()), String.valueOf(result));
        ContextCompat.getMainExecutor(context).execute(() -> Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show());

    }



}

