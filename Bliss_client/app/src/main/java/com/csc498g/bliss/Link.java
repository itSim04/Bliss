package com.csc498g.bliss;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Link {

    //Creating a task that will run in parallel, in the background of our application
    public static class GET extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            URLConnection http;

            try{
                url = new URL(urls[0]);
                //Try to open a connection
                http = url.openConnection();
                //Read the output of the API with a reader object
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                //Cursor that reads the data
                int data = reader.read();
                /*Since the reader is returning an ASCII code
                we need to convert to char*/
                while(data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            }catch(Exception e)
            {
                Log.i("Download Task: Do In Background", e.toString());
                return null;
            }
            return result;
        }
        
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
        
        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
            try{
                //Convert our String to a JSON object
                if(s != null) {
                    JSONObject json = new JSONObject(s);
                }

            }catch(Exception e)
            {
                Log.i("Download Task: On Post Execute", e.toString());
            }

        }
    }

    public static ArrayList<Gem> get_all_tweets() {

        try {

            GET retrieve = new GET();
            retrieve.execute("http://192.168.0.103/Bliss/Bliss_server/get_all_tweets.php");
            JSONObject response = new JSONObject(retrieve.get());
            JSONArray tweets_json = response.getJSONArray("result");
            Log.i("Get All Tweets", tweets_json.toString());
            for(int i = 0; i < tweets_json.length(); i++) {

                JSONObject current = tweets_json.getJSONObject(i);
                int tweet_id = current.getInt("tweet_id");
                String tweet_date = current.getString("tweet_date");
                String edit_date = current.getString("edit_date");
                int type = current.getInt("type");
                int owner_id = current.getInt("owner_id");
                // Gem current_tweet = new Gem(tweet_id, tweet_date, edit_date, type, owner_id)

            }

        } catch (ExecutionException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return null;
    }
}
