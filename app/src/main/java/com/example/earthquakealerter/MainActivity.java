package com.example.earthquakealerter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("STARTING","WORKING PROPERLY");

        EarthQuakeTask task = new EarthQuakeTask();
        task.execute();

    }

    private class EarthQuakeTask extends AsyncTask<URL, Void, ArrayList<Earthquake_data_class>>{
        protected void onPreExecute(){
            Toast.makeText(MainActivity.this, "Fetching data...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Earthquake_data_class> doInBackground(URL... urls) {
            String JSONData = GetJSONStringResponse();
            ArrayList<Earthquake_data_class> earthquakes = EarthQuery.getInstance().extractEarthquakes(JSONData);
            return earthquakes;
        }

        protected void onPostExecute(ArrayList<Earthquake_data_class> earthquakes_list){
            if (earthquakes_list==null){
                return;
            }
            UpdateUI(earthquakes_list);
        }

//        protected void onProgressUpdate(){
//
//        }
    }

    private URL CreateURLObj(String url){
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlObj;
    }

    private HttpURLConnection MakeHttpConnect(URL url){
        HttpURLConnection new_connection = null;
        try {
            new_connection = (HttpURLConnection) url.openConnection();
            new_connection.setConnectTimeout(10000);
            new_connection.setReadTimeout(15000);
            new_connection.connect();
            if (new_connection.getResponseCode()!=200){
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new_connection;
    }

    public String GetJSONStringResponse(){
        HttpURLConnection connection_obj = MakeHttpConnect(CreateURLObj(USGS_URL));
        InputStream inputStream = null;
        String JsonResponse = "";
        try {
            inputStream = connection_obj.getInputStream();
            JsonResponse = GetJsonResponse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse;
    }

    private String GetJsonResponse(InputStream inputStream){
        StringBuilder responseJSON = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (line != null) {
                responseJSON.append(line);
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        Log.v("STRING BUILDER",responseJSON.toString());
        return responseJSON.toString();
    }

    private void UpdateUI(ArrayList<Earthquake_data_class> earthquake_list){
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        EarthAdapter adapter = new EarthAdapter(this,earthquake_list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}