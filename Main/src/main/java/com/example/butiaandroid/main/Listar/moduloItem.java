package com.example.butiaandroid.main.Listar;

/**
 * Created by Rodrigo on 09/06/14.
 */
public class moduloItem {
    String nombre;
    String value;

    public moduloItem(String nombre, String value) {
        this.nombre = nombre;
        this.value = value;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValue() {
        return value;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
