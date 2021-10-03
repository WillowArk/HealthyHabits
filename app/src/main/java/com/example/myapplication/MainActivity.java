package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public int steps = 0;
    public TextView tv_steps;

    public SensorManager sensorManager;
    public Sensor sensor;
    boolean running;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_steps = (TextView) findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            running = true;
            System.out.println("DOOMIE");
        }
        else
        {
            tv_steps.setText("Nope.");
            running = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("BOOBIe");
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        //Sensor counterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        /*if(sensor != null)
        {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    protected void onPause() {
        System.out.println("MOOMIE");
        super.onPause();
        //if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            //sensorManager.unregisterListener(this, sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent eve) {
        System.out.println("AWFBAWAWJ");
        tv_steps.setText("OWAIWGNGAW");
        if(eve.sensor == sensor )
        {
            steps = (int) eve.values[0];
            tv_steps.setText(String.valueOf(steps));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void goToFitness(View view) {
        Intent intent = new Intent(this, FitnessTab.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToFood(View view) {
        Intent intent = new Intent(this, FoodTab.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToSleep(View view) {
        Intent intent = new Intent(this, SleepTab.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToOptions(View view) {
        Intent intent = new Intent(this, OptionsTab.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
       // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void test(View view)
    {
        System.out.println(sensor.getType());
    }
}