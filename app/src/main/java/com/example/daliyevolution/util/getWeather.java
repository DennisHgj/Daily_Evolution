package com.example.daliyevolution.util;

import android.content.Context;

import com.example.daliyevolution.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * getWeather.java
 * request info from internet and form a json data structure
 * @author Lingyu Xia
 *
 * @ID u6483756
 */

public class getWeather {
    // initialize the common url
    private static final String OPEN_WEATHER_MAP_API = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static JSONObject getJSON(Context context, String city){
        try {
            // form the url with the parameter city, append client key to have access to the website
            String url_str = OPEN_WEATHER_MAP_API + city + "&units=metric&APPID=" +context.getString(R.string.open_weather_maps_app_id);
            URL url = new URL(url_str);
            // open the connection
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // get the info as inputstream and form a json object
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            return data;

        }catch(Exception e){
            return null;
        }
    }
}
