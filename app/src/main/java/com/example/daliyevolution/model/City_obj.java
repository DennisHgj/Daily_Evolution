package com.example.daliyevolution.model;

import android.app.Activity;
import android.content.SharedPreferences;

/***
 * city_obj
 * give a default city to form the url and request weather info when start the app
 * receive a input city to form a new url and request weather info
 * the info is and will not be stored in the database
 * @author Lingyu Xia
 *
 * @ID u6483756
 */
public class City_obj {
    SharedPreferences prefs;

    public City_obj(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // give a default city (Canberra) when first start the app
    public String getCity(){
        return prefs.getString("city", "Canberra");
    }

    public void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}
