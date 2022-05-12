package com.example.sensors_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    ListView sensorValueList;
    TextView sensorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        getSupportActionBar().hide();

        sensorValueList = findViewById(R.id.sensorValueList);
        sensorName = findViewById(R.id.sensorName);

       sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
       int type = Integer.parseInt(getIntent().getStringExtra("typeSensor"));
       if (sensorManager != null)
           sensor = sensorManager.getDefaultSensor(type);

       List<String> list = new ArrayList<>();
       ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
       sensorValueList.setAdapter(adapter);

       sensorEventListener = new SensorEventListener() {
           @Override
           public void onSensorChanged(SensorEvent sensorEvent) {
               list.clear();
               float[] nums = sensorEvent.values;
               String[] a=Arrays.toString(nums).split("[\\[\\]]")[1].split(", ");
               list.addAll(Arrays.asList(a));
               adapter.notifyDataSetChanged();

               sensorName.setText(sensor.getName());
           }

           @Override
           public void onAccuracyChanged(Sensor sensor, int i) {

           }
       };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}