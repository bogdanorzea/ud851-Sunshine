package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {

    //  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public static synchronized void syncWeather(Context context) {
        // COMPLETED (3) Within syncWeather, fetch new weather data

        try {
            // Get the URL for the forecast JSON
            URL requestUrl = NetworkUtils.getUrl(context);

            // Get the response JSON from the request URL
            String responseString = NetworkUtils.getResponseFromHttpUrl(requestUrl);

            // Parse the JSON into an array of content values
            ContentValues[] contentValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, responseString);

            // COMPLETED (4) If we have valid results, delete the old data and insert the new
            if (contentValues != null && contentValues.length > 0) {

                context.getContentResolver().delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                context.getContentResolver().bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
