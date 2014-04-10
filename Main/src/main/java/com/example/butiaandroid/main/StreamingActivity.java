package com.example.butiaandroid.main;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.butiaandroid.main.vistas.LayoutControl;


public class StreamingActivity extends Activity {

    VideoView videoView;
    LayoutControl control;
    ImageView robot;
    int velMAX = 1000;
    Robot butia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);

        control = (LayoutControl) findViewById(R.id.control);
        robot = (ImageView) findViewById( R.id.robot);
        videoView = (VideoView)this.findViewById(R.id.videoView);

        control.setTransparente(true);


        butia = Robot.getInstance();


        Control c = new Control( control, robot);
        control.setOnTouchListener(c);

        //add controls to a MediaPlayer like play, pause.
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //Set the path of Video or URI
        videoView.setVideoURI(Uri.parse("rtsp://192.168.1.110:8554/"));

        //Set the focus
        videoView.requestFocus();
        videoView.start();


    }





}
