package com.example.sergei.changelocationmodule3;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.location.Criteria.ACCURACY_HIGH;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Task> ts = new ArrayList<>();
    static LocationManager locationManager;
    static SettingsFragment settingsFragment;
    static boolean isTaskFromMap = false;
    static TaskAdapter taskAdapter;
    static LocationProvider locationProvider;

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    Date date;
    static Location fakeloc;

    static Intent intent;
    static PendingIntent pendingIntent;
    static AlarmManager am;

    TaskSetter taskSetter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    //readContacts();
                } else {
                    // permission denied
                }
                return;
        }
    }
    Intent createIntent(String action, String extra) {
        Intent intent = new Intent(this, TaskSetter.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ts.add(new Task(00.00000,00.00000,0,0));


        taskSetter = new TaskSetter();



       // am = (AlarmManager) getSystemService(ALARM_SERVICE);

        //intent = createIntent("action","extra");

       // pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Thread t = new Thread(new Runnable() {

                public void run () {
                    while (true) {
                        try {
                            runOnUiThread(runn);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

            }

            Runnable runn = new Runnable() {
                public void run() {
                    taskAdapter.notifyDataSetChanged();

                    SharedPreferences.Editor spEdit = getSharedPreferences("spKey",0).edit();
                    Set<String> set = new HashSet<String>();
                    for(Task t:ts){
                        set.add(t.toString());
                    }
                    spEdit.putStringSet("Tasks",set);
                    spEdit.commit();
                    //ts.notify();
                }
            };
        });



        t.start();


        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
            locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
            locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
            locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

            fakeloc = new Location(MainActivity.locationProvider.getName());
            fakeloc.setTime(System.currentTimeMillis());
            fakeloc.setAccuracy(3);
            fakeloc.setBearing(3);
            fakeloc.setElapsedRealtimeNanos(1);
            fakeloc.setSpeed(3);

            //fakeloc.setSpeedAccuracyMetersPerSecond(ACCURACY_HIGH);
            //fakeloc.setBearingAccuracyDegrees(ACCURACY_HIGH);
            //fakeloc.setVerticalAccuracyMeters(ACCURACY_HIGH);

    }

    public void loc(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            //  here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            // int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)), Toast.LENGTH_SHORT);
        toast.show();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor spEdit = getSharedPreferences("spKey",0).edit();
        Set<String> set = new HashSet<String>();
        for(Task t:ts){
            set.add(t.toString());
        }
        spEdit.putStringSet("Tasks",set);
        spEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("spKey",0);
        Set<String> set = sp.getStringSet("Tasks", null);
        ts.clear();
        if(set!=null) {
            for (String s : set) {
                ts.add(new Task(s));
            }
        }
        Collections.sort(ts, Task.SORTBYECONDS);
        if(!ts.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            date=calendar.getTime();
            taskSetter = new TaskSetter();
            taskSetter.setTask(getApplicationContext());



            //am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pendingIntent);

//            Log.d("myLogs", "triggerAtMillis + "+Long.toString( (ts.get(0).toSeconds() - date.getHours()*3600-date.getMinutes()*60)*1000));
//
//            am.set(AlarmManager.RTC, System.currentTimeMillis() + (ts.get(0).toSeconds() - date.getHours()*3600-date.getMinutes()*60)*1000, pendingIntent);
//            am.cancel(pendingIntent);
//            am.set(AlarmManager.RTC, System.currentTimeMillis() + (ts.get(0).toSeconds() - date.getHours()*3600-date.getMinutes()*60)*1000, pendingIntent);
//            Toast.makeText(getApplicationContext(), "\n 1 задача сработает через " + (ts.get(0).toSeconds() - date.getHours()*3600-date.getMinutes()*60) + "с ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor spEdit = getSharedPreferences("spKey",0).edit();
        Set<String> set = new HashSet<String>();
        for(Task t:ts){
            set.add(t.toString());
        }
        spEdit.putStringSet("Tasks",set);
        spEdit.commit();
        Log.d("help","onpause main" + set.toString());
    }
    public Intent getIntent(){
        return this.getParentActivityIntent();
    }
//    public static void saver(){
//
//        SharedPreferences.Editor spEdit = getSharedPreferences("spKey",0).edit();
//        Set<String> set = new HashSet<String>();
//        for(Task t:ts){
//            set.add(t.toString());
//        }
//        spEdit.putStringSet("Tasks",set);
//        spEdit.commit();
//    }

}
