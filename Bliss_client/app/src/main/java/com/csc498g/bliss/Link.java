package com.csc498g.bliss;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
}
