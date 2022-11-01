package com.csc498g.bliss;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Relay extends AsyncTask<String, Void, String> {

    private final Map<String, Object> parameters;
    private final String url;
    private final PROCESS executor;
    private final ERROR error;
    private final String api;
    private String mode = "GET";

    public Relay(@NonNull String api, @NonNull PROCESS content, @NonNull ERROR error) {

        super();
        this.api = api;
        this.url = Constants.URL.buildUrl(api);
        this.executor = content;
        this.error = error;
        this.parameters = new HashMap<>();

    }

    public <T> void addParam(String key, T value) {

        parameters.put(key, value);

    }

    public void addParams(Map<String, ?> params) {

        parameters.putAll(params);

    }

    public void setConnectionMode(MODE mode1) {

        switch (mode1) {

            case GET:

                this.mode = "GET";
                break;

            case POST:

                this.mode = "POST";
                break;
        }

    }

    public void sendRequest() {

        this.execute();

    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            URL current_url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) current_url.openConnection();
            connection.setRequestMethod(mode);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(20000);

            if (mode.equals("POST")) {
                Uri.Builder builder = new Uri.Builder();
                parameters.forEach((s, o) -> builder.appendQueryParameter(s, String.valueOf(o)));

                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuilder chain = new StringBuilder();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

            for (String line = rd.readLine(); line != null; line = rd.readLine()) {

                chain.append(line);

            }

            return chain.toString();

        } catch (IOException e) {

            error.DEBUG(api, e);
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

                JSONObject json_results = json.optJSONObject(Constants.Response.QUERY_RESULT);

                HashMap<String, ArrayList<?>> results = new HashMap<>();
                if(json_results != null) {
                    json_results.keys().forEachRemaining(t -> {

                                JSONArray current = null;
                                try {
                                    current = json_results.getJSONArray(t);

                                    switch (t) {

                                        case (Constants.Classes.USER):

                                            results.put(t, Helper.rebaseUsersFromJSON(current));
                                            break;

                                        case (Constants.Classes.GEM):

                                            results.put(t, Helper.rebaseGemsFromJSON(current));
                                            break;


                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                    );
                    Log.i("CONTENT", results.toString());
                }

                JSONObject json_availability = json.optJSONObject(Constants.Response.IS_AVAILABLE);

                int availability = Constants.Availability.NONE_AVAILABLE;
                if (json_availability != null) {
                    if (json_availability.getBoolean(Constants.Users.USERNAME) && !json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Availability.USERNAME_AVAILABLE;

                    } else if (!json_availability.getBoolean(Constants.Users.USERNAME) && json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Availability.EMAIL_AVAILABLE;

                    } else if (json_availability.getBoolean(Constants.Users.USERNAME) && json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Availability.ALL_AVAILABLE;

                    }
                }

                response = new Response(

                        json.optInt(Constants.Response.LAST_ID),
                        json.optString(Constants.Response.ERROR),
                        json.optBoolean(Constants.Response.SUCCESS),
                        json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                        results,
                        availability

                );

                Log.i("POST", json.toString());
                executor.ACCESS(response);
            }


        } catch (Exception e) {

            error.DEBUG(api, e);

        }

    }

    enum MODE {

        GET, POST
    }
}
