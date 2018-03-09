package com.yougen.anticafemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by thymomenosgata on 09.12.17.
 */

public class MiniSQLiteHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "Host";

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_TABLES = "tabs";
    public final static String COLUMN_ALLM = "Allmin";
    public final static String COLUMN_CASH = "cash";
    public final static String COLUMN_ALLCO = "Allcount";
    public final static String COLUMN_MONTHS = "monthss";


    //Имя файла базы данных
    private static final String DATABASE_NAME = "host.db";

    //Версия базы данных. При изменении схемы увеличить на единицу
    private static final int DATABASE_VERSION = 7;


    public MiniSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TABLES + " INTEGER, "
                + COLUMN_ALLM + " INTEGER, "
                + COLUMN_CASH + " INTEGER, "
                + COLUMN_ALLCO + " INTEGER, "
                + COLUMN_MONTHS + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем старую таблицу и создаём новую
        db.execSQL(SQL_DELETE_ENTRIES);
        // Создаём новую таблицу
        onCreate(db);
    }
}
