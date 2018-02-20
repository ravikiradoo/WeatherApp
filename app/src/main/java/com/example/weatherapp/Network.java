package com.example.weatherapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.System.in;

/**
 * Created by CUBASTION on 20-02-2018.
 */

public class Network {

    private static String url="api.openweathermap.org/data/2.5/weather";

    private static String key="77151234dc6d802759350b27de1f33db";

    public String BuildUrl(String lat,String lon)
    {
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("lat",lat)
                .appendQueryParameter("lon",lon)
                .appendQueryParameter("cnt","13")
                .appendQueryParameter("APPID",key).build();

        try {
            URL url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String GetData(URL url)
    {
        String result="";
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);

            scanner.useDelimiter("\\A");

            if(scanner.hasNext())
            {
                result=scanner.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
