package com.example.butiaandroid.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.butiaandroid.main.vistas.LayoutControl;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    TextView texto;
    LayoutControl control;
    ImageView robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView) findViewById(R.id.cord);
        control = (LayoutControl) findViewById(R.id.control);
        robot = (ImageView) findViewById( R.id.robot);

       // canvas.drawCircle(getWidth()/2, getHeight()/2, 100, paint);

        control.setOnTouchListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if ( view.getId() == R.id.control) {
            return manejar(motionEvent);
        }

        return true;
    }

    private boolean manejar(MotionEvent motionEvent) {

        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY();
        String mensaje = "";


        if ((x  > control.getCentroX() - control.getRadio()) &&(x  < control.getCentroX() + control.getRadio()) &&
                (y  > control.getCentroY() - control.getRadio()) &&(y  < control.getCentroY() + control.getRadio())){
            mensaje = mensaje + "adentro: ";


            //set the image to the new coordinates based on where the user is touching and dragging
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(robot.getLayoutParams());
            marginParams.setMargins(x-  control.getLocationX() -60, y -control.getLocationY()-60,0, 0);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
            robot.setLayoutParams(layoutParams);

        }else{
            mensaje = mensaje + "afuera";
        }


         texto.setText(mensaje);

        return true;
    }
}
