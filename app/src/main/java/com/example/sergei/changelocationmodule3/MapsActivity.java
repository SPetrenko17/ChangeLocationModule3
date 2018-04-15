package com.example.sergei.changelocationmodule3;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
    int DIALOG_TIME = 1;
    int myHour ;
    int myMinute ;
    String h,m;
    static GoogleMap mMap;
    static TimePickerDialog.OnTimeSetListener t;
    static TimePickerDialog timePickerDialog;
    String locTime;
    Date lTime;
    LatLng item;
    //Calendar time=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lTime = new Date();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (MainActivity.isTaskFromMap) {
            showDialog(DIALOG_TIME);
        }

    }
    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            lTime = calendar.getTime();
            if(hourOfDay>lTime.getHours()){
               lTime.setHours(hourOfDay);
               lTime.setMinutes(minute);
            }
            else if(hourOfDay==lTime.getHours()&&minute>lTime.getMinutes()){
                lTime.setHours(hourOfDay);
                lTime.setMinutes(minute);

            }
            else {
                lTime = null;
            }
            if(lTime!=null){
                Log.d("TIME", "\n  Time is " + lTime.getTime() + " hours " + lTime.getMinutes() + " minutes");
            }
            else{
                Log.d("TIME", "\n Time=null or time<current time");
            }


        }
    };
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
        }

        return super.onCreateDialog(id);
    }

    private void showLocButton() {
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
        mMap.setMyLocationEnabled(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        AddAllMarkers(MainActivity.ts);
        showLocButton();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
               // executorService.scheduleAtFixedRate(MainActivity.timeThread,0,1000, TimeUnit.MILLISECONDS);
                //MainActivity.timer.cancel();

                if(MainActivity.isTaskFromMap&&lTime!=null) {
//                    if(MainActivity.timeBool) {
//                        MainActivity.timer.cancel();
//                        MainActivity.timeBool=false;
//                    }

                    mMap.addMarker(new MarkerOptions().position(latLng).title("\n Время задачи:" + lTime.getHours()+":"+lTime.getMinutes()));
                    MainActivity.ts.add(new Task(latLng.latitude,latLng.longitude,lTime.getHours(),lTime.getMinutes()));
                    MainActivity.isTaskFromMap=false;
                    Collections.sort(MainActivity.ts,Task.SORTBYECONDS);
                    if(!MainActivity.isTimerStart) {
                        Date date = Calendar.getInstance().getTime();
                        date.setHours(MainActivity.ts.get(0).getHours());
                        date.setMinutes(MainActivity.ts.get(0).getMinutes());
                        date.setSeconds(0);
                        Log.d("TRD", "\n Готовлю поток на " + date.getHours() + ":" + date.getMinutes());
                        //Если в потоке не поставилась новая дата то делаем это здесь
                        MainActivity.timer.schedule(MainActivity.timeThread, date, 1000);//период проверить!!!!!!!!!!
                    }
                    closeActivity();
                }
                else if(lTime==null){

                    closeActivity();
                    Toast.makeText(getApplicationContext(),"\n Время не выбрано или меньше текущего",Toast.LENGTH_SHORT).show();
                }

               // MainActivity.timer.schedule(MainActivity.timeThread,0,1000);

            }
        });

    }
    private void closeActivity() {
        startActivity(new Intent(MapsActivity.this, MainActivity.class));
        this.finish();
    }

    public void AddAllMarkers(ArrayList<Task> ts){
        for(Task t:ts){
            mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLat(),t.getLng())).title("Время задачи: "+lTime.getHours()+":"+lTime.getMinutes()));
        }
    }


    public void showItemOnMap(ListView l, int position){
        item = new LatLng(Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[0])  ,  Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[1]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(item));

    }

    @Override
    protected void onDestroy() {
        Collections.sort(MainActivity.ts,Task.SORTBYECONDS);
        for(Task t:MainActivity.ts){
            Log.d("SORT","\n"+t.toString());
        }

        MainActivity.taskAdapter.notifyDataSetChanged();
        super.onDestroy();
    }
}
