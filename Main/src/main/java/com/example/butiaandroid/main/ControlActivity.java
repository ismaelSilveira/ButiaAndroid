package com.example.butiaandroid.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.butiaandroid.main.vistas.LayoutControl;

public class ControlActivity extends Activity implements OnTouchListener {

    TextView texto;
    LayoutControl control;
    ImageView robot;
    int velMAX = 1000;


    Robot butia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView) findViewById(R.id.cord);
        control = (LayoutControl) findViewById(R.id.control);
        robot = (ImageView) findViewById( R.id.robot);

       // canvas.drawCircle(getWidth()/2, getHeight()/2, 100, paint);

        control.setOnTouchListener(this);

        butia = Robot.getInstance();
       // butia.set2MotorSpeed("1","380","1","3");

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

            if (Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)) <= Math.pow(control.getRadio(), 2)) {
                mensaje = mensaje + "adentro: x=" + x + ", y=" + y;
                setPosition(x, y);
                //se calcula la velocidaad
                Double distCentro= Math.sqrt(Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)));
                Double velRuedaRapida = (distCentro / control.getRadio()) * velMAX;//constante vel maxima

                Integer velDerecha=0;
                Integer velIzquierda=0;
                Integer sentido=0;

                //se reparte en 4 cuadrantes
                //PRIMEer
                if ((x > control.getCentroX() && y < control.getCentroY())){
                    Double alpha= Math.toDegrees(Math.atan(Math.abs(y-control.getCentroY())/ Math.abs(x - control.getCentroX())));
                    velIzquierda = velRuedaRapida.intValue();
                    velDerecha = ((Double)((alpha/90) * velRuedaRapida)).intValue();
                    sentido = 1;

                //4 cuadrante
                }else if ((x < control.getCentroX() && y < control.getCentroY())){
                    Double alpha= Math.toDegrees(Math.atan(Math.abs(y-control.getCentroY())/ Math.abs(x - control.getCentroX())));
                    velDerecha = velRuedaRapida.intValue();
                    velIzquierda = ((Double)((alpha/90) * velRuedaRapida)).intValue();
                    sentido = 1;

                } else   if ((x > control.getCentroX() && y > control.getCentroY())){
                    Double alpha= Math.toDegrees(Math.atan(Math.abs(y-control.getCentroY())/ Math.abs(x - control.getCentroX())));
                    velIzquierda = velRuedaRapida.intValue();
                    velDerecha = ((Double)((alpha/90) * velRuedaRapida)).intValue();
                    sentido = 0;

                }else if ((x < control.getCentroX() && y > control.getCentroY())){
                    Double alpha= Math.toDegrees(Math.atan(Math.abs(y-control.getCentroY())/ Math.abs(x - control.getCentroX())));
                    velDerecha = velRuedaRapida.intValue();
                    velIzquierda = ((Double)((alpha/90) * velRuedaRapida)).intValue();
                    sentido = 0;
                }

                    // MUEVE LOS MOTORES!!
                 butia.set2MotorSpeed(String.valueOf(sentido),String.valueOf(velIzquierda),String.valueOf(sentido),String.valueOf(velDerecha));
                // butia.set2MotorSpeed("1","380","1","3");


/*
                System.out.print(velIzquierda);
                System.out.print(velDerecha);
                System.out.print(alpha);

*/
                mensaje = "sentido" + String.valueOf(sentido) + " velocidad izq " + String.valueOf(velIzquierda)
                 + " velocidad der " + String.valueOf(velDerecha);
/*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
*/


            } else {
                mensaje = mensaje + "afuera";
            }

        } else{

            mensaje = "CentroX: " + control.getCentroX() + ", CentroY: " + control.getCentroY() + ".";
            setPosition((int) control.getCentroX(), (int) control.getCentroY());
            butia.set2MotorSpeed("0","0","0","0");



        }

        texto.setText(mensaje);
        return true;

    }
}
