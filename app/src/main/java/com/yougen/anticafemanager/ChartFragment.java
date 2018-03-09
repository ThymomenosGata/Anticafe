package com.yougen.anticafemanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yougen.anticafemanager.data.DATAHelper;
import com.yougen.anticafemanager.data.GrapfHSqliteHelper;
import com.yougen.anticafemanager.data.GraphDSQLiteHelper;
import com.yougen.anticafemanager.data.MiniSQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;

import im.dacer.androidcharts.LineView;

/**
 * Created by thymomenosgata on 27.10.17.
 */

public class ChartFragment extends Fragment {
    public ChartFragment() {
    }

    @NonNull
    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    MiniSQLiteHelper sqlite;
    GrapfHSqliteHelper sqliteH;
    GraphDSQLiteHelper sqliteD;

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chart_fragment, container, false);

        sqlite = new MiniSQLiteHelper(getContext());
        sqliteH = new GrapfHSqliteHelper(getContext());
        sqliteD = new GraphDSQLiteHelper(getContext());

        final SQLiteDatabase mdb = sqlite.getWritableDatabase();
        final SQLiteDatabase Hdb = sqliteH.getWritableDatabase();
        final SQLiteDatabase Ddb = sqliteD.getWritableDatabase();

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

        final Button buttonDay = (Button) v.findViewById(R.id.buttonDay);
        final Button buttonMonth = (Button) v.findViewById(R.id.buttonMonth);
        final Button buttonYear = (Button) v.findViewById(R.id.buttonYear);

        takeData1(Hdb);

        buttonDay.setTextColor(v.getResources().getColor(R.color.white));
        buttonDay.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

        buttonMonth.setTextColor(v.getResources().getColor(R.color.black));
        buttonMonth.setBackgroundColor(v.getResources().getColor(R.color.gray));

        buttonYear.setTextColor(v.getResources().getColor(R.color.black));
        buttonYear.setBackgroundColor(v.getResources().getColor(R.color.gray));

        buttonDay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                takeData1(Hdb);
                buttonDay.setTextColor(v.getResources().getColor(R.color.white));
                buttonDay.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

                buttonMonth.setTextColor(v.getResources().getColor(R.color.black));
                buttonMonth.setBackgroundColor(v.getResources().getColor(R.color.gray));

                buttonYear.setTextColor(v.getResources().getColor(R.color.black));
                buttonYear.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });

        buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeData2(Ddb);
                buttonMonth.setTextColor(v.getResources().getColor(R.color.white));
                buttonMonth.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

                buttonDay.setTextColor(v.getResources().getColor(R.color.black));
                buttonDay.setBackgroundColor(v.getResources().getColor(R.color.gray));

                buttonYear.setTextColor(v.getResources().getColor(R.color.black));
                buttonYear.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });

        buttonYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeData3(mdb);
                buttonYear.setTextColor(v.getResources().getColor(R.color.white));
                buttonYear.setBackgroundColor(v.getResources().getColor(R.color.colorPrimary));

                buttonMonth.setTextColor(v.getResources().getColor(R.color.black));
                buttonMonth.setBackgroundColor(v.getResources().getColor(R.color.gray));

                buttonDay.setTextColor(v.getResources().getColor(R.color.black));
                buttonDay.setBackgroundColor(v.getResources().getColor(R.color.gray));
            }
        });


        return v;
    }

    void takeData1(SQLiteDatabase db){
        ArrayList<ArrayList<Integer>> gl = new ArrayList<ArrayList<Integer>>();
        ArrayList<graphList> graf = new ArrayList<graphList>();
        ArrayList<Integer> il = new ArrayList<Integer>();
        ArrayList<String> sl = new ArrayList<String>();
        Cursor curs = db.query(
                GrapfHSqliteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(curs.moveToFirst()) {
            int idH = curs.getColumnIndex(GrapfHSqliteHelper.COLUMN_HHOURS);
            int idC = curs.getColumnIndex(GrapfHSqliteHelper.COLUMN_CASH);
            do{
                graf.add(new graphList(curs.getInt(idC),curs.getInt(idH)));
            }while (curs.moveToNext());
        }
        curs.close();

        int[] hh = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        int[] cc = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0; i<graf.size(); i++ ){
           for(int j = 0; j<24; j++){
               if(graf.get(i).getDat() == hh[j]){
                   cc[j] += graf.get(i).getCash();
               }
           }
        }
        for(int i=0; i<cc.length; i++ ){
            sl.add(Integer.toString(hh[i]));
            il.add(cc[i]);
        }
        gl.add(il);

        LineView lineView = (LineView) v.findViewById(R.id.line_view);
        lineView.setDrawDotLine(true); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(sl);
        lineView.setColorArray(new int[]{Color.RED,Color.GREEN,Color.GRAY,Color.CYAN});
        lineView.setDataList(gl); //or lineView.setFloatDataList(floatDataLists)
    }

    void takeData2(SQLiteDatabase db){
        ArrayList<ArrayList<Integer>> gl = new ArrayList<ArrayList<Integer>>();
        ArrayList<graphList> graf = new ArrayList<graphList>();
        ArrayList<Integer> il = new ArrayList<Integer>();
        ArrayList<String> sl = new ArrayList<String>();
        Cursor curs = db.query(
                GraphDSQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(curs.moveToFirst()) {
            int idH = curs.getColumnIndex(GraphDSQLiteHelper.COLUMN_DDAYS);
            int idC = curs.getColumnIndex(GrapfHSqliteHelper.COLUMN_CASH);
            do{
                graf.add(new graphList(curs.getInt(idC),curs.getInt(idH)));
            }while (curs.moveToNext());
        }
        curs.close();

        int[] hh = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        int[] cc = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0; i<graf.size(); i++ ){
            for(int j = 0; j<31; j++){
                if(graf.get(i).getDat() == hh[j]){
                    cc[j] += graf.get(i).getCash();
                }
            }
        }
        for(int i=0; i<cc.length; i++ ){
            sl.add(Integer.toString(hh[i]));
            il.add(cc[i]);
        }
        gl.add(il);

        LineView lineView = (LineView) v.findViewById(R.id.line_view);
        lineView.setDrawDotLine(true); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(sl);
        lineView.setColorArray(new int[]{Color.GRAY,Color.GREEN,Color.GRAY,Color.CYAN});
        lineView.setDataList(gl); //or lineView.setFloatDataList(floatDataLists)
    }

    void takeData3(SQLiteDatabase db){
        ArrayList<ArrayList<Integer>> gl = new ArrayList<ArrayList<Integer>>();
        ArrayList<graphList> graf = new ArrayList<graphList>();
        ArrayList<Integer> il = new ArrayList<Integer>();
        ArrayList<String> sl = new ArrayList<String>();
        Cursor curs = db.query(
                MiniSQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(curs.moveToFirst()) {
            int idH = curs.getColumnIndex(MiniSQLiteHelper.COLUMN_MONTHS);
            int idC = curs.getColumnIndex(MiniSQLiteHelper.COLUMN_CASH);
            do{
                graf.add(new graphList(curs.getInt(idC),curs.getInt(idH)));
            }while (curs.moveToNext());
        }
        curs.close();

        int[] hh = {1,2,3,4,5,6,7,8,9,10,11,12};
        int[] cc = {0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0; i<graf.size(); i++ ){
            for(int j = 0; j<cc.length; j++){
                if(graf.get(i).getDat() + 1 == hh[j]){
                    cc[j] += graf.get(i).getCash();
                }
            }
        }
        for(int i=0; i<12; i++ ){
            sl.add(Integer.toString(hh[i]));
            il.add(cc[i]);
        }
        gl.add(il);

        LineView lineView = (LineView) v.findViewById(R.id.line_view);
        lineView.setDrawDotLine(true); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(sl);
        lineView.setColorArray(new int[]{Color.MAGENTA,Color.GREEN,Color.GRAY,Color.CYAN});
        lineView.setDataList(gl); //or lineView.setFloatDataList(floatDataLists)
    }

}
