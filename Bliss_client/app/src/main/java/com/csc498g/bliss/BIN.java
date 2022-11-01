package com.csc498g.bliss;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

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

        JSONObject user_json = response.getQuery_result();
        User result = Helper.rebaseUserFromJSON(user_json);
        assert result != null;
        Temp.TEMP_USERS.put(result.getUser_id(), result);
        ((GemsAdapter) list.getAdapter()).add(gem);
        list.setAdapter(list.getAdapter());

    }

}
