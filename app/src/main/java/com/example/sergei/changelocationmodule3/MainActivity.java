package com.example.sergei.changelocationmodule3;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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



public class MainActivity extends AppCompatActivity {
    // private Timer mTimer;
    // private MyTimerTask mMyTimerTask;
    static ArrayList<Task> ts = new ArrayList<>();
    static LocationManager locationManager;
    static Methods methods;
    SettingsFragment settingsFragment;
    //ListFragmentListView listFragmentListView;
    LocationProvider mocLocationProvider;
    static TestLocationListenner testLocationListener = new TestLocationListenner();
    static boolean isTaskFromMap = false;
    static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        methods = new Methods();
        testLocationListener.SetUpLocationListener(this);


        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,cordList );
        //.setAdapter(adapter);
       // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //locationTaskList = (ListView) findViewById(R.id.LocationTaskList);
        //dataViewsFragment.textView3.setText("Ниже представлена форма заполнения задачи: Широта,Долгота,Ч:М");
        //String[] MockLoc = str.split(",");

       // mocLocationProvider = locationManager.getProvider(LOCATION_SERVICE);

       //setNewLoc(); !!!!!!!!!!!!!!!!!

        //Location location = new Location(mocLocationProvider);
//        Double lat = Double.valueOf(MockLoc[0]);
//        location.setLatitude(lat);
//        Double longi = Double.valueOf(MockLoc[1]);
//        location.setLongitude(longi);
//        Double alti = Double.valueOf(MockLoc[2]);
//        location.setAltitude(alti);

        showAll();


//        mTimer = new Timer();
//        mMyTimerTask = new MyTimerTask();
//        mTimer.schedule(mMyTimerTask, 0, 1000);


       // new MyRunnableTask();// !!!!!ВЫЛЕТАЕТ ПРИ ПОВОРОТЕ


    }
    void setNewLoc(){
        locationManager.removeTestProvider( LocationManager.GPS_PROVIDER);
        locationManager.addTestProvider ( LocationManager.GPS_PROVIDER, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "", "hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "", android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE );
        Location newLocation = new Location(LocationManager.GPS_PROVIDER);
        newLocation.setLatitude (55.743621);
        newLocation.setLongitude(37.681500);
        newLocation.setAccuracy(500);
        locationManager.setTestProviderEnabled ( LocationManager.GPS_PROVIDER, true );
        locationManager.setTestProviderStatus ( LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis() );
        locationManager.setTestProviderLocation ( LocationManager.GPS_PROVIDER, newLocation );
    }
    public void showAll(){
        FragmentManager in = getSupportFragmentManager();
        settingsFragment =(SettingsFragment)getFragmentManager().findFragmentById(R.id.tfragmentSettings);
        //listFragmentListView (ListFragmentListView)getFragmentManager().findFragmentById(R.id.tListFragment);

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
