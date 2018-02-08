package com.example.sergei.changelocationmodule3;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//import clientGeo.YaGeocoder;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> cordList;
    ArrayList<String> timeList;
    EditText addTask;
    Button approveEdits;
    Button wtf;
    Button sortButton;
    ListView locationTaskList;
    TextView textView;//gps enabled
    TextView textView2;//my coordinates
    TextView textView3;//coordinates from list
    TextView textView4;//Current time
    TextView textView5;//Current task
    // TextView textView6;//Sort test
    LocationManager locationManager;

    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    Methods methods; //ранее CheckCorrect

    String strTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //classes
        methods = new Methods();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //edittext
        addTask = (EditText) findViewById(R.id.AddTask);
        //buttons
        approveEdits = (Button) findViewById(R.id.ApproveEdits);
        wtf = (Button) findViewById(R.id.wtf);
        sortButton = (Button)findViewById(R.id.sortList);
        //textview
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        //listview
        locationTaskList = (ListView) findViewById(R.id.LocationTaskList);

        textView3.setText("Ниже представлена форма заполнения задачи: Широта,Долгота,Ч:М");



        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);

        cordList = new ArrayList<String>();
        cordList.add(0, "00.0000,00.0000,00:00");
        showMyLocation();



    }
    public ArrayList<String> getCordList(){
        return  cordList;
    }

    public class MyTimerTask extends TimerTask {


        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "HH:mm", Locale.getDefault());
            strTime = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    textView4.setText("Current time: " + strTime);
                    checkCurrentTask();

                }

            });
        }
    }
    public void onClickLocationSettings(View view) {
       // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    };
    public void addListElement(View view){
        if(methods.isCorrect(addTask.getText().toString())){

            cordList.add(addTask.getText().toString());
            addTask.setText(null);
            methods.info=null;
            cordList=methods.SortList(cordList);
        }
        else {
            Toast.makeText(this, "pizdets: "+methods.getInfo(), Toast.LENGTH_LONG).show();
            methods.info=null;
        }
        locationTaskList.invalidate();
    }
    public void checkCurrentTask(){ //Добавить распознование ближайшей задачи
        //boolean taskFound=false;
        String currentTask="";
        for(String inTask:cordList){
            if(true){
                if(inTask.split(",")[2].split(":")[0].equals(strTime.split(":")[0])){
                    textView5.setText("Current task:"+ inTask);
                    currentTask=inTask.toString();
                    //taskFound = true;

                }
                else {

                    textView5.setText("Current task:Not found "+"LastTaskInForeach-"+inTask.split(",")[2].split(":")[0]+"TimeHour-"+strTime.split(":")[0]);
                }
            }
            else{
                textView5.setText("Current task: already founded-" + currentTask);
            }
        }
    }
    //код выводит геолокацию на textview
    public void showMyLocation(){
        Location location = new Location(locationManager.GPS_PROVIDER);

        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            textView2.setText(formatLocation(location) + " gps");
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            textView2.setText(formatLocation(location) + " net");
        }
        showLocation(location);
    }
    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            textView2.setText(formatLocation(location)+ " gps");
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            textView2.setText(formatLocation(location)+" net");
        }
    }
    private String formatLocation(Location location) {
        if (location == null) {
            return "";
        }
        return String.format("Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT", location.getLatitude(), location.getLongitude(), new Date(location.getTime()));
    }


    public void sortList(View view){
        String bigTask=" ";
        String minTask=" ";
        //String[] cordMas = new = cordList.toArray();
        int maxsize =cordList.size();
//        if(isSmaller("10:10","10:15")){
//            Toast.makeText(this, "robit", Toast.LENGTH_LONG).show();
//        }
//        else  Toast.makeText(this, " ne robit", Toast.LENGTH_LONG).show();

        Toast.makeText(this, cordList.size()+" ", Toast.LENGTH_LONG).show();
        for(int i=1;i<cordList.size();i++){

            if(methods.isSmaller(cordList.get(i).split(",")[2],cordList.get(i-1).split(",")[2] )){
                minTask=cordList.get(i);
                bigTask=cordList.get(i-1);
                //cordList.set(i-1,minTask); // тупит, если заменить на идентичный текст
                //cordList.set(i,bigTask);
                //locationTaskList.invalidate();
                // Toast.makeText(this, "robit "+i+" bt="+ bigTask+ " mt="+ minTask, Toast.LENGTH_LONG).show();
                i=0;
            }

        }
        //Toast.makeText(this, "robit "+" bt="+ bigTask+ " mt="+ minTask, Toast.LENGTH_LONG).show();
    }
    @Override //сохраняем
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list",MainActivity.cordList);
       // Log.d("list", "onSaveInstanceState");
    }

    @Override //запихиваем
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cordList= savedInstanceState.getStringArrayList("list");
        //Log.d("list", "onRestoreInstanceState");
    }
}
