package com.example.weatherapp;

import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static java.lang.System.in;

/**
 * Created by CUBASTION on 20-02-2018.
 */

public class Network {

    private static String url="http://api.openweathermap.org/data/2.5/weather";
    private static String url_5_day="http://api.openweathermap.org/data/2.5/forecast";


    private static String key="77151234dc6d802759350b27de1f33db";

    public  static URL BuildUrl(String lat,String lon)
    {
        URL Url=null;
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("lat",lat)
                .appendQueryParameter("lon",lon)
                .appendQueryParameter("cnt","13")
                .appendQueryParameter("APPID",key).build();

        try {
             Url=new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
       System.out.print(Url);
        return Url;
    }
    public  static URL BuildUrlWithCity(String city)
    {
        URL Url=null;
        Uri uri = Uri.parse(url).buildUpon()

                .appendQueryParameter("q",city)
                .appendQueryParameter("cnt","7")
                .appendQueryParameter("APPID",key).build();

        try {
            Url=new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.print(Url);
        return Url;
    }

    public  static URL BuildUrl5DayForecast(String city)
    {
        URL Url=null;
        Uri uri = Uri.parse(url_5_day).buildUpon()

                .appendQueryParameter("id",city)

                .appendQueryParameter("APPID",key).build();

        try {
            Url=new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.print(Url);
        return Url;
    }



    public static String GetData(URL url)
    {
        String result="";
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result=result+line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
