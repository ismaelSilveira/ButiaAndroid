package com.example.butiaandroid.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class FragmentControlSimple extends ControlCircular{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View V = inflater.inflate(R.layout.control_simple, container, false);
        ButterKnife.inject(this, V);
        return V;
    }


/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_simple);
        ButterKnife.inject(this);

        butia = Robot.getInstance();

    }

*/

}
