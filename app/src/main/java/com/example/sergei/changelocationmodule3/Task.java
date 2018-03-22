package com.example.sergei.changelocationmodule3;

import java.util.Comparator;

/**
 * Created by sergei on 22.03.2018.
 */

public class Task {
    private double lat;
    private double lng;
    private int hours;
    private int minutes;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
    public String toString(){
        return lat+","+lng+","+hours+":"+minutes;
    }
   public int toSeconds() {
        return hours*3600+minutes*60;
    }

    public Task(double lat, double lng, int hours, int minutes) {
        this.lat=lat;
        this.lng=lng;
        this.hours=hours;
        this.minutes=minutes;
    }

    public static final Comparator<Task> SORTBYECONDS = new Comparator<Task>(){
        @Override
        public int compare(Task task, Task t1) {
            return Integer.compare(task.toSeconds(),t1.toSeconds());
        }
    };
}
