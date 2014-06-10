package com.example.butiaandroid.main.Listar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.butiaandroid.main.R;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Rodrigo
 * Date: 10/05/14
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class CustomAdapter extends BaseAdapter {
    private static final String LOG_TAG = CustomAdapter.class
            .getSimpleName();

    private final LinkedList<moduloItem> _items;
    private final LayoutInflater _inflater;

    public CustomAdapter(Context context, LinkedList<moduloItem> items) {
        _inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _items = items;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return _items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    static class ViewHolder {
        TextView nombreP;
        TextView cant;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        /*Log.d(LOG_TAG, "position: " + position + ", convert view: "
                + (convertView != null) + ", parent: " + parent.getId());
        */
        if (convertView == null) {
            // 1.
            convertView = _inflater.inflate(R.layout.item_module, null);

            // 2.
            holder = new ViewHolder();
            holder.nombreP = (TextView) convertView.findViewById(R.id.nombre);
            holder.cant = (TextView) convertView.findViewById(R.id.valor);

            convertView.setTag(holder);
        } else {
            // 2.
            holder = (ViewHolder) convertView.getTag();
        }

        moduloItem c = (moduloItem) getItem(position);

        // 3.
       holder.nombreP.setText(c.getNombre());
       holder.cant.setText(String.valueOf(c.getValue()));


        return convertView;

    }
}