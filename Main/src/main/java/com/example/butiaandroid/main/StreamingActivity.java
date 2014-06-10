package com.example.butiaandroid.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.butiaandroid.main.streamer.MjpegInputStream;
import com.example.butiaandroid.main.streamer.MjpegView;
import com.example.butiaandroid.main.vistas.LayoutControl;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StreamingActivity   extends ButiaActivity {


    @InjectView(R.id.mv) MjpegView mv;



    private static final int MENU_QUIT = 1;

    private static final boolean DEBUG=false;
    private static final String TAG = "MJPEG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);
        ButterKnife.inject(this);

        butia = Robot.getInstance();


        /*con vlc era solo usar un mediaplayer
        //add controls to a MediaPlayer like play, pause.
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //Set the path of Video or URI
       // videoView.setVideoURI(Uri.parse("rtsp://192.168.1.110:8554/"));
        videoView.setVideoURI(Uri.parse("tcp://192.168.43.88:8090/"));
     //Set the focus
        videoView.requestFocus();
        videoView.start();
        */

        new DoRead().execute( butia.getHost(), butia.getPortStreaming());
    }


    public void onResume() {
        if(DEBUG) Log.d(TAG,"onResume()");
        super.onResume();
        if(mv!=null){
    //        mv.resumePlayback();
            int i=1;
        }

    }

    public void onStart() {
        if(DEBUG) Log.d(TAG,"onStart()");
        super.onStart();
    }
    public void onPause() {
        if(DEBUG) Log.d(TAG,"onPause()");
        super.onPause();
        if(mv!=null){
            mv.stopPlayback();
        }
    }
    public void onStop() {
        if(DEBUG) Log.d(TAG,"onStop()");
        super.onStop();
    }

    public void onDestroy() {
        if(DEBUG) Log.d(TAG,"onDestroy()");

        if(mv!=null){
          //  mv.freeCameraMemory();
        }

        super.onDestroy();
    }

    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
        protected MjpegInputStream doInBackground( String... params){
            Socket socket = null;
            try {
                socket = new Socket( params[0], Integer.valueOf( params[1]));
                return (new MjpegInputStream(socket.getInputStream()));
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            mv.setSource(result);
            if(result!=null) {
                result.setSkip(1);
            }
            mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            mv.showFps(true);
        }
    }
}