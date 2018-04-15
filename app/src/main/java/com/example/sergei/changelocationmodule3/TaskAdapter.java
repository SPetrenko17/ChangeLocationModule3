package com.example.sergei.changelocationmodule3;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.location.Criteria.ACCURACY_HIGH;

/**
 * Created by sergei on 24.03.2018.
 */

public class TaskAdapter extends BaseAdapter {
    Context ctx;
    Button delBtn;
    Button setLoc;
    LayoutInflater lInflater;
    ArrayList<Task> objects;
    Location fakeloc;
    int myHour, myMinute;

    TaskAdapter(Context context, ArrayList<Task> objects) {
        this.ctx = context;
        this.objects = objects;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fakeloc = new Location(MainActivity.locationProvider.getName());
        fakeloc.setTime(System.currentTimeMillis());
        fakeloc.setAccuracy(ACCURACY_HIGH);
        fakeloc.setBearingAccuracyDegrees(ACCURACY_HIGH);
        fakeloc.setSpeedAccuracyMetersPerSecond(ACCURACY_HIGH);
        fakeloc.setVerticalAccuracyMeters(ACCURACY_HIGH);
        fakeloc.setElapsedRealtimeNanos(1);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = lInflater.inflate(R.layout.task_xml, viewGroup, false);
        }
        delBtn = view.findViewById(R.id.deleteBtn);
        setLoc = view.findViewById(R.id.locBtn);
        TextView latView = view.findViewById(R.id.latView);
        TextView lngView = view.findViewById(R.id.lngView);
        TextView timeView = view.findViewById(R.id.timeView);
        latView.setText("lat: " + objects.get(i).getLat() + " ");
        lngView.setText("lng: " + objects.get(i).getLng() + " ");
        timeView.setText("time-" + objects.get(i).getHours()+":"+objects.get(i).getMinutes());

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.ts.remove(i);
                MainActivity.taskAdapter.notifyDataSetChanged();
            }
        });
        setLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fakeloc.setLatitude(MainActivity.ts.get(i).getLat());
                fakeloc.setLongitude(MainActivity.ts.get(i).getLng());
                MainActivity.locationManager.clearTestProviderLocation(fakeloc.getProvider());
                MainActivity.locationManager.setTestProviderLocation(fakeloc.getProvider(), fakeloc);
            }
        });
        return view;
    }




}