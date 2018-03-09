package com.yougen.anticafemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by thymomenosgata on 16.12.17.
 */

public class DATAHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "Datass";

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_FIRSTYEAR = "firstyear";
    public final static String COLUMN_FIRSTMONTH = "firstmonth";
    public final static String COLUMN_FIRSTDAY = "firstday";


    //Имя файла базы данных
    private static final String DATABASE_NAME = "Datass.db";

    //Версия базы данных. При изменении схемы увеличить на единицу
    private static final int DATABASE_VERSION = 7;

    public DATAHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_FIRSTYEAR + " INTEGER, "
                + COLUMN_FIRSTMONTH + " INTEGER, "
                + COLUMN_FIRSTDAY + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем старую таблицу и создаём новую
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
