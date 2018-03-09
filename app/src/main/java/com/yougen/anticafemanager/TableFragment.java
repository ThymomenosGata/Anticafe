package com.yougen.anticafemanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yougen.anticafemanager.data.DATAHelper;
import com.yougen.anticafemanager.data.GrapfHSqliteHelper;
import com.yougen.anticafemanager.data.GraphDSQLiteHelper;
import com.yougen.anticafemanager.data.MiniSQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Konstantin on 23.10.2017.
 */

public class TableFragment extends Fragment {
    public TableFragment() {
    }

    public static TableFragment newInstance() {
        return new TableFragment();
    }

    MiniSQLiteHelper sqlite;
    GrapfHSqliteHelper sqliteH;
    GraphDSQLiteHelper sqliteD;
    TabletAdapter tabletAdapter;
    View v;
    public ListView tabletList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sqlite = new MiniSQLiteHelper(getContext());
        sqliteH = new GrapfHSqliteHelper(getContext());
        sqliteD = new GraphDSQLiteHelper(getContext());

        final SQLiteDatabase mdb = sqlite.getWritableDatabase();
        final SQLiteDatabase Hdb = sqliteH.getWritableDatabase();
        final SQLiteDatabase Ddb = sqliteD.getWritableDatabase();

        v = inflater.inflate(R.layout.table_fragment, container, false);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DATAHelper dataH = new DATAHelper(getContext());
        SQLiteDatabase Dtdb = dataH.getWritableDatabase();


