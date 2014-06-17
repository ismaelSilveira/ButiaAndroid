package com.example.butiaandroid.main;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

//codigo sacado del 2012
public class FragmentControlAcelerometro extends Fragment implements SensorEventListener {

    private Robot api = null;
    ControlActivity padre;
    SensorManager sm;

    public void setPadre(ControlActivity padre) {
        this.padre = padre;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       // View V = inflater.inflate(R.layout.control_acelerometro, container, false);
        View V = inflater.inflate(R.layout.control_acelerometro, null);
        ButterKnife.inject(this, V);

        api = Robot.getInstance();

        sm = (SensorManager) getActivity().getSystemService(this.padre.SENSOR_SERVICE);
        //SensorManager sm = (SensorManager) getSystemService(padre.SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }

        return V;
    }


    @Override
    public void onDestroy() {
        sm.unregisterListener(this);
        super.onDestroy();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            float x = event.values[0];
            float y = event.values[1];
            // float z = event.values[2];

            if(x > 45){
                x = 45;
            }else if(x < -45){
                x = -45;
            }
            if(y > 45){
                y = 45;
            }else if(y < -45){
                y = -45;
            }

            float xplusy = x + y;
            float yminusx = y - x;

            // rueda izq la de id mayor
            int sentidoIzq = 0; //forward
            if (xplusy < 0){
                xplusy = Math.abs(xplusy);
                sentidoIzq = 1;
            }

            // rueda der la de id menor
            int sentidoDer = 0; //forward
            if (yminusx < 0){
                yminusx = Math.abs(yminusx);
                sentidoDer = 1;
            }
            // por que 990 y no 1023 (velocidad maxima) ??
            xplusy = xplusy*100;
            if(xplusy > 990){
                xplusy = 990;
            }
            yminusx = yminusx*100;
            if(yminusx > 990){
                yminusx = 990;
            }

            api.set2MotorMsg(sentidoIzq + "", (int) xplusy + "", sentidoDer+ "", (int) yminusx + "");
        }
    }






}
