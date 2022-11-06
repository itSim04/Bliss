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

    // The direct Link between the APIs and the Frontend

    private final Map<String, Object> parameters; // The parameters to be sent
    private final String url; // The url to be sent
    private final PROCESS executor; // The post executor
    private final ERROR error; // The error handler
    private final String api; // The API name
    private String mode = "GET"; // The connection mode

    public Relay(@NonNull String api, @NonNull PROCESS content, @NonNull ERROR error) {

        // Constructor
        super();
        this.api = api;
        this.url = Constants.URL.buildUrl(api);
        this.executor = content;
        this.error = error;
        this.parameters = new HashMap<>();

    }

    public <T> void addParam(String key, T value) {

        // Adds a parameter
        parameters.put(key, value);

    }

    public void setConnectionMode(MODE mode1) {

        // Sets the connection mode
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

        // Sends the request
        this.execute();

    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            // Builds the URL
            StringBuilder url_string = new StringBuilder(this.url);

            // Adds URL parameters
            if (mode.equals("GET")) {

                if (!parameters.isEmpty()) {

                    url_string.append("?");

                    parameters.forEach((s, o) -> {

                        url_string.append(s);
                        url_string.append("=");
                        url_string.append(o);
                        url_string.append("&");

                    });
                    url_string.deleteCharAt(url_string.length() - 1);
                }

            }

            // Initializes the actual URL
            URL current_url = new URL(url_string.toString());

            // Opens the connection
            HttpURLConnection connection = (HttpURLConnection) current_url.openConnection();

            // Sets the connection settings
            connection.setRequestMethod(mode);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(20000);
            connection.setDoOutput(true);

            // Handles the body parameters
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

            // Sends the connection
            connection.connect();

            // Retrieves the response
            InputStream inputStream = connection.getInputStream();
            StringBuilder chain = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            for (String line = rd.readLine(); line != null; line = rd.readLine()) {

                chain.append(line);

            }

            return chain.toString();

        } catch (IOException e) {

            // Handles errors
            error.DEBUG(api, e);
            return null;

        }

    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        try {

            if (s != null) {

                //Convert our String to a JSON object
                JSONObject json = new JSONObject(s);
                Response response;

                // Retrieves the Query result
                JSONObject json_results = json.optJSONObject(Constants.Response.QUERY_RESULT);

                // Stores the results
                HashMap<String, ArrayList<?>> results = new HashMap<>();
                if (json_results != null) {
                    json_results.keys().forEachRemaining(t -> {

                                JSONArray current = null;
                                try {
                                    current = json_results.getJSONArray(t);

                                    switch (t) {

                                        case (Constants.Response.Classes.USER):

                                            results.put(t, Helper.rebaseUsersFromJSON(current));
                                            break;

                                        case (Constants.Response.Classes.GEM):

                                            results.put(t, Helper.rebaseGemsFromJSON(current));
                                            break;


                                    }


                                } catch (JSONException e) {
                                    Log.i("Error", e.getMessage());
                                }

                            }
                    );
                    Log.i("CONTENT", results.toString());
                }

                // Retrieves the availability
                JSONObject json_availability = json.optJSONObject(Constants.Response.IS_AVAILABLE);

                // Stores the availability
                int availability = Constants.Response.Availability.NONE_AVAILABLE;
                if (json_availability != null) {
                    if (json_availability.getBoolean(Constants.Users.USERNAME) && !json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Response.Availability.USERNAME_AVAILABLE;

                    } else if (!json_availability.getBoolean(Constants.Users.USERNAME) && json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Response.Availability.EMAIL_AVAILABLE;

                    } else if (json_availability.getBoolean(Constants.Users.USERNAME) && json_availability.getBoolean(Constants.Users.EMAIL)) {

                        availability = Constants.Response.Availability.ALL_AVAILABLE;

                    }
                }

                // Compactualizes the response
                response = new Response(

                        json.optInt(Constants.Response.LAST_ID),
                        json.optString(Constants.Response.ERROR),
                        json.optBoolean(Constants.Response.SUCCESS),
                        json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                        results,
                        availability

                );

                // Executes
                executor.ACCESS(response);
            }


        } catch (Exception e) {

            // Handles errors
            error.DEBUG(api, e);

        }

    }

    // Connection modes
    enum MODE {

        GET, POST
    }
}
