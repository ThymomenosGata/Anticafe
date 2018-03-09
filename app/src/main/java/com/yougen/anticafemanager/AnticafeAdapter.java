package com.yougen.anticafemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thymomenosgata on 07.11.17.
 */

public class AnticafeAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListForListView> objects;

    AnticafeAdapter(Context context, ArrayList<ListForListView> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list, parent, false);
        }

        ListForListView listVV = getListVV(position);

        // заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.textTable)).setText(String.valueOf(listVV.numTable));
        ((TextView) view.findViewById(R.id.textCount)).setText(String.valueOf(listVV.countPeopls));
        ((TextView) view.findViewById(R.id.textVip)).setText(listVV.vipOrNonLimited);
        ((TextView) view.findViewById(R.id.textHookah)).setText(listVV.hookah);
        ((TextView) view.findViewById(R.id.textTime)).setText(listVV.onTime);

        return view;
    }

    // товар по позиции
    ListForListView getListVV(int position) {
        return ((ListForListView) getItem(position));
    }
}
