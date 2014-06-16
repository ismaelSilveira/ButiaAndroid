package com.example.butiaandroid.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.butiaandroid.main.Listar.CustomAdapter;
import com.example.butiaandroid.main.Listar.moduloItem;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ListarModulosActivity extends ActionBarActivity {
    @InjectView(R.id.listView) protected ListView list;

    Robot butia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_modulos);
        ButterKnife.inject(this);


        butia = Robot.getInstance();

        LinkedList l = new LinkedList();
//DEVUELVE admin:0,button:4,grey:5,grey:6,pnp:7,motors:8
        String modulos = butia.get_modules_list();
        modulos = butia.get_modules_list();

        for (int i=0 ; i<10; i++){
            moduloItem m = new moduloItem ("modulo" + i, String.valueOf(i));
            l.add(m);
        }

       //vacio  modulos = null

        list.setAdapter(new CustomAdapter(this,l));


    }






}
