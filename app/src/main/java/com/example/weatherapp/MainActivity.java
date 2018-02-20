package com.example.weatherapp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static String lat = "";
    static String lon = "";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            {
                textView.setText(s);
            }
        }
    }


}


