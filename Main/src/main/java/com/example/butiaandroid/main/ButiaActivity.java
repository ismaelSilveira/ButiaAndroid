package com.example.butiaandroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.butiaandroid.main.vistas.LayoutControl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTouch;

/**
 * Created by Rodrigo on 09/06/14.
 */
public class ButiaActivity extends ActionBarActivity {
    Robot butia;
    int velMAX = 1023;


    @InjectView(R.id.control)
    LayoutControl control;
    @InjectView(R.id.robot)
    ImageView robot;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.butia_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.listarModulos) {
            Intent listarIntent = new Intent(this, ListarModulosActivity.class);
            startActivity(listarIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //implementa el control del robot
    @Override
    protected void onResume() {
        super.onResume();
        butia.start2MotorThread();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        butia.setOff();
        //   butia.closeService();
        //   butia.close();
    }


    @OnTouch( R.id.control)
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.control) {
            return manejar(motionEvent);
        }
        return true;
    }


    private void setPosition(int posX, int posY) {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(robot.getLayoutParams());
        marginParams.setMargins(posX - control.getLocationX() - (robot.getWidth() / 2), posY - control.getLocationY() - (robot.getHeight() / 2), 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        robot.setLayoutParams(layoutParams);
    }

    private boolean manejar(MotionEvent motionEvent) {
        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY();
        // String mensaje = "";


        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            int velDerecha = 0;
            int velIzquierda = 0;
            int sentido = 0;

            Double alpha = (Math.atan(Math.abs(y - control.getCentroY()) / Math.abs(x - control.getCentroX())));

            //pone la tortuga en el borde del circulo si esta afuera
            if (Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)) > Math.pow(control.getRadio(), 2)) {
  /*
               da problema....
               if ((x > control.getCentroX() && y < control.getCentroY())) {
                    y = (int) (control.getCentroY() - (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() +  (control.getRadio() *  (Math.cos(alpha))));
                } else if ((x < control.getCentroX() && y < control.getCentroY())) {
                    y = (int) (control.getCentroY() - (control.getRadio()  *  (Math.sin(alpha)))) ;
                    x = (int) (control.getCentroX() -  (control.getRadio() *  (Math.cos(alpha)))) ;
                } else if ((x > control.getCentroX() && y > control.getCentroY())) {
                    y = (int) (control.getCentroY() + (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() +  (control.getRadio() *  (Math.cos(alpha))));
                } else if ((x < control.getCentroX() && y > control.getCentroY())) {
                    y = (int) (control.getCentroY() + (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() -  (control.getRadio() *  (Math.cos(alpha))));
                }
    */
            } else {
                setPosition(x, y);
                alpha = Math.toDegrees(alpha);

                //se calculan las velocidades
                Double distCentro = Math.sqrt(Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)));
                Double velRuedaRapida = (distCentro / control.getRadio()) * velMAX;
                //PRIMEer
                if ((x > control.getCentroX() && y < control.getCentroY())) {
                    velIzquierda = velRuedaRapida.intValue();
                    velDerecha = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                    sentido = 0;

                } else if ((x < control.getCentroX() && y < control.getCentroY())) {
                    velDerecha = velRuedaRapida.intValue();
                    velIzquierda = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                    sentido = 0;

                } else if ((x > control.getCentroX() && y > control.getCentroY())) {
                    velIzquierda = velRuedaRapida.intValue();
                    velDerecha = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                    sentido = 1;

                } else if ((x < control.getCentroX() && y > control.getCentroY())) {
                    velDerecha = velRuedaRapida.intValue();
                    velIzquierda = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                    sentido = 1;
                }
                // MUEVE LOS MOTORES!!
                butia.set2MotorMsg(String.valueOf(sentido), String.valueOf(velIzquierda), String.valueOf(sentido), String.valueOf(velDerecha));


            }

        } else {
            setPosition((int) control.getCentroX(), (int) control.getCentroY());
            butia.set2MotorMsg("0", "0", "0", "0");
        }

        return true;
    }









}
