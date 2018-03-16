package com.example.sergei.changelocationmodule3;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockContentProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//import clientGeo.YaGeocoder;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> cordList;
    ArrayList<String> timeList;
    static LocationManager locationManager;

   // private Timer mTimer;
   // private MyTimerTask mMyTimerTask;
    static Methods methods; //ранее CheckCorrect
    static ListView locationTaskList;
    String strTime;
    String str;
    static DataViewsFragment dataViewsFragment;
    EditTextFragment editTextFragment;
    SettingsFragment settingsFragment;
    ListFragmentListView listFragmentListView;

    LocationProvider mocLocationProvider;
    static TestLocationListenner testLocationListener = new TestLocationListenner();
    //static ArrayAdapter<String> adapter;

    static boolean isTaskFromMap = false;

    static TimePickerDialog.OnTimeSetListener t;
    static TimePickerDialog timePickerDialog;
    static String locationTime ="";


    static ArrayAdapter adapter;//ListAdapter

    Calendar time=Calendar.getInstance();
    static String locTime=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //classes
        methods = new Methods();
        testLocationListener.SetUpLocationListener(this);

        TimePickerDialog.OnTimeSetListener t;
        TimePickerDialog timePickerDialog;

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,cordList );
        //.setAdapter(adapter);
       // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //locationTaskList = (ListView) findViewById(R.id.LocationTaskList);
        //dataViewsFragment.textView3.setText("Ниже представлена форма заполнения задачи: Широта,Долгота,Ч:М");
        //String[] MockLoc = str.split(",");

       // mocLocationProvider = locationManager.getProvider(LOCATION_SERVICE);

       //setNewLoc(); !!!!!!!!!!!!!!!!!

        //Location location = new Location(mocLocationProvider);
//        Double lat = Double.valueOf(MockLoc[0]);
//        location.setLatitude(lat);
//        Double longi = Double.valueOf(MockLoc[1]);
//        location.setLongitude(longi);
//        Double alti = Double.valueOf(MockLoc[2]);
//        location.setAltitude(alti);

        showAll();


//        mTimer = new Timer();
//        mMyTimerTask = new MyTimerTask();
//        mTimer.schedule(mMyTimerTask, 0, 1000);

        cordList = new ArrayList<String>();
        cordList.add(0, "55.743621, 37.681500,00:00");
        cordList.add(1, "11.111111, 22.222222,01:00");
       // new MyRunnableTask();// !!!!!ВЫЛЕТАЕТ ПРИ ПОВОРОТЕ




    }
    void setNewLoc(){
        locationManager.removeTestProvider( LocationManager.GPS_PROVIDER);
        locationManager.addTestProvider ( LocationManager.GPS_PROVIDER, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "", "hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "", android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE );
        Location newLocation = new Location(LocationManager.GPS_PROVIDER);
        newLocation.setLatitude (55.743621);
        newLocation.setLongitude(37.681500);
        newLocation.setAccuracy(500);
        locationManager.setTestProviderEnabled ( LocationManager.GPS_PROVIDER, true );
        locationManager.setTestProviderStatus ( LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis() );
        locationManager.setTestProviderLocation ( LocationManager.GPS_PROVIDER, newLocation );
    }
    public void showAll(){
        FragmentManager in = getSupportFragmentManager();
        dataViewsFragment = (DataViewsFragment)getFragmentManager().findFragmentById(R.id.tfragmentDataView);
        editTextFragment =(EditTextFragment)getFragmentManager().findFragmentById(R.id.tfragmetEdit);
        settingsFragment =(SettingsFragment)getFragmentManager().findFragmentById(R.id.tfragmentSettings);
        //listFragmentListView (ListFragmentListView)getFragmentManager().findFragmentById(R.id.tListFragment);

    }
    public ArrayList<String> getCordList(){
        return  cordList;
    }


    public void onClickLocationSettings(View view) {

        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    public void onClickShowMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    };

    public void addListElement(View view){
        if(methods.isCorrect(editTextFragment.addTask.getText().toString())){

            cordList.add(editTextFragment.addTask.getText().toString());
            editTextFragment.addTask.setText(null);
            Toast.makeText(this, "added "+methods.getInfo(), Toast.LENGTH_LONG).show();
            methods.info=null;
            //cordList=methods.SortList(cordList);

        }
        else {
            Toast.makeText(this, "pizdets: "+methods.getInfo(), Toast.LENGTH_LONG).show();
            methods.info=null;
        }
        //adapter.notifyDataSetChanged();
        //listFragmentListView.updView();
        //listFragmentListView.getListView().invalidate();
       // listFragmentListView.itemsAdapter.notifyDataSetChanged();
        //locationTaskList.invalidate();

    }
    public void addMapTask(View view) throws InterruptedException {
        t=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);

                //timesetted=true;

            }



        };
        setTime(t);
        isTaskFromMap=true;

            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
                    */

        startActivity(new Intent(MainActivity.this, MapsActivity.class));


    }
    public void setTime(TimePickerDialog.OnTimeSetListener t) throws InterruptedException {
        timePickerDialog=  new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
        timePickerDialog.show();//Проблема в том, что я не знаю как уобработать кнопку ок на диалоге
        locTime=time.getTime().getHours()+":"+time.getTime().getMinutes();



    }
    public void checkCurrentTask(){ //Добавить распознование ближайшей задачи
        //boolean taskFound=false;
        String currentTask="";
        for(String inTask:cordList){
            if(true){
                if(inTask.split(",")[2].split(":")[0].equals(strTime.split(":")[0])){
                    dataViewsFragment.textView3.setText("Current task:"+ inTask);
                    currentTask=inTask.toString();
                    //taskFound = true;

                }
                else {

                    dataViewsFragment.textView3.setText("Current task:Not found "+"LastTaskInForeach-"+inTask.split(",")[2].split(":")[0]+"TimeHour-"+strTime.split(":")[0]);
                }
            }
            else{
                dataViewsFragment.textView3.setText("Current task: already founded-" + currentTask);
            }
        }
    }
    //код выводит геолокацию на textview








    public void sortList(View view){
        String[] localArray = new String[cordList.size()];
        String test="0,0,00:00";
        int counter =0;
        int i=0;
        for(String s:cordList) {
            localArray[i]=s;
            i++;
        }
        while(counter!=localArray.length) {
            for(i=1;i<localArray.length;i++ ) {
                if(methods.returnSeconds(localArray[i])<methods.returnSeconds(localArray[i-1])) {
                    test = localArray[i-1];
                    localArray[i-1]=localArray[i];
                    localArray[i]=test;
                }
            }
            counter++;
        }


        cordList = new ArrayList<>(Arrays.asList(localArray)); // Полагаю надо уведомить слушателя листвью
        Toast.makeText(this, "crodlist is updated", Toast.LENGTH_LONG).show();
        for(String s:cordList) {
            Log.d("SORT",s );
        }

        //adapter.notifyAll();

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
    public void updButton(View view){
        //listFragmentListView.itemsAdapter.notifyDataSetChanged();
        //view.invalidate();
    }

}
