package com.example.sergei.changelocationmodule3;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sergei on 04.02.18.
 */

public class SettingsFragment extends Fragment {
    View v;
    Button wtf;
    Button sortButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //v =inflater.inflate(R.layout.fragment_settings, container, false);
//        wtf = (Button) v.findViewById(R.id.wtf);
//        sortButton = (Button)v.findViewById(R.id.sortList);
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
