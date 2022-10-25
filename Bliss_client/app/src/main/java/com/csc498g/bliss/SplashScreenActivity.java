        package com.csc498g.bliss;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.WindowManager;

        import org.json.JSONObject;

        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.URL;
        import java.util.Objects;

        import javax.net.ssl.HttpsURLConnection;

public class SplashScreenActivity extends AppCompatActivity {

    //Creating a task that will run in parallel, in the background of our application
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpsURLConnection http;

            try{
                url = new URL(urls[0]);
                //Try to open a connection
                http = (HttpsURLConnection)url.openConnection();
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
                e.printStackTrace();
                return null;
            }
            return result;
        }
        protected void onPostExecute(String s){
            //Uncomment to get the String returned
            //Log.i("Gems:", s);
            super.onPostExecute(s);
            try{
                //Convert our String to a JSON object
                JSONObject json = new JSONObject(s);
                String tweet = json.getString("tweet");
                Log.i("tweet", tweet);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_splash_screen);

        String url = "http://192.168.196.1/Bliss/Bliss_server/get_all_tweets.php";
        DownloadTask Task = new DownloadTask();
        Task.execute(url);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}