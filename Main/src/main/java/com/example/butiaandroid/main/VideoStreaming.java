package com.example.butiaandroid.main;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoStreaming extends ActionBarActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);


        videoView = (VideoView)this.findViewById(R.id.videoView);

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
