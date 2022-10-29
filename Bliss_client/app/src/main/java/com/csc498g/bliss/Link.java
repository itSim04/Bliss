package com.csc498g.bliss;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

    public static void getUser(int user_id) {

        POST get_user = new POST(response -> {
            JSONObject user_json = response.getQuery_result();
            User result = Helper.rebaseUserFromJSON(user_json);
            assert result != null;
            Temp.TEMP_USERS.put(result.getUser_id(), result);
        });
        get_user.execute(Constants.URL.buildUrl(Constants.APIs.GET_USER), String.format(Locale.US, "{\"user_id\": %d}", user_id));

    }

    public static void get_all_gems() {

        GET get_all_gems_API_call = new GET(

                response -> {
                    JSONArray gems_json = response.getQuery_results();
                    ArrayList<Gem> result = Helper.rebaseGemsFromJSON(gems_json);
                    result.forEach(gem -> {
                        Temp.TEMP_GEMS.put(gem.getGem_id(), gem);
                        getUser(gem.getOwner_id());
                    });
                });

        get_all_gems_API_call.execute(Constants.URL.buildUrl(Constants.APIs.GET_ALL_GEMS));

    }

    //Creating a task that will run in parallel, in the background of our application
    public static class GET extends AsyncTask<String, Void, String> {

        private final PROCESS executor;

        public GET(PROCESS content) {

            super();
            this.executor = content;

        }

        @Override
        protected String doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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

                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONObject(Constants.Response.QUERY_RESULT),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    } else {
                        response = new Response(

                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONArray(Constants.Response.QUERY_RESULTS),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    }
                    executor.ACCESS(response);
                }


            } catch (JSONException e) {

                Log.i("Download Task: On Post Execute", e.toString());

            }

        }
    }

    public static class POST extends AsyncTask<String, Void, String> {

        private final PROCESS executor;

        public POST(PROCESS content) {

            super();
            executor = content;

        }

        @Override
        protected String doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder();
                JSONObject params = new JSONObject(urls[1]);
                params.keys().forEachRemaining(t ->

                        {
                            try {
                                builder.appendQueryParameter(t, params.getInt(t) + "");
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

                Log.i("doInBackground: Return", chain.toString());
                return chain.toString();

            } catch (JSONException e) {

                Log.i("doInBackground: JSONException", e.toString());
                return null;

            } catch (ConnectException e) {

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
                Log.i("POST 1", s);
                //Convert our String to a JSON object
                if (s != null) {
                    JSONObject json = new JSONObject(s);
                    Response response;
                    Log.i("QUERY", json.toString());
                    if (json.has(Constants.Response.QUERY_RESULT)) {
                        response = new Response(

                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONObject(Constants.Response.QUERY_RESULT),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    } else {
                        response = new Response(

                                json.optString(Constants.Response.ERROR),
                                json.optBoolean(Constants.Response.SUCCESS),
                                json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                                json.optJSONArray(Constants.Response.QUERY_RESULTS),
                                json.optBoolean(Constants.Response.IS_AVAILABLE)

                        );
                    }
                    executor.ACCESS(response);
                }


            } catch (Exception e) {

                Log.i("Download Task: On Post Execute", e.toString());

            }

        }

    }
}

