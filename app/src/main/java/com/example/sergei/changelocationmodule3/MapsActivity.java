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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
    int DIALOG_TIME = 1;
    int myHour ;
    int myMinute ;

    public static GoogleMap mMap;
    public GoogleMap gm;
    public LatLng item;

    static TimePickerDialog.OnTimeSetListener t;
    static TimePickerDialog timePickerDialog;
    Calendar time=Calendar.getInstance();
//    private Calendar time=Calendar.getInstance();
    public String locTime=" ";
    //Boolean timesetted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        showDialog(DIALOG_TIME);

        /*
         t=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);
                //timesetted=true;

            }


        };
        try {
            setTime(t);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        */





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
    public void setTime(TimePickerDialog.OnTimeSetListener t) throws InterruptedException {
        timePickerDialog=  new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
        timePickerDialog.show();//Проблема в том, что я не знаю как уобработать кнопку ок на диалоге

        locTime=time.getTime().getHours()+":"+time.getTime().getMinutes();
        Log.d("TIME",locTime);

    }


//    public void setTime(TimePickerDialog.OnTimeSetListener t) {
//        timePickerDialog=  new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
//        timePickerDialog.show();//Проблема в том, что я не знаю как уобработать кнопку ок на диалоге
////         while (!timesetted){
////
////         }
////         timesetted=false;
////        timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
////            @Override
////            public void onShow(DialogInterface dialogInterface) {
////
////            }
////
////        });
////        timePickerDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
////            @Override
////            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
////                if(keyEvent.toString().equals(KeyEvent.keyCodeToString(KeyEvent.ACTION_UP))){
////
////                }
////                Log.d("KEYCODE " , keyEvent.toString());
////                return false;
////            }
////
////        });
//
//        //timePickerDialog.getSearchEvent().
////        while(!timePickerDialog.isShowing()){
////
////        }
////        if(timePickerDialog.dispatchKeyEvent(( TimePickerDialog.BUTTON_POSITIVE))
////        if(timePickerDialog.onClick((DialogInterface) this,DialogInterface.BUTTON_POSITIVE))
//        locTime=time.getTime().getHours()+":"+time.getTime().getMinutes();
//
//
//    }





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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //LatLng itSchool = new LatLng(Double.parseDouble((MainActivity.cordList.get(0).split(",")[0])) ,Double.parseDouble(MainActivity.cordList.get(0).split(",")[1]));

        //mMap.addMarker(new MarkerOptions().position(itSchool).title("Время задачи: "+MainActivity.cordList.get(0).split(",")[2]));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(itSchool));
        AddAllMarkers(MainActivity.ts);
        showLocButton();




        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(MainActivity.isTaskFromMap) {

                    //for(int i=0;i<Integer.MAX_VALUE;i++){}
                    //if(timePickerDialog.isShowing()==true) {}
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Время задачи:" + locTime));
                    MainActivity.ts.add(new Task(latLng.latitude,latLng.longitude,Integer.parseInt(locTime.split(":")[0]),Integer.parseInt(locTime.split(":")[1])));
                    //MainActivity.ts.add(latLng.latitude+","+latLng.longitude+","+MainActivity.locTime);
                    MainActivity.isTaskFromMap=false;
                    //Нужна пауза ибо не невозможно получить новое время для маркера
                    //startActivity(new Intent(MapsActivity.this, MainActivity.class));
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

        //item = new LatLng(-34,55);
        item = new LatLng(Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[0])  ,  Double.parseDouble(l.getItemAtPosition(position).toString().split(",")[1]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(item));

    }


}
