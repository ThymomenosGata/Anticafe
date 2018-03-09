package com.yougen.anticafemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thymomenosgata on 17.12.17.
 */

public class TabletAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListTableView> objects;

    TabletAdapter(Context context, ArrayList<ListTableView> products) {
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
            view = lInflater.inflate(R.layout.list_tablet, parent, false);
        }

        ListTableView listVV = getListVV(position);

        // заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.textTablet)).setText(String.valueOf(listVV.table));
        ((TextView) view.findViewById(R.id.textCounts)).setText(String.valueOf(listVV.count));
        ((TextView) view.findViewById(R.id.textTimes)).setText(String.valueOf(listVV.time));
        ((TextView) view.findViewById(R.id.textCash)).setText(String.valueOf(listVV.cash));

        return view;
    }

    // товар по позиции
    ListTableView getListVV(int position) {
        return ((ListTableView) getItem(position));
    }
}
