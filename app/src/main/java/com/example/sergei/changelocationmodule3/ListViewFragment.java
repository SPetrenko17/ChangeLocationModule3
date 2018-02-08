package com.example.sergei.changelocationmodule3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Bundle;

/**
 * Created by sergei on 04.02.18.
 */

public class ListViewFragment extends Fragment {
    Methods methods=new Methods();
    ListView locationTaskList;
    ArrayAdapter<String> itemsAdapter;
    //ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View v =inflater.inflate(R.layout.fragment_listview, parent, false);
        locationTaskList = v.findViewById(R.id.LocationTaskList);
        MainActivity.cordList = new ArrayList<String>();
        MainActivity.cordList.add(0, "55.743621, 37.681500,00:00");
        //MainActivity.cordList.add(0, "00.0000,00.0000,00:00");
        return  v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //locationTaskList= (ListView) view.findViewById(R.id.LocationTaskList);
        itemsAdapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, MainActivity.cordList);//изменил api с 19 на 23
        locationTaskList.setAdapter(itemsAdapter);
        //locationTaskList.invalidate();
        locationTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ((MainActivity)getActivity()).textView3.setText("Selected task: " + (String) locationTaskList.getItemAtPosition(position));
                //textView3.setText("Selected task: " + (String) locationTaskList.getItemAtPosition(position));
                //((MainActivity)getActivity()).show( TestData.urls[position]);


            }
        });


    }
    public void addListElement(View view){
        if(methods.isCorrect(((MainActivity)getActivity()).addTask.getText().toString())){

            MainActivity.cordList.add(((MainActivity)getActivity()).addTask.getText().toString());
            ((MainActivity)getActivity()).addTask.setText(null);
            methods.info=null;
            MainActivity.cordList=methods.SortList(MainActivity.cordList);
        }
        else {
            //Toast.makeText(this, "pizdets: "+methods.getInfo(), Toast.LENGTH_LONG).show();
            methods.info=null;
        }
        locationTaskList.invalidate();
    }




}
