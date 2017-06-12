package com.example.tbessho.weatherapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tbessho on 6/9/2017.
 */
//http://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b18a535959e762f33271e3dc7978fad3

public class DownloadTask extends AsyncTask <String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        //in case user does not have internet connection
        try {
            url = new URL (urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            //get intput stream from internet connection
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read(); //what is being read in is saved as an int data

            while( data != -1){ //when finished, data will read -1
                char current = (char) data;
                result += current;
                data = reader.read(); //exit statement
            }

            //return the result
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //result looks like copied string
        //convert to JSON to be able to parse through
        //try catch expetion in case person does not have internet connection

        try {
            JSONObject jsonObject = new JSONObject(result);

            String weatherInfo = jsonObject.getString("weather");

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));
            double tempInt = Double.parseDouble(weatherData.getString("temp"));
            int tempIn = (int) (tempInt * 1.8 - 459.67);

            String placeName = jsonObject.getString("name");

            JSONArray weatherStuff = new JSONArray(weatherInfo);

            JSONObject jsonPart = (JSONObject) weatherStuff.get(0); //take from array
            String condition = jsonPart.getString("main"); //next step on hierarchy, take from string stuff

            //Make sure to code in set Text for temp here
            MainActivity.temp.setText("Current Temperature: " + String.valueOf(tempIn) + "F");
            MainActivity.place.setText(placeName);
            MainActivity.weather.setText(condition);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
