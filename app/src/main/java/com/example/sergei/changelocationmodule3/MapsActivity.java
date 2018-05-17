package com.example.sergei.changelocationmodule3;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.math3.util.MathArrays;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
    int DIALOG_TIME = 1;
    int myHour ;
    int myMinute ;
    static GoogleMap mMap;
    Date lTime;
    static LatLng item;

    ArrayList<Task> mapts= new ArrayList<>();

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
        AddAllMarkers();
        showLocButton();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (MainActivity.isTaskFromMap && lTime != null) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title("\n Время задачи:" + lTime.getHours() + ":" + lTime.getMinutes()));
                    mapts.add(new Task(latLng.latitude, latLng.longitude, lTime.getHours(), lTime.getMinutes()));
                    MainActivity.isTaskFromMap = false;
                    Collections.sort(mapts, Task.SORTBYECONDS);
                    closeActivity();

                }
                else if (lTime == null) {

                    closeActivity();
                    Toast.makeText(getApplicationContext(), "\n Время не выбрано или меньше текущего", Toast.LENGTH_SHORT).show();
                }

            }

            private void closeActivity() {
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
            }


        });}

    public void AddAllMarkers() {
        for (Task t : mapts) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLat(), t.getLng())).title("Время задачи: " + t.getHours() + ":" + t.getMinutes()));
        }
    }

    public void addCurrMarker(Location location){

        mMap.addMarker(new MarkerOptions().position(new LatLng( location.getLatitude(),location.getLongitude())).title("You are here").alpha(0.5f));
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
    }


    public static void showItemOnMap(ListView l, int position) {
        item = new LatLng(Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[0]), Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[1]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(item));

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = getSharedPreferences("spKey",0);
         Set<String> set = sp.getStringSet("Tasks", null);
         mapts.clear();
         if(set!=null) {
             for (String s : set) {
                 mapts.add(new Task(s));
             }
         }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //gson.toJson(mapts);
        SharedPreferences.Editor spEdit = getSharedPreferences("spKey",0).edit();
        Set<String> set = new HashSet<String>();

        for(Task t:mapts){
            set.add(t.toString());
        }
        spEdit.putStringSet("Tasks",set);
        spEdit.commit();
        Log.d("help","onpause maps" + set.toString());
    }
}
