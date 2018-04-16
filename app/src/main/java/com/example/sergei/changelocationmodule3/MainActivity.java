package com.example.sergei.changelocationmodule3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.location.Criteria.ACCURACY_HIGH;


public class MainActivity extends AppCompatActivity {
    static ArrayList<Task> ts = new ArrayList<>();
    static LocationManager locationManager;
    static SettingsFragment settingsFragment;
    // static TestLocationListenner testLocationListener = new TestLocationListenner();
    static boolean isTaskFromMap = false;
    static TaskAdapter taskAdapter;
    static LocationProvider locationProvider;
    static LocationProvider lm;
    static int CurrTaskID;
    static MyLocationListener myLocationListener;
    static Timer timer, timer2;
    static TimeThread timeThread;
    static boolean whatTimer=true; //true-1 false-2
    Calendar calendar;
    Date date;
    Location fakeloc;
    static boolean isTimerStart;
    static boolean firstStart=true;

    static boolean timer1Enabled;
    static boolean timer2Enabled;


    @Override
    protected void onStart() {
        super.onStart();

        //ts = new ArrayList<>();

//        SharedPreferences prefs = getSharedPreferences("TaskList", Context.MODE_PRIVATE);
//        try {
//            ts = (ArrayList) ObjectSerializer.deserialize(prefs.getString("TaskList", ObjectSerializer.serialize(new ArrayList())));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //new MyRunnableTask();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeThread = new TimeThread();
        timer = new Timer();
        timer2 = new Timer();
        //timer.schedule(timeThread, 1, 1000);

        //ts = new ArrayList<>();
        //calendar.getT

        //locationManager.removeUpdates(MainActivity.this,PendingIntent.getActivities(getApplicationContext(),0,null,PendingIntent.FLAG_CANCEL_CURRENT,));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        //lm= locationManager.getProvider(LocationManager.GPS_PROVIDER);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        fakeloc = new Location(MainActivity.locationProvider.getName());
        fakeloc.setTime(System.currentTimeMillis());
        fakeloc.setAccuracy(ACCURACY_HIGH);
        fakeloc.setBearingAccuracyDegrees(ACCURACY_HIGH);
        fakeloc.setSpeedAccuracyMetersPerSecond(ACCURACY_HIGH);
        fakeloc.setVerticalAccuracyMeters(ACCURACY_HIGH);
        fakeloc.setElapsedRealtimeNanos(1);



    }
    public class TimeThread extends TimerTask {
        @Override
        public boolean cancel() {

            if(whatTimer) {
                //Если в потоке не поставилась новая дата то делаем это здесь

                if(ts.get(0)!=null) {
                   // whatTimer=!whatTimer;
                    Log.d("TRD", "\n (Timer1.cancel())Готовлю поток2 на " + date.getHours() + ":" + date.getMinutes());
                    timer2.schedule(MainActivity.timeThread, date, 100000);//период проверить!!!!!!!!!!
                    whatTimer=!whatTimer;
                }
            }
            else if(!whatTimer){
                //Если в потоке не поставилась новая дата то делаем это здесь

                if(ts.get(0)!=null) {
                    //whatTimer=!whatTimer;
                    Log.d("TRD", "\n (Timer2.cancel())Готовлю поток1 на " + date.getHours() + ":" + date.getMinutes());
                    timer.schedule(MainActivity.timeThread, date, 100000);//период проверить!!!!!!!!!! проверить максимальный период
                    whatTimer=!whatTimer;
                }
            }
                return super.cancel();


        }

        @Override
        public void run() {
            if(ts.get(0)!=null) {
                if(whatTimer) {
                    Log.d("TRD", "\n Работает первый поток");
                }
                else if(!whatTimer){
                    Log.d("TRD", "\n Работатет второй поток" );
                }
                //можно убрать
                calendar = Calendar.getInstance();
                date = calendar.getTime();
                date.setHours(ts.get(0).getHours());
                date.setMinutes(ts.get(0).getMinutes());
                date.setSeconds(0);


                fakeloc.setLatitude(ts.get(0).getLat());
                fakeloc.setLongitude(ts.get(0).getLng());
                Log.d("TRD", "\n (Timer.run()Геоданные в fakeloc установлены" + " lat:" + ts.get(0).getLat() + " lng:" + ts.get(0).getLng() + " h:" + ts.get(0).getHours() + " m:" + ts.get(0).getMinutes());
                Log.d("TRD", "\n (Timer.run())removing: " + ts.get(0));
                ts.remove(0);
                Log.d("TRD", "\n (Timer.run())Готовлю запуск runOnUIThread");

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TRD", "\n runOnUIThread работает");
                        taskAdapter.notifyDataSetChanged();
                        Log.d("TRD", "\n (Timer.runOnUIThread())Адаптер уведомлен");
                    }
                });
                // runOnUIThread не срабатывает в первый раз
                locationManager.clearTestProviderLocation(fakeloc.getProvider());
                locationManager.setTestProviderLocation(fakeloc.getProvider(), fakeloc);
                Log.d("TRD", "\n (Timer.run())Данные провайдера изменены");

                Log.d("TRD", "\nЛист:");
                for (Task t : ts) {
                    Log.d("TRD", "\n"+t.toString());
                }
            }
            else{
                //пробую все остановить
                if(whatTimer) {
                    timer.cancel();
                }
                else if(!whatTimer){
                    timer2.cancel();
                }
                Log.d("TRD", "\n (Timer.run())Чето не так");
            }

            if(whatTimer) {
                Log.d("TRD", "\n (Timer1.run())Отменяю поток1 в cancel()");
            }
            else if(!whatTimer){
                Log.d("TRD", "\n (Timer.run())Отменяю поток2 в cancel()");
            }
            this.cancel();


        }
    }


    public void loc(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                String.valueOf(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)), Toast.LENGTH_SHORT);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SharedPreferences prefs = getSharedPreferences("TaskList", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        try {
//            editor.putString("TaskList", ObjectSerializer.serialize(ts));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        editor.commit();

    }
    public Intent getIntent(){
        return this.getParentActivityIntent();
    }
}
