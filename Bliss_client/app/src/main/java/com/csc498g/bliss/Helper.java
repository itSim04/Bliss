package com.csc498g.bliss;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Helper {

    public static ArrayList<Gem> rebaseGemsFromJSON(JSONArray json) {

        ArrayList<Gem> result = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {

                JSONObject current = json.getJSONObject(i);

                int gem_id = current.getInt(Constants.Gems.GEM_ID);
                String mine_date = current.getString(Constants.Gems.MINE_DATE);
                String edit_date = current.getString(Constants.Gems.EDIT_DATE);
                int owner_id = current.getInt(Constants.Gems.OWNER_ID);
                int remines = current.getInt(Constants.Gems.REMINES);
                int diamonds = current.getInt(Constants.Gems.DIAMONDS);
                JSONObject content = new JSONObject(current.getString(Constants.Gems.CONTENT));

                int type = current.getInt(Constants.Gems.TYPE);
                Gem current_gem = null;
                switch (type) {

                    case 0:

                        String text = content.getString(Constants.Gems.Content.TEXT);
                        current_gem = new TextGem(gem_id, mine_date, edit_date, owner_id, text, diamonds, remines);
                        break;

                    case 1:

                        String img_src = content.getString(Constants.Gems.Content.IMG_SRC);
                        current_gem = new ImageGem(gem_id, mine_date, edit_date, owner_id, img_src, diamonds, remines);
                        break;

                    case 2:

                        //String vid_src = content.getString(Constants.Gems.Content.VID_SRC);
                        //current_gem = new TextGem(gem_id, mine_date, edit_date, owner_id, vid_src, 0, 0);
                        break;

                    case 3:

                        String prompt = content.getString(Constants.Gems.Content.PROMPT);
                        String option1 = content.getString(Constants.Gems.Content.OPTION1);
                        String option2 = content.getString(Constants.Gems.Content.OPTION2);
                        String option3 = content.getString(Constants.Gems.Content.OPTION3);
                        String option4 = content.getString(Constants.Gems.Content.OPTION4);
                        int option1perc = content.getInt(Constants.Gems.Content.OPTION1PERC);
                        int option2perc = content.getInt(Constants.Gems.Content.OPTION2PERC);
                        int option3perc = content.getInt(Constants.Gems.Content.OPTION3PERC);
                        int option4perc = content.getInt(Constants.Gems.Content.OPTION4PERC);
                        current_gem = new PollGem(gem_id, mine_date, edit_date, owner_id, prompt, option1, option1perc, option2, option2perc, option3, option3perc, option4, option4perc, diamonds, remines);
                        break;
                        
                }


                Log.i("CGEMS", String.valueOf(current_gem));
                result.add(current_gem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static User rebaseUserFromJSON(JSONObject json) {

        try {


            int user_id = json.getInt(Constants.Users.USER_ID);
            String username = json.getString(Constants.Users.USERNAME);
            String email = json.getString(Constants.Users.EMAIL);
            String birthday = json.getString(Constants.Users.BIRTHDAY);
            byte gender = (byte) json.getInt(Constants.Users.GENDER);
            String profile = json.getString(Constants.Users.PICTURE);
            String banner = json.getString(Constants.Users.BANNER);
            int followings = json.getInt(Constants.Users.FOLLOWINGS);
            int followers = json.getInt(Constants.Users.FOLLOWERS);

            User result = new User(user_id, username, email, birthday, gender, profile, banner, followings, followers);

            Log.i("User", result.toString());
            return result;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
