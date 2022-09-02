package com.example.earthquakealerter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.text.DecimalFormat;

public class EarthQuery {
    public static EarthQuery instance;

    private EarthQuery(){
    }

//    ArrayList<Earthquake_data_class> earthquakes = new ArrayList<>();

    public static EarthQuery getInstance() {
        return instance;
    }

    public static ArrayList<Earthquake_data_class> extractEarthquakes(String JSONData) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake_data_class> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject root = new JSONObject(JSONData);
            JSONArray features = root.getJSONArray("features");
//            Log.v("FIRST", String.valueOf(features));
            for (int i=0; i<features.length(); i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");

                Long milliseconds = properties.getLong("time");
                Date dateObj = new Date(milliseconds);

                SimpleDateFormat dateformatter = new SimpleDateFormat("d MMM yyyy\nHH:mm:ss");
                String date = dateformatter.format(dateObj);
                double mag = properties.getDouble("mag");

                earthquakes.add(new Earthquake_data_class(mag,properties.getString("place"),date));
            }

        } catch (org.json.JSONException exception) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results",exception);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}