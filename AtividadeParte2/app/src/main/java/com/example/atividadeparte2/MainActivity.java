package com.example.atividadeparte2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Button getGPSBtn;
    private SensorManager sensorManager;
    private Sensor light;
    private Sensor gyroscope;
    private Sensor magneticField;
    TextView lightValue;
    TextView gyroValue;
    TextView magValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightValue = findViewById(R.id.light);
        gyroValue = findViewById(R.id.gyro);
        magValue = findViewById(R.id.magnetic);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light != null)
        {
            sensorManager.registerListener(MainActivity.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            lightValue.setText("Light sensor not supported");
        }

        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null)
        {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            gyroValue.setText("Gyroscope sensor not supported");
        }

        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(magneticField != null)
        {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            magValue.setText("Magnetic field sensor not supported");
        }

        getGPSBtn = findViewById(R.id.getGPSBtn);
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        getGPSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if(l != null)
                {
                    double lat = l.getLatitude();
                    double longi = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LAT: "+ lat + " LONG: " +
                            longi, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lightValue.setText("Light Intensity: " + event.values[0]);
        }

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            gyroValue.setText("Gyroscope value\n\nX: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
        }

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magValue.setText("Magnetic field value\n\nX: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}