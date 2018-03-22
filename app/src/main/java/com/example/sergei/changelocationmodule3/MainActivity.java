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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {

   static ArrayList<Task> ts = new ArrayList<>();


    static LocationManager locationManager;

   // private Timer mTimer;
   // private MyTimerTask mMyTimerTask;
    static Methods methods;
    static DataViewsFragment dataViewsFragment;
    EditTextFragment editTextFragment;
    SettingsFragment settingsFragment;
    //ListFragmentListView listFragmentListView;

    LocationProvider mocLocationProvider;
    static TestLocationListenner testLocationListener = new TestLocationListenner();
    //static ArrayAdapter<String> adapter;

    static boolean isTaskFromMap = false;

//    static TimePickerDialog.OnTimeSetListener t;
//    static TimePickerDialog timePickerDialog;
    static String locationTime ="";


    static ArrayAdapter adapter;//ListAdapter

    Calendar time=Calendar.getInstance();
    //static String locTime=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //classes
        methods = new Methods();
        testLocationListener.SetUpLocationListener(this);


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


       // new MyRunnableTask();// !!!!!ВЫЛЕТАЕТ ПРИ ПОВОРОТЕ

        ts.add(new Task(55.743621, 37.681500,00,00));
        ts.add(new Task(11.111111, 22.222222,01,00));



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
//    public ArrayList<String> getCordList(){
//        return  cordList;
//    }


    public void onClickLocationSettings(View view) {

        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    public void onClickShowMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    };

    public void addListElement(View view){
        if(methods.isCorrect(editTextFragment.addTask.getText().toString())){
            String s = editTextFragment.addTask.getText().toString();
            ts.add(new Task(Double.parseDouble(s.split(",")[0])
                    ,Double.parseDouble(s.split(",")[1])
                        ,Integer.parseInt(s.split(",")[2].split(":")[0])
                            ,Integer.parseInt(s.split(",")[2].split(":")[0])));

            Toast.makeText(this, "added "+ts.toString(), Toast.LENGTH_LONG).show();

            //cordList.add(editTextFragment.addTask.getText().toString());

            editTextFragment.addTask.setText(null);

            //Toast.makeText(this, "added "+methods.getInfo(), Toast.LENGTH_LONG).show();
            //methods.info=null;
            //cordList=methods.SortList(cordList);

        }
        else {
            Toast.makeText(this, "Incorrect data", Toast.LENGTH_LONG).show();
            //methods.info=null;
        }
        //adapter.notifyDataSetChanged();
        //listFragmentListView.updView();
        //listFragmentListView.getListView().invalidate();
       // listFragmentListView.itemsAdapter.notifyDataSetChanged();
        //locationTaskList.invalidate();

    }
    public void addMapTask(View view) throws InterruptedException {
        /*t=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);

                //timesetted=true;

            }



        };

        setTime(t);
        */
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
   /* public void setTime(TimePickerDialog.OnTimeSetListener t) throws InterruptedException {
        timePickerDialog=  new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
        timePickerDialog.show();//Проблема в том, что я не знаю как уобработать кнопку ок на диалоге
        locTime=time.getTime().getHours()+":"+time.getTime().getMinutes();

    }
    */
    public void quickSort(View view){
        Collections.sort(ts,Task.SORTBYECONDS);
        for(Task t:ts){
            Log.d("SORT",t.toString());
        }
    }

    @Override //сохраняем
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putStringArrayList("list",MainActivity.cordList);
       // Log.d("list", "onSaveInstanceState");
    }

    @Override //запихиваем
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //cordList= savedInstanceState.getStringArrayList("list");
        //Log.d("list", "onRestoreInstanceState");
    }
    public void updButton(View view){
        //listFragmentListView.itemsAdapter.notifyDataSetChanged();
        //view.invalidate();
    }

}
