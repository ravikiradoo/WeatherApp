package com.example.weatherapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static String lat = "";
    static String lon = "";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textView;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather World");



        textView=(TextView)findViewById(R.id.weather);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lat=Double.toString(location.getLatitude());
                lon=Double.toString(location.getLongitude());

                textView.setText(lat+" "+lon);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                textView.setText(provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET},1);
            }

            else
            {
                locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                if(location!=null)
                {
                    locationListener.onLocationChanged(location);
                }

            }
        }
        else
        {
            locationManager.requestLocationUpdates("gps", 0, 0, locationListener);

            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            if(location!=null)
            {
                locationListener.onLocationChanged(location);
            }
        }

        URL url = Network.BuildUrl(lat,lon);
        Toast.makeText(this,url.toString(),Toast.LENGTH_LONG).show();
        new GetDataAsyncTask().execute(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                URL url = Network.BuildUrlWithCity(query);
                new GetDataAsyncTask().execute(url);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                }
        }
    }

    class GetDataAsyncTask extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... params) {

            String jsonString=Network.GetData(params[0]);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {textView.setText(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray weatherArray=jsonObject.getJSONArray("weather");

                    JSONObject weather=(JSONObject)weatherArray.get(0);
                    String main=weather.getString("main");
                    String desc=weather.getString("description");
                    LinearLayout linearLayout=(LinearLayout)findViewById(R.id.firstLayout);
                    if(main.equals("Clear"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i01));
                    }
                    if(main.equals("Clouds"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i03));
                    }
                    if(main.equals("Haze"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i04));
                    }
                    if(main.equals("Drizzle"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i05));
                    }
                    if(main.equals("Thunderstorm"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i06));
                    }

                    if(main.equals("Rain"))
                    {
                        linearLayout.setBackground(getDrawable(R.drawable.i09));
                    }

                    if(main.equals("Snow"))
                    {linearLayout.setBackground(getDrawable(R.drawable.i010));

                    }

                    JSONObject mainJson=jsonObject.getJSONObject("main");
                    String temp=mainJson.getString("temp");
                    String pressure=mainJson.getString("pressure");
                    String humidity=mainJson.getString("humidity");
                    Double tempmin=Double.parseDouble(mainJson.getString("temp_min"))-273;
                    Double tempmax=Double.parseDouble(mainJson.getString("temp_max"))-273;
                    TextView textView = (TextView)findViewById(R.id.temp);

                    Double Ctemp=Double.parseDouble(temp);
                    Ctemp=Ctemp-273;

                    textView.setText(""+Ctemp+"Celsius");

                    textView=(TextView)findViewById(R.id.mintemp);
                    textView.setText(""+tempmin+"Celsius");

                    textView=(TextView)findViewById(R.id.maxtemp);
                    textView.setText(""+tempmax+"Celsius");



                    textView= (TextView)findViewById(R.id.pressure);
                    textView.setText(pressure+"hPa");
                    textView=(TextView)findViewById(R.id.humidity);
                    textView.setText(humidity+"%");
                    textView=(TextView)findViewById(R.id.desc);
                    textView.setText("Weather status "+Capword(desc));


                    JSONObject sysJson=jsonObject.getJSONObject("sys");
                    long sunrise=Long.parseLong(sysJson.getString("sunrise"));
                    long sunset=Long.parseLong(sysJson.getString("sunset"));
                    String city=jsonObject.getString("name");
                    String country=sysJson.getString("country");
                    textView=(TextView)findViewById(R.id.city);
                    textView.setText(Capword(city)+","+Capword(country));



                    java.util.Date dateTime=new java.util.Date((sunrise*1000));
                    textView =(TextView)findViewById(R.id.sunrrise);
                    textView.setText(dateTime.toString());

                    dateTime=new java.util.Date((sunset*1000));
                    textView=(TextView)findViewById(R.id.sunset);
                    textView.setText(dateTime.toString());

                    JSONObject wind = jsonObject.getJSONObject("wind");
                    String windspeed=wind.getString("speed");

                    textView=(TextView)findViewById(R.id.windspeed);
                    textView.setText(windspeed);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String Capword(String string)
    {
        string=string.substring(0,1).toUpperCase()+string.substring(1);
        return string;
    }


}