        Cursor cur = Dtdb.query(
                DATAHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        ContentValues valz = new ContentValues();
        if(cur.moveToFirst()){
            int indYear = cur.getColumnIndex(DATAHelper.COLUMN_FIRSTYEAR);
            int indMonth = cur.getColumnIndex(DATAHelper.COLUMN_FIRSTMONTH);
            int indDay = cur.getColumnIndex(DATAHelper.COLUMN_FIRSTDAY);

            do{
                if(year>cur.getInt(indYear)){
                    valz.put(DATAHelper.COLUMN_FIRSTYEAR, year);
                    Dtdb.update(
                            DATAHelper.TABLE_NAME,
                            valz,
                            null,
                            null
                    );
                    mdb.delete(
                            MiniSQLiteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    Ddb.delete(
                            GraphDSQLiteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    Hdb.delete(
                            GrapfHSqliteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    valz.clear();
                }

                if(month>cur.getInt(indMonth)){
                    valz.put(DATAHelper.COLUMN_FIRSTMONTH, month);
                    Dtdb.update(
                            DATAHelper.TABLE_NAME,
                            valz,
                            null,
                            null
                    );
                    Ddb.delete(
                            GraphDSQLiteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    Hdb.delete(
                            GrapfHSqliteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    valz.clear();
                }

                if(day>cur.getInt(indDay)){
                    valz.put(DATAHelper.COLUMN_FIRSTDAY, day);
                    Dtdb.update(
                            DATAHelper.TABLE_NAME,
                            valz,
                            null,
                            null
                    );
                    Hdb.delete(
                            GrapfHSqliteHelper.TABLE_NAME,
                            null,
                            null
                    );
                    valz.clear();
                }
            }while (cur.moveToNext());

        }
        else{
            valz.put(DATAHelper.COLUMN_FIRSTYEAR, year);
            valz.put(DATAHelper.COLUMN_FIRSTMONTH, month);
            valz.put(DATAHelper.COLUMN_FIRSTDAY, day);
            Dtdb.insert(
                    DATAHelper.TABLE_NAME,
                    null,
                    valz
            );
            valz.clear();
        }

        cur.close();

        final Button bt = (Button) v.findViewById(R.id.button3);
        final Button btM = (Button) v.findViewById(R.id.button2);
        final Button btY = (Button) v.findViewById(R.id.button);

        updDay(Hdb);
        bt.setTextColor(v.getResources().getColor(R.color.white));
        bt.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

        btM.setTextColor(v.getResources().getColor(R.color.black));
        btM.setBackgroundColor(v.getResources().getColor(R.color.gray));

        btY.setTextColor(v.getResources().getColor(R.color.black));
        btY.setBackgroundColor(v.getResources().getColor(R.color.gray));


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              updDay(Hdb);
              bt.setTextColor(v.getResources().getColor(R.color.white));
              bt.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

              btM.setTextColor(v.getResources().getColor(R.color.black));
              btM.setBackgroundColor(v.getResources().getColor(R.color.gray));

              btY.setTextColor(v.getResources().getColor(R.color.black));
              btY.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });

        btM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updMonth(Ddb);

                btM.setTextColor(v.getResources().getColor(R.color.white));
                btM.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

                bt.setTextColor(v.getResources().getColor(R.color.black));
                bt.setBackgroundColor(v.getResources().getColor(R.color.gray));

                btY.setTextColor(v.getResources().getColor(R.color.black));
                btY.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });

        btY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updYear(mdb);
                btY.setTextColor(v.getResources().getColor(R.color.white));
                btY.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

                btM.setTextColor(v.getResources().getColor(R.color.black));
                btM.setBackgroundColor(v.getResources().getColor(R.color.gray));

                bt.setTextColor(v.getResources().getColor(R.color.black));
                bt.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });
        return v;
    }

    void updDay(SQLiteDatabase Hdb){

        ArrayList<Integer> listTime = new ArrayList<Integer>();
        ArrayList<Integer> listTable = new ArrayList<Integer>();
        ArrayList<Integer> listCount = new ArrayList<Integer>();
        ArrayList<Integer> listCash = new ArrayList<Integer>();
        ArrayList<ListTableView> listTab = new ArrayList<ListTableView>();

        Cursor c = Hdb.query(
                GrapfHSqliteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(c.moveToFirst()){
            int idT = c.getColumnIndex(GrapfHSqliteHelper.COLUMN_TABLES);
            int idC = c.getColumnIndex(GrapfHSqliteHelper.COLUMN_ALLCO);
            int idCa = c.getColumnIndex(GrapfHSqliteHelper.COLUMN_CASH);
            int idTi = c.getColumnIndex(GrapfHSqliteHelper.COLUMN_ALLM);
            do{
                listTable.add(c.getInt(idT));
                listCount.add(c.getInt(idC));
                listCash.add(c.getInt(idCa));
                listTime.add(c.getInt(idTi));
            }while (c.moveToNext());

            int k = count(listTable);
            int[] els = new int[k];
            int[] co = new int[k];
            int[] ti = new int[k];
            int[] ca = new int[k];
            els = el(listTable);
            for(int i = 0; i<k; i++){
                co[i] = 0;
                ti[i] = 0;
                ca[i] = 0;
            }

            for(int j = 0; j<k; j++){
                for(int i = 0; i<listTable.size(); i++){
                    if(els[j] == listTable.get(i)){
                        co[j] += listCount.get(i);
                        ca[j] += listCash.get(i);
                        ti[j] += listTime.get(i);
                    }
                }
            }

            for(int i = 0; i<k; i++){
                listTab.add(new ListTableView(els[i], ca[i], co[i], ti[i]));
            }
        }
        c.close();
        tabletAdapter = new TabletAdapter(getContext(), listTab);
        tabletList = (ListView) v.findViewById(R.id.tabletList);
        tabletList.setAdapter(tabletAdapter);
    }

    void updMonth(SQLiteDatabase Ddb){

        ArrayList<Integer> listTime = new ArrayList<Integer>();
        ArrayList<Integer> listTable = new ArrayList<Integer>();
        ArrayList<Integer> listCount = new ArrayList<Integer>();
        ArrayList<Integer> listCash = new ArrayList<Integer>();
        ArrayList<ListTableView> listTab = new ArrayList<ListTableView>();

        Cursor c = Ddb.query(
                GraphDSQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(c.moveToFirst()){
            int idT = c.getColumnIndex(GraphDSQLiteHelper.COLUMN_TABLES);
            int idC = c.getColumnIndex(GraphDSQLiteHelper.COLUMN_ALLCO);
            int idCa = c.getColumnIndex(GraphDSQLiteHelper.COLUMN_CASH);
            int idTi = c.getColumnIndex(GraphDSQLiteHelper.COLUMN_ALLM);
            do{
                listTable.add(c.getInt(idT));
                listCount.add(c.getInt(idC));
                listCash.add(c.getInt(idCa));
                listTime.add(c.getInt(idTi));
            }while (c.moveToNext());
            int k = count(listTable);
            int[] els = new int[k];
            int[] co = new int[k];
            int[] ti = new int[k];
            int[] ca = new int[k];
            els = el(listTable);
            for(int i = 0; i<k; i++){
                co[i] = 0;
                ti[i] = 0;
                ca[i] = 0;
            }

            for(int j = 0; j<k; j++){
                for(int i = 0; i<listTable.size(); i++){
                    if(els[j] == listTable.get(i)){
                        co[j] += listCount.get(i);
                        ca[j] += listCash.get(i);
                        ti[j] += listTime.get(i);
                    }
                }
            }

            for(int i = 0; i<k; i++){
                listTab.add(new ListTableView(els[i], ca[i], co[i], ti[i]));
            }
        }
        c.close();
        tabletAdapter = new TabletAdapter(getContext(), listTab);
        tabletList = (ListView) v.findViewById(R.id.tabletList);
        tabletList.setAdapter(tabletAdapter);
    }

    void updYear(SQLiteDatabase mdb){

        ArrayList<Integer> listTime = new ArrayList<Integer>();
        ArrayList<Integer> listTable = new ArrayList<Integer>();
        ArrayList<Integer> listCount = new ArrayList<Integer>();
        ArrayList<Integer> listCash = new ArrayList<Integer>();
        ArrayList<ListTableView> listTab = new ArrayList<ListTableView>();

        Cursor c = mdb.query(
                MiniSQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(c.moveToFirst()){
            int idT = c.getColumnIndex(MiniSQLiteHelper.COLUMN_TABLES);
            int idC = c.getColumnIndex(MiniSQLiteHelper.COLUMN_ALLCO);
            int idCa = c.getColumnIndex(MiniSQLiteHelper.COLUMN_CASH);
            int idTi = c.getColumnIndex(MiniSQLiteHelper.COLUMN_ALLM);
            do{
                listTable.add(c.getInt(idT));
                listCount.add(c.getInt(idC));
                listCash.add(c.getInt(idCa));
                listTime.add(c.getInt(idTi));
            }while (c.moveToNext());
            int k = count(listTable);
            int[] els = new int[k];
            int[] co = new int[k];
            int[] ti = new int[k];
            int[] ca = new int[k];
            els = el(listTable);
            for(int i = 0; i<k; i++){
                co[i] = 0;
                ti[i] = 0;
                ca[i] = 0;
            }

            for(int j = 0; j<k; j++){
                for(int i = 0; i<listTable.size(); i++){
                    if(els[j] == listTable.get(i)){
                        co[j] += listCount.get(i);
                        ca[j] += listCash.get(i);
                        ti[j] += listTime.get(i);
                    }
                }
            }

            for(int i = 0; i<k; i++){
                listTab.add(new ListTableView(els[i], ca[i], co[i], ti[i]));
            }
        }
        c.close();
        tabletAdapter = new TabletAdapter(getContext(), listTab);
        tabletList = (ListView) v.findViewById(R.id.tabletList);
        tabletList.setAdapter(tabletAdapter);
    }

    int count(ArrayList<Integer> listTab){
        int k = 0;
        int x = listTab.size();
        boolean [] flag = new boolean[x];

        for(int i = 0; i<listTab.size(); i++){
            flag[i] = true;
        }

        for(int i = 0; i<listTab.size(); i++){
            if(flag[i] == true){
                k++;
                for(int j = i; j<listTab.size(); j++){
                        flag[j] = ((listTab.get(j) != listTab.get(i)) && flag[j]);
                }
            }
        }

        return k;
    }

    int[] el(ArrayList<Integer> listTab){
        int [] els = new int[count(listTab)];
        int k = 0;
        boolean [] flag = new boolean[listTab.size()];

        for(int i = 0; i<listTab.size(); i++){
            flag[i] = true;
        }

        for(int i = 0; i<listTab.size(); i++){
            if(flag[i] == true){
                els[k] = listTab.get(i);
                k++;
                for(int j = i; j<listTab.size(); j++){
                        flag[j] = ((listTab.get(j) != listTab.get(i)) && flag[j]);
                }
            }
        }
        els = bubbleSort(els);
        return els;
    }

    int[] bubbleSort(int[] arr){
        for(int i = arr.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){

                if( arr[j] > arr[j+1] ){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
        return arr;
    }
}
