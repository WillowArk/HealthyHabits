package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public int steps;
    public TextView tv_steps;

    public SensorManager sensorManager;
    public Sensor sensor;
    boolean running;
    boolean up;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = 0;
        setContentView(R.layout.activity_main);
        //tv_steps = (TextView) findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            running = true;
            System.out.println("DOOMIE" + sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER).indexOf(1));
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
        //System.out.println(eve.values[1]);
        if(eve.values[1] > 1.5 && up)
        {
            up = false;
            FitnessTab.steps++;
            if(FitnessTab.tracked != null)
                FitnessTab.tracked.setText(FitnessTab.steps + " Steps!");
            //tv_steps.setText(String.valueOf(steps));
        }
        else if (eve.values[1] < -1.5 && !up)
        {
            up = true;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void goToFitness(View view) {
        Intent intent = new Intent(this, FitnessTab.class);
        String message = String.valueOf(steps);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void smallPopUp(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
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