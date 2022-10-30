package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class Link {

    public static Drawable GetImage(String url) {
        try {
            InputStream stream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(stream, "Image");
        } catch (Exception e) {
            Log.i("GetImage", e.getLocalizedMessage());
            return null;
        }
    }

    public static void getUser(Context context, int user_id, ListView list, Gem gem) {

        if (Temp.TEMP_USERS.containsKey(user_id)) {
            ((GemsAdapter) list.getAdapter()).add(gem);
            list.setAdapter(list.getAdapter());
        } else {
            POST get_user = new POST(context, response -> {
                JSONObject user_json = response.getQuery_result();
                User result = Helper.rebaseUserFromJSON(user_json);
                assert result != null;
                Temp.TEMP_USERS.put(result.getUser_id(), result);
                ((GemsAdapter) list.getAdapter()).add(gem);
                list.setAdapter(list.getAdapter());
                //list.setAdapter(new GemsAdapter(context, new ArrayList<>(Temp.TEMP_GEMS.values())));

            });
            get_user.execute(Constants.URL.buildUrl(Constants.APIs.GET_USER), String.format(Locale.US, "{\"user_id\": %d}", user_id));
        }
    }

    public static void checkAvailability(Context context, RegisterActivity activity, User user, String password) {

        POST check_username = new POST(context, username_response -> {

            if (username_response.is_available) {

                POST check_email = new POST(context, email_response -> {

                    if (email_response.is_available) {

                        add_user(context, user, password);

                    } else {

                        Toast.makeText(context, "Email Taken", Toast.LENGTH_LONG).show();

                    }

                });
                check_email.execute(Constants.URL.buildUrl(Constants.APIs.IS_EMAIL_AVAILABLE), String.format(Locale.US, "{\"email\": %s}", user.getEmail()));


            } else {

                Toast.makeText(context, "Username Taken", Toast.LENGTH_LONG).show();

            }

        });
        check_username.execute(Constants.URL.buildUrl(Constants.APIs.IS_USERNAME_AVAILABLE), String.format(Locale.US, "{\"username\": %s}", user.getUsername()));


    }

    private static void add_user(Context context, User user, String password) {

        POST get_user = new POST(context, response -> {

            if (response.success) {

                Intent intent = new Intent(context, FeedActivity.class);
                context.startActivity(intent);

            } else {

                Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();

            }
        });
        get_user.execute(Constants.URL.buildUrl(Constants.APIs.ADD_USER), String.format(Locale.US,

                "{\"username\": %s, " +
                        "\"password\": %s, " +
                        "\"email\": %s, " +
                        "\"banner\": %s, " +
                        "\"picture\": %s, " +
                        "\"gender\": %s, " +
                        "\"birthday\": %s}",
                user.getUsername(), password, user.getEmail(), user.getBanner(), user.getProfile(), String.valueOf(user.getGender()), user.getBirthday()));


    }

    public static void getUser(Context context, int user_id) {

        POST get_user = new POST(context, response -> {
            JSONObject user_json = response.getQuery_result();
            User result = Helper.rebaseUserFromJSON(user_json);
            assert result != null;
            Temp.TEMP_USERS.put(result.getUser_id(), result);

        });
        get_user.execute(Constants.URL.buildUrl(Constants.APIs.GET_USER), String.format(Locale.US, "{\"user_id\": %d}", user_id));

    }

    public static void get_all_gems_and_update_feed(Context context, SwipeRefreshLayout layout, ListView list) {

        GET get_all_gems_API_call = new GET(

                context, response -> {
            JSONArray gems_json = response.getQuery_results();
            ArrayList<Gem> result = Helper.rebaseGemsFromJSON(gems_json);
            ((GemsAdapter) list.getAdapter()).flush();
            Collections.reverse(result);
            result.forEach(gem -> {
                Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
                getUser(context, gem.getOwner_id(), list, gem);
                Log.i("GEMS", Temp.TEMP_GEMS.toString());
            });


            layout.setRefreshing(false);

        });

        get_all_gems_API_call.execute(Constants.URL.buildUrl(Constants.APIs.GET_ALL_GEMS));

    }

    public static void get_all_gems(Context context) {

        GET get_all_gems_API_call = new GET(

                context, response -> {
            JSONArray gems_json = response.getQuery_results();
            ArrayList<Gem> result = Helper.rebaseGemsFromJSON(gems_json);
            result.forEach(gem -> {
                Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
                getUser(context, gem.getOwner_id());
                Log.i("GEMS", Temp.TEMP_GEMS.toString());
            });
        });

        get_all_gems_API_call.execute(Constants.URL.buildUrl(Constants.APIs.GET_ALL_GEMS));

    }

    public static void authenticateUser(LoginActivity ac, Context context, String username, String password) {

        POST get_user = new POST(context, response -> {
            if (response.is_authenticated) {
                User user = Helper.rebaseUserFromJSON(response.getQuery_result());
                Helper.storeUser(ac, user, password);
                Intent i = new Intent(context, FeedActivity.class);
                context.startActivity(i);
            } else {
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
        get_user.execute(Constants.URL.buildUrl(Constants.APIs.AUTHENTICATE_LOGIN), String.format(Locale.US, "{\"username\": %s, \"password\": %s}", username, password));

    }

    //Creating a task that will run in parallel, in the background of our application
    public static class GET extends AsyncTask<String, Void, String> {

        private final Context context;
        private final PROCESS executor;

        public GET(Context context, PROCESS content) {

            super();
            this.context = context;
            this.executor = content;

        }

        @Override
        protected String doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(20000);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder chain = new StringBuilder();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

                for (String line = rd.readLine(); line != null; line = rd.readLine()) {

                    chain.append(line);

                }

                return chain.toString();

            } catch (IOException e) {

                Log.i("doInBackground", e.toString());
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                //Convert our String to a JSON object
                if (s != null) {
                    JSONObject json = new JSONObject(s);
                    Response response;
                    if (json.has(Constants.Response.QUERY_RESULT)) {
                        response = new Response(

                                json.optInt(Constants.Response.LAST_ID),
                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONObject(Constants.Response.QUERY_RESULT),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    } else {
                        response = new Response(

                                json.optInt(Constants.Response.LAST_ID),
                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONArray(Constants.Response.QUERY_RESULTS),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    }

                    Log.i("GET", json.toString());
                    executor.ACCESS(response);
                }


            } catch (JSONException e) {

                Log.i("Download Task: On Post Execute", e.toString());

            }

        }
    }

    public static class POST extends AsyncTask<String, Void, String> {

        private final Context context;
        private final PROCESS executor;

        public POST(Context context, PROCESS content) {

            super();
            this.context = context;
            executor = content;

        }

        @Override
        protected String doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(20000);

                Uri.Builder builder = new Uri.Builder();
                JSONObject params = new JSONObject(urls[1]);
                params.keys().forEachRemaining(t ->

                        {
                            try {
                                builder.appendQueryParameter(t, params.get(t) + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                );

                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                connection.setRequestMethod("POST");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder chain = new StringBuilder();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

                for (String line = rd.readLine(); line != null; line = rd.readLine()) {

                    chain.append(line);

                }

                return chain.toString();

            } catch (JSONException e) {

                Log.i("doInBackground: JSONException", e.toString());
                return null;

            } catch (ConnectException e) {

                Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                Log.i("doInBackground: ConnectException", e.toString());
                return null;

            } catch (ProtocolException e) {

                Log.i("doInBackground: ProtocolException", e.toString());
                return null;

            } catch (MalformedURLException e) {

                Log.i("doInBackground: MalformedURLException", e.toString());
                return null;

            } catch (UnsupportedEncodingException e) {

                Log.i("doInBackground: UnsupportedEncodingException", e.toString());
                return null;

            } catch (IOException e) {

                Log.i("doInBackground: IOException", e.toString());
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                //Convert our String to a JSON object
                if (s != null) {
                    JSONObject json = new JSONObject(s);
                    Response response;

                    if (json.has(Constants.Response.QUERY_RESULT)) {
                        response = new Response(

                                json.optInt(Constants.Response.LAST_ID),
                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONObject(Constants.Response.QUERY_RESULT),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    } else {
                        response = new Response(

                                json.optInt(Constants.Response.LAST_ID),
                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONArray(Constants.Response.QUERY_RESULTS),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    }
                    Log.i("POST", json.toString());
                    executor.ACCESS(response);
                }


            } catch (Exception e) {

                Log.i("POST: Download Task: On Post Execute", Arrays.toString(e.getStackTrace()));

            }

        }

    }
}

