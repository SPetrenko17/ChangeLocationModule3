package com.example.sergei.changelocationmodule3;

import android.app.Fragment;
import android.app.ListActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by sergei on 04.02.18.
 */

public class DataViewsFragment extends Fragment {
    View v;
    Handler h;
    Message msg;
    Bundle bundle = new Bundle();
    String strTime;
    TextView textView;//gps enabled
    TextView textView2;//my coordinates
    TextView textView3;//coordinates from list
    TextView textView4;//Current time
    TextView textView5;//Current task
    Timer mTimer;
    //MyTimerT mMyTimerTask;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dataview, container, false);

        return inflater.inflate(R.layout.fragment_dataview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textView);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
       //         mTimer = new Timer();
        //mMyTimerTask = new MyTimerT();
       // mTimer.schedule(mMyTimerTask, 0, 1000);

        //new MyThread().start();
//        h = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                textView4.setText("Current time: " + msg.toString());
//            }
//        };

    }
//следует заменить на таймеры

  /*  class MyThread extends Thread {

        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        "HH:mm", Locale.getDefault());
                strTime = simpleDateFormat.format(calendar.getTime());
                //textView4.setText("Current time: " + strTime);
                msg = new Message();

                bundle.putString("msg",strTime);
                msg.setData(bundle);
               // h.handleMessage(msg);
                h.sendMessage(msg);

            }
        }
    }
    */
}

