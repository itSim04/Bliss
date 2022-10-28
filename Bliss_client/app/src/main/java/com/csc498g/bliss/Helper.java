package com.csc498g.bliss;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Helper {

    public static ArrayList<Gem> rebaseGemFromJSON(JSONArray json) {

        ArrayList<Gem> result = new ArrayList<>();
        try {
        for (int i = 0; i < json.length(); i++) {

            JSONObject current = json.getJSONObject(i);

            int gem_id = current.getInt(Constants.Gems.GEM_ID);
            String mine_date = current.getString(Constants.Gems.MINE_DATE);
            String edit_date = current.getString(Constants.Gems.EDIT_DATE);
            int type = current.getInt(Constants.Gems.TYPE);
            int owner_id = current.getInt(Constants.Gems.OWNER_ID);
            JSONObject content = new JSONObject(current.getString(Constants.Gems.CONTENT));
            Log.i("Content", content.toString());

            Gem current_gem = new TextGem(gem_id, mine_date, edit_date, owner_id, content.getString(Constants.Gems.Content.TEXT), 0, 0);
            result.add(current_gem);
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

}
