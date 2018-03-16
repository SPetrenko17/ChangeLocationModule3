package com.example.sergei.changelocationmodule3;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by sergei on 10.03.2018.
 */

public class LocationTimePicker extends TimePicker {
    Calendar calendar = Calendar.getInstance();
    Time time;
    String myTime ;
    public LocationTimePicker(Context context) {
        super(context);
    }

    @Override
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        super.setOnTimeChangedListener(onTimeChangedListener);
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        time.setHours(Calendar.HOUR_OF_DAY, hourOfDay);
//        time.setMinutes(Calendar.MINUTE, minute);

    }
}
