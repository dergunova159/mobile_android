package com.example.sensors_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView countSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list); //переменная для связи с ListView
        countSensors = findViewById(R.id.countSensors); //переменная для связи вывода количества датчиков на экран

        //очистим таблицу со списком сенсоров
        DB.deleteAllRecords(this);

        //добавим все сенсоры данного устройства в базу данных

        //получим список всех сенсоров
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //в цикле добавим эти названия в таблицу в базе данных
        String str; // вспомогательная переменная
        for (int i = 0; i < deviceSensors.size(); i++) {
            str = deviceSensors.get(i).getName(); //считываем название сенсора
            DB.addSensor(str, this); // добавим название в базу данных
        }

        //отобразим количество датчиков на экране
        countSensors.setText("Общее количество сенсоров " + deviceSensors.size());

       //создадим новый адаптер для listView
       // третий параметр - это запрос к базе данных на получение списка сенсоров
       ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, DB.getDataFromBD(this));

        //покажем список с помощью listView
        listView.setAdapter(adapter);

    }
}