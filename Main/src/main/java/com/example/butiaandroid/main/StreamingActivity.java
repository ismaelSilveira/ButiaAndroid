package com.example.butiaandroid.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.butiaandroid.main.stream.MjpegInputStream;
import com.example.butiaandroid.main.stream.MjpegView;
import com.example.butiaandroid.main.vistas.LayoutControl;

import java.net.Socket;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class StreamingActivity extends Activity {

  //  VideoView videoView;
    LayoutControl control;
    ImageView robot;
    int velMAX = 1000;
    Robot butia;

    private MjpegView mv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);

        control = (LayoutControl) findViewById(R.id.control);
        robot = (ImageView) findViewById( R.id.robot);
//        videoView = (VideoView)this.findViewById(R.id.videoView);

        control.setTransparente(true);


        butia = Robot.getInstance();


        Control c = new Control( control, robot);
        control.setOnTouchListener(c);

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


//a probar
/*        String KEY_HOSTNAME = "hostname";
        String KEY_PORTNUM = "portnum";
        String KEY_WIDTH = "width";
        String KEY_HEIGHT = "height";
        // store the input data
        SharedPreferences sp = this.getPreferences( MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString( KEY_HOSTNAME, hostname);
        editor.putString( KEY_PORTNUM, portnum);
        editor.putString( KEY_WIDTH, width);
        editor.putString( KEY_HEIGHT, height);
        editor.commit();
*/

//hasta acaaaaaaaaaa

        int width = 800;
        int height = 600;

        // set the image size
        MjpegView.setImageSize( width, height);

        mv = (MjpegView) findViewById(R.id.mv);
        String hostname ="172.16.101.128";
        String portnum ="1234";
        new DoRead().execute( hostname, portnum);
    }


    public void onResume() {
        if(mv!=null){
            mv.resumePlayback();
        }

    }

    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
        if(mv!=null){
            mv.stopPlayback();
        }
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        if(mv!=null){
            mv.freeCameraMemory();
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
            if(result!=null) result.setSkip(1);
            mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            mv.showFps(true);
        }
    }


}
