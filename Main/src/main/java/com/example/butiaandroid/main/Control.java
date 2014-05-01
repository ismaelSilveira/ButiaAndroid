package com.example.butiaandroid.main;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.butiaandroid.main.vistas.LayoutControl;

/**
 * Created by Rodrigo on 10/04/14.
 */
public class Control  implements View.OnTouchListener  {

    //TextView texto;
    LayoutControl control;
    ImageView robot;
    int velMAX = 1023;


    Robot butia =  Robot.getInstance();;


    public Control(LayoutControl control, ImageView robot) {
        this.control = control;
        this.robot = robot;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.control) {
            return manejar(motionEvent);
        }
        return true;
    }

    private void setPosition(int posX, int posY)
    {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(robot.getLayoutParams());
        marginParams.setMargins(posX - control.getLocationX() - (robot.getWidth() / 2), posY - control.getLocationY() - (robot.getHeight() / 2), 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        robot.setLayoutParams(layoutParams);
    }

    private boolean manejar(MotionEvent motionEvent) {
        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY();
        String mensaje = "";

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            int velDerecha=0;
            int velIzquierda=0;
            int sentido=0;

            Double alpha = (Math.atan(Math.abs(y - control.getCentroY()) / Math.abs(x - control.getCentroX())));

            //pone la tortuga en el borde del circulo si esta afuera
            if (Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)) > Math.pow(control.getRadio(), 2)) {
                 if ((x > control.getCentroX() && y < control.getCentroY())) {
                    y = (int) (control.getCentroY() - (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() +  (control.getRadio() *  (Math.cos(alpha))));
                } else if ((x < control.getCentroX() && y < control.getCentroY())) {
                    y = (int) (control.getCentroY() - (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() -  (control.getRadio() *  (Math.cos(alpha))));
                } else if ((x > control.getCentroX() && y > control.getCentroY())) {
                    y = (int) (control.getCentroY() + (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() +  (control.getRadio() *  (Math.cos(alpha))));
                } else if ((x < control.getCentroX() && y > control.getCentroY())) {
                    y = (int) (control.getCentroY() + (control.getRadio()  *  (Math.sin(alpha))));
                    x = (int) (control.getCentroX() -  (control.getRadio() *  (Math.cos(alpha))));
                }
            }
            setPosition(x, y);
            alpha = Math.toDegrees(alpha);

            //se calculan las velocidades
            Double distCentro = Math.sqrt(Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)));
            Double  velRuedaRapida = (distCentro / control.getRadio()) * velMAX;
            //PRIMEer
            if ((x > control.getCentroX() && y < control.getCentroY())) {
                velIzquierda = velRuedaRapida.intValue();
                velDerecha = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                sentido = 1;

            //4 cuadrante
            } else if ((x < control.getCentroX() && y < control.getCentroY())) {
                velDerecha = velRuedaRapida.intValue();
                velIzquierda = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                sentido = 1;

            } else if ((x > control.getCentroX() && y > control.getCentroY())) {
                velIzquierda = velRuedaRapida.intValue();
                velDerecha = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                sentido = 0;

            } else if ((x < control.getCentroX() && y > control.getCentroY())) {
                velDerecha = velRuedaRapida.intValue();
                velIzquierda = ((Double) ((alpha / 90) * velRuedaRapida)).intValue();
                sentido = 0;
            }
            // MUEVE LOS MOTORES!!
            butia.set2MotorSpeed(String.valueOf(sentido), String.valueOf(velIzquierda), String.valueOf(sentido), String.valueOf(velDerecha));

        } else{
            setPosition((int) control.getCentroX(), (int) control.getCentroY());
            butia.set2MotorSpeed("0","0","0","0");
        }

        return true;
    }





}
