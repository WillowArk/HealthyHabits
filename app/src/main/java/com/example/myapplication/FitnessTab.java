package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import androidx.appcompat.app.AppCompatActivity;

public class FitnessTab extends AppCompatActivity {

    int GLOBALPASS = 0;

    final static int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_tab);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        try {
            int minutes = gymTime(REQUEST_LOCATION);
            System.out.println(minutes);


        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public int gymTime(int REQUEST_LOCATION) throws InterruptedException, IOException {
        LocationManager locationManager;
        OkHttpClient client = new OkHttpClient();

        TextView mTextView;
        double currlat;
        double currlongi;
        String currLatitude = null;
        String currLongitude = null;
        Request request = null;
        long startTime = 0, endTime;
        double time = 0;
        int size = 0;
        boolean oneTime = true;

        do {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS();
            } else {
                if (ActivityCompat.checkSelfPermission(
                        FitnessTab.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        FitnessTab.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        currlat = locationGPS.getLatitude();
                        currlongi = locationGPS.getLongitude();
                        currLatitude = String.valueOf(currlat);
                        currLongitude = String.valueOf(currlongi);

                    } else {
                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            // Capture the layout's TextView and set the string as its text
            //TextView textView = findViewById(R.id.textView);
            //textView.setText(message);
            //TimeUnit.MINUTES.sleep(1);


            String urL = "https://maps.googleapis.com/maps/api/place/search/json?location=" + currLatitude + "," + currLongitude + "&radius=81&types=gym&sensor=false&key=AIzaSyDcaxR3hFrF0yiB9nsYk3XyOR_p4DRrOEU";
            request = new Request.Builder().url(urL).build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        GLOBALPASS= responseBody.string().length();

                    }
                }
            });

        if(oneTime) {
            startTime = System.currentTimeMillis();
            oneTime = false;
        }

        }while(GLOBALPASS > 81);

        endTime = System.currentTimeMillis();

        time = endTime - startTime;
        time /= 1000;
        time /= 3600;

        if(time < 30)
            return 0;

        return (int) time;
    }


    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}