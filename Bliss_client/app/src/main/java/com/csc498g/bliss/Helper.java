package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Helper {

    public static void storeUser(Context context, User user) {

        Log.i("Store User", user.toString());
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);// .getSharedPreferences("com.csc498g.bliss", Context.MODE_PRIVATE);
        sp.edit().putInt(Constants.Users.USER_ID, user.getUser_id()).apply();
        sp.edit().putString(Constants.Users.EMAIL, user.getEmail()).apply();
        sp.edit().putString(Constants.Users.USERNAME, user.getUsername()).apply();
        sp.edit().putString(Constants.Users.BIO, user.getBio()).apply();
        sp.edit().putString(Constants.Users.PASSWORD, user.getPassword()).apply();
        sp.edit().putString(Constants.Users.PICTURE, user.getProfile()).apply();
        sp.edit().putString(Constants.Users.BANNER, user.getBanner()).apply();
        sp.edit().putInt(Constants.Users.GENDER, user.getGender()).apply();
        sp.edit().putString(Constants.Users.BIRTHDAY, user.getBirthday().toString()).apply();
        sp.edit().putInt(Constants.Users.FOLLOWERS, user.getFollowers()).apply();
        sp.edit().putInt(Constants.Users.FOLLOWINGS, user.getFollowings()).apply();
        Temp.TEMP_USERS.put(user.getUser_id(), user);

    }

    public static User extractUser(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        int user_id = sp.getInt(Constants.Users.USER_ID, -1);
        int followings = sp.getInt(Constants.Users.FOLLOWINGS, 0);
        int followers = sp.getInt(Constants.Users.FOLLOWERS, 0);
        String username = sp.getString(Constants.Users.USERNAME, "lorem ipsum");
        String password = sp.getString(Constants.Users.PASSWORD, "lorem ipsum");
        String email = sp.getString(Constants.Users.EMAIL, "lorem_ipsum@co.com");
        String bio = sp.getString(Constants.Users.BIO, "");
        String banner = sp.getString(Constants.Users.BANNER, "lorem ipsum");
        String profile = sp.getString(Constants.Users.PICTURE, "lorem ipsum");
        byte gender = (byte) sp.getInt(Constants.Users.GENDER, -1);
        String birthday = sp.getString(Constants.Users.BIRTHDAY, "1970-01-01");
        String join_date = sp.getString(Constants.Users.JOIN, "1970-01-01");

        return new User(user_id, username, password, email, bio, birthday, join_date, gender, profile, banner, followings, followers);


    }

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
                int comments = current.getInt(Constants.Gems.COMMENTS);
                int is_voted = current.getInt(Constants.Gems.OPTION);
                boolean is_liked = current.getInt(Constants.Gems.IS_DIAMONDED) != 0;
                int root = current.getInt(Constants.Gems.ROOT);
                JSONObject content = new JSONObject(current.getString(Constants.Gems.CONTENT));

                int type = current.getInt(Constants.Gems.TYPE);
                Gem current_gem = null;
                switch (type) {

                    case 0:

                        String text = content.getString(Constants.Gems.Content.TEXT);
                        current_gem = new TextGem(gem_id, mine_date, edit_date, owner_id, text, diamonds, remines, comments, root, is_liked, is_voted);
                        break;

                    case 1:

                        String img_src = content.getString(Constants.Gems.Content.IMG_SRC);
                        current_gem = new ImageGem(gem_id, mine_date, edit_date, owner_id, img_src, diamonds, remines, comments, root, is_liked, is_voted);
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
                        current_gem = new PollGem(gem_id, mine_date, edit_date, owner_id, prompt, option1, option1perc, option2, option2perc, option3, option3perc, option4, option4perc, diamonds, remines, comments, root, is_liked, is_voted);
                        break;
                        
                }


                Log.i("GEMS", String.valueOf(current_gem));
                result.add(current_gem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static ArrayList<User> rebaseUsersFromJSON(JSONArray json) {

        ArrayList<User> result = new ArrayList<>();
        try {

            for (int i = 0; i < json.length(); i++) {

                JSONObject current = json.getJSONObject(i);

                User user = rebaseUserFromJSON(current);

                result.add(user);

            }
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void mine(Context context) {

        context.startActivity(new Intent(context, MiningActivity.class));

    }

    public static void home(Context context) {

        context.startActivity(new Intent(context, FeedActivity.class));

    }

    public static User rebaseUserFromJSON(JSONObject json) {

        try {

            int user_id = json.getInt(Constants.Users.USER_ID);
            String password = json.getString(Constants.Users.PASSWORD);
            String username = json.getString(Constants.Users.USERNAME);
            String email = json.getString(Constants.Users.EMAIL);
            String bio = json.getString(Constants.Users.BIO);
            String birthday = json.getString(Constants.Users.BIRTHDAY);
            String join_date = json.getString(Constants.Users.JOIN);
            byte gender = (byte) json.getInt(Constants.Users.GENDER);
            String profile = json.getString(Constants.Users.PICTURE);
            String banner = json.getString(Constants.Users.BANNER);
            int followings = json.getInt(Constants.Users.FOLLOWINGS);
            int followers = json.getInt(Constants.Users.FOLLOWERS);

            User result = new User(user_id, username, password, email, bio, birthday, join_date, gender, profile, banner, followings, followers);

            Log.i("User", result.toString());
            return result;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatRemainingDate(LocalDateTime date) {

        long seconds = Duration.between(date, LocalDateTime.now()).getSeconds();

        long numberOfDays;
        long numberOfHours;
        long numberOfMinutes;

        numberOfDays = seconds / 86400;
        numberOfHours = (seconds % 86400) / 3600 ;
        numberOfMinutes = ((seconds % 86400) % 3600) / 60;

        if(numberOfDays == 0 && numberOfHours == 0 && numberOfMinutes == 0) {
            return  "Now";
        } else if (numberOfDays == 0 && numberOfHours == 0) {
            return  String.format("%dm", numberOfMinutes);
        } else if(numberOfDays == 0) {
            return  String.format("%dh", numberOfHours);
        } else if(numberOfDays <= 30) {
            return  String.format("%dd", numberOfDays);
        } else {
            return  String.format("%d-%d-%d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        }

    }
}
