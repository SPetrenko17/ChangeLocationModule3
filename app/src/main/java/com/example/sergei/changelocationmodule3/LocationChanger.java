package com.example.sergei.changelocationmodule3;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;

public class LocationChanger {
    LocationManager locationManager;
    LocationProvider locationProvider,lm;
    LocationChanger(){
        //locationManager = (LocationManager)MainActivity.ExtraData.class.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        lm= locationManager.getProvider(LocationManager.GPS_PROVIDER);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
    }

}
