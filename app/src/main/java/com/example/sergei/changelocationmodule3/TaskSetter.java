package com.example.sergei.changelocationmodule3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TaskSetter extends BroadcastReceiver {;
    static PendingIntent pi;
    Intent intent;
    static AlarmManager am;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        //PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //PowerManager.WakeLock wl= pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"YOUR TAG");
//Осуществляем блокировку
     //wl.acquire();
   if(!MainActivity.ts.isEmpty()) {


       Log.d("myLogs", "onReceive");

       MainActivity.fakeloc.setLatitude(MainActivity.ts.get(0).getLat());
       MainActivity.fakeloc.setLongitude(MainActivity.ts.get(0).getLng());
       MainActivity.locationManager.clearTestProviderLocation(MainActivity.fakeloc.getProvider());
       MainActivity.locationManager.setTestProviderLocation(MainActivity.fakeloc.getProvider(), MainActivity.fakeloc);

       Toast.makeText(context, "Данные подменены:"+MainActivity.ts.get(0).toString(), Toast.LENGTH_SHORT).show();

       Log.d("myLogs", "set "+MainActivity.ts.get(0).toString());
       Log.d("myLogs", "zero before remove"+MainActivity.ts.get(0).toString());
       MainActivity.ts.remove(0);
       if(!MainActivity.ts.isEmpty()) {
           Log.d("myLogs", "zero before notify" + MainActivity.ts.get(0).toString());
           MainActivity.taskAdapter.notifyDataSetChanged();
           Log.d("myLogs", "zero after notify" + MainActivity.ts.get(0).toString());

           Calendar calendar = Calendar.getInstance();
           Long currtime = (long) (calendar.getTime().getHours() * 3600000 + calendar.getTime().getMinutes() * 60000);
           long triggerAtMillis = MainActivity.ts.get(0).toMilliSeconds() - currtime;

           am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           intent = new Intent(context, TaskSetter.class);
           intent.putExtra("onetime", Boolean.FALSE);//Задаем параметр интента
           pi = PendingIntent.getBroadcast(context, 0, intent, 0);
           Log.d("myLogs", "am.set "+MainActivity.ts.get(0).toString());
           am.set(AlarmManager.RTC, System.currentTimeMillis() + triggerAtMillis, pi);

       }
        else Log.d("myLogs", "ts is empty");
   }
   else Log.d("myLogs", "ts is empty");

    }
    public void setTask(Context context)
    {
        this.context=context;
        Calendar calendar = Calendar.getInstance();
        Long currtime =(long)(calendar.getTime().getHours()*3600000+calendar.getTime().getMinutes()*60000);
        Log.d("myLogs", "setTask");
        am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        intent=new Intent(context, TaskSetter.class);
        intent.putExtra("onetime", Boolean.FALSE);//Задаем параметр интента
        pi= PendingIntent.getBroadcast(context,0, intent,0);
        am.cancel(pi);
        long triggerAtMillis =MainActivity.ts.get(0).toMilliSeconds()-currtime;
        am.set(AlarmManager.RTC,System.currentTimeMillis()+triggerAtMillis,pi);

        Log.d("myLogs", "currtime-"+currtime);
        Log.d("myLogs", "task time -"+MainActivity.ts.get(0).toMilliSeconds());
        Log.d("myLogs", "triggertime-"+triggerAtMillis);
        Log.d("myLogs", "am.set "+MainActivity.ts.get(0).toString());
    }

}
