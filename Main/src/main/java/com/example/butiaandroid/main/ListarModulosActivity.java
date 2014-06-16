package com.example.butiaandroid.main;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.butiaandroid.main.Listar.CustomAdapter;
import com.example.butiaandroid.main.Listar.moduloItem;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ListarModulosActivity extends ActionBarActivity {
    @InjectView(R.id.listView) protected ListView list;

    Robot butia;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_modulos);
        ButterKnife.inject(this);

        butia = Robot.getInstance();


        new ModulosOperation().execute("");

//        mHandler.postDelayed(runnable, 1);

    }





    private class ModulosOperation extends AsyncTask<String, Void, LinkedList> {

        @Override
        protected LinkedList doInBackground(String... params) {

            String[] listmodulos = butia.get_modules_list();


            LinkedList l = new LinkedList();

            for (String modulo:listmodulos ) {
                moduloItem m = new moduloItem (modulo, butia.getModuleValue(modulo));
                l.add(m);
            }

            return l;
        }

        @Override
        protected void onPostExecute(LinkedList result) {
            list.setAdapter(new CustomAdapter(getApplicationContext(),result));
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }










}
