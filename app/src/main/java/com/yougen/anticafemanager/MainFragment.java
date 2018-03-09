package com.yougen.anticafemanager;

/**
 * Created by Konstantin on 23.10.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yougen.anticafemanager.data.DATAHelper;
import com.yougen.anticafemanager.data.GrapfHSqliteHelper;
import com.yougen.anticafemanager.data.GraphDSQLiteHelper;
import com.yougen.anticafemanager.data.MiniSQLiteHelper;
import com.yougen.anticafemanager.data.SQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;

import static android.widget.AdapterView.*;

public class MainFragment extends Fragment implements MainActivity.DataLoaded {
    public MainFragment() {
    }

    SQLiteHelper sql;
    MiniSQLiteHelper sqlite;
    GrapfHSqliteHelper sqliteH;
    GraphDSQLiteHelper sqliteD;
    DATAHelper datasH;
    public ListView mainList;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getActivity();
        ((MainActivity) context).dataLoaded = this;
    }

    private static final int REQWEST_DATA = 0;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    ArrayList<ListForListView> arrayList = new ArrayList<ListForListView>();
    ArrayList<Integer> HoursList = new ArrayList<Integer>();
    ArrayList<Integer> MinutesList = new ArrayList<Integer>();
    AnticafeAdapter anticafeAdapter;

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.main_fragment, container, false);
        sql = new SQLiteHelper(getContext());
        sqlite = new MiniSQLiteHelper(getContext());
        sqliteH = new GrapfHSqliteHelper(getContext());
        sqliteD = new GraphDSQLiteHelper(getContext());
        datasH = new DATAHelper(getContext());

        SQLiteDatabase db = sql.getWritableDatabase();
        SQLiteDatabase mdb = sqlite.getWritableDatabase();
        SQLiteDatabase Hdb = sqliteH.getWritableDatabase();
        SQLiteDatabase Ddb = sqliteD.getWritableDatabase();
        SQLiteDatabase Dtdb = datasH.getWritableDatabase();


        upd(db,Dtdb);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DialogFragment dialog = new DialogFragment();
                dialog.setTargetFragment(MainFragment.this, REQWEST_DATA);
                dialog.show(fragmentManager,"DialogFragment");
            }
        });

        return v;
        
    }

    @Override
    public void loader(final ListV l) {
        String hh = "";
        String vv = "";

        if(l.issHookah()){
            if(l.issHard())
                hh = "тяжелый кальян";
            else
                if(l.issNormal())
                hh = "средний кальян";
            else
                if(l.issLigth())
                    hh = "легкий кальян";
            else
                hh = "кальян-сюрприз";
        }

        if(l.issVIP())
            vv = "VIP";
        else
            if(l.issNonLimited())
                vv = "NonLimited";
        else
            vv = "Обычный зал";

        Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(calendar.HOUR_OF_DAY);
        final int m = calendar.get(calendar.MINUTE);

        String time = hour + ":" + m;

        if(m<10)
            time = hour + ":" + "0" + m;

//
        final SQLiteDatabase db = sql.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TIME, time);
        values.put(SQLiteHelper.COLUMN_H, hour);
        values.put(SQLiteHelper.COLUMN_M, m);
        values.put(SQLiteHelper.COLUMN_TABLES, l.getTable());
        values.put(SQLiteHelper.COLUMN_COUNT, l.getCpeop());
        values.put(SQLiteHelper.COLUMN_HOOKAH, hh);
        values.put(SQLiteHelper.COLUMN_HOLL, vv);
//
        db.insert(
                SQLiteHelper.TABLE_NAME,
                null,
                values);




        MinutesList.add(m);
        HoursList.add(hour);
        arrayList.add(new ListForListView(l.getTable(), l.getCpeop(), hh, vv, time));
        mainList = (ListView)v.findViewById(R.id.mainList);
        mainList.setAdapter(anticafeAdapter);

    }

    int getTimes(int h1, int h2, int m1, int m2){
        int times;
        if(h1>=h2){
            if(m1>=m2){
                m1 = m1 - m2;
                h1 = h1 - h2;
            }else if(m1<m2){
                m1 = m1 + 60 - m2;
                h1 = h1 - h2 - 1;
            }
        }
        else if(h1<h2){
            if(m1>=m2){
                m1 = m1 - m2;
                h1 = h1 + 24 - h2;
            }else if(m1<m2){
                m1 = m1 + 60 - m2;
                h1 = h1 + 24 - h2 - 1;
            }
        }
        times = h1*60 + m1;
        return times;
    }

    int getPrices(int time, String h, String z, int count){

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        int hPrice, hPHookahH, hPHookahN, hPHookahL,
                hVIP,hSCH;
        hPrice = Integer.valueOf(settings.getString("PriceAC", "2"));
        hPHookahH = Integer.valueOf(settings.getString("HardHookah", "900"));
        hPHookahN = Integer.valueOf(settings.getString("NormalHookah", "700"));
        hPHookahL = Integer.valueOf(settings.getString("LightHookah", "500"));
        hVIP = Integer.valueOf(settings.getString("PriceUN", "7"));
        hSCH = Integer.valueOf(settings.getString("StopCheck", "600"));

        int price;

        if(z.equals("VIP")){
            if(h.equals("тяжелый кальян")){
                price = hPHookahH;
            }else if(h.equals("средний кальян")){
               price = hPHookahN;
            }else if(h.equals("легкий кальян")){
                price = hPHookahL;
            }else if(h.equals("кальян-сюрприз")){
                price = 1000;
            }else{
                price = hVIP*time*count;
            }
        }else if(z.equals("NonLimited")){
            if(h.equals("тяжелый кальян")){
                price = hSCH + hPHookahH;
            }else if(h.equals("средний кальян")){
                price = hSCH + hPHookahN;
            }else if(h.equals("легкий кальян")){
                price = hSCH + hPHookahL;
            }else if(h.equals("кальян-сюрприз")){
                price = hSCH + 1000;
            }else{
                price = hSCH*count;
            }
        }else{
            if(h.equals("тяжелый кальян")){
                price = hPHookahH;
            }else if(h.equals("средний кальян")){
                price = hPHookahH;
            }else if(h.equals("легкий кальян")){
                price = hPHookahH;
            }else if(h.equals("кальян-сюрприз")){
                price = 1000;
            }else{
                price = hPrice*time*count;
            }
        }

        return price;
    }

    void upd(final SQLiteDatabase db, final SQLiteDatabase Dtdb){
        Cursor c = db.query(
                SQLiteHelper.TABLE_NAME,  // The table to query
                null,                               // The columns to re
                null,                                // The columns for the W
                null,                            // The values for th
                null,                                     // don't group the r
                null,                                     // don't filter by r
                null                                 // The sort order
        );
//
//
        if(c.moveToFirst()) {
            int TimeIndex = c.getColumnIndex(SQLiteHelper.COLUMN_TIME);
            int HourIndex = c.getColumnIndex(SQLiteHelper.COLUMN_H);
            int MinuteIndex = c.getColumnIndex(SQLiteHelper.COLUMN_M);
            int TableIndex = c.getColumnIndex(SQLiteHelper.COLUMN_TABLES);
            int CountIndex = c.getColumnIndex(SQLiteHelper.COLUMN_COUNT);
            int HookahIndex = c.getColumnIndex(SQLiteHelper.COLUMN_HOOKAH);
            int HollIndex = c.getColumnIndex(SQLiteHelper.COLUMN_HOLL);
//
            do {
                MinutesList.add(c.getInt(MinuteIndex));
                HoursList.add(c.getInt(HourIndex));
                arrayList.add(new ListForListView(c.getInt(TableIndex), c.getInt(CountIndex),
                        c.getString(HookahIndex), c.getString(HollIndex), c.getString(TimeIndex)));
            } while (c.moveToNext());
        }
        c.close();

        anticafeAdapter = new AnticafeAdapter(getContext(), arrayList);
        mainList = (ListView) v.findViewById(R.id.mainList);
        mainList.setAdapter(anticafeAdapter);

        mainList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Calendar calendar = Calendar.getInstance();
                int hour1 = calendar.get(calendar.HOUR_OF_DAY);
                int m11 = calendar.get(calendar.MINUTE);

                int m1 = MinutesList.get(position);
                int hh = HoursList.get(position);

                int times = getTimes(hour1,hh,m11,m1);
                int price = getPrices(times, arrayList.get(position).hookah,
                        arrayList.get(position).vipOrNonLimited
                        ,arrayList.get(position).countPeopls);


                SQLiteDatabase Hdb = sqliteH.getWritableDatabase();

                ContentValues vals = new ContentValues();

                vals.put(GrapfHSqliteHelper.COLUMN_TABLES, arrayList.get(position).numTable);
                vals.put(GrapfHSqliteHelper.COLUMN_ALLCO, arrayList.get(position).countPeopls);
                vals.put(GrapfHSqliteHelper.COLUMN_ALLM, times);
                vals.put(GrapfHSqliteHelper.COLUMN_HHOURS, calendar.get(Calendar.HOUR_OF_DAY));
                vals.put(GrapfHSqliteHelper.COLUMN_CASH, price);

                Hdb.insert(
                        GrapfHSqliteHelper.TABLE_NAME,
                        null,
                        vals
                );

                ContentValues vals1 = new ContentValues();

                SQLiteDatabase Ddb = sqliteD.getWritableDatabase();
                vals1.put(GraphDSQLiteHelper.COLUMN_TABLES, arrayList.get(position).numTable);
                vals1.put(GraphDSQLiteHelper.COLUMN_ALLM, times);
                vals1.put(GraphDSQLiteHelper.COLUMN_CASH, price);
                vals1.put(GraphDSQLiteHelper.COLUMN_ALLCO, arrayList.get(position).countPeopls);
                vals1.put(GraphDSQLiteHelper.COLUMN_DDAYS, calendar.get(Calendar.DAY_OF_MONTH));

                Ddb.insert(
                        GraphDSQLiteHelper.TABLE_NAME,
                        null,
                        vals1
                );

                ContentValues vals2 = new ContentValues();

                SQLiteDatabase mdb = sqlite.getWritableDatabase();
                vals2.put(MiniSQLiteHelper.COLUMN_TABLES, arrayList.get(position).numTable);
                vals2.put(MiniSQLiteHelper.COLUMN_ALLM, times);
                vals2.put(MiniSQLiteHelper.COLUMN_CASH, price);
                vals2.put(MiniSQLiteHelper.COLUMN_ALLCO, arrayList.get(position).countPeopls);
                vals2.put(MiniSQLiteHelper.COLUMN_MONTHS, calendar.get(Calendar.MONTH));

                mdb.insert(
                        MiniSQLiteHelper.TABLE_NAME,
                        null,
                        vals2
                );

                db.delete(SQLiteHelper.TABLE_NAME,null,null);

                arrayList.remove(position);
                HoursList.remove(position);
                MinutesList.remove(position);

                int i = 0;

                ContentValues val = new ContentValues();
                while(i < arrayList.size()){
                    val.put(SQLiteHelper.COLUMN_TABLES, arrayList.get(i).numTable);
                    val.put(SQLiteHelper.COLUMN_TIME, arrayList.get(i).onTime);
                    val.put(SQLiteHelper.COLUMN_H, HoursList.get(i));
                    val.put(SQLiteHelper.COLUMN_M, MinutesList.get(i));
                    val.put(SQLiteHelper.COLUMN_COUNT, arrayList.get(i).countPeopls);
                    val.put(SQLiteHelper.COLUMN_HOOKAH, arrayList.get(i).hookah);
                    val.put(SQLiteHelper.COLUMN_HOLL, arrayList.get(i).vipOrNonLimited);
                    i++;
                    db.insert(SQLiteHelper.TABLE_NAME,
                            null,
                            val);
                }

                mainList = (ListView)v.findViewById(R.id.mainList);
                mainList.setAdapter(anticafeAdapter);

                Toast toast = Toast.makeText(getContext(),
                        times + " минут и это " + price + "рублей", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        });
    }

}
