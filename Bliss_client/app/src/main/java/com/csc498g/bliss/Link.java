package com.csc498g.bliss;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Link {

    public static Drawable GetImage(String url) {
        try {
            InputStream stream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(stream, "Image");
            return drawable;
        } catch (Exception e) {
            Log.i("GetImage", e.getLocalizedMessage());
            return null;
        }
    }

    public static void get_all_gems() {

        ArrayList<Gem> result = new ArrayList<>();

        GET get_all_gems_API_call = new GET(

                new GET_PROCESS() {

                    @Override
                    public void GET(Response response) {
                        JSONArray gems_json = response.getQuery_result();
                        ArrayList<Gem> result = Helper.rebaseGemFromJSON(gems_json);
                        Temp.TEMP_GEMS.addAll(result);
                    }
                });

        get_all_gems_API_call.execute(Constants.URL.buildUrl(Constants.APIs.GET_ALL_GEMS));

    }

    //Creating a task that will run in parallel, in the background of our application
    public static class GET extends AsyncTask<String, Void, String> {

        private static GET_PROCESS executor;

        public GET(GET_PROCESS content) {

            super();
            this.executor = content;

        }

        @Override
        protected String doInBackground(String... urls) {


            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder chaine = new StringBuilder("");

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                for (String line = rd.readLine(); line != null; line = rd.readLine()) {
                    chaine.append(line);
                }
                return chaine.toString();
            } catch (IOException e) {
                // Writing exception to log
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
                    Response response = new Response(

                            json.optString(Constants.Response.ERROR),
                            json.optBoolean(Constants.Response.SUCCESS),
                            json.optBoolean(Constants.Response.IS_AUTHENTICATED),
                            json.optJSONArray(Constants.Response.QUERY_RESULT),
                            json.optBoolean(Constants.Response.IS_AVAILABLE)

                    );
                    executor.GET(response);
                }


            } catch (Exception e) {

                Log.i("Download Task: On Post Execute", e.toString());

            }

        }
    }
}
