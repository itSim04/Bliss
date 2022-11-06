package com.csc498g.bliss;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BIN {

    public static Drawable GetImage(String url) {
        try {
            InputStream stream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(stream, "Image");
        } catch (Exception e) {
            Log.i("GetImage", e.getLocalizedMessage());
            return null;
        }
    }

    public static void getUserAndStoreInTemp(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_USER, response -> getUserAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getUserAndStoreInTempRESPONSE(Context context, Response response) {

        User result = (User) response.getQueryResult().get(Constants.Response.Classes.USER).get(0);
        assert result != null;
        Temp.TEMP_USERS.put(result.getUser_id(), result);

    }


    public static void getUserStoreInTempAndUpdateListView(Context context, int user_id, ListView list, Gem gem) {

        if (Temp.TEMP_USERS.containsKey(user_id)) {

            ((GemsAdapter) list.getAdapter()).add(gem);
            list.setAdapter(list.getAdapter());

        } else {

            Relay relay = new Relay(Constants.APIs.GET_USER, response -> getUserStoreInTempAndUpdateListViewRESPONSE(context, response, list, gem), (api, e) -> Link.error(api, context, e, "Error Connecting to Server"));

            relay.setConnectionMode(Relay.MODE.POST);

            relay.addParam(Constants.Users.USER_ID, user_id);

            relay.sendRequest();

        }
    }

    public static void getUserStoreInTempAndUpdateListViewRESPONSE(Context context, Response response, ListView list, Gem gem) {

        //JSONObject user_json = response.getQueryResult();
        //User result = Helper.rebaseUserFromJSON(user_json);
        //assert result != null;
        //Temp.TEMP_USERS.put(result.getUser_id(), result);
        ((GemsAdapter) list.getAdapter()).add(gem);
        list.setAdapter(list.getAdapter());

    }

    public static void getAllGemsAndStoreInTemp(Context context, int user_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS, response -> getAllGemsAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error Fetching Gems from Server"));

        relay.setConnectionMode(Relay.MODE.GET);

        relay.addParam(Constants.Users.USER_ID, user_id);

        relay.sendRequest();

    }

    private static void getAllGemsAndStoreInTempRESPONSE(Context context, Response response) {

        ArrayList<User> user_result = (ArrayList<User>) response.getQueryResult().get(Constants.Response.Classes.USER);
        user_result.forEach(user -> Temp.TEMP_USERS.put(user.getUser_id(), user));

        ArrayList<Gem> gems_result = (ArrayList<Gem>) response.getQueryResult().get(Constants.Response.Classes.GEM);
        gems_result.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));

    }

    public static void getAllGemsByUserAndStoreInTemp(Context context, int owner_id) {

        Relay relay = new Relay(Constants.APIs.GET_ALL_GEMS_BY_USER, response -> getAllGemsByUserAndStoreInTempRESPONSE(context, response), (api, e) -> error(api, context, e, "Error fetching data from the server"));

        relay.setConnectionMode(Relay.MODE.GET);
        relay.addParam(Constants.Gems.OWNER_ID, owner_id);
        relay.addParam(Constants.Users.USER_ID, PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.Users.USER_ID, -1));
        relay.sendRequest();


    }

    private static void getAllGemsByUserAndStoreInTempRESPONSE(Context context, Response response) {

        ArrayList<Gem> gems = (ArrayList<Gem>) response.getQueryResult().get(Constants.Response.Classes.GEM);
        gems.forEach(gem -> Temp.TEMP_GEMS.put(gem.getGem_id(), gem));


    }


}
