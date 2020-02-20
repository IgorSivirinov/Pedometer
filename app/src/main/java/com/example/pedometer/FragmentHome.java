package com.example.pedometer;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;



public class FragmentHome extends Fragment {
    private TextView stepsOut;



    public FragmentHome() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_home,container,false);
        ProgressBar progressBar = view.findViewById(R.id.pb);
        TextView stepsOut = view.findViewById(R.id.steps);
        return view;
    }








}
