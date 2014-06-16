package com.example.butiaandroid.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.butiaandroid.main.streamer.MjpegInputStream;
import com.example.butiaandroid.main.streamer.MjpegView;

import java.net.Socket;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FragmentControlStreaming extends ControlCircular{

    @InjectView(R.id.mv) MjpegView mv;
    private static final int MENU_QUIT = 1;
    private static final boolean DEBUG=false;
    private static final String TAG = "MJPEG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View V = inflater.inflate(R.layout.control_video_streaming, container, false);
        ButterKnife.inject(this, V);

        Robot butia = Robot.getInstance();
        //new DoRead().execute( butia.getHost(), butia.getPortStreaming());

        return V;
        /*con RTSP era solo usar un mediaplayer
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
            } catch (Exception e) {
                padre.mostrarError("Ocurrio un error al conectar");
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