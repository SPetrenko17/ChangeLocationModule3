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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
    int DIALOG_TIME = 1;
    int myHour ;
    int myMinute ;
    static GoogleMap mMap;
    static TimePickerDialog.OnTimeSetListener t;
    static TimePickerDialog timePickerDialog;
    String locTime=" ";
    LatLng item;
    //Calendar time=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (MainActivity.isTaskFromMap) {
            showDialog(DIALOG_TIME);
        }

    }
    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            locTime=myHour+":"+myMinute;
            Log.d("TIME", "Time is " + myHour + " hours " + myMinute + " minutes");
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
                if(MainActivity.isTaskFromMap) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Время задачи:" + locTime));
                    MainActivity.ts.add(new Task(latLng.latitude,latLng.longitude,Integer.parseInt(locTime.split(":")[0]),Integer.parseInt(locTime.split(":")[1])));
                    MainActivity.isTaskFromMap=false;
                }

            }
        });

    }


    public void AddAllMarkers(ArrayList<Task> ts){
        for(Task t:ts){
            mMap.addMarker(new MarkerOptions().position(new LatLng(t.getLat(),t.getLng())).title("Время задачи: "+t.getHours()+t.getMinutes()));
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
            Log.d("SORT",t.toString());
        }
        MainActivity.adapter.notifyDataSetChanged();
        super.onDestroy();
    }
}
