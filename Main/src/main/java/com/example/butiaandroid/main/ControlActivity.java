package com.example.butiaandroid.main;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.devspark.appmsg.AppMsg;


/**
 * Created by Rodrigo on 09/06/14.
 */
public class ControlActivity extends ActionBarActivity {
    Robot butia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butia_activity);

        butia = Robot.getInstance();
        //FrameLayout layout  = (FrameLayout) findViewById(R.id.container);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled  (true);


        //lista de acciones
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.action_list_controles, android.R.layout.simple_spinner_dropdown_item);

        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            // Get the same strings provided for the drop-down's ArrayAdapter
            String[] strings = getResources().getStringArray(R.array.action_list_controles);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                switch (position) {
                    case 0:
                        controlSimple();
                        break;
                    case 1:
                        if (butia.isStreaming()){
                            controlStreaming();
                        }else{
                            mostrarError("Configure el streaming al conectarse para poder usarlo");
                            return false;
                        }
                        break;
                    case 2:
                        controlAcelerometro();
                        break;
                }
                return true;
            }
        };

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

        if (butia.isStreaming()){
            this.getActionBar().setSelectedNavigationItem(1);
        }else{
            this.getActionBar().setSelectedNavigationItem(0);
        }

        //controlSimple();

    }



    private void controlSimple() {
        // Create new fragment and transaction
        FragmentControlSimple newFragment = new FragmentControlSimple();
        newFragment.setPadre(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        // transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    private void controlStreaming() {
        // Create new fragment and transaction
        FragmentControlStreaming newFragment = new FragmentControlStreaming();
        newFragment.setPadre(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        // transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }





    private void controlAcelerometro() {
        // Create new fragment and transaction
        FragmentControlAcelerometro newFragment = new FragmentControlAcelerometro();
        newFragment.setPadre(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container, newFragment);
        // transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


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


    public void mostrarError(String error){
        AppMsg appMsg = AppMsg.makeText(this, error,  AppMsg.STYLE_ALERT);
        appMsg.setDuration(5000);
        appMsg.show();
    }

    //implementa el control del robot
    @Override
    protected void onResume() {
        super.onResume();
       // butia.start2MotorThread();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        butia.setOff();
    }




}
