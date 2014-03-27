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
                double distCentro= Math.sqrt(Math.pow(x - control.getCentroX(), 2) + (Math.pow(y - control.getCentroY(), 2)));
                double velRuedaRapida = (distCentro / control.getRadio()) * 400;//constante vel maxima
                double alpha= Math.atan(Math.abs(y-control.getCentroY()) / Math.abs(x - control.getCentroX()));
                
                double velDerecha;
                double velIzquierda;

                //se reparte en 4 cuadrantes
                //cuadrante 1
                if (x>control.getCentroX() && y > control.getCentroY()){
                    velIzquierda = velRuedaRapida;
                    velDerecha = (alpha/90) * velRuedaRapida;

                } else if  (x<control.getCentroX() && y > control.getCentroY()){

                }









            } else {
                mensaje = mensaje + "afuera";
            }

        } else{

            mensaje = "CentroX: " + control.getCentroX() + ", CentroY: " + control.getCentroY() + ".";
            setPosition((int) control.getCentroX(), (int) control.getCentroY());

        }

        texto.setText(mensaje);
        return true;

    }
}
