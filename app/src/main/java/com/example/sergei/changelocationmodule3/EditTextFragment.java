package com.example.sergei.changelocationmodule3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by sergei on 04.02.18.
 */

public class EditTextFragment extends Fragment {
    // android:onClick="addListElement"
    EditText addTask;
    Button approveEdits;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) { //parent у vv73
        View v =inflater.inflate(R.layout.fragment_task, container, false);
        addTask = v.findViewById(R.id.AddTask);
        //approveEdits = v.findViewById(R.id.ApproveEdits);
        return  v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {//непонятно
        final Activity activity = getActivity();
        super.onViewCreated(view, savedInstanceState);
    }
    /* public void addListElement(View view){
        if(MainActivity.methods.isCorrect(addTask.getText().toString())){

            cordList.add(addTask.getText().toString());
            addTask.setText(null);
            MainActivity.methods.info=null;
            cordList=MainActivity.methods.SortList(cordList);

        }
        else {
            //Toast.makeText(this, "pizdets: "+methods.getInfo(), Toast.LENGTH_LONG).show();
            MainActivity.methods.info=null;
        }
        MainActivity.locationTaskList.invalidate();
    }
    */


}
