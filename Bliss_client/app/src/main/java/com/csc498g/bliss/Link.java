package com.csc498g.bliss;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Link {

    //Creating a task that will run in parallel, in the background of our application
    public static class GET extends AsyncTask<String, Void, String> {

        private JSONObject response = new JSONObject();

        public JSONObject getResponse() {
            return response;
        }

        @Override
        protected String doInBackground(String... urls){

            URL url;
            URLConnection http;

            try{

                //Extracts the URL
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
                String result = "";
                while(data != -1) {

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch(Exception e) {

                Log.i("Download Task: Do In Background", e.toString());
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
                    response = json;
                }

            }catch(Exception e)
            {
                Log.i("Download Task: On Post Execute", e.toString());
            }

        }
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

    public static ArrayList<Gem> get_all_gems() {

        ArrayList<Gem> result = new ArrayList<>();
        try {

            GET get_all_gems_API_call = new GET();
            get_all_gems_API_call.execute("http://192.168.0.103/Bliss/Bliss_server/get_all_gems.php");
            JSONObject response = get_all_gems_API_call.getResponse();

            if(response.has(Constants.Response.QUERY_RESULT)) {

                JSONArray gems_json = response.getJSONArray(Constants.Response.QUERY_RESULT);

                for(int i = 0; i < gems_json.length(); i++) {

                    JSONObject current = gems_json.getJSONObject(i);

                    int gem_id = current.getInt(Constants.Gems.GEM_ID);
                    String mine_date = current.getString(Constants.Gems.MINE_DATE);
                    String edit_date = current.getString(Constants.Gems.EDIT_DATE);
                    int type = current.getInt(Constants.Gems.TYPE);
                    int owner_id = current.getInt(Constants.Gems.OWNER_ID);
                    JSONObject content = new JSONObject(current.getString(Constants.Gems.CONTENT));
                    Log.i("Content",  content.toString());

                    Gem current_gem = new TextGem(gem_id, mine_date, edit_date, owner_id, content.getString(Constants.Gems.Content.TEXT), 0, 0);
                    result.add(current_gem);

                }
            }

        } catch (JSONException e) {

            Log.i("Download Task: JSONException", e.toString());

        }
        return result;
    }


}
