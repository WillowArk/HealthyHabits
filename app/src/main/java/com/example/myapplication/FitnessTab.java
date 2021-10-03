package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FitnessTab extends AppCompatActivity {

    public static int steps = 1712;
    public static TextView tracked = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_tab);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        tracked = findViewById(R.id.stepstracked);
        tracked.setText(String.valueOf(steps) + " Steps!");

    }


}