package com.example.sergei.changelocationmodule3;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockContentProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.location.Criteria.ACCURACY_HIGH;


public class MainActivity extends AppCompatActivity {
    static ArrayList<Task> ts = new ArrayList<>();
    static LocationManager locationManager;
    static Methods methods;
    SettingsFragment settingsFragment;
    LocationProvider mocLocationProvider;
    static TestLocationListenner testLocationListener = new TestLocationListenner();
    static boolean isTaskFromMap = false;
    static TaskAdapter taskAdapter;
    static LocationProvider locationProvider;
    static LocationProvider lm;
     String TEST_PROVIDER = LocationManager.GPS_PROVIDER;

    //static Location fakeLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        methods = new Methods();
        testLocationListener.SetUpLocationListener(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        lm= locationManager.getProvider(LocationManager.GPS_PROVIDER);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

    }

    public void showAll(){
        FragmentManager in = getSupportFragmentManager();
        settingsFragment =(SettingsFragment)getFragmentManager().findFragmentById(R.id.tfragmentSettings);

    }


    public void onClickLocationSettings(View view) {

        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    public void onClickShowMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    };

    public void addMapTask(View view) throws InterruptedException {
        isTaskFromMap=true;
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }



    @Override //сохраняем
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putStringArrayList("list",MainActivity.cordList);
       // Log.d("list", "onSaveInstanceState");
    }

    @Override //запихиваем
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //cordList= savedInstanceState.getStringArrayList("list");
        //Log.d("list", "onRestoreInstanceState");
    }

}
