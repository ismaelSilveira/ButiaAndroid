package com.example.butiaandroid.main;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.butiaandroid.main.vistas.LayoutControl;

public class SinStreamingActivity extends Activity  {
    //TextView texto;
    LayoutControl control;
    ImageView robot;
    Robot butia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = (LayoutControl) findViewById(R.id.control);
        robot = (ImageView) findViewById( R.id.robot);


        butia = Robot.getInstance();

        Control c = new Control( control, robot);
        control.setOnTouchListener(c);

    }


}
