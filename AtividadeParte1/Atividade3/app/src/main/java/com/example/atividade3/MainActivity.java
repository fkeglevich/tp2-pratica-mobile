package com.example.atividade3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    EditText etX;
    EditText etY;
    EditText etZ;

    Double oldX;
    Double oldY;
    Double oldZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        etX = findViewById(R.id.etX);
        etY = findViewById(R.id.etY);
        etZ = findViewById(R.id.etZ);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double newX = sensorEvent.values[0];
        double newY = sensorEvent.values[1];
        double newZ = sensorEvent.values[2];

        if (oldX != null) {
            //Gravidade tem valor aprox de -9.81, então uma mudança de 8 é considerada uma aceleração significativa
            if ( Math.abs(oldX - newX) > 8 || Math.abs(oldY - newY) > 8 || Math.abs(oldZ - newZ) > 8  ) {
                Intent intent = new Intent(MainActivity.this, PositionActivity.class);
                startActivity(intent);
            }
        }

        etX.setText(newX + "");
        etY.setText(newY + "");
        etZ.setText(newZ + "");

        oldX = newX;
        oldY = newY;
        oldZ = newZ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}