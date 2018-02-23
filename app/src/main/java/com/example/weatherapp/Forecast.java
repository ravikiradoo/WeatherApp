package com.example.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class Forecast extends AppCompatActivity {
   private ProgressBar progressBar;
    TextView textView;
    ArrayList<Weather> arrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String city=bundle.getString("city");
        progressBar=(ProgressBar)findViewById(R.id.progress2);
       URL url=Network.BuildUrl5DayForecast(city);
       textView=(TextView)findViewById(R.id.textView4);
        arrayList = new ArrayList<Weather>();

        recyclerView=(RecyclerView)findViewById(R.id.rv);

        new ForecastAsyncTask().execute(url);

    }

    class ForecastAsyncTask extends AsyncTask<URL,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            String Data=Network.GetData(params[0]);
            return Data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                try {

                    JSONObject Data = new JSONObject(s);

                    int N = Data.getInt("cnt");
                    for(int i=0;i<N;i++)
                    {
                        JSONArray list=Data.getJSONArray("list");
                        JSONObject data=(JSONObject)list.get(i);
                        JSONObject main=data.getJSONObject("main");

                        JSONArray weather=data.getJSONArray("weather");
                        JSONObject jsonObject=(JSONObject) weather.get(0);

                        String desc=jsonObject.getString("description");

                        String temp=main.getString("temp");

                        String  icon=jsonObject.getString("icon");

                        String time=data.getString("dt_text");


                        Weather WEATHER=new Weather(temp,"e"+icon,time,desc);

                        arrayList.add(WEATHER);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            progressBar.setVisibility(View.GONE);
        }
    }


}
