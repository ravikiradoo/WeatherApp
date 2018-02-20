package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static String lat="";
    static String lon="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetDataAsyncTask().execute(lat,lon);
    }


}
class GetDataAsyncTask extends AsyncTask<String,Void,URL>
{

    @Override
    protected URL doInBackground(String... params) {
        return null;
    }
}
