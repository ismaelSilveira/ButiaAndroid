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
    ModulosOperation tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_modulos);
        ButterKnife.inject(this);

        butia = Robot.getInstance();

        tarea =new ModulosOperation();
        tarea.execute("");
    }


    @Override
    public void onDestroy() {
        tarea.cancel(true);
        super.onDestroy();
    }


    private class ModulosOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            while (!isCancelled()){
                try {
                    String[] listmodulos = butia.getModulesList();
                    LinkedList l = new LinkedList();

                    for (String modulo:listmodulos ) {
                        moduloItem m = new moduloItem (modulo, butia.getModuleValue(modulo));
                        l.add(m);
                    }
                    list.setAdapter(new CustomAdapter(getApplicationContext(),l));

                    Thread.sleep(2000);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }



}
